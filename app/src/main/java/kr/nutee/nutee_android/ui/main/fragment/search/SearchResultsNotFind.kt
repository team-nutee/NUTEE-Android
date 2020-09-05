package kr.nutee.nutee_android.ui.main.fragment.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search_results_find.*
import kotlinx.android.synthetic.main.activity_search_results_not_find.*
import kotlinx.android.synthetic.main.activity_search_view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.main.MainActivity

/*
 * Created by 88yhtesrof
 * DESC: 검색 결과 창 : 검색 결과가 없는 경우 Activity
 */

class SearchResultsNotFind : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_results_not_find)

		//검색어 창 기능
		tv_search_results_not_find.setOnClickListener{
			finish()
		}
	}
}