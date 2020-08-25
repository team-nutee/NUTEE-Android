package kr.nutee.nutee_android.ui.main.fragment.notice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notice_fragment_event.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.Notice
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeRecyclerAdapter

/*
 * Created by eunseo5355
 * DESC: 공지사항의 행사공지 Fragment
 */

class EventFragment : Fragment() {

	private val noticedatas = mutableListOf<Notice>()
	lateinit var noticeRecyclerAdapter: NoticeRecyclerAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.notice_fragment_event, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		rv_notice_event.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		rv_notice_event.setHasFixedSize(true)

		noticeRecyclerAdapter = NoticeRecyclerAdapter(view.context)

		noticeRecyclerAdapter.noticedatas = noticedatas
		noticeRecyclerAdapter.notifyDataSetChanged()

		rv_notice_event.adapter = noticeRecyclerAdapter

	}

}
