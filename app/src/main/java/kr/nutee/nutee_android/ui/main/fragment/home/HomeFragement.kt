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
import kr.nutee.nutee_android.databinding.MainFragmentAddPhotoItemBinding
import kr.nutee.nutee_android.databinding.MainFragmentHomeBinding
import kr.nutee.nutee_android.databinding.MemberRegisterSelectCategoryFragmentBinding

class HomeFragement() : Fragment() {

	private var binding: MainFragmentHomeBinding ?= null

	private lateinit var homeTabLayout: TabLayout
	private lateinit var homeViewpager:ViewPager2
	private lateinit var homeAdapter: HomeAdapter
	private lateinit var homeTapTextList :ArrayList<String>

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding =MainFragmentHomeBinding.inflate(inflater, container, false)
		//return inflater.inflate(R.layout.main_fragment_home, container, false)
		return requireBinding().root
	}

	private fun requireBinding():MainFragmentHomeBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//게시글 탭 기능
		homeTapTextList= arrayListOf("즐겨찾기", "내 전공", "전체 게시글")
		homeTabLayout = requireBinding().tabMainHome
		//homeTabLayout = view.findViewById(R.id.tab_main_home)
		homeViewpager=requireBinding().vpMainHome
		//homeViewpager=view.findViewById(R.id.vp_main_home)
		homeAdapter=HomeAdapter(this)

		homeViewpager.adapter = homeAdapter

		TabLayoutMediator(homeTabLayout, homeViewpager) { tab, position ->
			tab.text =homeTapTextList[position]
		}.attach()
	}
}
