package kr.nutee.nutee_android.ui.main.fragment.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter

/*
 * Created by 88yhtserof
 * DESC: 메인뷰 홈 전체 게시글 Fragment
 */

class FullPostFragment : Fragment() {

	private lateinit var recyclerView: RecyclerView
	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.home_full_post_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		recyclerView= view.findViewById(R.id.rv_main_home_full_post)
		recyclerView.apply {
			layoutManager= LinearLayoutManager(this.context,
				LinearLayoutManager.VERTICAL, false)
			setHasFixedSize(true)
		}
		loadFullList()
	}
	private fun loadFullList(){
		requestToServer.backService.requestCategoryList(
			//"Bearer "+ App.prefs.local_login_token,
			"Bearer "+ TestToken.testToken,
			TestToken.testCategory,
			QueryValue.lastId,
			QueryValue.limit
		).customEnqueue(
			onSuccess = {
				recyclerView.adapter = HomeRecyclerViewAdapter(it.body()?.body!!)
			},
			onError = {}
		)
	}
}