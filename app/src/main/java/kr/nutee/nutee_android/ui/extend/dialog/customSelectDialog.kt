package kr.nutee.nutee_android.ui.extend.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import kr.nutee.nutee_android.R

fun Context.customSelectDialog(
	reportVisible:Int,
	replyVisible:Int,
	rewriteVisible:Int,
	delVisible:Int,
	onReport:()->Unit = {},
	onReply:()->Unit = {},
	onRewrite:()->Unit = {},
	onDel:()->Unit = {}
)
{
	val dialog = Dialog(this)
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
	dialog.setCancelable(false)
	dialog.setContentView(R.layout.custom_select_dialog)

	val report_button = dialog.findViewById<Button>(R.id.report_button)
	val reply_button = dialog.findViewById<Button>(R.id.reply_button)
	val fix_button = dialog.findViewById<Button>(R.id.fix_button)
	val del_button = dialog.findViewById<Button>(R.id.del_button)
	val cancle_button = dialog.findViewById<TextView>(R.id.cancel_button_select)

	report_button.visibility = reportVisible
	reply_button.visibility = replyVisible
	fix_button.visibility = rewriteVisible
	del_button.visibility = delVisible

	dialog.show()

	report_button.setOnClickListener {
		dialog.dismiss()
		onReport()
	}
	reply_button.setOnClickListener {
		dialog.dismiss()
		onReply()
	}
	fix_button.setOnClickListener {
		dialog.dismiss()
		onRewrite()
	}
	del_button.setOnClickListener {
		dialog.dismiss()
		onDel()
	}
	cancle_button.setOnClickListener {
		dialog.dismiss()
	}
}