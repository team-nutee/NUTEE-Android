package kr.nutee.nutee_android.ui.main.fragment.notice

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.NoticeItem

/*
 * Created by eunseo5355
 * DESC: 공지사항의 ViewHolder
 */

class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	val tv_notice_title = itemView.findViewById<TextView>(R.id.tv_notice_title)
	val tv_notice_date = itemView.findViewById<TextView>(R.id.tv_notice_date)
	val tv_notice_author = itemView.findViewById<TextView>(R.id.tv_notice_author)

	fun bind(noticeData: NoticeItem) {
		tv_notice_title.text = noticeData.title
		tv_notice_date.text = noticeData.date
		tv_notice_author.text = noticeData.author
	}

}