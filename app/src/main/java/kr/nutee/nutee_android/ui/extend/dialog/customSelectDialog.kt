package kr.nutee.nutee_android.ui.extend.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import kr.nutee.nutee_android.R
import org.w3c.dom.Text

fun Context.customSelectDialog(
	reportVisible:Int,
	fixVisible:Int,
	delVisible:Int,
	onReport:()->Unit = {},
	onFix:()->Unit = {},
	onDel:()->Unit = {}
)
{
	val dialog = Dialog(this)
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
	dialog.setCancelable(false)
	dialog.setContentView(R.layout.custom_select_dialog)

	val report_button = dialog.findViewById<Button>(R.id.report_button)
	val fix_button = dialog.findViewById<Button>(R.id.fix_button)
	val del_button = dialog.findViewById<Button>(R.id.del_button)
	val cancle_button = dialog.findViewById<TextView>(R.id.cancel_button_select)

	report_button.visibility = reportVisible
	fix_button.visibility = fixVisible
	del_button.visibility = delVisible

	dialog.show()

	report_button.setOnClickListener {
		dialog.dismiss()
		onReport()
	}
	fix_button.setOnClickListener {
		dialog.dismiss()
		onFix()
	}
	del_button.setOnClickListener {
		dialog.dismiss()
		onDel()
	}
	cancle_button.setOnClickListener {
		dialog.dismiss()
	}
}