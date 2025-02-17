package fr.insacvl.home2sec.utils

/**
 * Interface pour les fonctions de manipulation de date
 *
 * La manipulation des dates est propre à chaque plateforme, avec chacune sa propre API.
 * On passe donc par une interface à implémenter et à ajouter dans la *configuration*
 */
interface DateUtils {
    /**
     * Transforme le dateTime string sous la forme ISO-8601 en date plus lisible
     */
    fun FormatDate(isoDate: String): String
}