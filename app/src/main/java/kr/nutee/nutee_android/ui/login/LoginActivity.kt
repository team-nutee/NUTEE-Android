package kr.nutee.nutee_android.ui.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_activity.*
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.textChangedListener
import kr.nutee.nutee_android.ui.main.MainActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

	private val REQUEST_CODE = 1


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.login_activity)

		//prefs에 저장된 정보가있다면 ID&PW창을 채워주고 자동로그인
		if (!App.prefs.local_login_id.isNullOrBlank() && !App.prefs.local_login_pw.isNullOrBlank()) {
			et_login_id.setText(App.prefs.local_login_id)
			et_login_pw.setText(App.prefs.local_login_pw)

			Handler().postDelayed({
				val intent = Intent(this, MainActivity::class.java)
				startActivity(intent)
				finish()
			}, 500)
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
		val logTag = "LoginActivityButtonEv"
		//해당 View의 id 값을 가져올 변수 id 생성

		//각 버튼인 경우 동작 매칭
		when (v!!.id) {
			//id&pw 찾기 버튼 클릭시
			R.id.text_forget_id_or_pw_button -> {
				Log.d(logTag, "forget id or pw text button test")
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
					if (check_login_save.isChecked) {
						//로그인 유지시 id/pw 저장
						App.prefs.local_login_id = et_login_id.text.toString()
						App.prefs.local_login_pw = et_login_pw.text.toString()
					}
					val intent = Intent(this, MainActivity::class.java)
					startActivity(intent)
					finish()
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

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				et_login_id.setText(data?.getStringExtra("id"))
				et_login_pw.setText(data?.getStringExtra("pw"))
			}
		}
	}
}

