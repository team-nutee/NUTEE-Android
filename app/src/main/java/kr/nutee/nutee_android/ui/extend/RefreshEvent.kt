package kr.nutee.nutee_android.ui.extend

import android.content.Context
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


/*
* Created by 88yhsterof
* DESC: Refresh 이벤트를 위한 확장 함수
*/

fun Context.RefreshEvent(view: SwipeRefreshLayout, refreshListener: () -> Unit){
    view.apply {
        setColorSchemeResources(kr.nutee.nutee_android.R.color.nuteeBase)
        setOnRefreshListener{
            refreshListener()
            view.isRefreshing = false
        }
    }
}