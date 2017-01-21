package dclib.geometry

fun Short.and(other: Short): Short {
    return toInt().and(other.toInt()).toShort()
}

fun Short.inv(): Short {
    return toInt().inv().toShort()
}