package kr.nutee.nutee_android.ui.main.fragment.notice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notice_fragment_class.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.NoticeFragmentBachelorBinding
import kr.nutee.nutee_android.databinding.NoticeFragmentClassBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeRecyclerAdapter

/*
 * Created by eunseo5355
 * DESC: 공지사항의 수업공지 Fragment
 */

class ClassFragment : Fragment() {

	private var binding: NoticeFragmentClassBinding?= null

	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		//return inflater.inflate(R.layout.notice_fragment_class, container, false)
		binding = NoticeFragmentClassBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): NoticeFragmentClassBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		requireBinding().rvNoticeClass.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		requireBinding().rvNoticeClass.setHasFixedSize(true)

		loadClass()

	}

	private fun loadClass() {
		requestToServer.noticeService.requestClass(
		).customEnqueue (
				onSuccess = {
					requireBinding().rvNoticeClass.adapter = NoticeRecyclerAdapter(this.context!!, it.body()!!.body)
				})
			}
}
