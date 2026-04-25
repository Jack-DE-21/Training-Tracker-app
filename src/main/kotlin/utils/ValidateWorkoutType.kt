package utils

val workoutTypes =
    setOf(
        "Strength",
        "Cardio",
        "Mobility",
        "Hypertrophy",
        "Conditioning",
        "Recovery",
    )

fun isValidWorkoutType(typeToCheck: String): Boolean {
    return workoutTypes.any { type ->
        type.equals(typeToCheck, ignoreCase = true)
    }
}
