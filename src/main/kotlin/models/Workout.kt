package models

data class Workout(
    var workoutName: String,
    var workoutDate: String,
    var workoutType: String,
    var durationMinutes: Int,
    var isCompleted: Boolean = false,
    var exercises: MutableSet<Exercise> = mutableSetOf(),
) {
    override fun toString(): String {
        val completedStatus = if (isCompleted) "Completed" else "Not Completed"

        return """
            Workout: $workoutName
            Date: $workoutDate
            Type: $workoutType
            Duration: $durationMinutes minutes
            Status: $completedStatus
            Exercises: ${exercises.size}
            """.trimIndent()
    }
}
