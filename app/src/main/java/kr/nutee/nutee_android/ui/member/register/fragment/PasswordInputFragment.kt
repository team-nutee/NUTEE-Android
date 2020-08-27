package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener

/*
* Created by jinsu4755
* DESC: 회원가입시 비밀번호 입력을 담당하는 플래그먼트
*/

class PasswordInputFragment : Fragment(),View.OnClickListener {

	private val password: String? = null
	private val passwordCheck: String? = null

	private var onRegisterDataSetListener: OnRegisterDataSetListener? = null

	private var passwordInputEventListener: (
		(password: EditText, passwordResutl: TextView) -> Unit
	)? = null
	private var passwordCheckInputEventListener: (
		(passwordCheck: EditText, passwordCheckResult: TextView) -> Unit
	)? = null
	private var registerPasswordPreviousEvnetListener: (() -> Unit)? = null
	private var registerPasswordNextEventListener: (() -> Unit)? = null

	fun setPasswordInputEventListener(
		listener: (password: EditText, passwordResutl: TextView) -> Unit
	) {
		this.passwordInputEventListener = listener
	}

	fun setPasswordCheckInputEventListener(
		listener: (passwordCheck: EditText, passwordCheckResult: TextView) -> Unit
	) {
		this.passwordCheckInputEventListener = listener
	}

	fun setRegisterPasswordPreviousEventListener(listener: () -> Unit) {
		this.registerPasswordPreviousEvnetListener = listener
	}

	fun setReigsterPasswordNextEventListener(listener: () -> Unit){
		this.registerPasswordNextEventListener = listener
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(
			R.layout.member_register_password_fragment,
			container,
			false
		)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context !is OnRegisterDataSetListener)
			throw RuntimeException(context.toString() + "must implement OnRegisterDataSetListener")
		onRegisterDataSetListener = context
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		passwordInputButtonEventMapping()
		passwordInputButtonEnableEvent()
	}

	private fun passwordInputButtonEventMapping(){

	}

	private fun passwordInputButtonEnableEvent(){

	}

	override fun onClick(passwordInputFragmentButton: View?) {
		when (passwordInputFragmentButton!!.id) {

		}
	}


	override fun onDetach() {
		super.onDetach()
		onRegisterDataSetListener = null
	}
}