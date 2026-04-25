package utils

fun isValidRange(
    numberToCheck: Int,
    min: Int,
    max: Int,
): Boolean {
    return numberToCheck in min..max
}

fun isValidDoubleRange(
    numberToCheck: Double,
    min: Double,
    max: Double,
): Boolean {
    return numberToCheck in min..max
}
