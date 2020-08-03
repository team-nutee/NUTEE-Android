package kr.nutee.nutee_android.ui.extend.animation

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import kr.nutee.nutee_android.R

/* 애니메이션 설정 */
fun Context.showTextShake(myTextView: TextView, msg: String, color:Int) {
	myTextView.text = msg
	myTextView.setTextColor(this.getColor(color))
	val animation: Animation =
		AnimationUtils.loadAnimation(this, R.anim.shake)
	myTextView.startAnimation(animation)
	myTextView.visibility = View.VISIBLE
}