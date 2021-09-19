package kr.nutee.nutee_android.ui.member

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_activity.*
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.member.login.RequestLogin
import kr.nutee.nutee_android.databinding.LoginActivityBinding
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.dialog.CustomLodingDialog
import kr.nutee.nutee_android.ui.extend.dialog.customDialogDevInfo
import kr.nutee.nutee_android.ui.extend.dialog.customDialogSingleButton
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.main.MainActivity
import kr.nutee.nutee_android.ui.member.register.RegisterActivity
import kotlin.math.log

class LoginActivity : AppCompatActivity(), View.OnClickListener {

	private val binding by lazy { LoginActivityBinding.inflate(layoutInflater) }
	private val REQUEST_CODE = 1
	private val requestToServer = RequestToServer
	lateinit var loadingDialog: CustomLodingDialog
	private val logTag = "LoginActivityButtonEv"
	private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
		if (it.resultCode == Activity.RESULT_OK) {
				binding.etLoginId.setText(it.data?.getStringExtra("id"))
		}
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
		loadingDialog = CustomLodingDialog(this)
		autoLogin()

		//ID&PW 입력 이벤트 처리
		binding.etLoginId.textChangedListener {
			binding.btnLogin.isEnabled = binding.etLoginId.text.isNotEmpty() && binding.etLoginPw.text.isNotEmpty()
		}
		binding.etLoginPw.textChangedListener {
			binding.btnLogin.isEnabled = binding.etLoginId.text.isNotEmpty() && binding.etLoginPw.text.isNotEmpty()
		}

		//버튼 이벤트 처리
		binding.textForgetIdOrPwButton.setOnClickListener(this)
		binding.btnLogin.setOnClickListener(this)
		binding.textRegisterButton.setOnClickListener(this)
	}

	private fun autoLogin() {
		//자동 로그인 설정 시 ID&PW창을 채워주고 자동로그인
		if (App.prefs.local_login_oto) {
			binding.etLoginId.setText(App.prefs.local_login_id)
			binding.etLoginPw.setText(App.prefs.local_login_pw)
			requestlogin(App.prefs.local_login_id, App.prefs.local_login_pw)
			return
		}
	}


	//클릭 이벤트 구현
	override fun onClick(v: View?) {
		//해당 View의 id 값을 가져올 변수 id 생성

		//각 버튼인 경우 동작 매칭
		when (v!!.id) {
			//id&pw 찾기 버튼 클릭시
			binding.textForgetIdOrPwButton.id -> {
				val intent = Intent(this, UserFindActivity::class.java)
				startActivity(intent)
			}

			//로그인 버튼 클릭 시
			binding.btnLogin.id ->{
				Log.d(logTag, "login button click")

				if(binding.etLoginId.text.isNullOrBlank() || binding.etLoginPw.text.isNullOrBlank()){
					when(true){
						binding.etLoginId.text.isNullOrBlank() -> Snackbar.make(
							v,
							"아이디를 입력하세요",
							Snackbar.LENGTH_SHORT
						).show()
						binding.etLoginPw.text.isNullOrBlank() -> Snackbar.make(
							v,
							"패스워드를 입력하세요",
							Snackbar.LENGTH_SHORT
						).show()
					}
				}else {
					requestlogin(binding.etLoginId.text.toString(), binding.etLoginPw.text.toString())
				}
			}

			//회원가입 버튼 클릭시
			binding.textRegisterButton.id ->{
				Log.d(logTag, "register button test")

				val intent = Intent(this, RegisterActivity::class.java)
				//startActivityForResult(intent, REQUEST_CODE)
				getContent.launch(intent)
			}

			/*
			//id&pw 찾기 버튼 클릭시
			R.id.text_forget_id_or_pw_button -> {
				val intent = Intent(this, UserFindActivity::class.java)
				startActivity(intent)
			}
			//로그인 버튼 클릭시
			R.id.btn_login -> {
				Log.d(logTag, "login button click")
				if(binding.etLoginId.text.isNullOrBlank() || binding.etLoginPw.text.isNullOrBlank()){
					when(true){
						binding.etLoginId.text.isNullOrBlank() -> Snackbar.make(
								v,
								"아이디를 입력하세요",
								Snackbar.LENGTH_SHORT
						).show()
						binding.etLoginPw.text.isNullOrBlank() -> Snackbar.make(
								v,
								"패스워드를 입력하세요",
								Snackbar.LENGTH_SHORT
						).show()
					}
				}else {
					requestlogin(binding.etLoginId.text.toString(), binding.etLoginPw.text.toString())
				}
			}
			//회원가입 버튼 클릭시
			R.id.text_register_button -> {
				Log.d(logTag, "register button test")
				val intent = Intent(this, RegisterActivity::class.java)
				startActivityForResult(intent, REQUEST_CODE)
			}*/

		}
	}

	private fun requestlogin(id: String, pw: String) {
		loadingDialog.startLoadingDialog()
		requestToServer.authService
			.requestLogin(
				RequestLogin(
					userId = id,
					password = pw
				)
			).customEnqueue(
				onSuccess = {
					Log.d(logTag, "로그인 성공")

					//자동 로그인 설정 시 설정 여부 저장
					if(binding.checkLoginSave.isChecked)
						App.prefs.local_login_oto=true

					App.prefs.local_login_id = binding.etLoginId.text.toString()
					App.prefs.local_login_pw = binding.etLoginPw.text.toString()

					val token = it.body()!!.body.accessToken
					App.prefs.local_login_token = token
					App.prefs.local_user_id = it.body()!!.body.memberId.toString()
					Log.d(logTag, App.prefs.local_login_token)
					Log.d(logTag, App.prefs.local_user_id)

					loadingDialog.dismissDialog()

					val intent = Intent(this, MainActivity::class.java)
					startActivity(intent)
					finish()

				},
				onError = {
					loadingDialog.dismissDialog()
					Log.d(logTag, "로그인 실패")
					showTextShake(binding.textLoginIdCheck, "아이디 혹은 비밀번호가 확실하지 않습니다")
					showTextShake(binding.textLoginPwCheck, "아이디 혹은 비밀번호가 확실하지 않습니다")
				}
			)
	}

	//TODO 이부분이 불안정한 코드이므로 수정이 필요하다
	//FIXME
	/*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				binding.etLoginId.setText(data?.getStringExtra("id"))
			}
		}
	}*/

	/* 애니메이션 설정 */
	private fun showTextShake(myTextView: TextView, msg: String) {
		myTextView.text = msg
		val animation: Animation =
			AnimationUtils.loadAnimation(applicationContext, R.anim.shake)
		myTextView.startAnimation(animation)
		myTextView.visibility = View.VISIBLE
	}

	companion object {
		private const val REQUEST_CODE = 1
	}

	override fun onBackPressed() {
		//뒤로 가기 버튼 막음
	}
}