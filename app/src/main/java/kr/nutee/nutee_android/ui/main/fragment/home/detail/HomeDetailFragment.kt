package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_fragment_home_detail.*
import kotlinx.android.synthetic.main.main_fragment_home_detail.view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.home.Comment
import kr.nutee.nutee_android.data.main.home.ResponseMainItem
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.imageSetting
import kr.nutee.nutee_android.ui.extend.loadFragment
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.main.fragment.home.HomeFlagement

/**
 * A simple [Fragment] subclass.
 */
class HomeDetailFragment(private var lastId: Int) : Fragment(),View.OnClickListener {

	private lateinit var homeDetailCommentAdapter: HomeDetailCommentAdpater
	private var postId:Int = 0

	private val requestToServer = RequestToServer



	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		loadDetail()
		val view = inflater.inflate(R.layout.main_fragment_home_detail, container, false)
		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		backPressEvent(view)
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
		val userImageLoad = imageSetting(responseMainItem.User.Image?.src)
		context?.let { Glide.with(it).load(userImageLoad).into(img_detail_profile) }
		//유저 정보 매핑
		text_detail_nick.text = responseMainItem.User.nickname
		text_detail_time.text =
			responseMainItem.createdAt?.let { DateParser(it).calculateDiffDate() }
		text_detail_content.text = responseMainItem.content
		// 컨텐츠 이미지 매핑
		imageLoadAndSet(responseMainItem)
		//댓글 관련 매핑
		et_detail_comment.textChangedListener {
			if (!it.isNullOrBlank()) {
				img_comment_upload_btn.visibility = View.VISIBLE
			}
		}
		img_comment_upload_btn.setOnClickListener(this)
	}

	private fun setCommentAdpater(comments: List<Comment>) {
		homeDetailCommentAdapter = HomeDetailCommentAdpater(comments,this.context!!)
		rv_home_detail_comment.adapter = homeDetailCommentAdapter
	}

	override fun onClick(v: View?) {
		when (v!!.id) {
			R.id.img_comment_upload_btn ->{
				requestToServer.service.requestComment(
					App.prefs.PREFS_TOKEN,
					postId,
					et_detail_comment.text.toString()
				).customEnqueue {
					if (it.isSuccessful) {
						loadDetail()
					}
				}
			}
		}
	}

	private fun imageLoadAndSet(response: ResponseMainItem) {
		if (response.Images.isNotEmpty()) {
			when (response.Images.size) {
				1 -> {
					cl_detail_image1.visibility = View.VISIBLE
					context?.let {
						Glide.with(it).load(imageSetting(response.Images[0].src))
							.into(img_detail_image1_1)
					}
				}
				2 -> {
					cl_detail_image2.visibility = View.VISIBLE
					val imageList = listOf<ImageView>(img_detail_image2_1, img_detail_image2_2)
					for (i in 0 until 2) {
						context?.let {
							Glide.with(it).load(imageSetting(response.Images[i].src))
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
							Glide.with(it).load(imageSetting(response.Images[i].src))
								.into(imageList[i])
						}
					}
				}
				else -> {
					cl_detail_image_more.visibility = View.VISIBLE
					context?.let {
						Glide.with(it).load(imageSetting(response.Images[0].src))
							.into(img_detail_image_more_top)
					}
				}
			}
		}

	}

	private fun backPressEvent(view: View) {
		view.isFocusableInTouchMode = true
		view.requestFocus();
		view.setOnKeyListener { _, keyCode, _ ->
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				context?.loadFragment(HomeFlagement())
				return@setOnKeyListener true
			}
			return@setOnKeyListener false
		}

	}
}

