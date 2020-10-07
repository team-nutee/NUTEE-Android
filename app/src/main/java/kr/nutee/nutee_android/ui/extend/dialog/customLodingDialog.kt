package kr.nutee.nutee_android.ui.extend.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import kr.nutee.nutee_android.R

class CustomLodingDialog(private val activity: Activity){

	private lateinit var alertDialog:AlertDialog

	fun startLoadingDialog(){
		val bulider: AlertDialog.Builder = AlertDialog.Builder(activity)

		val layoutInflater:LayoutInflater = activity.layoutInflater
		bulider.setView(layoutInflater.inflate(R.layout.custom_loading_dialog, null))
		bulider.setCancelable(false)

		alertDialog = bulider.create()
		alertDialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
		alertDialog.show()
	}

	fun dismissDialog(){
		alertDialog.dismiss()
	}
}