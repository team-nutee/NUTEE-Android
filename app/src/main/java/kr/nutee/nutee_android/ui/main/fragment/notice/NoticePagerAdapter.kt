package kr.nutee.nutee_android.ui.main.fragment.notice

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.nutee.nutee_android.ui.main.fragment.notice.fragment.*

/*
 * Created by eunseo5355
 * DESC: 공지사항의 ViewPager Adapter
 */

/*6개의 fragment를 달아줄 adapter 클래스*/
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
