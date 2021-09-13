package kr.nutee.nutee_android.ui.main.fragment.notice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notice_fragment_general.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.NoticeFragmentExchangeBinding
import kr.nutee.nutee_android.databinding.NoticeFragmentGeneralBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeRecyclerAdapter

/*
 * Created by eunseo5355
 * DESC: 공지사항의 일반공지 Fragment
 */

class GeneralFragment : Fragment() {

	private var binding: NoticeFragmentGeneralBinding?= null

	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		//return inflater.inflate(R.layout.notice_fragment_general, container, false)
		binding = NoticeFragmentGeneralBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): NoticeFragmentGeneralBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		requireBinding().rvNoticeGeneral.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		requireBinding().rvNoticeGeneral.setHasFixedSize(true)

		loadGeneral()

	}

	private fun loadGeneral() {
		requestToServer.noticeService.requestGeneral(
		).customEnqueue(
				onSuccess = {
					requireBinding().rvNoticeGeneral.adapter = NoticeRecyclerAdapter(this.requireContext(), it.body()!!.body)
				}
		)}

}
