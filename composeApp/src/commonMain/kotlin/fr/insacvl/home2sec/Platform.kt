package fr.insacvl.home2sec

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform