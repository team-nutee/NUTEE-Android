package kr.nutee.nutee_android.ui.main.fragment.search

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

class CategoryMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val requestToServer=RequestToServer

    private val textviewCategory: TextView =itemView.findViewById(R.id.text_search_category_item)

    fun bind(categoryData: String) {
        textviewCategory.text=categoryData

        itemView.setOnClickListener { clickItemViewEvent(categoryData) }
    }

    private fun clickItemViewEvent(categoryData: String) {
        val intent = Intent(itemView.context, SearchResultsView::class.java)
        intent.putExtra("categorySearch",categoryData) //카테고리 또는 전공 이름
        itemView.context.startActivity(intent)
    }
}