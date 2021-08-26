package kr.nutee.nutee_android.ui.main.fragment.profile.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.main_fragment_profile_written_post.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.databinding.MainFragmentProfileWrittenCommentBinding
import kr.nutee.nutee_android.databinding.MainFragmentProfileWrittenPostBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.RefreshEvent
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter

class MyPostFragment: Fragment() {

	private var binding: MainFragmentProfileWrittenPostBinding? = null
	private lateinit var recyclerView: RecyclerView
	val requestToServer = RequestToServer
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = MainFragmentProfileWrittenPostBinding.inflate(inflater, container, false)
		return requireBinding().root
		//return inflater.inflate(R.layout.main_fragment_profile_written_post, container, false)
	}

	private fun requireBinding(): MainFragmentProfileWrittenPostBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		//swipeRefreshLayout= view.findViewById(R.id.swipe_profile_written_post_refresh)
		//recyclerView= view.findViewById(R.id.rv_profile_written_post)
		requireBinding().rvProfileWrittenPost.setHasFixedSize(true)
		//recyclerView.setHasFixedSize(true)
		loadMyPost()
		detailRefreshEvnet()

	}

	private fun loadMyPost() {
		requestToServer.snsService.requestMyPosts(
				"Bearer "+ App.prefs.local_login_token,
			QueryValue.lastId,
			QueryValue.limit
		).customEnqueue(
			onSuccess = {
				Log.d("Network", "내 포스트 정보 통신 성공")
				if (it.body()?.body.isNullOrEmpty()) {
					with(requireBinding()){
						clMainProfileNoPost.visibility = View.VISIBLE
						rvProfileWrittenPost.visibility = View.GONE
					}
					//cl_main_profile_no_post.visibility = View.VISIBLE
					//rv_profile_written_post.visibility=View.GONE
				}
				else {
					with(requireBinding()){
						rvProfileWrittenPost.visibility=View.VISIBLE
						clMainProfileNoPost.visibility = View.GONE
						rvProfileWrittenPost.adapter = HomeRecyclerViewAdapter(it.body()?.body!!)
					}
					//rv_profile_written_post.visibility=View.VISIBLE
					//cl_main_profile_no_post.visibility = View.GONE
					//recyclerView.adapter = HomeRecyclerViewAdapter(it.body()?.body!!)
				}
			}
		)
	}
	private fun detailRefreshEvnet() {
		context?.RefreshEvent(requireBinding().swipeProfileWrittenPostRefresh,true){ loadMyPost()}
		//context?.RefreshEvent(swipeRefreshLayout,true){ loadMyPost()}
	}
}