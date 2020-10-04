package kr.nutee.nutee_android.data.main.search

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View

class Hashtag(): ClickableSpan() {
	override fun updateDrawState(ds:TextPaint) {
		super.updateDrawState(ds)
		ds.color = Color.BLUE
	}

	override fun onClick(view: View) {
		Log.d("hashtagText", "클릭")
	}
}