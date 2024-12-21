package org.sazib.iosandroid

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform