package kr.nutee.nutee_android.ui.main.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment_home.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.data.main.home.ResponseMainItem
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.InfiniteScrollListener
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.CustomLodingDialog

class HomeFragement() : Fragment() {

	private lateinit var homeAdapter: HomeAdapter
	val requestToServer = RequestToServer
	val isLoading = false

	var lastId = 0
	var loadId = 0
	var limit = 10

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
		rv_home.addItemDecoration(DividerItemDecoration(this.context, LinearLayout.VERTICAL))
		loadMain(lastId) {
			contentArrayList = it
			setAdapter(it)
			loadId = it.last()!!.id!!
		}

		refreshEvent()
		addScrollerListener()
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

	private fun setAdapter(mainItem: ResponseMain) {
		homeAdapter = HomeAdapter(mainItem, this.context!!)
		rv_home.adapter = homeAdapter
	}

	private fun refreshEvent() {
		rv_home_refresh.setProgressBackgroundColorSchemeColor(
			ContextCompat.getColor(
				this.context!!,
				R.color.nuteeBase
			)
		)
		rv_home_refresh.setColorSchemeColors(Color.WHITE)
		rv_home_refresh.setOnRefreshListener {
			loadMain(lastId) {
				contentArrayList = it
				setAdapter(it)
				loadId = it.last()!!.id!!
			}
			rv_home_refresh.isRefreshing = false
		}
	}

	private fun addScrollerListener() {
		rv_home.addOnScrollListener(
			InfiniteScrollListener(
				{
					loadMain(loadId) {

						contentArrayList.addAll(it)
						rv_home.adapter?.notifyDataSetChanged()
						loadId = it.last()?.id!!

					}
				},
				rv_home.layoutManager as LinearLayoutManager
			)
		)
	}


}
