package kr.nutee.nutee_android.ui.main.fragment.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.search.ResponseSearch
import kr.nutee.nutee_android.data.main.search.ResponseSearchMain

class SearchResultsViewRecyclerAdapter(private var data: ResponseSearchMain)
	: RecyclerView.Adapter<SearchResultsViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
		return SearchResultsViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.main_list_item, parent, false
			)
		)
		Log.d("testCheck","확인6")

	}

	override fun getItemCount(): Int {
		return data.size
	}

	override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
		Log.d("testCheck","확인4")
		holder.bindWithView(data[position])
		Log.d("testCheck","확인5")
	}
}