package kr.nutee.nutee_android.ui.main.fragment.search

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
	}
}