package kr.nutee.nutee_android.ui.member.register

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.register_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.dialog.customDialog
import kr.nutee.nutee_android.ui.extend.loadFragmentAddtoBackStack
import kr.nutee.nutee_android.ui.member.register.fragment.EmailAuthFragment


class RegisterActivity : AppCompatActivity(),OnRegisterDataSetListener {

	private var registerEmail:String? = null

	val requestToServer = RequestToServer

	private val emailAuthFragment = EmailAuthFragment()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.register_activity)
		init()
	}

	private fun init() {
		registerButtonEventMapping()
		emailAuthFragmentMapping()
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

	private fun emailAuthFragmentMapping() {
		emailAuthFragment.setEmailAuthEventListener {email->
			Log.d("EmailTest",email.text.toString())
		}
		loadFragmentAddtoBackStack(
			emailAuthFragment,
			R.id.fl_register_frame_layout,
			"emailAuth"
		)
	}

	override fun onRegisterEmailDataSetListener(email: String) {
		this.registerEmail = email
	}

}

