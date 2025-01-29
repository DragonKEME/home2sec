package fr.insacvl.home2sec.implementation

import fr.insacvl.home2sec.utils.DateUtils
import java.text.ParseException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class DateUtilsJava: DateUtils {
    override fun FormatDate(isoDate: String): String {
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
    }
}