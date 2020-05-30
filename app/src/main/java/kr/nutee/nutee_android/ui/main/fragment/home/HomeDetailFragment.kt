package kr.nutee.nutee_android.ui.main.fragment.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.loadFragment

/**
 * A simple [Fragment] subclass.
 */
class HomeDetailFragment : Fragment() {

	var lastId = 0
	var limit = 1

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_fragment_home_detail, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		backPressEvent(view)
	}

	fun backPressEvent(view: View){
		view.isFocusableInTouchMode = true
		view.requestFocus();
		view.setOnKeyListener { _, keyCode, _ ->
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				context?.loadFragment(HomeFlagement())
				return@setOnKeyListener true
			}
			return@setOnKeyListener false
		}

	}
}
