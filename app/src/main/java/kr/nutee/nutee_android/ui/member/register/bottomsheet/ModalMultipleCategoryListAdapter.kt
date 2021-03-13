package kr.nutee.nutee_android.ui.member.register.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.member.register.CategoryDomain
import kr.nutee.nutee_android.databinding.ItemModelListBinding

class ModalMultipleCategoryListAdapter : RecyclerView.Adapter<ModalMultipleCategoryListAdapter.ViewHolder>() {
    private val listData = mutableListOf<CategoryDomain>()

    fun addAllData(datas: List<String>) {
        listData.clear()
        val categoryDomainList = datas.map { CategoryDomain(it, false) }
        listData.addAll(categoryDomainList)
        notifyDataSetChanged()
    }

    fun getSelectedItemList(): List<String> = listData
        .filter { it.isSelected }
        .map { it.category }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemModelListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(
        private val binding: ItemModelListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CategoryDomain) {
            binding.itemModelListText.text = item.category
            itemView.setOnClickListener {
                item.isSelected = !item.isSelected
                binding.itemModelListText.setTextColor(
                    if (item.isSelected)
                        ContextCompat.getColor(binding.root.context, R.color.nuteeBase)
                    else
                        ContextCompat.getColor(binding.root.context, R.color.colorGray)
                )
            }
        }
    }
}
