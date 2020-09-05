package kr.nutee.nutee_android.ui.main.fragment.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.main_fragment_search.*
import kr.nutee.nutee_android.R


class SearchFragment : Fragment() {
	//검색어 문자열 선언
	var searchBoxText: String=""

	override fun onResume() {
		//검색창 강제 선택
		et_search_bar.requestFocus()
		//검색창 강제 선택시 키보드가 자동으로 보이게 함
		val inputMethodManager : InputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.showSoftInput(et_search_bar,0)

		super.onResume()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?

	): View? {
		return inflater.inflate(R.layout.main_fragment_search,container,false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		//검색어 입력
		et_search_bar.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
			if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
				//검색어
				searchBoxText = et_search_bar.text.toString()

				//검색어가 없는 경우
				if (searchBoxText.length == 0) {
					Toast.makeText(getActivity(), "검색어를 입력해 주세요", Toast.LENGTH_LONG).show()
				}
				//검색어가 있는 경우
				if (searchBoxText.length != 0) {

					val intentSearchResults= Intent(getActivity(),SearchResultsView::class.java)
					intentSearchResults.putExtra("searchBoxText",searchBoxText)
					startActivity(intentSearchResults)
				}
				return@OnKeyListener true
			}
			false
		})
	}
}
