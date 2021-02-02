package kr.nutee.nutee_android.ui.main.fragment.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_search_results_view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.data.main.home.Body
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

/*
 * Created by 88yhtesrof
 * DESC: 검색 결과 창 Activity
 */

class SearchResultsView : AppCompatActivity() {
	lateinit var searchBoxText:String
	private val requestToServer = RequestToServer
	lateinit var bodyList: Array<Body>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_results_view)

		//검색어
		searchBoxText=intent.getStringExtra("searchBoxText")

		loadSesrch(searchBoxText)

		//검색어 창 기능
		tv_search_results_find.apply {
			setOnClickListener{ finish() }
			text = searchBoxText
		}
	}

	fun setFrag(searchResult: Boolean){
		val fragmentManager=supportFragmentManager.beginTransaction()

		fragmentManager.apply {
			if(searchResult) {
				replace(R.id.fl_search_results_view,
					SearchResultsFindFragment())
			}else
				replace(R.id.fl_search_results_view, SearchResultsNotFindFragment())
			commit()
		}
	}

	override fun onBackPressed() {
		if(supportFragmentManager.backStackEntryCount > 0)
			supportFragmentManager.popBackStack()
		else
			super.onBackPressed()
	}

	private fun loadSesrch(searchBoxText: String) {
		requestToServer.backService.requestSearch(
			searchBoxText,
			QueryValue.lastId,
			QueryValue.limit
		).customEnqueue(
			onSuccess = {
				bodyList=it.body()?.body!!
				if (it.body()?.body.isNullOrEmpty())
					setFrag(false)
				else
					setFrag(true)
			}
		)
	}
}
