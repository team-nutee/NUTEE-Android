package kr.nutee.nutee_android.data.main.search

import android.content.Context
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast

class Hashtag(context: Context): ClickableSpan() {

//	interface HashtagClickEventListener{
//		fun onClickEvent(data:String){
//		}
//	}

	//private var clickEventListener: HashtagClickEventListener? =null
	//private lateinit var textPaint:TextPaint


//	fun setOnClickEventListener(listener:HashtagClickEventListener ){
//		clickEventListener = listener
//	}

	override fun updateDrawState(ds:TextPaint) {
		ds.color = ds.linkColor
	}

	override fun onClick(view: View) {
		Log.d("hashtagText","클릭")
//		val tv: TextView = widget as TextView
//		val spanned: Spanned = tv.text as Spanned
//		val start = spanned.getSpanStart(this)
//		val end = spanned.getSpanEnd(this)
//		val theWord =spanned.subSequence(start + 1, end).toString()
		//clickEventListener?.onClickEvent(theWord)
	}
}