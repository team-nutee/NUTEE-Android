package kr.nutee.nutee_android.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.main.MainActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

	private val REQUEST_CODE = 1


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.login_activity)

		et_login_id.addTextChangedListener(textWatcher)
		et_login_pw.addTextChangedListener(textWatcher)

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
					val intent = Intent(this, MainActivity::class.java)
					startActivity(intent)
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

	private val textWatcher = object : TextWatcher {
		override fun afterTextChanged(s: Editable?) {

		}

		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

		}

		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			btn_login.isEnabled = et_login_id.text.isNotEmpty() && et_login_pw.text.isNotEmpty()
		}

	}


}
