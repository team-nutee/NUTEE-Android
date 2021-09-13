package kr.nutee.nutee_android.ui.main.fragment.notice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notice_fragment_event.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.NoticeFragmentClassBinding
import kr.nutee.nutee_android.databinding.NoticeFragmentEventBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeRecyclerAdapter

/*
 * Created by eunseo5355
 * DESC: 공지사항의 행사공지 Fragment
 */

class EventFragment : Fragment() {

	private var binding: NoticeFragmentEventBinding?= null

	val requestToServer = RequestToServer

	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		//return inflater.inflate(R.layout.notice_fragment_event, container, false)
		binding = NoticeFragmentEventBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): NoticeFragmentEventBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		requireBinding().rvNoticeEvent.layoutManager = LinearLayoutManager(this.context,
				LinearLayoutManager.VERTICAL, false)
		requireBinding().rvNoticeEvent.setHasFixedSize(true)

		loadEvent()

	}

	private fun loadEvent() {
		requestToServer.noticeService.requestEvent(
		).customEnqueue(
				onSuccess = {
					requireBinding().rvNoticeEvent.adapter = NoticeRecyclerAdapter(this.requireContext(), it.body()!!.body)
				}
		)
	}
}
