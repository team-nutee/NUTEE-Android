package kr.nutee.nutee_android.ui.extend.animation

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun Context.glideProgressDrawable(): CircularProgressDrawable {
	val circularProgressDrawable = CircularProgressDrawable(this)
	circularProgressDrawable.strokeWidth = 5f
	circularProgressDrawable.centerRadius = 30f
	circularProgressDrawable.start()

	return circularProgressDrawable
}