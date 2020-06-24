package kr.nutee.nutee_android.ui.extend.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import kr.nutee.nutee_android.R

fun Context.cumstomReportDialog(reportEvent:(String)->Unit) {
	val dialog = Dialog(this)
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
	dialog.setCancelable(false)
	dialog.setContentView(R.layout.custom_report_dialog)

	val et_report = dialog.findViewById<EditText>(R.id.et_report)
	val cancel_button = dialog.findViewById<Button>(R.id.btn_cancel_button)
	val report_button = dialog.findViewById<Button>(R.id.btn_report_button)

	dialog.show()

	cancel_button.setOnClickListener { dialog.dismiss() }
	report_button.setOnClickListener {
		dialog.dismiss()
		reportEvent(et_report.text.toString())
	}


}