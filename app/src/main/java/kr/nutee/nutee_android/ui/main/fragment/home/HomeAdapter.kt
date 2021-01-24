package kr.nutee.nutee_android.ui.main.fragment.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.nutee.nutee_android.ui.main.fragment.home.fragment.FullPostFragment
import kr.nutee.nutee_android.ui.main.fragment.home.fragment.MyMajorPostFragment
import kr.nutee.nutee_android.ui.main.fragment.home.fragment.SuggestedPostFragment

/*
 * Created by 88yhtserof
 * DESC: 메인뷰 ViewPager의 Fragment Adapter
 */

class HomeAdapter(fragement: HomeFragement) : FragmentStateAdapter(fragement) {

	override fun getItemCount(): Int = 3

	override fun createFragment(position: Int): Fragment {
		return when (position) {
			0 -> SuggestedPostFragment()
			1 -> MyMajorPostFragment()
			else -> FullPostFragment()
		}
	}
}