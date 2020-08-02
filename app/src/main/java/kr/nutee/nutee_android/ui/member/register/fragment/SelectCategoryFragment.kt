package kr.nutee.nutee_android.ui.member.register.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.nutee.nutee_android.R

class SelectCategoryFragment : Fragment() {
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(
			R.layout.member_register_select_category_fragment,
			container,
			false
		)
	}
}