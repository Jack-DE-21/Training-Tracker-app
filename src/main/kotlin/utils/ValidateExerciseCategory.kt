package utils

val exerciseCategories =
    setOf(
        "Chest",
        "Back",
        "Legs",
        "Shoulders",
        "Arms",
        "Core",
        "Cardio",
        "Full Body",
    )

fun isValidExerciseCategory(categoryToCheck: String): Boolean {
    return exerciseCategories.any { category ->
        category.equals(categoryToCheck, ignoreCase = true)
    }
}
