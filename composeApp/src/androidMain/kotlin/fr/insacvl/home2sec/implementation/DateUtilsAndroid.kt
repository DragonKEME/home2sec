package fr.insacvl.home2sec.implementation

import android.os.Build
import fr.insacvl.home2sec.utils.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

class DateUtilsAndroid: DateUtils {

    override fun FormatDate(isoDate: String): String {
        if (Build.VERSION.SDK_INT >= 26) {
            return try {
                // Parse message at UTC
                val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                LocalDateTime.parse(isoDate, formatter).atOffset(ZoneOffset.UTC)
                    // Format it at system default timezone
                    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        .withZone(ZoneId.systemDefault())
                    )
            }catch (e: ParseException){
                // Error: keep original message
                isoDate.replace('T',' ')
            }
        }else {
            // Old SDK (never tested)
            val formatter = SimpleDateFormat("yyyy-MM-ddTHH:mm:ss", Locale.getDefault())
            return formatter.parse(isoDate)?.toString() ?: isoDate.replace('T', ' ')
        }
    }

}