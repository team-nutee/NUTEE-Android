package kr.nutee.nutee_android.ui.main.fragment.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kr.nutee.nutee_android.ui.main.fragment.profile.fragment.LikePostFragment
import kr.nutee.nutee_android.ui.main.fragment.profile.fragment.MyCommentFragment
import kr.nutee.nutee_android.ui.main.fragment.profile.fragment.MyPostFragment

class ProfilePagerAdapter(fragment: ProfileFragment) : FragmentStateAdapter(fragment) {
	override fun getItemCount(): Int {
		return 3
	}

	override fun createFragment(position: Int): Fragment {
		return when(position) {
			0 -> MyPostFragment()
			1 -> MyCommentFragment()
			else -> LikePostFragment()
		}
	}
}