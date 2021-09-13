package kr.nutee.nutee_android.ui.main.fragment.notice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notice_fragment_scholarship.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.NoticeFragmentGeneralBinding
import kr.nutee.nutee_android.databinding.NoticeFragmentScholarshipBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeRecyclerAdapter

/*
 * Created by eunseo5355
 * DESC: 공지사항의 장학공지 Fragment
 */

class ScholarshipFragment : Fragment() {

	private var binding: NoticeFragmentScholarshipBinding?= null

	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		//return inflater.inflate(R.layout.notice_fragment_scholarship, container, false)
		binding = NoticeFragmentScholarshipBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): NoticeFragmentScholarshipBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		requireBinding().rvNoticeScholarship.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		requireBinding().rvNoticeScholarship.setHasFixedSize(true)

		loadScholarship()

	}

	private fun loadScholarship() {
		requestToServer.noticeService.requestScholarship(
		).customEnqueue(
				onSuccess = {
					requireBinding().rvNoticeScholarship.adapter = NoticeRecyclerAdapter(this.requireContext(), it.body()!!.body)
				}
		)}


}
