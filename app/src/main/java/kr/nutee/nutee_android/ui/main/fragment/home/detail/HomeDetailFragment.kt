package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_fragment_home_detail.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.RequestReport
import kr.nutee.nutee_android.data.main.home.Comment
import kr.nutee.nutee_android.data.main.home.Image
import kr.nutee.nutee_android.data.main.home.ResponseMainItem
import kr.nutee.nutee_android.data.main.home.detail.RequestComment
import kr.nutee.nutee_android.data.main.home.detail.ShowDetailImageView
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.*
import kr.nutee.nutee_android.ui.extend.dialog.cumstomReportDialog
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.extend.imageSetting.setImageURLSetting
import kr.nutee.nutee_android.ui.main.MainActivity
import kr.nutee.nutee_android.ui.main.fragment.home.HomeFragement

/**
 * A simple [Fragment] subclass.
 */
class HomeDetailFragment(private var lastId: Int) : Fragment(),View.OnClickListener {

	private lateinit var homeDetailCommentAdapter: HomeDetailCommentAdpater
	private var postId:Int = 0

	private lateinit var detailImageViewIntent: Intent

	private val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		loadDetail()
		return inflater.inflate(R.layout.main_fragment_home_detail, container, false)
	}

	private fun loadDetail() {
		requestToServer.service.requestMain(lastId, 1)
			.customEnqueue { response ->
				response.body()?.let {
					bindDetail(it[0])
					setCommentAdpater(it[0].Comments)
				}
			}
	}

	private fun bindDetail(responseMainItem: ResponseMainItem) {
		//postId 저장
		postId = responseMainItem.id!!
		//유저 이미지 매핑
		val userImageLoad =
			setImageURLSetting(
				responseMainItem.User?.Image?.src
			)
		context?.let { Glide.with(it).load(userImageLoad).into(img_detail_profile) }
		//유저 정보 매핑
		text_detail_nick.text = responseMainItem.User?.nickname
		text_detail_time.text =
			responseMainItem.createdAt?.let { DateParser(it).calculateDiffDate() }
		text_detail_content.text = responseMainItem.content
		// 컨텐츠 이미지 매핑
		if (responseMainItem.Images.isNotEmpty()) {
			imageFrameLoad(responseMainItem.Images)
		}
		//댓글 관련 매핑
		et_detail_comment.textChangedListener {
			if (!it.isNullOrBlank()) {
				img_comment_upload_btn.visibility = View.VISIBLE
			}
		}
		img_comment_upload_btn.setOnClickListener(this)
		img_detail_more.setOnClickListener { detailMore(responseMainItem) }
	}

	private fun setCommentAdpater(comments: List<Comment>) {
		homeDetailCommentAdapter = HomeDetailCommentAdpater(comments,this.context!!)
		rv_home_detail_comment.adapter = homeDetailCommentAdapter
	}

	override fun onClick(v: View?) {
		when (v!!.id) {
			R.id.img_comment_upload_btn ->{
				requestToServer.service.requestComment(
					App.prefs.local_login_token,
					postId,
					RequestComment(et_detail_comment.text.toString())
				).customEnqueue {
					if (it.isSuccessful) {
						val transaction = (context as MainActivity).supportFragmentManager.beginTransaction()
						transaction.detach(this)
						transaction.attach(this)
						transaction.commit()
						et_detail_comment.text = null
					}
				}
			}
			R.id.cl_detail_image3 -> {
				Log.d("DetailTag","DetailImageView")
			}
		}
	}

	private fun imageFrameLoad(images: List<Image>) {
		val imageList = listOf<ImageView>(
			img_detail_image3_1,
			img_detail_image3_2,
			img_detail_image3_3
		)
		cl_detail_image3.visibility = View.VISIBLE
		detailImageViewIntent = Intent(requireContext(),ShowDetailImageView::class.java)
		cl_detail_image3.setOnClickListener(this)
		if (images.isNotEmpty()) {
			cl_detail_image3.visibility = View.VISIBLE
			cl_detail_image3.setOnClickListener(this)

			/*when (response.Images.size) {
				1 -> {
					cl_detail_image1.visibility = View.VISIBLE
					context?.let {
						Log.d("imageUrl",RequestToServer.retrofit.baseUrl().toString()+response.Images[0].src)
						Glide.with(it).load(RequestToServer.retrofit.baseUrl().toString()+response.Images[0].src)
							.into(img_detail_image1_1)
					}
				}
				2 -> {
					cl_detail_image2.visibility = View.VISIBLE
					val imageList = listOf<ImageView>(img_detail_image2_1, img_detail_image2_2)
					for (i in 0 until 2) {
						context?.let {
							Glide.with(it).load(
								setImageURLSetting(
									response.Images[i].src
								)
							)
								.into(imageList[i])
						}
					}
				}
				3 -> {
					cl_detail_image3.visibility = View.VISIBLE
					val imageList =
						listOf<ImageView>(
							img_detail_image3_1,
							img_detail_image3_2,
							img_detail_image3_3
						)
					for (i in 0 until 3) {
						context?.let {
							Glide.with(it).load(
								setImageURLSetting(
									response.Images[i].src
								)
							)
								.into(imageList[i])
						}
					}
				}
				else -> {
					cl_detail_image_more.visibility = View.VISIBLE
					context?.let {
						Glide.with(it).load(
							setImageURLSetting(
								response.Images[0].src
							)
						)
							.into(img_detail_image_more_top)
					}
				}
			}*/
		}

	}

	private fun setDetailImage(){

	}

	private fun detailMore(res: ResponseMainItem) {
		if (res.User?.id.toString() == App.prefs.local_user_id) {
			context?.customSelectDialog(View.GONE, View.VISIBLE, View.VISIBLE,
				{},
				{
					Log.d("글수정 버튼", "누름")
				},
				{
					Log.d("글삭제 버튼", "누름")
					requestToServer.service.requestDelete(
						App.prefs.local_login_token,
						res.id
					).customEnqueue {
						if (it.isSuccessful) {
							HomeFragement()
						}
					}
				})
		} else {
			context?.customSelectDialog(View.VISIBLE, View.GONE, View.GONE,
				{
					Log.d("글신고", "누름")
					context?.cumstomReportDialog{
						requestToServer.service.requestReport(
							RequestReport(it), res.id)
							.customEnqueue{ res->
								if (res.isSuccessful) {
									Toast
										.makeText(context,"신고가 성공적으로 접수되었습니다.", Toast.LENGTH_SHORT)
										.show()
								}
							}
					}
				}
			)
		}
	}
}

