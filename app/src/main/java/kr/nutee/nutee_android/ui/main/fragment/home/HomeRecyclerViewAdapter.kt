package kr.nutee.nutee_android.ui.main.fragment.home

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ResponseMain


class HomeRecyclerViewAdapter(
	private val onLoadMoreListener: ONLoadMoreListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private var data: ResponseMain = ResponseMain()

	private var lastItemId = 0

	private var isMoreLoading = true

	override fun getItemViewType(position: Int): Int =
		if (data[position] != null) VIEW_ITEM else VIEW_PROG

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		if (viewType == VIEW_ITEM) {
			return HomeViewHolder(makeMainItemViewHolder(parent)!!)
		}
		return ProgressViewHolder(makeLoadingItemViewHolder(parent)!!)
	}

	private fun makeMainItemViewHolder(parent: ViewGroup): View? {
		val view = LayoutInflater.from(parent.context)
		return view.inflate(R.layout.main_list_item, parent, false)
	}

	private fun makeLoadingItemViewHolder(parent: ViewGroup): View? {
		val view = LayoutInflater.from(parent.context)
		return view.inflate(R.layout.item_progress_bar, parent, false)
	}

	fun showLoading() {
		if (isMoreLoading && !data.isNullOrEmpty() && onLoadMoreListener != null) {
			isMoreLoading = false
			val handler = Handler()
			val runnable:Runnable = Runnable {
				data.add(null)
				notifyItemInserted(data.lastIndex)
				onLoadMoreListener.onLoadMore()
			}
			handler.post(runnable)
		}
	}

	fun setMore(isMore: Boolean) {
		this.isMoreLoading = isMore
	}

	private fun dismissLoading() {
		if (!data.isNullOrEmpty()) {
			data.removeAt(data.indexOf(null))
			notifyItemRemoved(data.size)
		}
	}

	fun addAll(data: ResponseMain) {
		this.data.clear()
		this.data.addAll(data)
		this.lastItemId = this.data.last()?.id ?: 0
		notifyDataSetChanged()
	}

	fun addItemMore(data: ResponseMain) {
		val currentListSize = this.data.size
		this.data.addAll(data)
		this.lastItemId = this.data.last()?.id ?: lastItemId
		dismissLoading()
		notifyItemRangeChanged(currentListSize, this.data.size)
	}

	fun getLastItemId(): Int = lastItemId

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		if (holder is HomeViewHolder) {
			data[position]?.let { holder.bind(it) }
		}
	}

	override fun getItemCount(): Int = data.size

	inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	}


	companion object {
		private const val VIEW_ITEM = 1
		private const val VIEW_PROG = 0
	}
}

fun interface ONLoadMoreListener {
	fun onLoadMore()
}