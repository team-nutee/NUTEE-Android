package kr.nutee.nutee_android.ui.main.fragment.search


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_results_find.*
import kotlinx.android.synthetic.main.activity_search_view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.search.ResponseSearch
import kr.nutee.nutee_android.data.main.search.ResponseSearchMain
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchView : AppCompatActivity() {

	//데이터 배열 선언
	private var previousSearchResultsList = ArrayList<String>()
	private lateinit var searchViewRecyclerAdapter: SearchViewRecyclerAdapter

	//검색어 문자열 선언
	var searchBoxText: String=""

	//서버연결
	private val requestToServer = RequestToServer

	var lastId = 0
	var limit = 10


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_view)

		//전역 SharedPreference
		val prefsSearch = App.prefsSearch


		//이전 검색어 기록, 리스트에 저장(검색한 순서대로 저장되도록 key 값 확인)
		for (key in 1..prefsSearch.KeyList.size) {
			if (prefsSearch.contains(key)) {
				previousSearchResultsList.add(prefsSearch.getString(key))
			}
		}
		//최근 검색 기록이 상단에 뜨도록 역순으로 정렬
		previousSearchResultsList.reverse()


		//검색어 입력
		et_search_box.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
			if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == ACTION_DOWN) {
				//검색어
				val searchBoxText = et_search_box.text.toString()

				//검색어가 없는 경우
				if (searchBoxText.length == 0) {
					Toast.makeText(this, "검색어를 입력해 주세요", Toast.LENGTH_LONG).show()
				}
				//검색어가 있는 경우
				if (searchBoxText.length != 0) {
					//SharedPreferences에 검색어 저장
					prefsSearch.setString(prefsSearch.KeyList.size + 1, searchBoxText)

					//저장된 이전 검색어 받기
					val inputText = prefsSearch.getString(prefsSearch.KeyList.size + 1)

					//데이터 배열 준비: 이전 검색어 리스트에 추가
					previousSearchResultsList.add(inputText)

					val intentSearchBoxText=Intent(this@SearchView,SearchResultsFind::class.java)
					intentSearchBoxText.putExtra("searchBoxText",searchBoxText)
					//검색
					requestSearch(searchBoxText, lastId, limit,intentSearchBoxText)
				}
				return@OnKeyListener true
			}
			false
		})


		//어댑터 인스턴스 생성
		searchViewRecyclerAdapter = SearchViewRecyclerAdapter(previousSearchResultsList)
		rv_search_previous_search_results.apply {
			//RecyclerView 설정
			layoutManager =
				LinearLayoutManager(this@SearchView, LinearLayoutManager.VERTICAL, false)
			adapter = searchViewRecyclerAdapter
		}


		//'<' 뒤로 가기 버튼 기능
		img_search_back_btn.setOnClickListener {
			finish()
		}

		//'전체 삭제' 기능
		tv_search_all_history_delete.setOnClickListener {
			//이전 검색 결과 리스트 전체 삭제
			previousSearchResultsList.clear()
			//어댑터에서도 적용
			rv_search_previous_search_results.adapter?.notifyDataSetChanged()
			//SharesPreferece 목록에서 전체 삭제
			prefsSearch.AllDelete()
		}
	}

	fun requestSearch(txt: String, id: Int, limt: Int, intentSearchBoxText:Intent) {
		requestToServer.service.requestSearch(
			text = txt,
			lastId = id,
			limit = limt
		).customEnqueue { response ->
			if(response.body().isNullOrEmpty()){
				Log.d("testCheck", "확인emty")
				val intent= Intent(this,SearchResultsNotFind::class.java)
				startActivity(intent)
			}
			else{
				response.body()?.let {
				startActivity(intentSearchBoxText)
				Log.d("testCheck", "확인1")
				}
			}
		}
	}
}