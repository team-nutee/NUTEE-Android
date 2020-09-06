package kr.nutee.nutee_android.ui.main.fragment.search


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recyclerview_previous_search_results.view.*

/*
 * Created by 88yhtesrof
 * DESC: 검색 창 RecyclerView Holder
 */

class SearchViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
	//item_recyclerview_previous_search_result.xml 뷰를 받는 Holder

	var itemContent: TextView = itemView.tv_search_recyclerview_item
	var itemDelete:ImageView=itemView.iv_search_delete

	fun bindWithView(item:String){
		itemContent.text=item

	}


}