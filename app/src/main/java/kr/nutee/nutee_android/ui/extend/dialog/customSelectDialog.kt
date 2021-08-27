package kr.nutee.nutee_android.ui.extend.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.TextView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.CustomDialogBinding
import kr.nutee.nutee_android.databinding.CustomSelectDialogBinding
import kr.nutee.nutee_android.databinding.MainActivityBinding

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
	val binding: CustomSelectDialogBinding = CustomSelectDialogBinding.inflate(LayoutInflater.from(this))

	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
	dialog.setCancelable(false)
	dialog.setContentView(binding.root)
	//dialog.setContentView(R.layout.custom_select_dialog)

	val report_button = binding.reportButton
	val reply_button = binding.replyButton
	val fix_button = binding.fixButton
	val del_button = binding.delButton
	val cancle_button = binding.cancelButtonSelect

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