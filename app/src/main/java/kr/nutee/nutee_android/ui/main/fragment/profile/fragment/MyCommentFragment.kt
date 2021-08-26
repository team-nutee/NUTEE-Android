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
import kotlinx.android.synthetic.main.main_fragment_profile_written_comment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.databinding.MainFragmentProfileLikePostBinding
import kr.nutee.nutee_android.databinding.MainFragmentProfileWrittenCommentBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.RefreshEvent
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter

class MyCommentFragment: Fragment() {

	private var binding: MainFragmentProfileWrittenCommentBinding? = null
	private lateinit var recyclerView: RecyclerView
	val requestToServer = RequestToServer
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = MainFragmentProfileWrittenCommentBinding.inflate(inflater, container, false)
		return requireBinding().root
		//return inflater.inflate(R.layout.main_fragment_profile_written_comment, container, false)
	}

	private fun requireBinding(): MainFragmentProfileWrittenCommentBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		//swipeRefreshLayout= view.findViewById(R.id.swipe_profile_written_comment_refresh)
		//recyclerView=view.findViewById(R.id.rv_profile_written_comment)

		requireBinding().rvProfileWrittenComment.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		requireBinding().rvProfileWrittenComment.setHasFixedSize(true)
		/*recyclerView.layoutManager = LinearLayoutManager(this.context,
				LinearLayoutManager.VERTICAL, false)
		recyclerView.setHasFixedSize(true)*/
		loadMyCommentList()
		detailRefreshEvnet()

	}

	private fun loadMyCommentList() {
		requestToServer.snsService.requestMyCommentPosts(
				"Bearer "+ App.prefs.local_login_token,
				QueryValue.lastId,
				QueryValue.limit
		).customEnqueue(
				onSuccess = {
					Log.d("Network", "내 포스트 정보 통신 성공")
					if (it.body()?.body.isNullOrEmpty()) {
						requireBinding().clMainProfileNoComment.visibility = View.VISIBLE
						requireBinding().rvProfileWrittenComment.visibility = View.GONE
						//cl_main_profile_no_comment.visibility = View.VISIBLE
						//rv_profile_written_comment.visibility=View.GONE
					}
					else
						requireBinding().rvProfileWrittenComment.visibility=View.VISIBLE
						//rv_profile_written_comment.visibility=View.VISIBLE
						requireBinding().rvProfileWrittenComment.adapter = HomeRecyclerViewAdapter(it.body()?.body!!)
						//recyclerView.adapter = HomeRecyclerViewAdapter(it.body()?.body!!)
				},
				onError = {
					Log.d("Network", "통신 에러")
				}
		)
	}
	private fun detailRefreshEvnet() {
		context?.RefreshEvent(requireBinding().swipeProfileWrittenCommentRefresh,true){ loadMyCommentList()}
		//context?.RefreshEvent(swipeRefreshLayout,true){ loadMyCommentList()}
	}
}