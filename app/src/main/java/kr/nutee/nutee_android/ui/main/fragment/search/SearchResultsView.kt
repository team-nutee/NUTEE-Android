package kr.nutee.nutee_android.ui.main.fragment.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_results_view.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.main.search.ResponseSearch
import kr.nutee.nutee_android.data.main.search.ResponseSearchMain
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue



class SearchResultsView : AppCompatActivity() {

	private var contentArrayList = ArrayList<ResponseSearch>()
	private lateinit var searchResultsViewRecyclerAdapter: SearchResultsViewRecyclerAdapter

	//서버연결
	private val requestToServer = RequestToServer

	var lastId = 0
	var limit = 10

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_search_results_view)

		val searchBoxText = intent.getStringExtra("searchBoxText")

		//서버 연결
		requestSearch(searchBoxText, lastId, limit) {
			contentArrayList = it
			//어댑터 인스턴스 생성
			searchResultsViewRecyclerAdapter = SearchResultsViewRecyclerAdapter(it)
		}

		//어댑터 적용
		rv_search_results.apply {
			layoutManager =
				LinearLayoutManager(this@SearchResultsView, LinearLayoutManager.VERTICAL, false)
			adapter = searchResultsViewRecyclerAdapter
		}
	}

	/*
	* 질문: 검색어를 입력하고 화면이 넘어가면 오류가 나서 앱이 중단된다.
	* 		어디 부분이 잘못된 건지 잘 모르겠다.
	* 		이 부분 이외에도 이상한 부분이 있으면 알려줬으면 좋겠다.
	* */


	private fun requestSearch(txt: String?, id: Int, limt: Int
							  ,loadfun:(resMain:ResponseSearchMain)->Unit) {
		Log.d("testCall", "호출성공")
		requestToServer.service.requestSearch(
			txt,id, limt
		).customEnqueue {
				response ->
			response.body()?.let { it ->
				loadfun(it)
				Log.d("testCall", "성공")

			}
		}


		//여기는 안 봐도 됨

//		Log.d("testCall", "호출성공")
//		RequestToServer.service.requestSearch(text = txt, lastId = id, limit = limt)
//			.enqueue(object :
//				Callback<ResponseSearchMain> {
//				override fun onFailure(call: Call<ResponseSearchMain>, t: Throwable) {
//					Log.d("testCall", "불통과")
//				}
//
//				override fun onResponse(
//					call: Call<ResponseSearchMain>,
//					response: Response<ResponseSearchMain>
//				) {
//					if (response.isSuccessful) {
//						Log.d("testCall", "통과")
//						val test=response.body().toString()
//						Log.d("testCall", test)
//					}
//
//				}
//			})
	}
}