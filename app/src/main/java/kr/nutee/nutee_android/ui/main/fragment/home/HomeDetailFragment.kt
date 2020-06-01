package kr.nutee.nutee_android.ui.main.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.main_fragment_home_detail.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.DateParser
import kr.nutee.nutee_android.data.main.home.ResponseMainItem
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.imageSetting
import kr.nutee.nutee_android.ui.extend.loadFragment
import kr.nutee.nutee_android.ui.main.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class HomeDetailFragment(var lastId: Int) : Fragment() {


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
		requestToServer.service.requestMain(lastId,1)
			.customEnqueue { response ->
			response.body()?.let {
				bindDetail(it[0])
			}
		}
	}

	private fun bindDetail(responseMainItem: ResponseMainItem) {
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
	}

	private fun imageLoadAndSet(response: ResponseMainItem) {
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
						Glide.with(it).load(imageSetting(response.Images[i].src)).into(imageList[i])
					}
				}
			}
			3 -> {
				cl_detail_image3.visibility = View.VISIBLE
				val imageList =
					listOf<ImageView>(img_detail_image3_1, img_detail_image3_2, img_detail_image3_3)
				for (i in 0 until 3) {
					context?.let {
						Glide.with(it).load(imageSetting(response.Images[i].src)).into(imageList[i])
					}
				}
			}
			else -> {
				cl_detail_image_more.visibility = View.VISIBLE
				context?.let {
					Glide.with(it).load(imageSetting(response.Images[0].src)).into(img_detail_image_more_top)
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
