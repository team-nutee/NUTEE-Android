package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.member_register_email_auth_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.ValidData
import kr.nutee.nutee_android.databinding.MemberRegisterEmailAuthFragmentBinding
import kr.nutee.nutee_android.databinding.SettingMainFragmentBinding
import kr.nutee.nutee_android.ui.extend.animation.constraintDownInAnimation
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener

/*
* Created by jinsu4755
* DESC: 이메일 인증과 관련된 회원가입 Fragment
*/

class EmailAuthFragment : Fragment(), View.OnClickListener {

    private var binding: MemberRegisterEmailAuthFragmentBinding? =null
    private var email: String? = null
    private var otpNum: String? = null
    private var isEmailAuthSuccess = false
    private var isEmailOTPAuthSuccess = false
    private var isNextButtonEnabled = false

    private var onRegisterDataSetListener: OnRegisterDataSetListener? = null
    private val validData = ValidData()

    private var emailAuthEventListener: (
        (email: EditText, result: TextView) -> Unit
    )? = null
    private var emailAuthOTPEventListener: ((OTPNum: EditText, result: TextView) -> Unit)? = null
    private var registerEmailPrevious: (() -> Unit)? = null
    private var registerEmailNext: (() -> Unit)? = null

    fun setEmailAuthEventListener(
        listener: (email: EditText, result: TextView) -> Unit
    ) {
        this.emailAuthEventListener = listener
    }

    fun setEmailAuthOTPEventListener(listener: (OTPNum: EditText, result: TextView) -> Unit) {
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MemberRegisterEmailAuthFragmentBinding.inflate(inflater, container, false)
        return requireBinding().root
    }

    private fun requireBinding(): MemberRegisterEmailAuthFragmentBinding = binding
            ?: error("binding is not init")

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
        requireBinding().tvEmailAuthNext.isEnabled = isNextButtonEnabled
    }

    private fun emailAuthButtonEventMapping() {
        requireBinding().tvEmailAuthButton.setOnClickListener(this)
        requireBinding().tvEmailAuthOtpButton.setOnClickListener(this)
        requireBinding().tvEmailAuthPrevious.setOnClickListener(this)
        requireBinding().tvEmailAuthNext.setOnClickListener(this)
    }

    private fun buttonEnableEventMapping() {
        requireBinding().etRegisterEmailInput.textChangedListener { email ->
            emailInputButtonEnable(email)
        }
        requireBinding().etEmailOtpAuth.textChangedListener { otpNum ->
            requireBinding().tvEmailAuthOtpButton.isEnabled = !otpNum.isNullOrBlank()
        }
    }

    private fun emailInputButtonEnable(email: Editable?) {
        if (validData.isValidEmail(email.toString())) {
            requireBinding().tvEmailAuthButton.isEnabled = true
            requireBinding().tvRegisterEmailAuthResult.text = ""
            return
        }
        requireBinding().tvEmailAuthButton.isEnabled = false
        requireContext().showTextShake(
            requireBinding().tvRegisterEmailAuthResult,
            "올바르지 못한 이메일 입니다",
            R.color.colorRed
        )
    }

    // fragment 종료시 내용 초기화.
    override fun onDetach() {
        onRegisterDataSetListener = null
        email = null
        otpNum = null
        super.onDetach()
    }

    override fun onClick(emailFragmentView: View?) {
        when (emailFragmentView!!.id) {
            requireBinding().tvEmailAuthButton.id -> emailAuthButtonClickEvent()
            requireBinding().tvEmailAuthOtpButton.id -> emailOTPAuthButtonClickEvnet()
            requireBinding().tvEmailAuthPrevious.id -> {
                registerEmailPrevious?.invoke()
            }
            requireBinding().tvEmailAuthNext.id -> {
                email?.let { onRegisterDataSetListener?.onRegisterEmailDataSetListener(it) }
                registerEmailNext?.invoke()
            }
        }
    }

    private fun emailAuthButtonClickEvent() {
        if (isEmailAuthSuccess && (email == requireBinding().etRegisterEmailInput.text.toString())) {
            requireContext().showTextShake(
                requireBinding().tvRegisterEmailAuthResult,
                "전송된 이메일을 확인해주세요!!",
                R.color.nuteeBase
            )
            return
        }
        emailAuthEventListener?.invoke(
            requireBinding().etRegisterEmailInput,
            requireBinding().tvRegisterEmailAuthResult
        )
    }

    fun emailAuthSuccessEvent() {
        email = requireBinding().etRegisterEmailInput.text.toString()
        isEmailAuthSuccess = true
    }

    fun enableOTPInputLayout() {
        requireContext().constraintDownInAnimation(requireBinding().clInputRegisterEmailOtpAuth)
        requireBinding().clInputRegisterEmailAuth.visibility = View.VISIBLE
    }

    private fun emailOTPAuthButtonClickEvnet() {
        if (isEmailOTPAuthSuccess) {
            requireContext().showTextShake(
                requireBinding().tvRegisterEmailOtpResult,
                "이미 OTP 인증이 완료되었습니다!",
                R.color.nuteeBase
            )
            return
        }

        with(requireBinding()) {
            emailAuthOTPEventListener?.invoke(etEmailOtpAuth, tvRegisterEmailOtpResult)
        }
    }

    fun emailOTPSuccessEvent() {
        otpNum = requireBinding().etEmailOtpAuth.text.toString()
        isEmailOTPAuthSuccess = (otpNum == requireBinding().etEmailOtpAuth.text.toString())
        isNextButtonEnabled = true
        requireBinding().tvEmailAuthNext.isEnabled = isNextButtonEnabled
    }
}
