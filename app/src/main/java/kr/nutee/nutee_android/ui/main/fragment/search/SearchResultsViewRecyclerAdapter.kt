package kr.nutee.nutee_android.ui.main.fragment.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.search.ResponseSearchMain
import kr.nutee.nutee_android.ui.main.fragment.home.detail.HomeDetailActivity

/*import kr.nutee.nutee_android.ui.main.fragment.home.detail.HomeDetailFragment*/

/*
 * Created by 88yhtesrof
 * DESC: 검색 결과 창 : 검색 결과가 있는 경우 RecyclerView Adapter
 */

class SearchResultsViewRecyclerAdapter(private var data: ResponseSearchMain)
	: RecyclerView.Adapter<SearchResultsViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
		return SearchResultsViewHolder(
			LayoutInflater.from(parent.context).inflate(
				R.layout.main_list_item, parent, false
			)
		)
	}

	override fun getItemCount(): Int {
		return data.size
	}

	override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
		holder.bindWithView(data[position])

		val holderItemView = holder.itemView
		holderItemView.setOnClickListener() {
			val gotoDetailIntent = Intent(holderItemView.context, HomeDetailActivity::class.java)
			gotoDetailIntent.putExtra("Detail_id", data[position].id)
			holderItemView.context.startActivity(gotoDetailIntent)
		}
	}

}