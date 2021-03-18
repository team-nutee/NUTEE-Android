package kr.nutee.nutee_android.ui.main.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_results_find.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ResponseMainBody
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter

/*
 * Created by 88yhtesrof
 * DESC: 검색 결과 창:검색 결과 있음 Fragment
 */

class SearchResultsFindFragment : Fragment() {

	lateinit var bodyList: Array<ResponseMainBody>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		bodyList=(activity as SearchResultsView).bodyList
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_search_results_find, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		//loadSesrch(searchBoxText, lastId, limit)
		rv_search_results.apply {
			layoutManager= LinearLayoutManager(
					activity, LinearLayoutManager.VERTICAL, false
			)
			adapter= HomeRecyclerViewAdapter(bodyList)
		}
	}
}