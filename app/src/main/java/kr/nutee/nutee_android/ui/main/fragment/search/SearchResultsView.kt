package kr.nutee.nutee_android.ui.main.fragment.search

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.search_results_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.data.TestToken
import kr.nutee.nutee_android.data.main.home.Body
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

/*
 * Created by 88yhtesrof
 * DESC: 검색 결과 창 Activity
 */

class SearchResultsView : FragmentActivity() {
	lateinit var searchBoxText:String
	private val requestToServer = RequestToServer
	lateinit var bodyList: Array<Body>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.search_results_activity)

		loadSearchView()

		//검색어 창 기능
		tv_searchBoxText.apply {
			setOnClickListener{ finish() }
			text = searchBoxText
		}
		img_search_back_btn.setOnClickListener {
			onBackPressed()
		}
	}

	private fun loadSearchView(){
		if(intent.hasExtra("categorySearch")){
			searchBoxText=intent.getStringExtra("categorySearch")!!
			requestToServer.backService.requestCategoryList(
					"Bearer "+TestToken.testToken,
					//"Bearer "+App.prefs.local_login_token,
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
			return
		}
		searchBoxText=intent.getStringExtra("searchBoxText")!!
		loadSesrch(searchBoxText)
	}

	private fun setFrag(searchResult: Boolean){
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
			"Bearer "+ TestToken.testToken,
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
