package kr.nutee.nutee_android.ui.main.fragment.notice

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.NoticeItem

/*
 * Created by eunseo5355
 * DESC: 공지사항의 ViewHolder
 */

class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	private val tv_notice_title: TextView = itemView.findViewById(R.id.tv_notice_title)
	private val tv_notice_date: TextView = itemView.findViewById(R.id.tv_notice_date)
	private val tv_notice_author: TextView = itemView.findViewById(R.id.tv_notice_author)
	private val img_notice_priority_high: ImageView = itemView.findViewById(R.id.img_notice_priority_high)

	fun bind(noticeData: NoticeItem) {
		tv_notice_title.text = noticeData.title
		tv_notice_date.text = noticeData.date
		tv_notice_author.text = noticeData.author

		if(noticeData.no == "공지")
			img_notice_priority_high.visibility = View.VISIBLE
	}

}