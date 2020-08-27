package kr.nutee.nutee_android.ui.main.fragment.notice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notice_fragment_general.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.Notice
import kr.nutee.nutee_android.data.main.home.NoticeItem
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeRecyclerAdapter

/*
 * Created by eunseo5355
 * DESC: 공지사항의 일반공지 Fragment
 */

class GeneralFragment : Fragment() {

	private var noticedatas = arrayListOf<NoticeItem>()
	lateinit var noticeRecyclerAdapter: NoticeRecyclerAdapter
	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.notice_fragment_general, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		rv_notice_general.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		rv_notice_general.setHasFixedSize(true)

		loadGeneral{
			noticedatas = it
			noticeRecyclerAdapter.noticedatas = noticedatas as Notice
			noticeRecyclerAdapter.notifyDataSetChanged()
			setAdapter(it)
		}


	}

	private fun loadGeneral(loadfun:(resBachelor: Notice)->Unit) {
		requestToServer.noticeService.requestGeneral(
		).customEnqueue { response ->
			response.body()?.let {
				loadfun(it)
			}
		}
	}

	private fun setAdapter(noticeItem: Notice){
		noticeRecyclerAdapter = NoticeRecyclerAdapter(this.context!!, noticeItem)
		rv_notice_general.adapter = noticeRecyclerAdapter
	}

}
