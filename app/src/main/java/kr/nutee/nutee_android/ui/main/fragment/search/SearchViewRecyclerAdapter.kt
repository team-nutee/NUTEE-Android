package kr.nutee.nutee_android.ui.main.fragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App

/*
 * Created by 88yhtesrof
 * DESC: 검색 창 RecyclerView Adapter
 */

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
		//전역 SharedPreference
		val prefsSearch=App.prefsSearch

		//제거될 아이템 위치부터 다시 SharedPreference 저장
		for(n in position downTo 0 ) {
			if(position>0){
				prefsSearch.setString(
				prefsSearch.KeyList.size-n,
				prefsSearch.getString(prefsSearch.KeyList.size-n+1))
			}
		}
		prefsSearch.remove(prefsSearch.KeyList.size)

		previousSearchResultsList.removeAt(position)
		notifyItemRemoved(position)
		notifyItemRangeChanged(position, previousSearchResultsList.size)
	}
}
