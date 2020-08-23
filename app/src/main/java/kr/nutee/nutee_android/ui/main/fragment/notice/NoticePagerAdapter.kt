package kr.nutee.nutee_android.ui.main.fragment.notice

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.nutee.nutee_android.ui.main.fragment.notice.bachelornotice.BachelorFragment
import kr.nutee.nutee_android.ui.main.fragment.notice.classnotice.ClassFragment
import kr.nutee.nutee_android.ui.main.fragment.notice.eventnotice.EventFragment
import kr.nutee.nutee_android.ui.main.fragment.notice.exchange.ExchangeFragment
import kr.nutee.nutee_android.ui.main.fragment.notice.generalnotice.GeneralFragment
import kr.nutee.nutee_android.ui.main.fragment.notice.scholarshipnotice.ScholarshipFragment

class NoticePagerAdapter(fragmentActivity: NoticeFragment)
	: FragmentStateAdapter(fragmentActivity) {

	override fun getItemCount(): Int {
		return 6
	}

	override fun createFragment(position: Int): Fragment {
		return when(position){
			0 -> BachelorFragment()
			1 -> ClassFragment()
			2 -> ExchangeFragment()
			3 -> ScholarshipFragment()
			4 -> GeneralFragment()
			else -> EventFragment()
		}
	}

}
