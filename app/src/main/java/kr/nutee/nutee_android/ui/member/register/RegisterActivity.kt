package kr.nutee.nutee_android.ui.member.register

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.dialog.customDialog
import kr.nutee.nutee_android.ui.extend.loadFragment
import kr.nutee.nutee_android.ui.extend.loadFragmentAddtoBackStack
import kr.nutee.nutee_android.ui.member.register.fragment.*

/*
* Created by jinsu4755
* DESC: 회원가입 Activity 엑티비티 레이아웃을 띄우고 각 Fragment의 서버통신 로직을 구현함.
*/


class RegisterActivity : AppCompatActivity(), OnRegisterDataSetListener {

	private var registerEmail: String? = null
	private var registerId: String? = null
	private var nickName: String? = null
	private var password: String? = null

	val requestToServer = RequestToServer

	private val emailAuthFragment = EmailAuthFragment()
	private val idInputFragment = IdInputFragment()
	private val nickNameInputFragment = NickNameInputFragment()
	private val selectCategoryFragment = SelectCategoryFragment()
	private val selectDepartmentFragment = SelectDepartmentFragment()

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

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.register_activity)
		init()
	}

	private fun init() {
		registerButtonEventMapping()
		loadEmailAuthFragment()
	}

	private fun registerButtonEventMapping() {
		tv_register_top_back_btn.setOnClickListener {
			onBackPressed()
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
		emailAuthFragment.setEmailAuthEventListener { email ->
			/*
			* TODO
			*  이메일 인증 서버통신
			*  실패시 null 리턴
			*/
		}
	}

	private fun setEmailAuthFragmentOTPAuthEventMapping() {
		emailAuthFragment.setEmailAuthOTPEventListener { otpNum ->
			/*
			* TODO
			*  OTP확인 시 서버통신
			*  실패시 null 리턴
			*/
		}
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
			/*
			* TODO
			*  id 입력 후 다음 버튼 눌렀을 때 서버통신
			*  실패시 null 리턴
			*/
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
		loadFragmentAddtoBackStack(
			nickNameInputFragment,
			R.id.fl_register_frame_layout,
			null
		)
	}

	private fun loadSelectCategoryFragment() {
		loadFragmentAddtoBackStack(
			selectCategoryFragment,
			R.id.fl_register_frame_layout,
			null
		)
	}

	private fun loadSelectDepartmentFragment() {
		loadFragmentAddtoBackStack(
			selectDepartmentFragment,
			R.id.fl_register_frame_layout,
			null
		)
	}
}

