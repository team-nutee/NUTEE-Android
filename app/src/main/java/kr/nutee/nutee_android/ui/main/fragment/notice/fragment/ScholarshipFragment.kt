package kr.nutee.nutee_android.ui.main.fragment.notice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notice_fragment_scholarship.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.Notice
import kr.nutee.nutee_android.data.main.home.NoticeItem
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeRecyclerAdapter

/*
 * Created by eunseo5355
 * DESC: 공지사항의 장학공지 Fragment
 */

class ScholarshipFragment : Fragment() {

	private var noticedatas = arrayListOf<NoticeItem>()
	lateinit var noticeRecyclerAdapter: NoticeRecyclerAdapter
	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.notice_fragment_scholarship, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		rv_notice_scholarship.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		rv_notice_scholarship.setHasFixedSize(true)

		loadScholarship {
			noticedatas = it
			noticeRecyclerAdapter.noticedatas = noticedatas as Notice
			setAdapter(it)
			noticeRecyclerAdapter.notifyDataSetChanged()
		}


	}

	private fun loadScholarship(loadfun:(resBachelor: Notice)->Unit) {
		requestToServer.noticeService.requestScholarship(
		).customEnqueue { response ->
			response.body()?.let {
				loadfun(it)
			}
		}
	}

	private fun setAdapter(noticeItem: Notice){
		noticeRecyclerAdapter = NoticeRecyclerAdapter(this.context!!, noticeItem)
		rv_notice_scholarship.adapter = noticeRecyclerAdapter
	}

}
