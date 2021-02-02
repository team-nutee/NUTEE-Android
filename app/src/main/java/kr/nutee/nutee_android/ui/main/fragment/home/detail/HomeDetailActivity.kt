package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.volokh.danylo.hashtaghelper.HashTagHelper
import kotlinx.android.synthetic.main.main_home_detail_activtiy.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.*
import kr.nutee.nutee_android.data.main.home.detail.RequestComment
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.animation.glideProgressDrawable
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.main.fragment.search.SearchResultsView
import java.util.*


/*
* created by jinsu47555
* DESC: 디테일 페이지를 표시하는 엑티비티
* //FIXME 증말 급하게 만들어서 나중에 리펙함 하자...하.. 이게 모니...
*
* created by 88yhtserofG
* DESC: 해시태그 기능. Hashtag Helper 라이브러리 사용
* */

class HomeDetailActivity : AppCompatActivity(),View.OnClickListener,
	HashTagHelper.OnHashTagClickListener {

	private var postId: Int? = 0

	private var sendDataToShowDetailImageView: (() -> Unit)? = null
	private var clickDetailMoreEvent: (() -> Unit)? = null

	private lateinit var imageViewList: List<ImageView>

	private lateinit var homeDetailCommentAdpater: HomeDetailCommentAdpater

	private lateinit var mTextHashTagHelper: HashTagHelper
	private lateinit var mHashTagText: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_home_detail_activtiy)
		init()

		//해시태그 기능
		val additionalSymbols = '#'

		mHashTagText = text_detail_content
		mTextHashTagHelper = HashTagHelper.Creator.create(
			ContextCompat.getColor(this, R.color.nuteeBase), this, additionalSymbols
		)
		mTextHashTagHelper.handle(mHashTagText)
	}

	private fun init() {
		postId = intent?.getIntExtra("Detail_id", 0)
		imageViewList = listOf<ImageView>(
			img_detail_image3_1,
			img_detail_image3_2,
			img_detail_image3_3
		)
		detailRefreshEvnet()
		loadDetailPage()
		detailViewClickEvnet()
		detailViewButtonEnableEvent()
	}

	private fun detailRefreshEvnet() {
		swipe_refresh_detail_view.setProgressBackgroundColorSchemeColor(
			ContextCompat.getColor(applicationContext, R.color.nuteeBase)
		)
		swipe_refresh_detail_view.setColorSchemeColors(Color.WHITE)
		swipe_refresh_detail_view.setOnRefreshListener {
			onRestart()
			swipe_refresh_detail_view.isRefreshing = false
		}
	}

	private fun loadDetailPage() {
		RequestToServer.backService
			.requestDetail(postId!!)
			.customEnqueue(
				onSuccess = {
					onSucessLoadDetailView(it.body()) },
				onError = { onErrorDetailPage() }
			)
	}

	private fun onErrorDetailPage() {
		customDialogSingleButton("네트워크 오류") {
			finish()
		}
	}

	private fun onSucessLoadDetailView(response: LookUpDetail?) {
		if (response != null) {
			if (response.body == null) {
				nullPostEvnet()
				return
			}
			bindDetailPostEvent(response.body!!)
		}
	}

	private fun nullPostEvnet() {
		customDialogSingleButton("해당 글은 존재하지 않습니다.\n 지속적으로 해당 글이 보일 경우 문의 바랍니다.") {
			finish()
		}
	}

	private fun bindDetailPostEvent(responseMainItem: Body) {
		val userImageLoad = setImageURLSetting(responseMainItem.User?.Image?.src)
		Glide.with(applicationContext).load(userImageLoad).placeholder(glideProgressDrawable())
			.into(img_detail_profile)
		text_detail_nick.text = responseMainItem.User?.nickname
		text_detail_time.text =
			responseMainItem.CreatedAt?.let { DateParser(it).calculateDiffDate() }
		text_detail_content.text = responseMainItem.content
		//추후수정-댓글
		//setCommentAdpater(responseMainItem.Comments)
		clickDetailMoreEvent = { detailMore(responseMainItem) }

		if (responseMainItem.images?.isNotEmpty()!!) imageFrameLoad(responseMainItem.images)
	}

	private fun imageFrameLoad(images: Array<Image>) {
		sendDataToShowDetailImageView = { loadDetailImagePage(images) }
		if (images.count() > 3) {
			loadMoreImageFrame(images)
			return
		}
		loadLessThenThreeImageFragme(images)
	}

	private fun loadDetailImagePage(images: Array<Image>) {
		val detailImageViewIntent = Intent(applicationContext, ShowDetailImageView::class.java)
		val bundle = Bundle()
		bundle.putParcelableArrayList("Images", images as ArrayList<Image>)
		detailImageViewIntent.putExtras(bundle)
		startActivity(detailImageViewIntent)

	}

	private fun loadLessThenThreeImageFragme(images: Array<Image>) {
		cl_detail_image3.visibility = View.VISIBLE
		for (i in 0 until images.count()) {
			Glide.with(applicationContext)
				.load(setImageURLSetting(images[i].src))
				.into(imageViewList[i])
			imageViewList[i].visibility = View.VISIBLE
		}

	}

	private fun loadMoreImageFrame(images: Array<Image>) {
		cl_detail_image_more.visibility = View.VISIBLE
		Glide.with(applicationContext)
			.load(setImageURLSetting(images[0].src))
			.into(imageViewList[0])
	}

	private fun setCommentAdpater(comments: List<CommentBody>) {
		homeDetailCommentAdpater = HomeDetailCommentAdpater(comments, applicationContext)
		rv_home_detail_comment.adapter = homeDetailCommentAdpater
	}

	private fun detailMore(res: Body) {
		if (res.User?.id.toString() == App.prefs.local_user_id) {
			customSelectDialog(View.GONE, View.VISIBLE, View.VISIBLE,
				{},
				{
					Log.d("글수정 버튼", "누름")
				},
				{
					Log.d("글삭제 버튼", "누름")
					RequestToServer.backService.requestDelete(
						App.prefs.local_login_token,
						res.id)
						.customEnqueue(
							onSuccess = {finish()},
							onError = {}
						)
				})
		} else {
			customSelectDialog(View.VISIBLE, View.GONE, View.GONE,
				{
					Log.d("글신고", "누름")
					cumstomReportDialog {
						RequestToServer.backService.requestReport(
							RequestReport(it), res.id
						)
							.customEnqueue(
								onSuccess = {Toast
										.makeText(
											applicationContext,
											"신고가 성공적으로 접수되었습니다.",
											Toast.LENGTH_SHORT
										)
										.show()},
								onError = {}
							)
					}
				}
			)
		}
	}

	private fun detailViewClickEvnet() {
		cl_detail_image3.setOnClickListener(this)
		cl_detail_image_more.setOnClickListener(this)
		img_comment_upload_btn.setOnClickListener(this)
		img_detail_more.setOnClickListener(this)
		img_detail_top_back_btn.setOnClickListener(this)
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
			R.id.cl_detail_image3 -> sendDataToShowDetailImageView?.invoke()
			R.id.cl_detail_image_more -> sendDataToShowDetailImageView?.invoke()
			R.id.img_detail_more -> clickDetailMoreEvent?.invoke()
			R.id.img_detail_top_back_btn -> onBackPressed()
			R.id.img_comment_upload_btn -> {
				RequestToServer.backService.requestComment(
					App.prefs.local_login_token,
					postId!!,
					RequestComment(et_detail_comment.text.toString())
				).customEnqueue(
					onSuccess = {
						et_detail_comment.text = null
						finish()
						startActivity(intent)
					},
					onError = {}
				)
			}
		}
	}

	override fun onHashTagClicked(hashTag: String) {
		val intentSearchResults= Intent(this, SearchResultsView::class.java)
		intentSearchResults.putExtra("searchBoxText", "#$hashTag")
		startActivity(intentSearchResults)
	}
}
