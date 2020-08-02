package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener
import java.lang.RuntimeException

class EmailAuthFragment : Fragment() {

	private var onRegisterDataSetListener:OnRegisterDataSetListener? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.member_register_email_auth_fragment, container, false)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context !is OnRegisterDataSetListener) {
			throw RuntimeException(context.toString() + "must implement OnRegisterDataSetListener")
		}
		onRegisterDataSetListener = context
	}

	override fun onDetach() {
		super.onDetach()
		onRegisterDataSetListener = null
	}
}