package kr.nutee.nutee_android.ui.main.fragment.search

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_view.*
import kotlinx.android.synthetic.main.item_recyclerview_previous_search_results.*
import kr.nutee.nutee_android.R

class SearchView : AppCompatActivity() {

	//데이터 배열 선언
	private var previousSearchResultsList=ArrayList<String>()
	private lateinit var searchViewRecyclerAdapter:SearchViewRecyclerAdapter
	lateinit var searchBoxText:String
	lateinit var sharedPreferences:SharedPreferences

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_view)

		// SharedPreferences 객체 얻기
		sharedPreferences = getSharedPreferences("search", Context.MODE_PRIVATE)
		val editor = sharedPreferences.edit()


		//검색어 입력
		et_search_box.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
			if (keyCode == KeyEvent.KEYCODE_ENTER) {

				searchBoxText=et_search_box.text.toString()

				//검색어가 없는 경우
				if (searchBoxText.length == 0) {
					Toast.makeText(this, "검색어를 입력해 주세요", Toast.LENGTH_LONG).show()
				}
				//검색어가 있는 경우
				if (searchBoxText.length != 0) {

					//SharedPreferences에 검색어 저장
					editor.putString("search${previousSearchResultsList.size}", searchBoxText)
					editor.commit()

					//저장된 이전 검색어 받기
					val inputText=sharedPreferences.getString("search${previousSearchResultsList.size}","")

					//데이터 배열 준비: 이전 검색어 리스트에 추가
					previousSearchResultsList.add(inputText.toString())

				}

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



		//'<' 버튼 기능
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