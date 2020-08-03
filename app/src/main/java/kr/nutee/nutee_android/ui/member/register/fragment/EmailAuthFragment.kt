package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.member_register_email_auth_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.ValidData
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener
import java.lang.RuntimeException

class EmailAuthFragment : Fragment(), View.OnClickListener {

	private var email: String? = null
	private var otpNum: String? = null

	private var onRegisterDataSetListener: OnRegisterDataSetListener? = null
	private val validData = ValidData()

	private var emailAuthEventListener: ((email: EditText) -> Unit)? = null
	private var emailAuthOTPEventListener: ((OTPNum: EditText) -> Unit)? = null
	private var registerEmailPrevious: (() -> Unit)? = null
	private var registerEmailNext: (() -> Unit)? = null

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if (context !is OnRegisterDataSetListener) {
			throw RuntimeException(context.toString() + "must implement OnRegisterDataSetListener")
		}
		onRegisterDataSetListener = context
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.member_register_email_auth_fragment, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		emailAuthButtonEventMapping()
		buttonEnableEventMapping()
	}

	private fun emailAuthButtonEventMapping() {
		tv_email_auth_button.setOnClickListener(this)
		tv_email_auth_otp_button.setOnClickListener(this)
		tv_email_auth_previous.setOnClickListener(this)
		tv_email_auth_next.setOnClickListener(this)
	}

	private fun buttonEnableEventMapping(){
		et_register_email_input.textChangedListener { email->
			emailInputButtonEnable(email)
		}
		et_email_otp_auth.textChangedListener { otpNum->
			tv_email_auth_otp_button.isEnabled = !otpNum.isNullOrBlank()
		}
	}

	private fun emailInputButtonEnable(email: Editable?) {
		if (validData.isValidEmail(email.toString())) {
			tv_email_auth_button.isEnabled = true
			tv_register_email_auth_result.text = ""
			return
		}
		tv_email_auth_button.isEnabled = false
		requireContext().showTextShake(
			tv_register_email_auth_result,
			"올바르지 못한 이메일 입니다",
			R.color.colorRed
		)
	}

	//fragment 종료시 내용 초기화.
	override fun onDetach() {
		onRegisterDataSetListener = null
		email = null
		otpNum = null
		super.onDetach()
	}

	override fun onClick(emailFragmentView: View?) {
		when (emailFragmentView!!.id) {
			R.id.tv_email_auth_button -> emailAuthClickEvent()
			R.id.tv_email_auth_otp_button -> {
				emailAuthOTPEventListener?.invoke(et_email_otp_auth)
			}
			R.id.tv_email_auth_previous -> {
				registerEmailPrevious?.invoke()
			}
			R.id.tv_email_auth_next -> {
				email?.let { onRegisterDataSetListener?.onRegisterEmailDataSetListener(it) }
				registerEmailNext?.invoke()
			}
		}
	}

	private fun emailAuthClickEvent() {
		if (email == et_register_email_input.text.toString()) {
			requireContext().showTextShake(
				tv_register_email_auth_result,
				"전송된 이메일을 확인해주세요!!",
				R.color.nuteeBase
			)
			return
		}
		emailAuthEventListener?.invoke(et_register_email_input)
		requireContext().showTextShake(
			tv_register_email_auth_result,
			"이메일을 전송했습니다. 확인해주세요!!",
			R.color.nuteeBase
		)
		email = et_register_email_input.text.toString()
	}

	fun setEmailAuthEventListener(listener: (email: EditText) -> Unit) {
		this.emailAuthEventListener = listener
	}

	fun setEmailAuthOTPEventListener(listener: (OTPNum: EditText) -> Unit) {
		this.emailAuthOTPEventListener = listener
	}

	fun setRegisterEmailPrevious(listener: () -> Unit) {
		this.registerEmailPrevious = listener
	}

	fun setRegisterEmailNext(listener: () -> Unit) {
		this.registerEmailNext = listener
	}
}