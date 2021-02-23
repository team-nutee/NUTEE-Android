package kr.nutee.nutee_android.ui.member.register

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.member.register.*
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.animation.showTextShake
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.CustomLodingDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.extend.loadFragment
import kr.nutee.nutee_android.ui.extend.loadFragmentAddtoBackStack
import kr.nutee.nutee_android.ui.member.register.fragment.*
import retrofit2.Response

/*
* Created by jinsu4755
* DESC: 회원가입 Activity 엑티비티 레이아웃을 띄우고 각 Fragment의 서버통신 로직을 구현함.
*/

class RegisterActivity : AppCompatActivity(), OnRegisterDataSetListener {

    private var registerEmail: String? = null
    private var registerId: String? = null
    private var nickName: String? = null
    private var password: String? = null
    private var categoryList: List<String>? = null
    private var majorList: List<String>? = null

    lateinit var customLodingDialog: CustomLodingDialog

    val requestToServer = RequestToServer

    private val emailAuthFragment = EmailAuthFragment()
    private val idInputFragment = IdInputFragment()
    private val nickNameInputFragment = NickNameInputFragment()

    private val selectCategoryFragment = SelectCategoryFragment()
    private val selectDepartmentFragment = SelectDepartmentFragment()
    private val passwordInputFragment = PasswordInputFragment()

    override fun onRegisterEmailDataSetListener(email: String) {
        this.registerEmail = email
    }

    override fun onRegisterIdDataSetListener(id: String) {
        this.registerId = id
    }

    override fun onRegisterNickNameDataSetListerner(nickName: String) {
        this.nickName = nickName
    }

    override fun onRegisterPasswordDataSetListener(password: String) {
        this.password = password
    }

    override fun onRegisterCategoryDataSetListener(categoryList: List<String>) {
        this.categoryList = categoryList
    }

    override fun onRegisterMajorDataSetListener(majorList: List<String>) {
        this.majorList = majorList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        customLodingDialog = CustomLodingDialog(this)
        init()
    }

    private fun init() {
        registerButtonEventMapping()
        loadEmailAuthFragment()
    }

    private fun registerButtonEventMapping() {
        tv_register_top_back_btn.setOnClickListener {
            // FIXME 완벽한 방법은 아닌듯
            super.onBackPressed()
        }
    }

