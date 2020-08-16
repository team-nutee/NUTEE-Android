package kr.nutee.nutee_android.ui.main.fragment.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_view.*
import kotlinx.android.synthetic.main.item_recyclerview_previous_search_results.*
import kr.nutee.nutee_android.R

class SearchView : AppCompatActivity() {

	//데이터 배열 선언
	private var previousSearchResultsList=ArrayList<String>()
	private lateinit var searchViewRecyclerAdapter:SearchViewRecyclerAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_view)


		//데이터 배열 준비
		for(i in 1..20) {
			previousSearchResultsList.add("test1 $i")
		}

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
		}
	}
}