package kr.nutee.nutee_android.ui.main.fragment.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.main_fragment_home.*
import kotlinx.coroutines.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.home.ResponseMain
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import java.lang.Runnable

class HomeFragement() : Fragment(), SwipeRefreshLayout.OnRefreshListener, ONLoadMoreListener {

	private lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
	private var contentArrayList: ResponseMain = ResponseMain()
	val requestToServer = RequestToServer

	private val loadMoreScope = CoroutineScope(Job() + Dispatchers.IO)

	private val mRunnable: Runnable = Runnable {
		rv_home_refresh.isRefreshing = false
		loadData(INITIAL_LOAD_ID) { onSuccessLoadMain(it) }
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.main_fragment_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		homeRecyclerViewAdapter = HomeRecyclerViewAdapter(this)
		rv_home_refresh.setOnRefreshListener(this)
		rv_home.adapter = homeRecyclerViewAdapter
		rv_home.addOnScrollListener(object : RecyclerView.OnScrollListener(){
			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
				super.onScrolled(recyclerView, dx, dy)
				val layoutManager = rv_home.layoutManager as LinearLayoutManager
				val lastVisibleItemPostion = layoutManager.findLastCompletelyVisibleItemPosition()
				val currnentListSize = homeRecyclerViewAdapter.itemCount
				if (
					dy > 0
					&& lastVisibleItemPostion == (currnentListSize - 1)
				){
					homeRecyclerViewAdapter.showLoading()
				}
			}
		}
		)
	}

	override fun onStart() {
		super.onStart()
		loadData(INITIAL_LOAD_ID) { onSuccessLoadMain(it) }
	}

	override fun onRefresh() {
		Handler().postDelayed(mRunnable, REfRASH_TIME)
	}

	override fun onLoadMore() {
		runBlocking { loadMoreContent() }
	}

	private suspend fun loadMoreContent() =
		withContext(Dispatchers.Default) {
			loadData(lastId = homeRecyclerViewAdapter.getLastItemId()) { onSuccessLoadMore(it) }
		}

	private fun loadData(
		lastId: Int,
		onSuccessListener: ((response: ResponseMain?) -> Unit)? = null
	) {
		RequestToServer.service
			.requestMain(
				lastId = lastId,
				limit = LIMIT
			)
			.customEnqueue(
				onSuccess = { onSuccessListener?.invoke(it.body()) },
				onFail = { onFailLoadMainMessage() },
				onError = { onFailLoadMainMessage() },
			)
	}

	private fun onSuccessLoadMain(response: ResponseMain?) {
		response?.let { contentArrayList.addAll(it) }
		homeRecyclerViewAdapter.addAll(contentArrayList)
	}

	private fun onSuccessLoadMore(response: ResponseMain?) {
		response?.let {
			if (it.isEmpty()) {
				Toast.makeText(
					requireActivity(),
					"가장 마지막 글입니다.",
					Toast.LENGTH_SHORT
				)
					.show()
			}
			contentArrayList.addAll(it)
			homeRecyclerViewAdapter.apply {
				addItemMore(it)
				setMore(true)
			}
		}
	}

	private fun onFailLoadMainMessage() {
		Toast.makeText(
			requireContext(),
			"서버 에러가 있습니다. 잠시후 다시 이용해주세요!!",
			Toast.LENGTH_SHORT
		)
			.show()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		loadMoreScope.cancel()
	}

	/*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
*/
	companion object {
		private const val LIMIT = 10
		private const val INITIAL_LOAD_ID = 0
		private const val REfRASH_TIME: Long = 1000
	}

}
