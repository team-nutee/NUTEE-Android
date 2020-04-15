package kr.nutee.nutee_android.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kr.nutee.nutee_android.fragment.home.HomeFlagement

class MainPagerAdapter(fregmentMenager: FragmentManager) :
	FragmentPagerAdapter(fregmentMenager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
	override fun getItem(position: Int): Fragment {
		return when (position) {
			0 -> HomeFlagement()
			1 -> HomeFlagement()
			2 -> HomeFlagement()
			3 -> HomeFlagement()
			else -> HomeFlagement()
		}
	}

	override fun getCount() = 5


}