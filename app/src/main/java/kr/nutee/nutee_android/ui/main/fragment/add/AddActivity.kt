package kr.nutee.nutee_android.ui.main.fragment.add

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.main.MainActivity


class AddActivity : AppCompatActivity(){

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.add_activity)



		//내용이 변경되는 이벤트 처리
		et_add_content.addTextChangedListener(contentWatcher)
		//자동으로 키보드가 올라오도록 한다.
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

		text_back_button.setOnClickListener {
			onBackPressed()
		}

	}

	override fun onBackPressed() {
		val dialog = Dialog(this)
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
		dialog.setCancelable(false)
		dialog.setContentView(R.layout.custom_dialog)

		val body = dialog.findViewById<TextView>(R.id.dialog_text)
		body.text = "작성을 취소하시겠습니까??"
		val okButton = dialog.findViewById<Button>(R.id.okButton)
		val cancelButton = dialog.findViewById<Button>(R.id.cancelButton)

		okButton.setOnClickListener {
			dialog.dismiss()
			val intent = Intent(this, MainActivity::class.java)
			startActivity(intent)
			finish()
		}
		cancelButton.setOnClickListener { dialog.dismiss() }

		dialog.show()
	}



	private val contentWatcher = object : TextWatcher {
		override fun afterTextChanged(s: Editable?) {

		}

		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

		}

		//내용이 변경될 경우
		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			//내용이 null or Blank가 아닌경우 create 버튼 활성화
			text_create_button.isEnabled = !s.isNullOrBlank()
		}

	}
}

