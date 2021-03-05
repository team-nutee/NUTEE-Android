package kr.nutee.nutee_android.ui.main.fragment.home.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.main_home_detail_activtiy.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.RefreshEvent
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter

/*
 * Created by 88yhtserof
 * DESC: 메인뷰 홈 전체 게시글 Fragment
 */

class FullPostFragment : Fragment() {

	private lateinit var recyclerView: RecyclerView
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout
	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.home_full_post_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		swipeRefreshLayout= view.findViewById(R.id.swipe_home_full_post_refresh)
		recyclerView= view.findViewById(R.id.rv_main_home_full_post)

		setAdapter()
		loadFullList()
		detailRefreshEvnet()
	}

	private fun setAdapter(){
		recyclerView.apply {
			layoutManager= LinearLayoutManager(this.context,
				LinearLayoutManager.VERTICAL, false)
			setHasFixedSize(true)
		}
	}
	private fun loadFullList(){
		requestToServer.backService.requestFullList(
			//"Bearer "+ App.prefs.local_login_token,
			"Bearer "+ TestToken.testToken,
			QueryValue.lastId,
			QueryValue.limit
		).customEnqueue(
			onSuccess = {
				Log.d("Network", "전체 게시글 통신 성공")
				recyclerView.adapter = HomeRecyclerViewAdapter(it.body()?.body!!)
			},
			onError = {Log.d("Network", "즐겨찾기 게시글 통신 실패")}
		)
	}

	private fun detailRefreshEvnet() {
		context?.RefreshEvent(swipeRefreshLayout,true){ loadFullList()}
	}
}