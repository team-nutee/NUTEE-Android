package kr.nutee.nutee_android.ui.main.fragment.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.main_fragment_notice.*
import kotlinx.android.synthetic.main.notice_fragment_bachelor.*

import kr.nutee.nutee_android.R

/*
 * Created by eunseo5355
 * DESC: 공지사항 Fragment
 */

class NoticeFragment : Fragment() {

	private val tabTextList = arrayListOf("학사공지", "수업공지", "학점교류", "장학공지", "일반공지", "행사공지")

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_fragment_notice, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// viewpager와 adapter 연결
		vp_notice.adapter = NoticePagerAdapter(this)

		// viewpager와 tablayout 연결
		TabLayoutMediator(tab_notice, vp_notice) { tab, position ->
			tab.text = tabTextList[position]
		}.attach()

	}

}
