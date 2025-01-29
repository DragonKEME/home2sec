package fr.insacvl.home2sec.implementation

import fr.insacvl.home2sec.utils.DateUtils

class DateUtilsJs: DateUtils {
    override fun FormatDate(isoDate: String): String {
        return isoDate.replace('T',' ').substring(0..18)
    }

}