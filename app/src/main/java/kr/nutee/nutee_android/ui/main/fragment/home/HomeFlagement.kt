package kr.nutee.nutee_android.ui.main.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.main_fragment_home.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import retrofit2.Response

class HomeFlagement() : Fragment() {

	private lateinit var homeAdapter: HomeAdapter
	val requestToServer = RequestToServer

	var lastId = 0
	var limit = 10

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.main_fragment_home,container,false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		rv_home.addItemDecoration(DividerItemDecoration(this.context, LinearLayout.VERTICAL))

		loadMain()
	}

	private fun loadMain() {
		requestToServer.service.requestMain(
			lastId, limit
		).customEnqueue { response ->
			response.body()?.let {
				setAdapter(it)
			}
		}
	}

	private fun setAdapter(mainItem: ResponseMain) {
		homeAdapter = HomeAdapter(mainItem, this.context!!)
		rv_home.adapter = homeAdapter
	}


}
