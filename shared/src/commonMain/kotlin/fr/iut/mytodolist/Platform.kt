package fr.iut.mytodolist

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform