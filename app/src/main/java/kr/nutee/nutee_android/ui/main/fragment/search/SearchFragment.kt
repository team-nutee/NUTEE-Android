package kr.nutee.nutee_android.ui.main.fragment.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.main_fragment_search.*


import kr.nutee.nutee_android.R


class SearchFragment : Fragment() {

	override fun onResume() {
		et_search_bar.requestFocus()
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
}
