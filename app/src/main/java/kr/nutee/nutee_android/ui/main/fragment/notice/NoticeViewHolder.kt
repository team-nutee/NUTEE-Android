package kr.nutee.nutee_android.ui.main.fragment.notice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R

class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	val tv_notice_text = itemView.findViewById<TextView>(R.id.tv_notice_text)
	val tv_notice_time = itemView.findViewById<TextView>(R.id.tv_notice_time)
	val tv_notice_school_department_name = itemView.findViewById<TextView>(R.id.tv_notice_school_department_name)


}