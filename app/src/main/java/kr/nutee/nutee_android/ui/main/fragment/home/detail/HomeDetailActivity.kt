package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_home_detail_activtiy.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.Comment
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.data.main.home.ResponseMainItem
import kr.nutee.nutee_android.data.main.home.detail.RequestComment
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting
import kr.nutee.nutee_android.ui.extend.textChangedListener
import retrofit2.Response
import java.util.ArrayList

/*
* created by jinsu47555
* DESC: 디테일 페이지를 표시하는 엑티비티
* //FIXME 증말 급하게 만들어서 나중에 리펙함 하자...하.. 이게 모니...
* */

class HomeDetailActivity : AppCompatActivity(),View.OnClickListener {

	private var postId:Int? = 0

	private var sendDataToShowDetailImageView:(()->Unit)? = null
	private var clickDetailMoreEvent:(()->Unit)? =null

	private lateinit var imageViewList:List<ImageView>

	private lateinit var homeDetailCommentAdpater: HomeDetailCommentAdpater

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main_home_detail_activtiy)
		init()
	}

	private fun init() {
		postId = intent?.getIntExtra("Detail_id",0)
		imageViewList = listOf<ImageView>(
			img_detail_image3_1,
			img_detail_image3_2,
			img_detail_image3_3
		)
		loadDetailPage()
		detailViewClickEvnet()
		detailViewButtonEnableEvent()
	}

	private fun loadDetailPage() {
		RequestToServer.service
			.requestDetail(postId!!)
			.customEnqueue(
				onSuccess = {response -> onSucessLoadDetailView(response) },
				onError = {onErrorDetailPage()}
			)
	}

	private fun onErrorDetailPage() {
		customDialogSingleButton("네트워크 오류")
	}

	private fun onSucessLoadDetailView(response: Response<ResponseMainItem?>) {
		if (response.body() == null) {
			nullPostEvnet()
			return
		}
		bindDetailPostEvent(response.body()!!)
	}

	private fun nullPostEvnet() {
		customDialogSingleButton("해당 글은 존재하지 않습니다.\n 지속적으로 해당 글이 보일 경우 문의 바랍니다.")
		finish()
	}

	private fun bindDetailPostEvent(responseMainItem: ResponseMainItem) {
		val userImageLoad = setImageURLSetting(responseMainItem.User?.Image?.src)
		Glide.with(applicationContext).load(userImageLoad).into(img_detail_profile)
		text_detail_nick.text = responseMainItem.User?.nickname
		text_detail_time.text =
			responseMainItem.createdAt?.let { DateParser(it).calculateDiffDate() }
		text_detail_content.text = responseMainItem.content
		setCommentAdpater(responseMainItem.Comments)
		clickDetailMoreEvent = {detailMore(responseMainItem)}

		if(responseMainItem.Images.isNotEmpty()) imageFrameLoad(responseMainItem.Images)
	}

	private fun imageFrameLoad(images: List<Image>) {
		sendDataToShowDetailImageView={loadDetailImagePage(images)}
		if (images.count() > 3) {
			loadMoreImageFrame(images)
			return
		}
		loadLessThenThreeImageFragme(images)
	}

	private fun loadDetailImagePage(images: List<Image>) {
		val detailImageViewIntent = Intent(applicationContext,ShowDetailImageView::class.java)
		val bundle =Bundle()
		bundle.putParcelableArrayList("Images",images as ArrayList<Image>)
		detailImageViewIntent.putExtras(bundle)
		startActivity(detailImageViewIntent)

	}

	private fun loadLessThenThreeImageFragme(images: List<Image>) {
		cl_detail_image3.visibility = View.VISIBLE
		for (i in 0 until images.count()) {
			Glide.with(applicationContext)
				.load(setImageURLSetting(images[i].src))
				.into(imageViewList[i])
			imageViewList[i].visibility = View.VISIBLE
		}

	}

	private fun loadMoreImageFrame(images: List<Image>) {
		cl_detail_image_more.visibility = View.VISIBLE
		Glide.with(applicationContext)
			.load(setImageURLSetting(images[0].src))
			.into(imageViewList[0])
	}

	private fun setCommentAdpater(comments: List<Comment>) {
		homeDetailCommentAdpater = HomeDetailCommentAdpater(comments,applicationContext)
		rv_home_detail_comment.adapter = homeDetailCommentAdpater
	}

	private fun detailMore(res: ResponseMainItem) {
		if (res.User?.id.toString() == App.prefs.local_user_id) {
			customSelectDialog(View.GONE, View.VISIBLE, View.VISIBLE,
				{},
				{
					Log.d("글수정 버튼", "누름")
				},
				{
					Log.d("글삭제 버튼", "누름")
					RequestToServer.service.requestDelete(
						App.prefs.local_login_token,
						res.id
					).customEnqueue {
						if (it.isSuccessful) {
							finish()
						}
					}
				})
		} else {
			customSelectDialog(View.VISIBLE, View.GONE, View.GONE,
				{
					Log.d("글신고", "누름")
					cumstomReportDialog{
						RequestToServer.service.requestReport(
							RequestReport(it), res.id)
							.customEnqueue{ res->
								if (res.isSuccessful) {
									Toast
										.makeText(applicationContext,"신고가 성공적으로 접수되었습니다.", Toast.LENGTH_SHORT)
										.show()
								}
							}
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
	}

	private fun detailViewButtonEnableEvent(){
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
			R.id.img_comment_upload_btn ->{
				RequestToServer.service.requestComment(
					App.prefs.local_login_token,
					postId!!,
					RequestComment(et_detail_comment.text.toString())
				).customEnqueue(
					onSuccess = {
						et_detail_comment.text = null
						onRestart()
					}
				)
			}
		}
	}

}