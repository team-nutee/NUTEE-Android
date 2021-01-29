package kr.nutee.nutee_android.ui.main.fragment.home.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

	//val handler:Handler= Handler()
	lateinit var mainHandler:Handler

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

		mainHandler= Handler()
		recyclerView= view.findViewById(R.id.rv_main_home_suggested_post)
		recyclerView.apply {
			layoutManager=LinearLayoutManager(
				this.context,
				LinearLayoutManager.VERTICAL, false
			)
			setHasFixedSize(true)
		}
		loadFavoriteList()
	}

	private fun loadFavoriteList(){
		requestToServer.backService.requestFavoriteList(
			lastId, limit
		).customEnqueue(
			onSuccess = {
				Log.d("Network", "통신 성공.")
				recyclerView.adapter = HomeRecyclerViewAdapter(it.body()?.bodyList!!)
			}
			,
			onError = {
				Log.d("Network", "통신 에러")
			}
		)
	}

}