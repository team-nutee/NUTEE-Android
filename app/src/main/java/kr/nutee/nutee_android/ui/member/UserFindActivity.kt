package kr.nutee.nutee_android.ui.member

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.user_find_activity.*
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.ui.extend.textChangedListener

class UserFindActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.user_find_activity)
		init()
	}

	private fun init() {
		editTextEvent()
	}

	private fun editTextEvent(){

		et_find_pw_id.setOnFocusChangeListener { _, hasFocus ->
			if (hasFocus && (cl_find_id.visibility != View.GONE)){hindFindId()}
		}
		et_find_pw_email.setOnFocusChangeListener{_,hasFocus->
			if(hasFocus&& (cl_find_id.visibility != View.GONE)){hindFindId()}
		}

	}

	private fun hindFindId() {
		val hindAnimation = AnimationUtils.loadAnimation(this, R.anim.up_out)
		hindAnimation.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(animation: Animation?) =Unit
			override fun onAnimationEnd(animation: Animation?) {
				cl_find_id.visibility = View.GONE
			}
			override fun onAnimationStart(animation: Animation?)= Unit
		})
		cl_find_id.startAnimation(hindAnimation)
	}

	private fun showFindId() {
		val showAnimation = AnimationUtils.loadAnimation(this,R.anim.down_in)
		showAnimation.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationRepeat(animation: Animation?) = Unit
			override fun onAnimationEnd(animation: Animation?) {
				cl_find_id.visibility = View.VISIBLE
			}
			override fun onAnimationStart(animation: Animation?) =Unit
		})
		cl_find_id.visibility = View.VISIBLE
		cl_find_id.startAnimation(showAnimation)
	}

	override fun onBackPressed() {
		if (cl_find_id.visibility == View.GONE) {
			showFindId()
		} else {
			super.onBackPressed()
		}
	}
}
