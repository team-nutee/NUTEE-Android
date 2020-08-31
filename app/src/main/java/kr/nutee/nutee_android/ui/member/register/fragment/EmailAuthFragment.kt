package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.member_register_email_auth_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.ValidData
import kr.nutee.nutee_android.ui.extend.animation.constraintDownInAnimation
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener

/*
* Created by jinsu4755
* DESC: 이메일 인증과 관련된 회원가입 Fragment
*/

class EmailAuthFragment : Fragment(), View.OnClickListener {

	private var email: String? = null
	private var otpNum: String? = null
	private var isEmailAuthSuccess = false
	private var isEmailOTPAuthSuccess = false
	private var isNextButtonEnabled = false

	private var onRegisterDataSetListener: OnRegisterDataSetListener? = null
	private val validData = ValidData()

	private var emailAuthEventListener: (
		(email: EditText, result:TextView) -> Unit
	)? = null
	private var emailAuthOTPEventListener: ((OTPNum: EditText,result:TextView) -> Unit)? = null
	private var registerEmailPrevious: (() -> Unit)? = null
	private var registerEmailNext: (() -> Unit)? = null

	fun setEmailAuthEventListener(
		listener: (email: EditText,result:TextView) -> Unit
	) {
		this.emailAuthEventListener = listener
	}

	fun setEmailAuthOTPEventListener(listener: (OTPNum: EditText,result:TextView) -> Unit) {
		this.emailAuthOTPEventListener = listener
	}

	fun setRegisterEmailPrevious(listener: () -> Unit) {
		this.registerEmailPrevious = listener
	}

	fun setRegisterEmailNext(listener: () -> Unit) {
		this.registerEmailNext = listener
	}

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
		return inflater.inflate(
			R.layout.member_register_email_auth_fragment,
			container,
			false
		)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		emailAuthButtonEventMapping()
		buttonEnableEventMapping()

		loadBackStackSettingStatus()
	}

	private fun loadBackStackSettingStatus() {
		if (isEmailAuthSuccess) {
			enableOTPInputLayout()
		}
		tv_email_auth_next.isEnabled = isNextButtonEnabled

	}

	private fun emailAuthButtonEventMapping() {
		tv_email_auth_button.setOnClickListener(this)
		tv_email_auth_otp_button.setOnClickListener(this)
		tv_email_auth_previous.setOnClickListener(this)
		tv_email_auth_next.setOnClickListener(this)
	}

	private fun buttonEnableEventMapping() {
		et_register_email_input.textChangedListener { email ->
			emailInputButtonEnable(email)
		}
		et_email_otp_auth.textChangedListener { otpNum ->
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
			R.id.tv_email_auth_button -> emailAuthButtonClickEvent()
			R.id.tv_email_auth_otp_button -> emailOTPAuthButtonClickEvnet()
			R.id.tv_email_auth_previous -> {
				registerEmailPrevious?.invoke()
			}
			R.id.tv_email_auth_next -> {
				email?.let { onRegisterDataSetListener?.onRegisterEmailDataSetListener(it) }
				registerEmailNext?.invoke()
			}
		}
	}

	private fun emailAuthButtonClickEvent() {
		if (isEmailAuthSuccess &&(email == et_register_email_input.text.toString())) {
			requireContext().showTextShake(
				tv_register_email_auth_result,
				"전송된 이메일을 확인해주세요!!",
				R.color.nuteeBase
			)
			return
		}
		emailAuthEventListener?.invoke(
			et_register_email_input,
			tv_register_email_auth_result
		)
	}

	fun emailAuthSuccessEvent() {
		email = et_register_email_input.text.toString()
		isEmailAuthSuccess = true
	}

	fun enableOTPInputLayout() {
		requireContext().constraintDownInAnimation(cl_input_register_email_otp_auth)
		cl_input_register_email_auth.visibility = View.VISIBLE
	}

	private fun emailOTPAuthButtonClickEvnet() {
		if (isEmailOTPAuthSuccess) {
			requireContext().showTextShake(
				tv_register_email_otp_result,
				"이미 OTP 인증이 완료되었습니다!",
				R.color.nuteeBase
			)
			return
		}
		emailAuthOTPEventListener?.invoke(et_email_otp_auth,tv_register_email_otp_result)
	}

	fun emailOTPSuccessEvent() {
		otpNum = et_email_otp_auth.text.toString()
		isEmailOTPAuthSuccess = (otpNum == et_email_otp_auth.text.toString())
		isNextButtonEnabled = true
		tv_email_auth_next.isEnabled = isNextButtonEnabled
	}

}