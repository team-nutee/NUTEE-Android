package kr.nutee.nutee_android.ui.extend.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import kr.nutee.nutee_android.R


fun Context.customDialog(msg: String, okClickEvent: ()-> Unit) {
	val dialog = Dialog(this)
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
	dialog.setCancelable(false)
	dialog.setContentView(R.layout.custom_dialog)

	val body = dialog.findViewById<TextView>(R.id.dialog_text)
	body.text = msg
	val okButton = dialog.findViewById<TextView>(R.id.okButton)
	val cancelButton = dialog.findViewById<TextView>(R.id.cancelButton)

	dialog.show()

	okButton.setOnClickListener {
		dialog.dismiss()
		okClickEvent()
	}
	cancelButton.setOnClickListener { dialog.dismiss() }
}