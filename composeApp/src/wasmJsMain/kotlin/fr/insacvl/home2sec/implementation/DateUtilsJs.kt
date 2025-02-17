package fr.insacvl.home2sec.implementation

import fr.insacvl.home2sec.utils.DateUtils

class DateUtilsJs: DateUtils {
    override fun FormatDate(isoDate: String): String {
        // I found nothing to format date in kotlinJS :(
        return isoDate.replace('T',' ').substring(0..18)
    }

}