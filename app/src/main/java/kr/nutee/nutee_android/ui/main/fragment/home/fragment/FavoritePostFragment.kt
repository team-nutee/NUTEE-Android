package kr.nutee.nutee_android.ui.main.fragment.home.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.home_favorite_post_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.QueryValue
import kr.nutee.nutee_android.databinding.HomeFavoritePostFragmentBinding
import kr.nutee.nutee_android.databinding.MemberRegisterIdInputFragmentBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.RefreshEvent
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.main.fragment.home.HomeRecyclerViewAdapter


/*
 * Created by 88yhtserof
 * DESC: 메인뷰 홈 즐겨찾기 게시글 Fragment
 */

class FavoritePostFragment : Fragment() {

	private var binding: HomeFavoritePostFragmentBinding?=null

	//val handler:Handler= Handler()
	lateinit var mainHandler:Handler

	private lateinit var recyclerView:RecyclerView
	val requestToServer = RequestToServer
	private lateinit var swipeRefreshLayout: SwipeRefreshLayout

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = HomeFavoritePostFragmentBinding.inflate(inflater, container, false)
		return requireBinding().root
		//return inflater.inflate(R.layout.home_favorite_post_fragment, container, false)
	}

	private fun requireBinding(): HomeFavoritePostFragmentBinding = binding
			?: error("binding is not init")

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		with(requireBinding()){
			swipeRefreshLayout = swipeHomeFavoritePostRefresh
			recyclerView = rvMainHomeFavoritePost
		}
		//swipeRefreshLayout= view.findViewById(R.id.swipe_home_favorite_post_refresh)
		//recyclerView= view.findViewById(R.id.rv_main_home_favorite_post)

		setAdapter()
		loadFavoriteList()
		detailRefreshEvnet()
	}

	private fun setAdapter(){
		recyclerView.apply {
			layoutManager=LinearLayoutManager(
				this.context,
				LinearLayoutManager.VERTICAL, false
			)
			setHasFixedSize(true)
		}
	}

	private fun loadFavoriteList(){
		requestToServer.snsService.requestFavoriteList(
				"Bearer "+ App.prefs.local_login_token,
			QueryValue.lastId,
			QueryValue.limit
		).customEnqueue(
			onSuccess = {
				Log.d("Network", "즐겨찾기 게시글 통신 성공")
				if (it.body()?.body.isNullOrEmpty()) {
					requireBinding().clMainHomeNoPost.visibility = View.VISIBLE
					//cl_main_home_no_post.visibility = View.VISIBLE
				}
				else
					recyclerView.adapter = HomeRecyclerViewAdapter(it.body()?.body!!)
			},
			onError = {
				Log.d("Network", "즐겨찾기 게시글 통신 에러")
			},
				onFail = {
					requireContext().customDialogSingleButton("네트워크 통신 오류")
				}
		)
	}

	private fun detailRefreshEvnet() {
		context?.RefreshEvent(swipeRefreshLayout,true){loadFavoriteList()}
	}

}