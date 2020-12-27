package kr.nutee.nutee_android.ui.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_show_detail_image_view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

class HomeFragement() : Fragment() {

	val requestToServer = RequestToServer
	val isLoading = false

	var lastId = 0
	var loadId = 0
	var limit = 10

	private lateinit var homeTabLayout: TabLayout
	private lateinit var homeViewpager:ViewPager2
	private lateinit var homeAdapter: HomeAdapter
	private lateinit var homeTapTextList :ArrayList<String>

	private lateinit var contentArrayList: ResponseMain

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.main_fragment_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		loadMain(lastId) {
			contentArrayList = it
			loadId = it.last()!!.id!!
		}

		//게시글 탭 기능
		homeTapTextList= arrayListOf("추천 게시글", "내 전공", "전체 게시글")
		homeTabLayout = view.findViewById(R.id.tab_main_home)
		homeViewpager=view.findViewById(R.id.vp_main_home)
		homeAdapter=HomeAdapter(this)

		homeViewpager.apply {
			adapter = homeAdapter
			orientation = ViewPager2.ORIENTATION_HORIZONTAL
		}

		TabLayoutMediator(homeTabLayout, homeViewpager) { tab, position ->
			tab.text =homeTapTextList[position]
		}.attach()
	}

	private fun loadMain(loadingId: Int, loadfun: (resMain: ResponseMain) -> Unit) {
		requestToServer.service.requestMain(
			loadingId, limit
		).customEnqueue { response ->
			response.body()?.let { it ->
				loadfun(it)
			}
		}
	}

}
