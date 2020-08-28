package kr.nutee.nutee_android.ui.main.fragment.notice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.notice_fragment_exchange.*

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.Notice
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.main.fragment.notice.NoticeRecyclerAdapter

/*
 * Created by eunseo5355
 * DESC: 공지사항의 학점교류 Fragment
 */

class ExchangeFragment : Fragment() {

	val requestToServer = RequestToServer

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.notice_fragment_exchange, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		rv_notice_exchange.layoutManager = LinearLayoutManager(this.context,
			LinearLayoutManager.VERTICAL, false)
		rv_notice_exchange.setHasFixedSize(true)

		loadExchange{}

	}

	private fun loadExchange(loadfun:(resBachelor: Notice)->Unit) {
		requestToServer.noticeService.requestExchange(
		).customEnqueue { response ->
			response.body()?.let {
				rv_notice_exchange.adapter = NoticeRecyclerAdapter(this.context!!, it)
			}
		}
	}

}
