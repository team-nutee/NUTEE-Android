package kr.nutee.nutee_android.ui.main.fragment.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_results_view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.search.ResponseSearch
import kr.nutee.nutee_android.data.main.search.ResponseSearchMain
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue



class SearchResultsView : AppCompatActivity() {

	//서버연결
	private val requestToServer = RequestToServer

	var lastId = 0
	var limit = 10

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_results_view)

		val searchBoxText = intent.getStringExtra("searchBoxText")

		//서버 연결
		requestSearch(searchBoxText, lastId, limit)
	}



	private fun requestSearch(txt: String?, id: Int, limt: Int) {
		Log.d("testCall", "호출성공")
		requestToServer.service.requestSearch(
			txt,id, limt
		).customEnqueue {
				response ->
			response.body()?.let {
				rv_search_results.apply {
					layoutManager =
						LinearLayoutManager(
							this@SearchResultsView, LinearLayoutManager.VERTICAL, false)
					adapter = SearchResultsViewRecyclerAdapter(it)
				}
				Log.d("testCall", "성공")
			}
		}
	}
}