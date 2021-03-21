package kr.nutee.nutee_android.ui.extend

import android.util.Log
import android.view.View
import android.widget.TextView
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.home.Liker

/*
* 88yhtserof
* 좋아요 이벤트 관련 확장함수
* */
fun setLikeEvent(it: View, countView: TextView, likers: Array<Liker>?,){
    val boolLike = likers?.any{ liker: Liker ->
        liker.id.toString() == App.prefs.local_login_id
    }
    Log.d("setLike", "댓글 좋아요 설정$boolLike")
    if (boolLike != null)
        it.isActivated = boolLike

    if(likers?.size!=null)
        countView.text=likers.size.toString()
}

fun loadUnLike(it: View,countView: TextView){
    it.isActivated = false
    (countView.text.toString().toInt()-1).toString()
            .also { countView.text = it }
}

fun loadLike(it: View,countView: TextView,likers: Array<Liker>?){
    it.isActivated = true
    countView.text = likers?.size.toString()
}