package kr.nutee.nutee_android.ui.member.register.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.databinding.ItemModelListBinding

class ModalMyMajorsListAdapter(
        private val itemClickListener: ((String) -> Unit)? = null,
        private val itemClickEndEvent: () -> Unit
) : RecyclerView.Adapter<ModalMyMajorsListAdapter.ViewHolder>() {
    private val listData = mutableListOf<String>()

    fun addAllData(datas: List<String>) {
        listData.clear()
        listData.addAll(datas)
        notifyDataSetChanged()
    }

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
        fun onBind(item: String) {
            binding.itemModelListText.text = item
            itemView.setOnClickListener {
                itemClickListener?.invoke(item)
                itemClickEndEvent.invoke()
            }
        }
    }
}