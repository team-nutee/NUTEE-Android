package kr.nutee.nutee_android.ui.main.fragment.profile.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter

class LikePostFragment: Fragment() {

	private lateinit var recyclerView: RecyclerView
	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_fragment_profile_like_post, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		recyclerView= view.findViewById(R.id.rv_profile_post_list)
		recyclerView.apply {
			layoutManager= LinearLayoutManager(this.context,
				LinearLayoutManager.VERTICAL, false)
			setHasFixedSize(true)
		}

		//loadRecommendedPost()

	}

	// 글이 있을 때와 없을 때 뷰를 다르게 보여주려고 만든 함수같음 (jinsu가 만듬)
//	private fun loadUserProfileList(res: ResponseProfile) {
//		RequestToServer.authService
//			.requestUserPosts(res.id)
//			.customEnqueue(
//				onSuccess = { response ->
//					if (response.body().isNullOrEmpty()) {
//						cl_my_profile_recommended_post_is_empty.visibility = View.VISIBLE
//						return@customEnqueue
//					}
//					cl_my_profile_recommended_post_list.visibility = View.VISIBLE
//					 loadRecommendedPost{}
//				}
//			)
//	}

//	private fun loadRecommendedPost() {
//		requestToServer.backService.requestMyLikePosts(
//			//"Bearer "+ TestToken.testToken,
//			"Bearer "+ TestToken.testToken,
//			QueryValue.lastId,
//			QueryValue.limit
//		).customEnqueue(
//			onSuccess = {
//				Log.d("Network", "좋아요한 포스트 통신 성공")
//				recyclerView.adapter = HomeRecyclerViewAdapter(it.body()!!)
//			}
//			,
//			onError = {
//				Log.d("Network", "통신 에러")
//			}
//		)
//	}

}