package kr.nutee.nutee_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.term_of_use_activity.*

class TermOfUseActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.term_of_use_activity)

		text_termofuse_ok_btn.setOnClickListener {
			finish()
		}
	}
}
