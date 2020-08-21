package kr.nutee.nutee_android.ui.main.fragment.search

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R

class SearchViewRecyclerAdapter(var previousSearchResultsList:ArrayList<String>)
	:RecyclerView.Adapter<SearchViewHolder>(){
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
		return SearchViewHolder(LayoutInflater.from(parent.context).inflate(
											R.layout.item_recyclerview_previous_search_results, parent, false))

	}

	override fun getItemCount(): Int {
		return previousSearchResultsList.size
	}

	override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

		//데이터와 뷰 묶기
		holder.bindWithView(previousSearchResultsList[position])


		//cancle 아이콘 클릭 이벤트
		holder.itemDelete.setOnClickListener {
			removeItemView(position)

		}
	}



	//이전 검색어 부분 삭제
	fun removeItemView(position: Int) {
		previousSearchResultsList.removeAt(position)
		notifyItemRemoved(position)
		notifyItemRangeChanged(position, previousSearchResultsList.size)


		/*
		질문:
		val sharedPreferences = getSharedPreferences("search", Context.MODE_PRIVATE)
		val editor=sharedPreferences.edit()
		editor.remove("search${position+1}")
		editor.apply()

		SharedPreferences 부분 삭제 기능을 추가하려고 위에 코드를 47번째 줄부터 넣고 싶은데,
		getSharedPreferences()가 Unresolved reference라고 떠서 remove(key)를 사용할 수 없다.
		따라서 왜 위의 코드가 안되는 지 궁금하고,
		더해서 SearchView 에서도 다른 방법으로 부분 삭제 기능을 추가하려고 했는데 잘 되지 않아
		다른 사람이라면 어떻게 부분 삭제 기능을 구현할 것인지 궁금하다.
		*/
	}
}
