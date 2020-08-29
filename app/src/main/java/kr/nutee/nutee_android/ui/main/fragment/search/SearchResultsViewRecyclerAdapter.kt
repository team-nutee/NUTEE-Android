package kr.nutee.nutee_android.ui.main.fragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.search.ResponseSearch
import kr.nutee.nutee_android.data.main.search.ResponseSearchMain

class SearchResultsViewRecyclerAdapter(private var contentArrayList: ArrayList<ResponseSearch>)
	: RecyclerView.Adapter<SearchResultsViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
		return SearchResultsViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.main_list_item, parent, false
			)
		)

	}

	override fun getItemCount(): Int {
		return contentArrayList.size
	}

	override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
		holder.bindWithView(contentArrayList[position])
	}
}