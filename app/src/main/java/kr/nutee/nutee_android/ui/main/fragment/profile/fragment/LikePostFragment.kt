package kr.nutee.nutee_android.ui.main.fragment.profile.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.main_fragment_profile_like_post.*
import kotlinx.android.synthetic.main.main_fragment_profile_written_post.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.RefreshEvent
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter

class LikePostFragment: Fragment() {

	private lateinit var recyclerView: RecyclerView
	val requestToServer = RequestToServer
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_fragment_profile_like_post, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		swipeRefreshLayout= view.findViewById(R.id.swipe_profile_like_post_list_refresh)
		recyclerView= view.findViewById(R.id.rv_profile_like_post_list)
		recyclerView.apply {
			layoutManager= LinearLayoutManager(this.context,
				LinearLayoutManager.VERTICAL, false)
			setHasFixedSize(true)
		}
		loadLikePostList()
		detailRefreshEvnet()

	}

	private fun loadLikePostList() {
		requestToServer.backService.requestMyLikePosts(
				"Bearer "+ App.prefs.local_login_token,
				QueryValue.lastId,
				QueryValue.limit
		).customEnqueue(
				onSuccess = {
					Log.d("Network", "내 포스트 정보 통신 성공")
					if (it.body()?.body.isNullOrEmpty()) {
						cl_main_profile_like_no_post.visibility = View.VISIBLE
						rv_profile_like_post_list.visibility=View.GONE
					}
					else {
						rv_profile_like_post_list.visibility=View.VISIBLE
						cl_main_profile_like_no_post.visibility = View.GONE
						recyclerView.adapter = HomeRecyclerViewAdapter(it.body()?.body!!)
					}
				},
				onError = {
					Log.d("Network", "통신 에러")
				}
		)
	}
	private fun detailRefreshEvnet() {
		context?.RefreshEvent(swipeRefreshLayout,true){loadLikePostList()}
	}
}