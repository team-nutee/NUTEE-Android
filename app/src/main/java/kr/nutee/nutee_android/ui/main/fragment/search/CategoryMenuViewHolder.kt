package kr.nutee.nutee_android.ui.main.fragment.search

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R

class CategoryMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val textviewCategory: TextView =itemView.findViewById(R.id.text_search_category_item)

    fun bind(categoryData: String) {
        textviewCategory.text=categoryData
    }
}