package kr.nutee.nutee_android.ui.main.fragment.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.MainFragmentNoticeBinding
import kr.nutee.nutee_android.databinding.MemberRegisterSelectCategoryFragmentBinding

/*
 * Created by eunseo5355
 * DESC: 공지사항 Fragment
 */

class NoticeFragment : Fragment() {

	private var binding: MainFragmentNoticeBinding? = null

	private val tabTextList = arrayListOf("학사공지", "수업공지", "학점교류", "장학공지", "일반공지", "행사공지")
	private lateinit var noticeTabLayout: TabLayout
	private lateinit var noticeViewPager: ViewPager2

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		binding = MainFragmentNoticeBinding.inflate(inflater, container, false)
		return requireBinding().root
		//return inflater.inflate(R.layout.main_fragment_notice, container, false)
	}

	private fun requireBinding(): MainFragmentNoticeBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		noticeTabLayout = requireBinding().tabNotice
		noticeViewPager = requireBinding().vpNotice
		//noticeTabLayout = view.findViewById(R.id.tab_notice)
		//noticeViewPager = view.findViewById(R.id.vp_notice)

		// viewpager와 adapter 연결
		noticeViewPager.adapter = NoticePagerAdapter(this)

		// viewpager와 tablayout 연결
		TabLayoutMediator(noticeTabLayout, noticeViewPager) { tab, position ->
			tab.text = tabTextList[position]
		}.attach()

	}

}
