package kr.nutee.nutee_android.ui.main.fragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.ItemModelListBinding
import kr.nutee.nutee_android.databinding.SearchCategoryItemBinding

class CategoryMenuRecyclerViewAdapter(private val categoryList: List<String>)
    : RecyclerView.Adapter<CategoryMenuViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMenuViewHolder {
        //val view=LayoutInflater.from(parent.context).inflate(R.layout.search_category_item,parent,false)
        //return CategoryMenuViewHolder(view)
        val binding = SearchCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return CategoryMenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryMenuViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}