package kr.nutee.nutee_android.ui.main.fragment.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_results_find.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.search.ResponseSearchMain
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import java.lang.Exception

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

		loadSesrch(searchBoxText,lastId,limit){}
		rv_search_results.layoutManager= LinearLayoutManager(
			this, LinearLayoutManager.VERTICAL, false
		)
	}
	private fun loadSesrch(searchBoxText: String, lastId: Int, limit: Int,
						   loadfun:(resBachelor: ResponseSearchMain)->Unit) {
		requestToServer.service.requestSearch(
			text = searchBoxText,
			lastId = lastId,
			limit = limit
		).customEnqueue {response ->
			response.body()?.let {
				Log.d("testCheck","확인2")
				rv_search_results.adapter = SearchResultsViewRecyclerAdapter(it)
				Log.d("testCheck","확인3")
//				if(){
	//				Log.d("testCheck","확인8")
//					val intent= Intent(this,SearchResultsNotFind::class.java)
//					startActivity(intent)
//				}
//				try {
//					rv_search_results.adapter = SearchResultsViewRecyclerAdapter(it)
//				}catch (e:Exception){
//					val intent= Intent(this,SearchResultsNotFind::class.java)
//					startActivity(intent)
				}
			}
		}
	}