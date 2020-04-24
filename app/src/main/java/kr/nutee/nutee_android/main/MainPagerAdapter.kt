package kr.nutee.nutee_android.main


/*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kr.nutee.nutee_android.fragment.add.AddActivity
import kr.nutee.nutee_android.fragment.home.HomeFlagement
import kr.nutee.nutee_android.fragment.notice.NoticeFlagment
import kr.nutee.nutee_android.fragment.profile.ProfileFragment
import kr.nutee.nutee_android.fragment.search.SearchFragment

class MainPagerAdapter(fregmentMenager: FragmentManager) :
	FragmentPagerAdapter(fregmentMenager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
	override fun getItem(position: Int): Fragment {
		return when (position) {
			0 -> HomeFlagement()
			1 -> SearchFragment()
			*/
/*2 -> SearchFragment()*//*

			2 -> NoticeFlagment()
			else -> ProfileFragment()
		}
	}

	override fun getCount() = 4


}*/
