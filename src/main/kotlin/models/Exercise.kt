package models

data class Exercise(
    var exerciseName: String,
    var sets: Int,
    var reps: Int,
    var weightKg: Double,
    var category: String,
    var isCompleted: Boolean = false
) {
    override fun toString(): String {
        val completedStatus = if (isCompleted) "Completed" else "Not Completed"

        return """
            Exercise: $exerciseName
            Sets: $sets
            Reps: $reps
            Weight: ${weightKg}kg
            Category: $category
            Status: $completedStatus
        """.trimIndent()
    }
}