    private fun loadExitRegisterDialog(okClickListener: () -> Unit) {
        customDialog("회원가입을 종료하시겠습니까?😥", okClickListener)
    }

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.fl_register_frame_layout)
        if (fragment == emailAuthFragment) {
            loadExitRegisterDialog { super.onBackPressed() }
            return
        }
        super.onBackPressed()
    }

    private fun loadEmailAuthFragment() {
        setEmailAuthFragmentEmailAuthEvnetMapping()
        setEmailAuthFragmentOTPAuthEventMapping()
        setEmailAuthFragmentPreviousEventMapping()
        setEmailAuthFragmentNextEventMapping()
        loadFragment(
            emailAuthFragment,
            R.id.fl_register_frame_layout
        )
    }

    private fun setEmailAuthFragmentEmailAuthEvnetMapping() {
        emailAuthFragment.setEmailAuthEventListener { email, result ->
            requestToEmailAuth(email, result)
        }
    }

    private fun requestToEmailAuth(email: EditText, result: TextView) {
        RequestToServer.authService
            .requestEmailOTP(body = RequestEmailOTP(email.text.toString()))
            .customEnqueue(
                onSuccess = { emailAuthSuccessEvent(result) },
                onError = { emailAuthErrorEvent(result) }
            )
    }

    private fun emailAuthSuccessEvent(result: TextView) {
        emailAuthFragment.enableOTPInputLayout()
        showTextShake(
            result,
            "입력하신 이메일로 OTP 인증번호가 발송되었습니다.",
            R.color.nuteeBase
        )
        emailAuthFragment.emailAuthSuccessEvent()
    }

    private fun emailAuthErrorEvent(result: TextView) {
        showTextShake(
            result,
            "이미 가입된 이메일입니다.",
            R.color.colorRed
        )
    }

    private fun setEmailAuthFragmentOTPAuthEventMapping() {
        emailAuthFragment.setEmailAuthOTPEventListener { otpNum, result ->
            requestToEmailAuthOTP(otpNum, result)
        }
    }

    private fun requestToEmailAuthOTP(otpNum: EditText, result: TextView) {
        RequestToServer.authService
            .requestOTPCheck(body = RequestOTPCheck(otpNum.text.toString()))
            .customEnqueue(
                onSuccess = { emailAuthOTPSuccessEvent(result) },
                onError = { emailAuthOTPErrorEvent(result) }
            )
    }

    private fun emailAuthOTPSuccessEvent(result: TextView) {
        showTextShake(
            result,
            "OTP 인증이 완료되었습니다!",
            R.color.nuteeBase
        )
        emailAuthFragment.emailOTPSuccessEvent()
    }

    private fun emailAuthOTPErrorEvent(result: TextView) {
        showTextShake(
            result,
            "잘못된 인증번호입니다.",
            R.color.colorRed
        )
    }

    private fun setEmailAuthFragmentNextEventMapping() {
        emailAuthFragment.setRegisterEmailNext {
            loadIdInputFragment()
        }
    }

    private fun setEmailAuthFragmentPreviousEventMapping() {
        emailAuthFragment.setRegisterEmailPrevious {
            onBackPressed()
        }
    }

    private fun loadIdInputFragment() {
        setIdInputFragmenIdInputEvnetMapping()
        setIdInputFragmentNextEvnetMapping()
        setIdInputFragmentPreviousEvnetMapping()
        loadFragmentAddtoBackStack(
            idInputFragment,
            R.id.fl_register_frame_layout,
            null
        )
    }

    private fun setIdInputFragmenIdInputEvnetMapping() {
        idInputFragment.setIdInputEventListener { id, resultText ->
            requestToIdInput(id, resultText)
        }
    }

    private fun requestToIdInput(id: EditText, resultText: TextView) {
        RequestToServer.authService
            .requestIdCheck(body = RequestIdCheck(id.text.toString()))
            .customEnqueue(
                onSuccess = { idInputCheckSuccessEvent(resultText) },
                onError = { response -> idInputCheckErrorEvnet(response, resultText) }
            )
    }

    private fun idInputCheckSuccessEvent(resultText: TextView) {
        showTextShake(
            resultText,
            "아이디 중복 검사 성공",
            R.color.nuteeBase
        )
        idInputFragment.idInputCheckSuccessEvnet()
    }

    private fun idInputCheckErrorEvnet(
        response: Response<Unit>,
        resultText: TextView
    ) {
        when (response.code()) {
            401 -> {
                showTextShake(
                    resultText,
                    "현재 로그인 중입니다.",
                    R.color.colorRed
                )
            }
            409 -> {
                showTextShake(
                    resultText,
                    "이미 사용중인 아이디입니다.",
                    R.color.colorRed
                )
            }
        }
    }

    private fun setIdInputFragmentNextEvnetMapping() {
        idInputFragment.setRegisterIdNextEvnetListener {
            loadNickNameFragment()
        }
    }

    private fun setIdInputFragmentPreviousEvnetMapping() {
        idInputFragment.setRegisterIdPreviousEventListener {
            onBackPressed()
        }
    }

    private fun loadNickNameFragment() {
        setNickNameFragmentNickNameInputEventMapping()
        setNickNameFragmentPreviousEventMapping()
        setNickNameFragmentNextEventMapping()
        loadFragmentAddtoBackStack(
            nickNameInputFragment,
            R.id.fl_register_frame_layout,
            null
        )
    }

    private fun setNickNameFragmentNickNameInputEventMapping() {
        nickNameInputFragment.setNickNameInputEventListener { nickName, resultText ->
            requestToNickName(nickName, resultText)
        }
    }

    private fun requestToNickName(
        nickName: EditText,
        resultText: TextView
    ) {
        RequestToServer.authService
            .requestNickCheck(RequestNickCheck(nickName.text.toString()))
            .customEnqueue(
                onSuccess = { nickNameCheckSuccessEvent(resultText) },
                onError = { nickNameCheckErrorEvent(resultText) }
            )
    }

    private fun nickNameCheckSuccessEvent(resultText: TextView) {
        showTextShake(
            resultText,
            "사용 불가능한 닉네임 입니다",
            R.color.nuteeBase
        )
        nickNameInputFragment.nickNameInputSuccessEvent()
    }

    private fun nickNameCheckErrorEvent(resultText: TextView) {
        showTextShake(
            resultText,
            "사용 불가능한 닉네임 입니다",
            R.color.colorRed
        )
    }

    private fun setNickNameFragmentPreviousEventMapping() {
        nickNameInputFragment.setRegisterNickNamePreviousEvnetListener {
            onBackPressed()
        }
    }

    private fun setNickNameFragmentNextEventMapping() {
        nickNameInputFragment.setRegisterNickNameNextEvnetListerer {
            loadPasswordInputFragment()
        }
    }

    private fun loadPasswordInputFragment() {
        setPasswordInputFragmentPreviousEvent()
        setPasswordInputFragmentNextEvent()
        loadFragmentAddtoBackStack(
            passwordInputFragment,
            R.id.fl_register_frame_layout,
            null
        )
    }

    private fun setPasswordInputFragmentPreviousEvent() {
        passwordInputFragment.setRegisterPasswordPreviousEventListener {
            onBackPressed()
        }
    }

    private fun setPasswordInputFragmentNextEvent() {
        passwordInputFragment.setReigsterPasswordNextEventListener {
            loadSelectCategoryFragment()
        }
    }

    private fun loadSelectCategoryFragment() {
        setSelectCategoryFragmentPreviousEvent()
        setSelectCategoryFragmentNextEvent()
        loadFragmentAddtoBackStack(
            selectCategoryFragment,
            R.id.fl_register_frame_layout,
            null
        )
    }

    private fun setSelectCategoryFragmentPreviousEvent() {
        selectCategoryFragment.setRegisterCategoryPreviousEventListener {
            onBackPressed()
        }
    }

    private fun setSelectCategoryFragmentNextEvent() {
        selectCategoryFragment.setReigsterCategoryNextEventListener {
            if (it) {
                Log.d("test", "다음으로 넘어가기\n ${categoryList?.toString()}")
                loadSelectDepartmentFragment()
            } else {
                Toast.makeText(this, "카테고리를 선택해주세요!", Toast.LENGTH_SHORT)
                    .show()
                Log.d("test", "카테고리 선택이 비어있음.")
            }
        }
    }

    private fun loadSelectDepartmentFragment() {
        setDepartmentFragmentPreviousEvent()
        setDepartmentFragmentNextEvent()
        loadFragmentAddtoBackStack(
            selectDepartmentFragment,
            R.id.fl_register_frame_layout,
            null
        )
    }

    private fun setDepartmentFragmentPreviousEvent() {
        selectDepartmentFragment.setRegisterDepartmentPreviousEventListener {
            onBackPressed()
        }
    }

    private fun setDepartmentFragmentNextEvent() {
        selectDepartmentFragment.setReigsterDepartmentNextEventListener {
            Log.d("test", "서벼연결 되기\n ${majorList?.toString()}")
            // TODO 서버 연결
        }
    }

    private fun requestToRegister() {
        if (isNotHaveAllData()) {
            showRegisterDialog()
            return
        }
        customLodingDialog.startLoadingDialog()
        RequestToServer.authService
            .requestRegister(body = createRegisterBody())
            .customEnqueue(
                onSuccess = { registerSuccessEvnet(it.body()!!) },
                onError = {}
            )
    }

    private fun isNotHaveAllData(): Boolean {
        return nickName.isNullOrBlank() ||
            password.isNullOrBlank() ||
            registerEmail.isNullOrBlank() ||
            registerId.isNullOrBlank()
    }

    private fun showRegisterDialog() {
        var registerErrorMessage = ""
        val registerErrorText: ArrayList<String> = arrayListOf("", "", "", "")
        when {
            registerEmail.isNullOrBlank() -> registerErrorText[0] = "이메일 인증이 필요합니다.\n"
            registerId.isNullOrBlank() -> registerErrorText[1] = "아이디 중복 확인이 필요합니다.\n"
            nickName.isNullOrBlank() -> registerErrorText[2] = "닉네임 중복 확인이 필요합니다.\n"
            password.isNullOrBlank() -> registerErrorText[3] = "비밀번호 입력이 필요합니다."
        }
        registerErrorText.forEach { registerErrorMessage += it }
        customDialogSingleButton(registerErrorMessage)
    }

    private fun createRegisterBody(): RequestRegister {
        return RequestRegister(
            nickName!!,
            password!!,
            registerEmail!!,
            registerId!!
        )
    }

    private fun registerSuccessEvnet(response: ResponseRegister) {
        customLodingDialog.dismissDialog()
        intent.putExtra("id", response.userId)
        finish()
    }
}
