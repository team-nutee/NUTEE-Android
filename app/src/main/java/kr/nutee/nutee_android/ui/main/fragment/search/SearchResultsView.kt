package kr.nutee.nutee_android.ui.main.fragment.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_search_results_view.*
import kr.nutee.nutee_android.R

/*
 * Created by 88yhtesrof
 * DESC: 검색 결과 창 Activity
 */

class SearchResultsView : AppCompatActivity() {
	lateinit var searchBoxText:String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_results_view)

		//검색어
		searchBoxText=intent.getStringExtra("searchBoxText")

		setFrage(SearchResultsFindFragment())

		//검색어 창 기능
		tv_search_results_find.apply {
			setOnClickListener{
				finish()
			}
			text = searchBoxText
		}
	}

	fun setFrage(name:Fragment){
		val fragmentManager=supportFragmentManager.beginTransaction()
		fragmentManager.apply {
			replace(R.id.fl_search_results_view,name)
			commit()
		}
	}

	override fun onBackPressed() {
		if(supportFragmentManager.backStackEntryCount > 0)
			supportFragmentManager.popBackStack()
		else
			super.onBackPressed()


	}
}