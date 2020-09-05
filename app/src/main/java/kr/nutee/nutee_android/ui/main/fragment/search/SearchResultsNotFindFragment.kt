package kr.nutee.nutee_android.ui.main.fragment.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.nutee.nutee_android.R
/*
* Created by 88yhtesrof
* DESC: 검색 결과 창:검색 결과 없음 Fragment
*/
class SearchResultsNotFindFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_search_results_not_find, container, false)
	}
}