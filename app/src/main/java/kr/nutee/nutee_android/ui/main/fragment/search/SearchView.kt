package kr.nutee.nutee_android.ui.main.fragment.search


import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.ACTION_DOWN
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.SearchActivityBinding
import kr.nutee.nutee_android.databinding.SearchResultsActivityBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue

/*
 * Created by 88yhtesrof
 * DESC: 검색 창 Activity 2.0 버전
 */
//FIXME 이전 검색어 기록 기능 추후에 수정 예정

class SearchView : AppCompatActivity() {

	private val binding by lazy { SearchActivityBinding.inflate(layoutInflater)}

	var requestToServer= RequestToServer

	lateinit var searchBackView:ImageView
	lateinit var searchBoxView:TextView
	lateinit var categoryRecyclerView:RecyclerView
	lateinit var majorRecyclerView:RecyclerView

	lateinit var searchBoxText:String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		//setContentView(R.layout.search_activity)

		//searchBackView=findViewById(R.id.img_search_back_btn)
		//searchBoxView=findViewById(R.id.et_search_box)
		//categoryRecyclerView=findViewById(R.id.rv_search_category_list)
		//majorRecyclerView=findViewById(R.id.rv_search_major_list)

		init()
	}

	private fun init() {
		loadCategoryMenu()
		loadMajorMenu()

		searchEventListener()
		binding.imgSearchBackBtn.setOnClickListener {
			finish()
		}
	}

	private fun searchEventListener() {
		binding.etSearchBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
			if (keyCode == KeyEvent.KEYCODE_ENTER
				&& event.action == ACTION_DOWN) {
				searchBoxText = binding.etSearchBox.text.toString()

				if (searchBoxText.isEmpty()) {
					Toast.makeText(this, "검색어를 입력해 주세요!", Toast.LENGTH_LONG).show()
				}
				else{
					val intentSearchResults= Intent(this,SearchResultsView::class.java)
					intentSearchResults.putExtra("searchBoxText", searchBoxText)
					startActivity(intentSearchResults)
				}
				return@OnKeyListener true
			}
			false
		})
	}

	private fun loadMajorMenu() {
		requestToServer.snsService.getMajors()
			.customEnqueue(
				onSuccess = {
					binding.rvSearchMajorList.adapter=CategoryMenuRecyclerViewAdapter(it.body()!!.body)
				}
			)
	}

	private fun loadCategoryMenu() {
		requestToServer.snsService.getCategory()
			.customEnqueue(
				onSuccess = {
					binding.rvSearchCategoryList.adapter=CategoryMenuRecyclerViewAdapter(it.body()!!.body)
				}
			)
	}
}