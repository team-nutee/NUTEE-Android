package kr.nutee.nutee_android.ui.main.fragment.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_results_find.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue


/*
 * Created by 88yhtesrof
 * DESC: 검색 결과 창 : 검색 결과가 있는 경우 Activity
 */

class SearchResultsFind : AppCompatActivity() {
	//서버연결
	private val requestToServer = RequestToServer
	lateinit var searchBoxText:String
	var lastId = 0
	var limit = 10
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_results_find)

		searchBoxText=intent.getStringExtra("searchBoxText")

		loadSesrch(searchBoxText, lastId, limit)
		rv_search_results.layoutManager= LinearLayoutManager(
			this, LinearLayoutManager.VERTICAL, false
		)
	}
	private fun loadSesrch(
		searchBoxText: String, lastId: Int, limit: Int
	) {
		requestToServer.service.requestSearch(
			text = searchBoxText,
			lastId = lastId,
			limit = limit
		).customEnqueue { response ->
			response.body()?.let {
				rv_search_results.adapter = SearchResultsViewRecyclerAdapter(it)
				}
			}
		}
}
