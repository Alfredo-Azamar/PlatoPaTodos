package mx.mr.platopatodos.model

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class MyDate() {
    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Mexico_City"))
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}