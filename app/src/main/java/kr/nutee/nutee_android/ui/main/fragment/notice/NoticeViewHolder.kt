package kr.nutee.nutee_android.ui.main.fragment.notice

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.NoticeItem
import kr.nutee.nutee_android.databinding.NoticeItemBinding

/*
 * Created by eunseo5355
 * DESC: 공지사항의 ViewHolder
 */

class NoticeViewHolder(private val binding: NoticeItemBinding) : RecyclerView.ViewHolder(binding.root) {

	/*private val tv_notice_title: TextView = itemView.findViewById(R.id.tv_notice_title)
	private val tv_notice_date: TextView = itemView.findViewById(R.id.tv_notice_date)
	private val tv_notice_author: TextView = itemView.findViewById(R.id.tv_notice_author)
	private val img_notice_priority_high: ImageView = itemView.findViewById(R.id.img_notice_priority_high)*/

	fun bind(noticeData: NoticeItem) {
		with(binding){
			tvNoticeTitle.text = noticeData.title
			tvNoticeDate.text = noticeData.date
			tvNoticeAuthor.text = noticeData.author
		}
		/*tv_notice_title.text = noticeData.title
		tv_notice_date.text = noticeData.date
		tv_notice_author.text = noticeData.author*/

		if(noticeData.no == "공지")
			//img_notice_priority_high.visibility = View.VISIBLE
			binding.imgNoticePriorityHigh.visibility = View.VISIBLE
	}

}