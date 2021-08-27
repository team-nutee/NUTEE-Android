package kr.nutee.nutee_android.ui.extend.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kr.nutee.nutee_android.R
import kr.nutee.nutee_android.databinding.CustomDialogBinding
import kr.nutee.nutee_android.databinding.CustomReportDialogBinding

fun Context.cumstomReportDialog(dialogText:String,reportEvent:(String)->Unit) {
	val dialog = Dialog(this)
	val binding: CustomReportDialogBinding = CustomReportDialogBinding.inflate(LayoutInflater.from(this))
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
	dialog.setCancelable(false)
	dialog.setContentView(binding.root)
	//dialog.setContentView(R.layout.custom_report_dialog)

	dialog.show()

	binding.dialogText.text=dialogText
	binding.btnCancelButton.setOnClickListener { dialog.dismiss() }
	binding.btnReportButton.setOnClickListener {
		dialog.dismiss()
		reportEvent(binding.etReport.text.toString())
	}
}