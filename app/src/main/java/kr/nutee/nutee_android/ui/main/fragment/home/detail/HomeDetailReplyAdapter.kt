package kr.nutee.nutee_android.ui.main.fragment.home.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ReComment

class HomeDetailReplyAdapter(
	val context: Context,
	var recommentList: Array<ReComment>,
	val postId: Int?,
	val commentId: Int?
)
	: RecyclerView.Adapter<HomeDetailReplyViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeDetailReplyViewHolder {
		val view=LayoutInflater.from(context).inflate(R.layout.main_home_detail_item,parent,false)
		return HomeDetailReplyViewHolder(view)
	}

	override fun getItemCount(): Int {
		return recommentList.size
	}

	override fun onBindViewHolder(holder: HomeDetailReplyViewHolder, position: Int) {
		holder.bindWithView(recommentList[position],context,postId,commentId)
	}

}
