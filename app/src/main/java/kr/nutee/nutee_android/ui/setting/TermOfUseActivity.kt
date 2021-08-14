package kr.nutee.nutee_android.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.term_of_use_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.TermOfUseActivityBinding

class TermOfUseActivity : AppCompatActivity() {

	private val binding by lazy{TermOfUseActivityBinding.inflate(layoutInflater)}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		binding.textTermofuseOkBtn.setOnClickListener {
			finish()
		}
	}
}
