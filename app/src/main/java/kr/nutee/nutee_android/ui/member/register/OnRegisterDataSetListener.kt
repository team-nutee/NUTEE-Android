package kr.nutee.nutee_android.ui.member.register

interface OnRegisterDataSetListener {

	fun onRegisterEmailDataSetListener(email:String)

	fun onRegisterIdDataSetListener(id: String)

	fun onRegisterNickNameDataSetListerner(nickName: String)

	fun onRegisterPasswordDataSetListener(password:String)
}