package kr.nutee.nutee_android.ui.main.fragment.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.nutee.nutee_android.R

/*
 * Created by 88yhtesrof
 * DESC: 검색 결과 창 : 검색 결과가 없는 경우 Activity
 */

class SearchResultsNotFind : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_results_not_find)
	}
}