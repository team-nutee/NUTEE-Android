package kr.nutee.nutee_android.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.NotificationActivityBinding

class NotificationActivity : AppCompatActivity() {

	private val binding by lazy { NotificationActivityBinding.inflate(layoutInflater) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
	}
}
