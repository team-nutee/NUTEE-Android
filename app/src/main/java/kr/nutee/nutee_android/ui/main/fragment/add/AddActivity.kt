package kr.nutee.nutee_android.ui.main.fragment.add

import android.app.Dialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_activity.*
import kotlinx.android.synthetic.main.main_fragment_search.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.main.MainActivity

/*
* 글쓰기
*/
class AddActivity : AppCompatActivity(){

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.add_activity)

		//내용이 변경되는 이벤트 처리
		et_add_content.addTextChangedListener(contentWatcher)


		text_back_button.setOnClickListener {
			onBackPressed()
		}
	}

	override fun onResume() {
		super.onResume()

		//해당 edit Text 에 자동 클릭하도록 한다.
		et_add_content.requestFocus()
		//자동으로 키보드가 올라오는 이벤트 처리
		val inputMethodManager : InputMethodManager = getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.showSoftInput(et_add_content,0)
	}

	override fun onBackPressed() {
		//뒤로가기 버튼을 클릭한경우 custom dialog를 띄우고 이벤트 처리
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

