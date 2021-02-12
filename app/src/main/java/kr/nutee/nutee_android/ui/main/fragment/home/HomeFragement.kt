package kr.nutee.nutee_android.ui.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kr.nutee.nutee_android.R

class HomeFragement() : Fragment() {

	private lateinit var homeTabLayout: TabLayout
	private lateinit var homeViewpager:ViewPager2
	private lateinit var homeAdapter: HomeAdapter
	private lateinit var homeTapTextList :ArrayList<String>

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.main_fragment_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//게시글 탭 기능
		homeTapTextList= arrayListOf("추천 게시글", "내 전공", "전체 게시글")
		homeTabLayout = view.findViewById(R.id.tab_main_home)
		homeViewpager=view.findViewById(R.id.vp_main_home)
		homeAdapter=HomeAdapter(this)

		homeViewpager.adapter = homeAdapter

		TabLayoutMediator(homeTabLayout, homeViewpager) { tab, position ->
			tab.text =homeTapTextList[position]
		}.attach()
	}
}
