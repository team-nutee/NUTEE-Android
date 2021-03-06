package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.volokh.danylo.hashtaghelper.HashTagHelper
import kotlinx.android.synthetic.main.main_home_detail_activtiy.*
import kotlinx.android.synthetic.main.main_home_detail_activtiy.img_detail_comment_favorit_btn
import kotlinx.android.synthetic.main.main_home_detail_activtiy.text_detail_comment_favorit_count
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.Body
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.data.main.home.LookUpDetail
import kr.nutee.nutee_android.data.main.home.detail.RequestComment
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.animation.glideProgressDrawable
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting
import kr.nutee.nutee_android.ui.main.fragment.add.AddActivity
import kr.nutee.nutee_android.ui.main.fragment.search.SearchResultsView
import kotlin.collections.isNullOrEmpty as isNullOrEmpty1


/*
* created by jinsu47555
* DESC: 디테일 페이지를 표시하는 엑티비티
*
* created by 88yhtserofG
* DESC: 해시태그 기능. Hashtag Helper 라이브러리 사용
*       디테일 페이지 2.0버전으로 수정 및 구현
* */

class HomeDetailActivity : AppCompatActivity(),View.OnClickListener,
	HashTagHelper.OnHashTagClickListener {

	val requestToServer = RequestToServer
	private var postId: Int? = 0
	private var commentId: Int? = 0

	private var sendDataToShowDetailImageView: (() -> Unit)? = null
	private var clickDetailMoreEvent: (() -> Unit)? = null

	private lateinit var imageViewList: List<ImageView>

	private lateinit var homeDetailCommentAdpater: HomeDetailCommentAdpater
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout

	private lateinit var mTextHashTagHelper: HashTagHelper
	private val additionalSymbols = '#'

	lateinit var detailContent: TextView
	lateinit var detailNickname: TextView
	lateinit var detailTime:TextView
	lateinit var detailRewrite:TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_home_detail_activtiy)
		detailNickname=findViewById(R.id.text_detail_nick)
		detailTime=findViewById(R.id.text_detail_time)
		detailContent=findViewById(R.id.text_detail_content)
		swipeRefreshLayout=findViewById(R.id.swipe_refresh_detail_view)
		detailRewrite=findViewById(R.id.text_detail_content_rewrite)

		init()
	}

	private fun init() {
		postId = intent?.getIntExtra("Detail_id", 0)
		commentId=intent?.getIntExtra("comment_id", 0)
		imageViewList = listOf<ImageView>(
			img_detail_main_image_1,
			img_detail_main_image_2,
			img_detail_main_image_3,
			img_detail_main_image_4
		)
		detailRefreshEvnet(true)
		loadDetailPage()
		loadCommentList()
		if(intent.hasExtra("rewriteComment"))
			bindRewriteComment()
		detailViewClickEvnet()
		detailViewButtonEnableEvent()
		//해시태그 기능
		mTextHashTagHelper = HashTagHelper.Creator.create(
			ContextCompat.getColor(this, R.color.nuteeBase), this, additionalSymbols
		)
		mTextHashTagHelper.handle(detailContent)
	}

	private fun detailRefreshEvnet(swipeBoolean: Boolean) {
		RefreshEvent(swipeRefreshLayout,swipeBoolean){loadCommentList()}
	}

	private fun loadDetailPage() {
		Log.d("ididid", "포스트아이디 확인 ${this.postId}")
		RequestToServer.backService
			.requestDetail(
				"Bearer " + TestToken.testToken,
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

	private fun bindDetailPostEvent(responseMainItem: Body) {
		Log.d("Glide test", "프로필 사진 로딩")
		GlideApp.with(applicationContext)
				.load(responseMainItem.user?.image?.src)
				//.placeholder(glideProgressDrawable())
				.fallback(R.mipmap.nutee_character_background_white_round)
				.into(img_detail_profile)
		detailNickname.text = responseMainItem.user?.nickname
		detailTime.text =
			responseMainItem.createdAt?.let { DateParser(it).calculateDiffDate() }
		detailContent.text = responseMainItem.content
		setLikeEvent(
			img_detail_comment_favorit_btn,
			text_detail_comment_favorit_count,
			responseMainItem.likers
		)
		clickDetailMoreEvent = { detailMore(responseMainItem) }
		if (!responseMainItem.images.isNullOrEmpty1()) imageFrameLoad(
			responseMainItem.images,
			responseMainItem.id
		)
		else Log.d("Network", "글 상세 뷰 사진 null")

		//수정 여부 표시
		if(responseMainItem.updatedAt !=responseMainItem.createdAt)
			detailRewrite.visibility=View.VISIBLE

		//답글 생성 시
		if(intent.hasExtra("reply")) {
			et_detail_comment.requestFocus()
			//키보드 자동 생성
			val inputMethodManager: InputMethodManager =
				getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
			inputMethodManager.showSoftInput(et_detail_comment, 0)
		}
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
		cl_detail_main_image.visibility = View.VISIBLE
		var boolImageSize= images.size
		if(images.size > 4) {
			bt_detail_main_image_more.apply {
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

	private fun detailMore(responseBody: Body) {
		contentMoreEvent(responseBody.user,
			View.GONE,{},
			{//게시글 수정
					if (responseBody.images.isNullOrEmpty1()) {
						rewritePost(responseBody)
					}
					else customDialogSingleButton(getString(R.string.UnableRewritePost))
			},
			{//게시글 삭제
					RequestToServer.backService.requestDelete(
						"Bearer " + TestToken.testToken,
						//App.prefs.local_login_token,
						responseBody.id
					).customEnqueue(
							onSuccess = { finish() },
							onError = {
								Toast.makeText(this, "네트워크오류", Toast.LENGTH_SHORT)
									.show()
							})},
			{//게시글 신고
					cumstomReportDialog("이 게시글을 신고하시겠습니까?") {
						RequestToServer.backService.requestReport(
							RequestReport(it), responseBody.id
						).customEnqueue(
								onSuccess = {
									Toast
										.makeText(
											applicationContext,
											"신고가 성공적으로 접수되었습니다.",
											Toast.LENGTH_SHORT
										).show()
								})}})
	}

	private fun detailViewClickEvnet() {
		cl_detail_main_image.setOnClickListener(this)
		bt_detail_main_image_more.setOnClickListener(this)
		img_comment_upload_btn.setOnClickListener(this)
		img_detail_more.setOnClickListener(this)
		img_detail_top_back_btn.setOnClickListener(this)
		img_detail_comment_favorit_btn.setOnClickListener(this)
	}

	private fun detailViewButtonEnableEvent() {
		et_detail_comment.textChangedListener {
			if (!it.isNullOrBlank()) {
				img_comment_upload_btn.visibility = View.VISIBLE
			}
		}
	}

	override fun onClick(detailClickableView: View?) {
		when (detailClickableView!!.id) {
			R.id.cl_detail_main_image -> sendDataToShowDetailImageView?.invoke()
			R.id.bt_detail_main_image_more -> sendDataToShowDetailImageView?.invoke()
			R.id.img_detail_more -> clickDetailMoreEvent?.invoke()
			R.id.img_detail_top_back_btn -> onBackPressed()
			R.id.img_detail_comment_favorit_btn -> likeClickEvent()
			R.id.img_comment_upload_btn -> {
				when {
					intent.hasExtra("rewriteComment") -> rewriteComment(postId, commentId)
					intent.hasExtra("reply") -> postReply(postId, commentId)
					else -> uploadComment()
				}
			}
		}
	}

	override fun onHashTagClicked(hashTag: String) {
		val intentSearchResults= Intent(this, SearchResultsView::class.java)
		intentSearchResults.putExtra("searchBoxText", "#$hashTag")
		startActivity(intentSearchResults)
	}

	private fun likeClickEvent() {
		val view=findViewById<ImageView>(R.id.img_detail_comment_favorit_btn)

		if (view.isActivated) {//이미 좋아요한 경우
			requestToServer.backService.requestDelLike(
				"Bearer " + TestToken.testToken,
				postId
			)
				.customEnqueue(
					onSuccess = {
						loadUnLike(view, text_detail_comment_favorit_count)
					},
					onError = {
						Log.d("Network", "좋아요 취소 에러")
					})
		} else {
			requestToServer.backService.requestLike(
				"Bearer " + TestToken.testToken,
				postId
			)
				.customEnqueue(
					onSuccess = {
						loadLike(view, text_detail_comment_favorit_count, it.body()?.body?.likers)
					},
					onError = {
						Log.d("Network", "좋아요 설정 에러")
					})
		}
	}

	private fun rewritePost(responseBody: Body?) {
		val intent=Intent(this, AddActivity::class.java)
		intent.putExtra("title", responseBody?.title)
		intent.putExtra("content", detailContent.text.toString())
		intent.putExtra("category", responseBody?.category)
		intent.putExtra("postId", responseBody?.id)
		//이미지 수정 기능 구현 필요함
		if (responseBody?.images?.isNullOrEmpty1() == false){
			val imageArrayList = responseBody.images.toCollection(ArrayList())
			intent.putParcelableArrayListExtra("rewriteImage", imageArrayList)
			finish()
			startActivity(intent)
			return
		}
		finish()
		startActivity(intent)
	}

	private fun loadCommentList() {
		requestToServer.backService.requestCommentList(
			"Bearer " + TestToken.testToken,
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
					rv_home_detail_comment.adapter = homeDetailCommentAdpater
				}
			},
			onError = {
				Toast.makeText(this, "댓글 조회 네트워크 오류", Toast.LENGTH_SHORT).show()
			}
		)
	}

	private fun uploadComment(){
		requestToServer.backService.requestComment(
			"Bearer " + TestToken.testToken,
			postId!!,
			RequestComment(et_detail_comment.text.toString())
		).customEnqueue(
			onSuccess = {
				Log.d("Network", "댓글 생성 성공")
			}
		)
		val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
		manager.hideSoftInputFromWindow(
			currentFocus!!.windowToken,
			InputMethodManager.HIDE_NOT_ALWAYS
		)
		et_detail_comment.setText("")
		detailRefreshEvnet(false)
	}

	private fun bindRewriteComment() {
		val comment= intent?.getStringExtra("rewriteComment").toString()
		et_detail_comment.apply {
			setText(comment)
			requestFocus()
		}
	}

	private fun rewriteComment(postId: Int?, commentId: Int?) {
		RequestToServer.backService.requestRewriteComment(
			"Bearer " + TestToken.testToken,
			postId,
			commentId,
			RequestComment(et_detail_comment.text.toString())
		).customEnqueue(
			onSuccess = {
				Log.d("Network", "댓글 수정 성공")

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
		et_detail_comment.setText("")
		detailRefreshEvnet(false)
	}

	private fun postReply(postId: Int?, commentId: Int?) {
		RequestToServer.backService.requestReply(
			"Bearer " + TestToken.testToken,
			postId,
			commentId,
			RequestComment(et_detail_comment.text.toString())
		).customEnqueue(
			onSuccess = {
				Log.d("Network", "답글 생성 성공")
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
		et_detail_comment.setText("")
		detailRefreshEvnet(false)
	}
}
