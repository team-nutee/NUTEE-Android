package kr.nutee.nutee_android.ui.main.fragment.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_results_find.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.search.ResponseSearch
import kr.nutee.nutee_android.data.main.search.ResponseSearchMain

class SearchResultsFind : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_results_find)

		val itResponseSearchResults= intent.getSerializableExtra<ResponseSearchMain>("responseSearchResultsList")
		rv_search_results.apply {
			adapter = SearchResultsViewRecyclerAdapter(itResponseSearchResults as ArrayList<ResponseSearch>)
			layoutManager = LinearLayoutManager(
				context, LinearLayoutManager.VERTICAL, false
			)
		}
	}
}