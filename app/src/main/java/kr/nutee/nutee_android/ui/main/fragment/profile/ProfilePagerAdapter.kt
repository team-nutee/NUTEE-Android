package kr.nutee.nutee_android.ui.main.fragment.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.nutee.nutee_android.ui.main.fragment.profile.fragment.RecommendedPostFragment
import kr.nutee.nutee_android.ui.main.fragment.profile.fragment.WrittenCommentFragment
import kr.nutee.nutee_android.ui.main.fragment.profile.fragment.WrittenPostFragment

class ProfilePagerAdapter(fragmentActivity: ProfileFragment) : FragmentStateAdapter(fragmentActivity) {
	override fun getItemCount(): Int {
		return 3
	}

	override fun createFragment(position: Int): Fragment {
		return when(position) {
			0 -> WrittenPostFragment()
			1 -> WrittenCommentFragment()
			else -> RecommendedPostFragment()
		}
	}
}