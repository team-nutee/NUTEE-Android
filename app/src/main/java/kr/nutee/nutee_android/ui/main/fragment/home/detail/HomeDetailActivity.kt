package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_home_detail_activtiy.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.data.main.home.LookUpDetail
import kr.nutee.nutee_android.data.main.home.ResponseMainBody
import kr.nutee.nutee_android.data.main.home.detail.RequestComment
import kr.nutee.nutee_android.databinding.MainHomeDetailActivtiyBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.main.fragment.add.AddActivity
import kr.nutee.nutee_android.ui.main.fragment.search.SearchResultsView
import java.util.regex.Pattern
import kotlin.collections.isNullOrEmpty as isNullOrEmpty1


/*
* created by jinsu47555
* DESC: 디테일 페이지를 표시하는 엑티비티
*
* created by 88yhtserofG
* DESC: 해시태그 기능. 리팩토링 완료
*       디테일 페이지 2.0버전으로 수정 및 구현
* */

class HomeDetailActivity : AppCompatActivity(),View.OnClickListener {

	private val binding by lazy { MainHomeDetailActivtiyBinding.inflate(layoutInflater) }

	val requestToServer = RequestToServer
	private var postId: Int? = 0
	private var commentId: Int? = 0
	private var commentDuplCheck=false

	private var sendDataToShowDetailImageView: (() -> Unit)? = null
	private var clickDetailMoreEvent: (() -> Unit)? = null

	private lateinit var imageViewList: List<ImageView>

	private lateinit var homeDetailCommentAdpater: HomeDetailCommentAdpater
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout

	lateinit var detailContent: TextView
	lateinit var detailNickname: TextView
	lateinit var detailTime:TextView
	lateinit var detailRewrite:TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//setContentView(R.layout.main_home_detail_activtiy)
		setContentView(binding.root)
		detailNickname=binding.textDetailNick
		detailTime=binding.textDetailTime
		detailContent=binding.textDetailContent
		swipeRefreshLayout=binding.swipeRefreshDetailView
		detailRewrite=binding.textDetailContentRewrite

