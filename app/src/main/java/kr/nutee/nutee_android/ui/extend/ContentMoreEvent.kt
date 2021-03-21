package kr.nutee.nutee_android.ui.extend

import android.content.Context
import android.util.Log
import android.view.View
import kr.nutee.nutee_android.data.App
import kr.nutee.nutee_android.data.main.home.User
import kr.nutee.nutee_android.ui.extend.dialog.customSelectDialog


/*
* Created by 88yhsterof
* DESC: 게시글, 댓글, 답글의 수정, 삭제, 신고 이벤트를 위한 확장 함수
*/
fun Context.contentMoreEvent(
    user: User?,
    replyVisible: Int,
    replyContent:()->Unit,
    rewriteContent: () -> Unit,
    deleteContent: () -> Unit,
    reportContent: () -> Unit
) {
    if (user?.id.toString() == App.prefs.local_user_id) {
        customSelectDialog(
            View.GONE, replyVisible, View.VISIBLE, View.VISIBLE, {},
            {Log.d("Network", " 답글 생성 버튼 누름")
                replyContent()
            },
            { Log.d("Network", " 콘텐츠 수정 버튼 누름")
                rewriteContent()
            },
            { Log.d("Network", " 콘텐츠 삭제 버튼 누름")
                deleteContent()
            })
    } else {
        customSelectDialog(
            View.VISIBLE, replyVisible, View.GONE, View.GONE,
            { Log.d("Network", " 콘텐츠 신고 버튼 누름")
                reportContent()
            },
            {Log.d("Network", " 답글 생성 버튼 누름")
                replyContent()
            }
        )
    }
}