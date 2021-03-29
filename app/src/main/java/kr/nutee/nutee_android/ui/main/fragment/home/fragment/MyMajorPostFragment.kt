package kr.nutee.nutee_android.ui.main.fragment.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.home_favorite_post_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.RefreshEvent
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter

/*
 * Created by 88yhtserof
 * DESC: 메인뷰 홈 내 전공 게시글 Fragment
 */

class MyMajorPostFragment : Fragment() {

	private lateinit var recyclerView: RecyclerView
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout
	val requestToServer = RequestToServer

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.home_my_major_post_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		swipeRefreshLayout= view.findViewById(R.id.swipe_home_my_major_post_refresh)
		recyclerView= view.findViewById(R.id.rv_main_home_my_major_post)

		setAdapter()
		loadFavoriteList()
		detailRefreshEvnet()
	}

	private fun setAdapter(){
		recyclerView.apply {
			layoutManager= LinearLayoutManager(this.context,
				LinearLayoutManager.VERTICAL, false)
			setHasFixedSize(true)
		}
	}

	private fun loadFavoriteList(){
		requestToServer.backService.requestMyMajorList(
				"Bearer "+ App.prefs.local_login_token,
			QueryValue.lastId,
			QueryValue.limit
		).customEnqueue(
			onSuccess = {
				if (it.body()?.body.isNullOrEmpty()) {
					cl_main_home_no_post.visibility = View.VISIBLE
				}
				else
					recyclerView.adapter = HomeRecyclerViewAdapter(it.body()?.body!!)
			},
			onError = {}
		)
	}

	private fun detailRefreshEvnet() {
		context?.RefreshEvent(swipeRefreshLayout,true){loadFavoriteList()}
	}
}