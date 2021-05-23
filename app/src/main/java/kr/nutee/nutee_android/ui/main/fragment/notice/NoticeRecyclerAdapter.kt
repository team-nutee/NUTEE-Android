package kr.nutee.nutee_android.ui.main.fragment.notice

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.NoticeItem

/*
 * Created by eunseo5355
 * DESC: 공지사항의 RecyclerView Adapter
 */

class NoticeRecyclerAdapter(private val context: Context, private var noticedatas: Array<NoticeItem>): RecyclerView.Adapter<NoticeViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
		val view = LayoutInflater.from(context).inflate(R.layout.notice_item, parent, false)
		return NoticeViewHolder(view)
	}

	override fun getItemCount(): Int = noticedatas.size

	override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
		holder.bind(noticedatas[position])

		holder.itemView.setOnClickListener {
			//안드로이드 웹브라우저앱을 이용하여 링크 열기
			val openURL = Intent(Intent.ACTION_VIEW)
			openURL.data = Uri.parse(noticedatas[position].href)
			startActivity(context, openURL, null)
		}

	}

}