package kr.nutee.nutee_android.ui.member

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_activity.*
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.data.member.login.RequestLogin
import kr.nutee.nutee_android.data.member.login.ResponseLogin
import kr.nutee.nutee_android.network.RequestToServer
import kr.nutee.nutee_android.ui.extend.customEnqueue
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.main.MainActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

	private val REQUEST_CODE = 1
	private val requestToServer = RequestToServer

	val logTag = "LoginActivityButtonEv"


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.login_activity)

		//prefs에 저장된 정보가있다면 ID&PW창을 채워주고 자동로그인
		if (!App.prefs.local_login_id.isBlank() && !App.prefs.local_login_pw.isBlank()) {
			et_login_id.setText(App.prefs.local_login_id)
			et_login_pw.setText(App.prefs.local_login_pw)
			requestlogin(App.prefs.local_login_id,App.prefs.local_login_pw)
		}

		//ID&PW 입력 이벤트 처리
		et_login_id.textChangedListener {
			btn_login.isEnabled = et_login_id.text.isNotEmpty() && et_login_pw.text.isNotEmpty()
		}
		et_login_pw.textChangedListener {
			btn_login.isEnabled = et_login_id.text.isNotEmpty() && et_login_pw.text.isNotEmpty()
		}


		//버튼 이벤트 처리
		text_forget_id_or_pw_button.setOnClickListener(this)
		btn_login.setOnClickListener(this)
		text_register_button.setOnClickListener(this)
	}


	//클릭 이벤트 구현
	override fun onClick(v: View?) {
		//해당 View의 id 값을 가져올 변수 id 생성

		//각 버튼인 경우 동작 매칭
		when (v!!.id) {
			//id&pw 찾기 버튼 클릭시
			R.id.text_forget_id_or_pw_button -> {
				val intent = Intent(this,UserFindActivity::class.java)
				startActivity(intent)
			}

			//로그인 버튼 클릭시
			R.id.btn_login -> {
				Log.d(logTag, "login button click")

				if (et_login_id.text.isNullOrBlank() || et_login_pw.text.isNullOrBlank()) {
					when (true) {
						et_login_id.text.isNullOrBlank() -> Snackbar.make(v, "아이디를 입력하세요", Snackbar.LENGTH_SHORT).show()
						et_login_pw.text.isNullOrBlank() -> Snackbar.make(v, "패스워드를 입력하세요", Snackbar.LENGTH_SHORT).show()
					}
				} else {
					requestlogin(et_login_id.text.toString(),et_login_pw.text.toString())
				}
			}

			//회원가입 버튼 클릭시
			R.id.text_register_button -> {
				Log.d(logTag, "register button test")

				val intent = Intent(this, RegisterActivity::class.java)
				startActivityForResult(intent,REQUEST_CODE)
			}

		}
	}

	private fun requestlogin(id:String,pw:String) {
		requestToServer.service.requestLogin(
			RequestLogin(
				userId = id,
				password = pw
			)
		).customEnqueue(
			onSuccess = {
				if (it.isSuccessful) {
					if (check_login_save.isChecked) {
						//로그인 유지시 id/pw 저장
						App.prefs.local_login_id = et_login_id.text.toString()
						App.prefs.local_login_pw = et_login_pw.text.toString()
					}
					Log.d(logTag,"로그인 성공")
					val cookie = it.headers()["Set-Cookie"].toString()
					val token = cookie.split(";")
					App.prefs.local_login_token = token[0]
					App.prefs.local_user_id = it.body()!!.id.toString()
					Log.d(logTag,App.prefs.local_login_token)
					Log.d(logTag,App.prefs.local_user_id)
					val intent = Intent(this, MainActivity::class.java)
					startActivity(intent)
					finish()
				} else if (it.code() == 401){
					Log.d(logTag,"로그인 실패")
					showTextShake(text_login_id_check,"아이디 혹은 비밀번호가 확실하지 않습니다")
					showTextShake(text_login_pw_check,"아이디 혹은 비밀번호가 확실하지 않습니다")
				}
			}
		)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				et_login_id.setText(data?.getStringExtra("id"))
				et_login_pw.setText(data?.getStringExtra("pw"))
			}
		}
	}

	/* 애니메이션 설정 */
	private fun showTextShake(myTextView: TextView, msg:String) {
		myTextView.text = msg
		val animation: Animation =
			AnimationUtils.loadAnimation(applicationContext, R.anim.shake)
		myTextView.startAnimation(animation)
		myTextView.visibility = View.VISIBLE
	}
}

