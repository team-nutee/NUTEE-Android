package kr.nutee.nutee_android.ui.main.fragment.notice.exchange

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kr.nutee.nutee_android.R

/**
 * A simple [Fragment] subclass.
 */
class ExchangeFlagment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.notice_fragment_exchange, container, false)
	}

}
