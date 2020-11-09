/*
package kr.nutee.nutee_android.ui.main.fragment.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ResponseMain

class HomeRecyclerViewAdapter(
	private val onLoadMoreListener: ONLoadMoreListener
): RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder>() {

    var data: MutableList<ResponseMain> = mutableListOf()

	private var isMoreLoading = true

	override fun getItemViewType(position: Int): Int = if(data.get(position) != null) VIEW_ITEM else VIEW_PROG

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		when (viewType) {
			VIEW_ITEM -> {}
			VIEW_PROG -> {}
		}
        val view = LayoutInflater.from(parent.context)
        val itemView = view.inflate(R.layout.main_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(customData: ResponseMain) {

        }

    }

	fun interface ONLoadMoreListener {
		fun onLoadMore()
	}

	inner class 



	companion object{
		private const val VIEW_ITEM = 1
		private const val VIEW_PROG = 0
	}
}*/
