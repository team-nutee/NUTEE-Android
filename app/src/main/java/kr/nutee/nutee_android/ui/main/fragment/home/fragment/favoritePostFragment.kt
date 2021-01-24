package kr.nutee.nutee_android.ui.main.fragment.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter

/*
 * Created by 88yhtserof
 * DESC: 메인뷰 홈 추천 게시글 Fragment
 */

class favoritePostFragment : Fragment() {

	private lateinit var recyclerView:RecyclerView
	val requestToServer = RequestToServer
	var lastId = 0
	var limit = 10

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.home_suggested_post_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		recyclerView= view.findViewById(R.id.rv_main_home_suggested_post)
		loadFavoriteList()
		recyclerView.apply {
			layoutManager=LinearLayoutManager(this.context,
				LinearLayoutManager.VERTICAL, false)
			setHasFixedSize(true)
		}

	}

	private fun loadFavoriteList(){
		requestToServer.backService.requestFavoriteList(
			lastId, limit
		).customEnqueue (
			onSuccess = {
				recyclerView.adapter=HomeRecyclerViewAdapter(it.body()?.bodyList!!)},
			onError = {}
		)
	}
}