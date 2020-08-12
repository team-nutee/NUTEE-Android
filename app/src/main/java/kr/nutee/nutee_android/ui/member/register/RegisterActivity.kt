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


class RegisterActivity : AppCompatActivity(),OnRegisterDataSetListener {

	private var registerEmail:String? = null

	val requestToServer = RequestToServer

	private val emailAuthFragment = EmailAuthFragment()
	private val idInputFragment = IdInputFragment()
	private val nickNameInputFragment = NickNameInputFragment()
	private val selectCategoryFragment = SelectCategoryFragment()
	private val selectDepartmentFragment = SelectDepartmentFragment()

	override fun onRegisterEmailDataSetListener(email: String) {
		this.registerEmail = email
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

	override fun onBackPressed() {
		customDialog(
			"회원가입을 종료하시겠습니까?😥"
		) {
			super.onBackPressed()
		}
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
		emailAuthFragment.setEmailAuthEventListener {email->
			Log.d("EmailTest",email.text.toString())
		}
	}

	private fun setEmailAuthFragmentOTPAuthEventMapping(){
		emailAuthFragment.setEmailAuthOTPEventListener { otpNum ->
			//opt 확인하기 누르면 실행될 이벤트 작성.
		}
	}

	private fun setEmailAuthFragmentNextEventMapping(){
		emailAuthFragment.setRegisterEmailNext {
			loadIdInputFragment()
		}
	}

	private fun setEmailAuthFragmentPreviousEventMapping(){
		emailAuthFragment.setRegisterEmailPrevious {
			onBackPressed()
		}
	}

	private fun loadIdInputFragment() {
		loadFragmentAddtoBackStack(
			idInputFragment,
			R.id.fl_register_frame_layout,
			null
		)
	}
}