		init()
	}

	private fun init() {
		postId = intent?.getIntExtra("Detail_id", 0)
		commentId=intent?.getIntExtra("comment_id", 0)
		imageViewList = listOf<ImageView>(
				binding.imgDetailMainImage1,
				binding.imgDetailMainImage2,
				binding.imgDetailMainImage3,
				binding.imgDetailMainImage4
		)
		detailRefreshEvnet(true)
		loadDetailPage()
		loadCommentList()
		if(intent.hasExtra("rewriteComment"))
			bindRewriteComment()
		detailViewClickEvnet()
		detailViewButtonEnableEvent()
	}

	private fun detailRefreshEvnet(swipeBoolean: Boolean) {
		RefreshEvent(swipeRefreshLayout, swipeBoolean){loadCommentList()}
	}

	private fun loadDetailPage() {
		Log.d("loadDetailPage", "포스트아이디 확인 ${this.postId}")
		RequestToServer.snsService
			.requestDetail(
					"Bearer " + App.prefs.local_login_token,
					this.postId!!
			)
			.customEnqueue(
					onSuccess = {
						Log.d("Network", "글 상세 통신 성공")
						onSuccessLoadDetailView(it.body())
					},
					onError = {
						onErrorDetailPage()
					}
			)
	}

	private fun onErrorDetailPage() {
		customDialogSingleButton("네트워크 오류")
			finish()
	}

	private fun onSuccessLoadDetailView(response: LookUpDetail?) {
		if (response != null) {
			if (response.body == null) {
				customDialogSingleButton("해당 글은 존재하지 않습니다.\n 지속적으로 해당 글이 보일 경우 문의 바랍니다.") {
					finish()
				}
			}
			bindDetailPostEvent(response.body!!)
		}
	}

	private fun bindDetailPostEvent(responseMainItem: ResponseMainBody) {
		GlideApp.with(applicationContext)
				.load(responseMainItem.user?.image?.src)
				.placeholder(R.drawable.ic_baseline_rotate_left_24)
				.error(R.mipmap.nutee_character_background_white_round)
				.fallback(R.mipmap.nutee_character_background_white_round)
				.into(binding.imgDetailProfile)
				//.into(img_detail_profile)
		detailNickname.text = responseMainItem.user?.nickname
		detailTime.text =
				responseMainItem.createdAt?.let { DateParser(it).calculateDiffDate() }
		detailContent.text = responseMainItem.content
		setLikeEvent(
				binding.imgDetailCommentFavoritBtn,
				binding.textDetailCommentFavoritCount,
				/*img_detail_comment_favorit_btn,
				text_detail_comment_favorit_count,*/
				responseMainItem.likers
		)
		clickDetailMoreEvent = { detailMore(responseMainItem) }
		if (!responseMainItem.images.isNullOrEmpty1()) imageFrameLoad(
				responseMainItem.images,
				responseMainItem.id
		)
		else Log.d("Network", "글 상세 뷰 사진 null")

		//수정 여부 표시
		if (responseMainItem.updatedAt != responseMainItem.createdAt)
			detailRewrite.visibility = View.VISIBLE

		//답글 생성 시
		if (intent.hasExtra("reply")) {
			binding.etDetailComment.requestFocus()
			//et_detail_comment.requestFocus()
			//키보드 자동 생성
			val inputMethodManager: InputMethodManager =
					getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
			inputMethodManager.showSoftInput(binding.etDetailComment, 0)
		}

		setHashtag()

	}

	private fun setHashtag() {
		val firstIndex = ArrayList<Int>()
		val lastIndex = ArrayList<Int>()
		val arrayHashtag= ArrayList<String>()

		val spannableText = detailContent.text as Spannable
		val partern = Pattern.compile("#([0-9a-zA-Z가-힣ㄱ-ㅎ]*)")
		val match = partern.matcher(detailContent.text)

		var i = 0
		while (match.find()) {
			firstIndex.add(match.start())
			lastIndex.add(match.end())
			arrayHashtag.add(match.group(1)!!)
			i++
		}

		i =0
		while (i < firstIndex.size) {
			val hashTagString=arrayHashtag[i]
			spannableText.setSpan(
					object: ClickableSpan() {
						override fun onClick(widget: View) {
							Log.d("hashtag", hashTagString)
							val intent=Intent(view.context,SearchResultsView::class.java)
							intent.putExtra("Hashtag",hashTagString)
							startActivity(intent)
						}},
					firstIndex[i],
					lastIndex[i],
					SPAN_INCLUSIVE_INCLUSIVE
			)
			i++
		}

		detailContent.text = spannableText
		detailContent.movementMethod=LinkMovementMethod.getInstance()
	}

	private fun imageFrameLoad(images: Array<Image>, postId: Int?) {
		sendDataToShowDetailImageView = { loadDetailImagePage(postId) }
		loadNoMoreImageFrame(images)
	}

	private fun loadDetailImagePage(postId: Int?) {
		val detailImageViewIntent = Intent(applicationContext, ShowDetailImageView::class.java)
		detailImageViewIntent.putExtra("postId", postId)
		startActivity(detailImageViewIntent)

	}

	private fun loadNoMoreImageFrame(images: Array<Image>) {
		binding.clDetailMainImage.visibility = View.VISIBLE
		var boolImageSize= images.size
		if(images.size > 4) {
			binding.btDetailMainImageMore.apply {
				visibility = View.VISIBLE
				text=getString(R.string.detailImageMore, images.size)
			}
			boolImageSize = 4
		}

		(0 until boolImageSize).forEach { i ->
			Glide.with(applicationContext)
				.load(images[i].src)
				.into(imageViewList[i])
			imageViewList[i].visibility = View.VISIBLE
		}


	}

	private fun detailMore(responseBody: ResponseMainBody) {
		contentMoreEvent(responseBody.user,
				View.GONE, {},
				{//게시글 수정
					rewritePost(responseBody)
				},
				{//게시글 삭제
					RequestToServer.snsService.requestDelete(
							"Bearer " + App.prefs.local_login_token,
							responseBody.id
					).customEnqueue(
							onSuccess = { finish() },
							onError = {
								Toast.makeText(this, "네트워크오류", Toast.LENGTH_SHORT)
										.show()
							})
				},
				{//게시글 신고
					cumstomReportDialog("이 게시글을 신고하시겠습니까?") {
						RequestToServer.snsService.requestReport(
								RequestReport(it), responseBody.id
						).customEnqueue(
								onSuccess = {
									Toast
											.makeText(
													applicationContext,
													"신고가 성공적으로 접수되었습니다.",
													Toast.LENGTH_SHORT
											).show()
								})
					}
				})
	}

	private fun detailViewClickEvnet() {
		binding.clDetailMainImage.setOnClickListener(this)
		binding.btDetailMainImageMore.setOnClickListener(this)
		binding.imgCommentUploadBtn.setOnClickListener(this)
		binding.imgDetailMore.setOnClickListener(this)
		binding.imgDetailTopBackBtn.setOnClickListener(this)
		binding.imgDetailCommentFavoritBtn.setOnClickListener(this)
		/*cl_detail_main_image.setOnClickListener(this)
		bt_detail_main_image_more.setOnClickListener(this)
		img_comment_upload_btn.setOnClickListener(this)
		img_detail_more.setOnClickListener(this)
		img_detail_top_back_btn.setOnClickListener(this)
		img_detail_comment_favorit_btn.setOnClickListener(this)*/
	}

	private fun detailViewButtonEnableEvent() {
		binding.etDetailComment.textChangedListener {
			if (!it.isNullOrBlank()) {
				binding.imgCommentUploadBtn.visibility = View.VISIBLE
			}
		}
	}

	override fun onClick(detailClickableView: View?) {
		when (detailClickableView!!.id) {
			binding.clDetailMainImage.id -> sendDataToShowDetailImageView?.invoke()
			binding.btDetailMainImageMore.id -> sendDataToShowDetailImageView?.invoke()
			binding.imgDetailMore.id -> clickDetailMoreEvent?.invoke()
			binding.imgDetailCommentFavoritBtn.id -> likeClickEvent()
			binding.imgCommentUploadBtn.id -> {
				when {
					intent.hasExtra("rewriteComment")
							&& !commentDuplCheck -> rewriteComment(postId, commentId)
					intent.hasExtra("reply")
							&& !commentDuplCheck -> postReply(postId, commentId)
					else -> uploadComment()
				}
			}

			/*R.id.cl_detail_main_image -> sendDataToShowDetailImageView?.invoke()
			R.id.bt_detail_main_image_more -> sendDataToShowDetailImageView?.invoke()
			R.id.img_detail_more -> clickDetailMoreEvent?.invoke()
			R.id.img_detail_top_back_btn -> onBackPressed()
			R.id.img_detail_comment_favorit_btn -> likeClickEvent()
			R.id.img_comment_upload_btn -> {
				when {
					intent.hasExtra("rewriteComment")
							&& !commentDuplCheck -> rewriteComment(postId, commentId)
					intent.hasExtra("reply")
							&& !commentDuplCheck -> postReply(postId, commentId)
					else -> uploadComment()
				}
			}*/
		}
	}

	private fun likeClickEvent() {
		val view = binding.imgDetailCommentFavoritBtn
		//val view=findViewById<ImageView>(R.id.img_detail_comment_favorit_btn)

		if (view.isActivated) {//이미 좋아요한 경우
			requestToServer.snsService.requestDelLike(
					"Bearer " + App.prefs.local_login_token,
					postId
			)
				.customEnqueue(
						onSuccess = {
							loadUnLike(view, binding.textDetailCommentFavoritCount)
						},
						onError = {
							Log.d("Network", "좋아요 취소 에러")
						})
		} else {
			requestToServer.snsService.requestLike(
					"Bearer " + App.prefs.local_login_token,
					postId
			)
				.customEnqueue(
						onSuccess = {
							loadLike(view, binding.textDetailCommentFavoritCount, it.body()?.body?.likers)
						},
						onError = {
							Log.d("Network", "좋아요 설정 에러")
						})
		}
	}

	private fun rewritePost(responseBody: ResponseMainBody?) {
		val intent=Intent(this, AddActivity::class.java)
		intent.putExtra("title", responseBody?.title)
		intent.putExtra("content", detailContent.text.toString())
		intent.putExtra("category", responseBody?.category)
		intent.putExtra("postId", responseBody?.id)

		finish()
		startActivity(intent)
	}

	private fun loadCommentList() {
		requestToServer.snsService.requestCommentList(
				"Bearer " + App.prefs.local_login_token,
				postId,
				QueryValue.lastId,
				QueryValue.limit
		).customEnqueue(
				onSuccess = {
					if (it.body()?.body != null) {
						homeDetailCommentAdpater = HomeDetailCommentAdpater(
								this,
								it.body()!!.body!!,
								postId
						)
						binding.rvHomeDetailComment.adapter = homeDetailCommentAdpater
					}
				},
				onError = {
					Toast.makeText(this, "댓글 조회 네트워크 오류", Toast.LENGTH_SHORT).show()
				}
		)
	}

	private fun uploadComment(){
		requestToServer.snsService.requestComment(
				"Bearer " + App.prefs.local_login_token,
				postId!!,
				RequestComment(binding.etDetailComment.text.toString())
		).customEnqueue(
				onSuccess = {
					Log.d("Network", "댓글 생성 성공")
					detailRefreshEvnet(false)
				}
		)
		val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
		manager.hideSoftInputFromWindow(
				currentFocus!!.windowToken,
				InputMethodManager.HIDE_NOT_ALWAYS
		)
		binding.etDetailComment.setText("")
	}

	private fun bindRewriteComment() {
		val comment= intent?.getStringExtra("rewriteComment").toString()
		binding.etDetailComment.apply {
			setText(comment)
			requestFocus()
		}
	}

	private fun rewriteComment(postId: Int?, commentId: Int?) {
		RequestToServer.snsService.requestRewriteComment(
				"Bearer " + App.prefs.local_login_token,
				postId,
				commentId,
				RequestComment(binding.etDetailComment.text.toString())
		).customEnqueue(
				onSuccess = {
					Log.d("Network", "댓글 수정 성공")
					commentDuplCheck = true
					detailRefreshEvnet(false)
				},
				onError = {
					Toast.makeText(this, "댓글 수정 네트워크 오류", Toast.LENGTH_SHORT).show()
				}
		)
		val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
		manager.hideSoftInputFromWindow(
				currentFocus!!.windowToken,
				InputMethodManager.HIDE_NOT_ALWAYS
		)
		binding.etDetailComment.setText("")
	}

	private fun postReply(postId: Int?, commentId: Int?) {
		RequestToServer.snsService.requestReply(
				"Bearer " + App.prefs.local_login_token,
				postId,
				commentId,
				RequestComment(binding.etDetailComment.text.toString())
		).customEnqueue(
				onSuccess = {
					Log.d("Network", "답글 생성 성공")
					commentDuplCheck = true
					detailRefreshEvnet(false)
				},
				onError = {
					Toast.makeText(this, "답글 생성 네트워크 오류", Toast.LENGTH_SHORT).show()
				}
		)
		val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
		manager.hideSoftInputFromWindow(
				currentFocus!!.windowToken,
				InputMethodManager.HIDE_NOT_ALWAYS
		)
		binding.etDetailComment.setText("")
	}
}
