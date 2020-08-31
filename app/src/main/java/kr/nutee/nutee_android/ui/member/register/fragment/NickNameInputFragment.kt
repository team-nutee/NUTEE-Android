package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.member_register_nick_name_input_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener

class NickNameInputFragment : Fragment(),View.OnClickListener{

	private var nickname: String? = null

	private var onRegisterDataSetListener: OnRegisterDataSetListener? = null

	private var nickNameInputEventListener: (
		(nickName: EditText, resultText: TextView) -> Unit
	)? = null
	private var registerNickNamePreviousEventListener: (() -> Unit)? = null
	private var registerNickNameNextEventListener: (() -> Unit)? = null

	fun setNickNameInputEventListener(
		listener: (nickName: EditText, resultText: TextView) -> Unit
	) {
		this.nickNameInputEventListener = listener
	}

	fun setRegisterNickNamePreviousEvnetListener(listener: () -> Unit) {
		this.registerNickNamePreviousEventListener = listener
	}

	fun setRegisterNickNameNextEvnetListerer(listener: () -> Unit) {
		this.registerNickNameNextEventListener = listener
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context !is OnRegisterDataSetListener)
			throw RuntimeException(context.toString() + "must implement OnRegisterDataSetListener")
		onRegisterDataSetListener = context
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(
			R.layout.member_register_nick_name_input_fragment,
			container,
			false
		)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		nickNameInputButtonEventMapping()
		nickNameInputButtonEnableEvent()
	}

	private fun nickNameInputButtonEventMapping(){
		tv_nick_name_next.setOnClickListener(this)
		tv_nick_name_previous.setOnClickListener(this)
	}

	private fun nickNameInputButtonEnableEvent(){
		et_register_nick_name_input.textChangedListener {
			tv_nick_name_next.isEnabled = !it.isNullOrBlank()
		}
	}

	override fun onClick(nicknameFragmentButton: View?) {
		when (nicknameFragmentButton!!.id) {
			R.id.tv_nick_name_next -> {
				nickNameInputEventListener?.invoke(
					et_register_nick_name_input,
					tv_register_nick_name_input_result_text
				)
			}
			R.id.tv_nick_name_previous ->{
				registerNickNamePreviousEventListener?.invoke()
			}
		}
	}

	fun nickNameInputSuccessEvent(){
		nickname = et_register_nick_name_input.text.toString()
		onRegisterDataSetListener?.onRegisterNickNameDataSetListerner(nickname!!)
		registerNickNameNextEventListener?.invoke()
	}

	override fun onDetach() {
		super.onDetach()
		onRegisterDataSetListener = null
		nickname = null
	}
}