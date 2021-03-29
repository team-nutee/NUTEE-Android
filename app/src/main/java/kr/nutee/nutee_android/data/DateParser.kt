package kr.nutee.nutee_android.data

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow

class DateParser(dateTiem:String) {
	/*2020-04-23T15:10:26.000Z*/
	private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	private val talkDateFormat = SimpleDateFormat("yyyy.MM.dd")
	private val TIMES_DIFFERENCE=32400000

	val dateYMD = dateTiem.split("T")[0]
	val dateT = dateTiem.split("T")[1].split(".")[0]
	val newDateTime = "$dateYMD $dateT"

	private fun parseStringToCalendar(raw: String) : Calendar {
		val date = dateFormat.parse(raw)
		return Calendar.getInstance().apply { time = date ?: Date()}
	}

	/* 시간차이 계산 함수*/
	fun calculateDiffDate() : String {
		val parsedDate = parseStringToCalendar(newDateTime)
		Log.d("parsedDate","$parsedDate")

		val diff
		=Calendar.getInstance(Locale.KOREA).timeInMillis-(parsedDate.timeInMillis+TIMES_DIFFERENCE)

		val diffMin = diff / 1000 / 60
		val diffDay = diffMin / 60 / 24
		val diffHour = diffMin / 60

		return when {
			diffDay >=30 -> convertToTalkDateString(parsedDate)
			diffDay in 1..29 -> "${diffDay}일 전"
			diffHour > 0 -> "${diffHour}시간 전"
			diffMin > 0 -> "${diffMin}분 전"
			else -> "방금"
		}
	}

	private fun convertToTalkDateString(cal: Calendar) : String
			= talkDateFormat.format(cal.time)

}