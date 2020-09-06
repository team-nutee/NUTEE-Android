package kr.nutee.nutee_android.ui.main.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_results_find.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

/*
 * Created by 88yhtesrof
 * DESC: 검색 결과 창:검색 결과 있음 Fragment
 */

class SearchResultsFindFragment : Fragment() {

	//서버연결
	private val requestToServer = RequestToServer
	lateinit var searchBoxText:String
	var lastId = 0
	var limit = 10

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		//검색어
		searchBoxText=(activity as SearchResultsView).searchBoxText


	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_search_results_find, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		loadSesrch(searchBoxText, lastId, limit)
		rv_search_results.layoutManager= LinearLayoutManager(
			getActivity(), LinearLayoutManager.VERTICAL, false
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
			if(response.body().isNullOrEmpty()){
				(activity as SearchResultsView).setFrage(SearchResultsNotFindFragment())
			}
			else {
				response.body()?.let {
					rv_search_results.adapter = SearchResultsViewRecyclerAdapter(it)
				}
			}
		}
	}
}