package kr.nutee.nutee_android.ui.member.register.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.databinding.ItemCategorySelectedBinding

class SelectedCategoryAdapter : RecyclerView.Adapter<SelectedCategoryAdapter.ViewHolder>() {
    private val selectedCategoryList = mutableListOf<String>()

    fun addAllData(list: List<String>) {
        selectedCategoryList.addAll(list)
        val newSelectedList = selectedCategoryList.toSet()
        selectedCategoryList.clear()
        selectedCategoryList.addAll(newSelectedList)
        notifyDataSetChanged()
    }

    fun hasSelectedCategory(): Boolean = selectedCategoryList.isNotEmpty()

    fun getSelectedCategory(): List<String> = selectedCategoryList

    private fun deleteItem(item: String) {
        val index = selectedCategoryList.indexOf(item)
        selectedCategoryList.removeAt(index)
        notifyItemRemoved(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemCategorySelectedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let { ViewHolder(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(selectedCategoryList[position])
    }

    override fun getItemCount(): Int = selectedCategoryList.size

    inner class ViewHolder(
        private val binding: ItemCategorySelectedBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: String) {
            binding.textCategorytItem.text = item
            binding.itemDelButton.setOnClickListener {
                deleteItem(item)
            }
        }
    }
}
