package kr.nutee.nutee_android.ui.main.fragment.search


import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

/*
 * Created by 88yhtesrof
 * DESC: 검색 창 Activity 2.0 버전
 */
//FIXME 이전 검색어 기록 기능 추후에 수정 예정

class SearchView : AppCompatActivity() {

	var requestToServer= RequestToServer

	lateinit var searchBackView:ImageView
	lateinit var searchBoxView:TextView
	lateinit var categoryRecyclerView:RecyclerView
	lateinit var majorRecyclerView:RecyclerView

	lateinit var searchBoxText:String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.search_activity)

		searchBackView=findViewById(R.id.img_search_back_btn)
		searchBoxView=findViewById(R.id.et_search_box)
		categoryRecyclerView=findViewById(R.id.rv_search_category_list)
		majorRecyclerView=findViewById(R.id.rv_search_major_list)

		init()
	}

	private fun init() {
		loadCategoryMenu()
		loadMajorMenu()

		searchEventListener()
		searchBackView.setOnClickListener {
			finish()
		}
	}

	private fun searchEventListener() {
		searchBoxView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
			if (keyCode == KeyEvent.KEYCODE_ENTER
				&& event.action == ACTION_DOWN) {
				searchBoxText = searchBoxView.text.toString()

				if (searchBoxText.isEmpty()) {
					Toast.makeText(this, "검색어를 입력해 주세요!", Toast.LENGTH_LONG).show()
				}
				else{
					val intentSearchResults= Intent(this,SearchResultsView::class.java)
					intentSearchResults.putExtra("searchBoxText", searchBoxText)
					startActivity(intentSearchResults)
				}
				return@OnKeyListener true
			}
			false
		})
	}

	private fun loadMajorMenu() {
		requestToServer.backService.getMajors()
			.customEnqueue(
				onSuccess = {
					majorRecyclerView.adapter=CategoryMenuRecyclerViewAdapter(it.body()!!.body)
				}
			)
	}

	private fun loadCategoryMenu() {
		requestToServer.backService.getCategory()
			.customEnqueue(
				onSuccess = {
					categoryRecyclerView.adapter=CategoryMenuRecyclerViewAdapter(it.body()!!.body)
				}
			)
	}

//FIXME 이전 검색어 저장 기능- 추후 리팩토링 예정

//	private var previousSearchResultsList = ArrayList<String>()
//	private lateinit var searchViewRecyclerAdapter: SearchViewRecyclerAdapter
//	lateinit var searchBoxText:String

//	override fun onCreate(savedInstanceState: Bundle?) {
//		super.onCreate(savedInstanceState)
//		setContentView(R.layout.search_activity)
//
//		val prefsSearch = App.prefsSearch
//		//이전 검색어 기록, 리스트에 저장(검색한 순서대로 저장되도록 key 값 확인)
//		for (key in 1..prefsSearch.KeyList.size) {
//			if (prefsSearch.contains(key)) {
//				previousSearchResultsList.add(prefsSearch.getString(key))
//			}
//		}
//
//
//		//검색어 입력
//		et_search_box.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//			if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == ACTION_DOWN) {
//				searchBoxText = et_search_box.text.toString()
//
//				if (searchBoxText.length == 0) {
//					Toast.makeText(this, "검색어를 입력해 주세요!", Toast.LENGTH_LONG).show()
//				}
//				else{
//					prefsSearch.setString(prefsSearch.KeyList.size + 1, searchBoxText)
//					previousSearchResultsList.add(
//						prefsSearch.getString(prefsSearch.KeyList.size + 1))
//
//					val intentSearchResults=Intent(this@SearchView,SearchResultsView::class.java)
//					intentSearchResults.putExtra("searchBoxText", searchBoxText)
//					startActivity(intentSearchResults)
//				}
//				return@OnKeyListener true
//			}
//			false
//		})
//
//		searchViewRecyclerAdapter = SearchViewRecyclerAdapter(previousSearchResultsList)
//		rv_search_previous_search_results.apply {
////			layoutManager =
////				LinearLayoutManager(this@SearchView, LinearLayoutManager.VERTICAL, false)
//			adapter = searchViewRecyclerAdapter
//		}
//
//		img_search_back_btn.setOnClickListener {
//			finish()
//		}
//
//		tv_search_all_history_delete.setOnClickListener {
//			previousSearchResultsList.clear()
//			rv_search_previous_search_results.adapter?.notifyDataSetChanged()
//			prefsSearch.AllDelete()
//		}
//	}
}