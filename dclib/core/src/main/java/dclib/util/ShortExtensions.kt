package dclib.util

fun Short.or(other: Short): Short {
    return toInt().or(other.toInt()).toShort()
}

fun Short.and(other: Short): Short {
    return toInt().and(other.toInt()).toShort()
}