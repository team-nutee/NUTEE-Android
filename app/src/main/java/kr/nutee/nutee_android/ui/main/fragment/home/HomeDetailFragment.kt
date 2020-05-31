package kr.nutee.nutee_android.ui.main.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class HomeDetailFragment : Fragment() {

	var lastId = 0
	private var limit = 1

	private val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		val view = inflater.inflate(R.layout.main_fragment_home_detail, container, false)
		loadDetail()
		return view
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		backPressEvent(view)
	}

	private fun loadDetail() {
		requestToServer.service.requestMain(
			lastId, limit
		).customEnqueue { response ->
			response.body()?.let {
				bindDetail(it[0])
			}
		}
	}

	private fun bindDetail(responseMainItem: ResponseMainItem) {
		//유저 이미지 매핑
		val userImageLoad = imageSetting(responseMainItem.User.Image?.src)
		Glide.with(context).load(userImageLoad).into(img_detail_profile)
		//유저 정보 매핑
		text_detail_nick.text = responseMainItem.User.nickname
		text_detail_time.text =
			responseMainItem.createdAt?.let { DateParser(it).calculateDiffDate() }
		text_detail_content.text = responseMainItem.content
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
