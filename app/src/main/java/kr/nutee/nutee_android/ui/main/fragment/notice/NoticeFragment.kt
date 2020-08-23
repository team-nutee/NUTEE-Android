package kr.nutee.nutee_android.ui.main.fragment.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.main_fragment_notice.*

import kr.nutee.nutee_android.R


class NoticeFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_fragment_notice, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		val tabTextList = arrayListOf("학사공지", "수업공지", "학점교류", "장학공지", "일반공지", "행사공지")

		vp_notice.adapter = NoticePagerAdapter(this)

		TabLayoutMediator(tab_notice, vp_notice) { tab, position ->
			tab.text = tabTextList[position]
		}.attach()

	}
}
