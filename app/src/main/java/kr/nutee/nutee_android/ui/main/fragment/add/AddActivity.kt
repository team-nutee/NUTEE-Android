package kr.nutee.nutee_android.ui.main.fragment.add

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_activity.*
import kotlinx.android.synthetic.main.custom_dialog.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.main.MainActivity


class AddActivity : AppCompatActivity() {

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
		onDialog()
		super.onBackPressed()
	}

	private fun onDialog() {
		val builder = AlertDialog.Builder(this)
		val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
		val dialogText = dialogView.findViewById<TextView>(R.id.dialog_text)
		dialogText.text = "작성을 취소하시겠습니까??"


		builder.setView(dialogView).setPositiveButton("예",this.okButton.setOnClickListener {  })
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
