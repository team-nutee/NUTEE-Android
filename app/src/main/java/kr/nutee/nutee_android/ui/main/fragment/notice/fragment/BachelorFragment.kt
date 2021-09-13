package kr.nutee.nutee_android.ui.main.fragment.notice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notice_fragment_bachelor.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.HomeFavoritePostFragmentBinding
import kr.nutee.nutee_android.databinding.NoticeFragmentBachelorBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeRecyclerAdapter

/*
 * Created by eunseo5355
 * DESC: 공지사항의 학사공지 Fragment
 * Created by 88yhtserof
 * DESC: 오류 수정
 */

class BachelorFragment : Fragment() {

	private var binding: NoticeFragmentBachelorBinding?= null

	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		//return inflater.inflate(R.layout.notice_fragment_bachelor, container, false)
		binding = NoticeFragmentBachelorBinding.inflate(inflater, container, false)
		return requireBinding().root
	}

	private fun requireBinding(): NoticeFragmentBachelorBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		requireBinding().rvNoticeBachelor.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		requireBinding().rvNoticeBachelor.setHasFixedSize(true)
		loadBachelor()
	}

	private fun loadBachelor() {
		requestToServer.noticeService.requestBachelor(
		).customEnqueue(
			onSuccess = {
				requireBinding().rvNoticeBachelor.adapter = NoticeRecyclerAdapter(this.requireContext(), it.body()!!.body)
			}
		)}
}
