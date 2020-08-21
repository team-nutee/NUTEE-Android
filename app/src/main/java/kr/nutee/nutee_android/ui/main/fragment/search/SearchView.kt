package kr.nutee.nutee_android.ui.main.fragment.search

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_view.*
import kr.nutee.nutee_android.R

import kotlin.collections.ArrayList

class SearchView : AppCompatActivity() {

	//데이터 배열 선언
	private var previousSearchResultsList=ArrayList<String>()
	private lateinit var searchViewRecyclerAdapter:SearchViewRecyclerAdapter
	//검색어 문자열 선언
	lateinit var searchBoxText:String
	// SharedPreferences 객체 선언
	lateinit var sharedPreferences:SharedPreferences


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_view)

		// SharedPreferences 객체 얻기
		sharedPreferences = getSharedPreferences("search", Context.MODE_PRIVATE)
		val editor = sharedPreferences.edit()


		//sharedPreferences에 저장된 key 리스트
		val test: List<String> =sharedPreferences.all.map { it.key }
		Log.d("리스트 확인", test.toString())


		//이전 검색어 기록, 리스트에 저장(검색한 순서대로 저장되도록 key 값 확인)
		for(key in 1.. test.size){
			if(sharedPreferences.contains("search$key")){
				sharedPreferences.getString("search$key","")?.let { previousSearchResultsList.add(it) }
			}
		}
		//최근 검색 기록이 상단에 뜨도록 역순으로 정렬
		previousSearchResultsList.reverse()



		//검색어 입력
		et_search_box.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
			if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == ACTION_DOWN) {

					searchBoxText=et_search_box.text.toString()

					//검색어가 없는 경우
					if (searchBoxText.length == 0) {
						Toast.makeText(this, "검색어를 입력해 주세요", Toast.LENGTH_LONG).show()
					}
					//검색어가 있는 경우
					if (searchBoxText.length != 0) {

						//SharedPreferences에 검색어 저장
						editor.putString("search${previousSearchResultsList.size+1}", searchBoxText)
						editor.apply()

						//저장된 이전 검색어 받기
						val inputText=sharedPreferences.getString("search${previousSearchResultsList.size+1}","")

						//데이터 배열 준비: 이전 검색어 리스트에 추가
						previousSearchResultsList.add(inputText.toString())
					}

					//sharedPreferences 목록 확인을 위한 Log
					Log.d("확인",sharedPreferences.all.toString())
					return@OnKeyListener true
			}
			false
		})


		//어댑터 인스턴스 생성
		searchViewRecyclerAdapter=SearchViewRecyclerAdapter(previousSearchResultsList)

		rv_search_previous_search_results.apply{
			//RecyclerView 설정
			layoutManager=LinearLayoutManager(this@SearchView, LinearLayoutManager.VERTICAL, false)
			adapter=searchViewRecyclerAdapter
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

			editor.clear()
			editor.commit()
		}

	}

}