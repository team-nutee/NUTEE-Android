package kr.nutee.nutee_android.ui.main.fragment.profile.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_fragment_profile_written_comment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter

class MyCommentFragment: Fragment() {

	private lateinit var recyclerView: RecyclerView
	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_fragment_profile_written_comment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		recyclerView=view.findViewById(R.id.rv_profile_written_comment)

		recyclerView.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		recyclerView.setHasFixedSize(true)
		loadMyCommentList()

	}

	private fun loadMyCommentList() {
		requestToServer.backService.requestMyCommentPosts(
				"Bearer "+ App.prefs.local_login_token,
				QueryValue.lastId,
				QueryValue.limit
		).customEnqueue(
				onSuccess = {
					Log.d("Network", "내 포스트 정보 통신 성공")
					if (it.body()?.body.isNullOrEmpty()) {
						cl_main_profile_no_comment.visibility = View.VISIBLE
					}
					else
						recyclerView.adapter = HomeRecyclerViewAdapter(it.body()?.body!!)
				},
				onError = {
					Log.d("Network", "통신 에러")
				}
		)
	}
}