package kr.nutee.nutee_android.ui.member.register.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.member_register_email_auth_fragment.*
import kotlinx.android.synthetic.main.member_register_password_fragment.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.MemberRegisterIdInputFragmentBinding
import kr.nutee.nutee_android.databinding.MemberRegisterPasswordFragmentBinding
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.member.DataValid
import kr.nutee.nutee_android.ui.member.register.OnRegisterDataSetListener
import kr.nutee.nutee_android.ui.setting.TermOfUseActivity

/*
* Created by jinsu4755
* DESC: 회원가입시 비밀번호 입력을 담당하는 플래그먼트
*/

class PasswordInputFragment : Fragment(), View.OnClickListener {

    private var binding: MemberRegisterPasswordFragmentBinding?= null

    private var password: String? = null
    private var passwordCheck: String? = null

    private var onRegisterDataSetListener: OnRegisterDataSetListener? = null
    private val dataValid = DataValid()

    private var registerPasswordPreviousEvnetListener: (() -> Unit)? = null
    private var registerPasswordNextEventListener: (() -> Unit)? = null

    fun setRegisterPasswordPreviousEventListener(listener: () -> Unit) {
        this.registerPasswordPreviousEvnetListener = listener
    }

    fun setReigsterPasswordNextEventListener(listener: () -> Unit) {
        this.registerPasswordNextEventListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MemberRegisterPasswordFragmentBinding.inflate(inflater, container, false)
        return requireBinding().root
    }

    private fun requireBinding(): MemberRegisterPasswordFragmentBinding = binding
            ?: error("binding is not init")

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

    private fun passwordInputButtonEventMapping() {
        requireBinding().tvCheckAgreeToPersonalInfo.setOnClickListener(this)
        requireBinding().tvPasswordPrevious.setOnClickListener(this)
        requireBinding().tvPasswordNext.setOnClickListener(this)
        requireBinding().cbAgreeToPersonalInfo.setOnClickListener(this)

        /*tv_check_agree_to_personal_info.setOnClickListener(this)
        tv_password_previous.setOnClickListener(this)
        tv_password_next.setOnClickListener(this)
        cb_agree_to_personal_info.setOnClickListener(this)*/
    }

    private fun passwordInputButtonEnableEvent() {
        passwordTextChangedListener()
        passwordCheckTextChangedListener()
    }

    private fun passwordTextChangedListener() {
        requireBinding().etRegisterPassword.textChangedListener { pw ->
            passwordInputEvent(pw)
        }
    }

    private fun passwordInputEvent(pw: Editable?) {
        if (dataValid.isValidPassword(pw.toString())) {
            password = pw?.toString()
            requireBinding().tvRegisterPasswordResult.text = ""
            passwordNextButtonEnable()
            return
        }
        password = null
        requireContext().showTextShake(
            requireBinding().tvRegisterPasswordResult,
            "8자 이상의 !@#\$%^&*_+- 가 포함된\n 영어 대문자, 소문자, 특수문자, 숫자가 포함된 비밀번호를 입력해주세요",
            R.color.colorRed
		)
    }

    private fun passwordCheckTextChangedListener() {
        requireBinding().etRegisterPasswordCheck.textChangedListener {
            passwordCheckInputEvent(it)
        }
    }

    private fun passwordCheckInputEvent(pw: Editable?): Boolean {
        if (pw?.toString() == password) {
            passwordCheck = pw.toString()
            requireBinding().tvRegisterPasswordCheckResult.text = ""
            passwordNextButtonEnable()
            return true
        }
        passwordCheck = null
        requireContext().showTextShake(
            requireBinding().tvRegisterPasswordCheckResult,
            "비밀번호가 일치하지 않습니다.",
            R.color.colorRed
		)
        return false
    }

    private fun passwordNextButtonEnable() {
        requireBinding().tvPasswordNext.isEnabled =
                requireBinding().cbAgreeToPersonalInfo.isChecked &&
                requireBinding().etRegisterPassword.text.toString() == password &&
                requireBinding().etRegisterPasswordCheck.text.toString() == passwordCheck

        /*tv_password_next.isEnabled =
            cb_agree_to_personal_info.isChecked &&
            et_register_password.text.toString() == password &&
            et_register_password_check.text.toString() == passwordCheck*/
    }

    override fun onClick(passwordInputFragmentButton: View?) {
        when(passwordInputFragmentButton!!.id){
            requireBinding().tvCheckAgreeToPersonalInfo.id -> gotoPersonalInfoEvent()
            requireBinding().tvPasswordPrevious.id -> registerPasswordPreviousEvnetListener?.invoke()
            requireBinding().tvPasswordNext.id -> {
                onRegisterDataSetListener?.onRegisterPasswordDataSetListener(password!!)
                registerPasswordNextEventListener?.invoke()
            }
            requireBinding().cbAgreeToPersonalInfo.id -> {
                passwordNextButtonEnable()
            }
        }
        /*when (passwordInputFragmentButton!!.id) {
			R.id.tv_check_agree_to_personal_info -> gotoPersonalInfoEvent()
			R.id.tv_password_previous -> registerPasswordPreviousEvnetListener?.invoke()
			R.id.tv_password_next -> {
				onRegisterDataSetListener?.onRegisterPasswordDataSetListener(password!!)
				registerPasswordNextEventListener?.invoke()
			}
			R.id.cb_agree_to_personal_info -> {
				passwordNextButtonEnable()
			}
        }*/
    }

    private fun gotoPersonalInfoEvent() {
        val gotoPersonalInfoEvent = Intent(requireContext(), TermOfUseActivity::class.java)
        startActivity(gotoPersonalInfoEvent)
    }

    override fun onDetach() {
        super.onDetach()
        onRegisterDataSetListener = null
    }
}
