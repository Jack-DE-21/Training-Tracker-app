package models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ExerciseTest {
    @Test
    fun `exercise stores correct details`() {
        val exercise =
            Exercise(
                exerciseName = "Bench Press",
                sets = 3,
                reps = 10,
                weightKg = 60.0,
                category = "Chest",
            )

        assertEquals("Bench Press", exercise.exerciseName)
        assertEquals(3, exercise.sets)
        assertEquals(10, exercise.reps)
        assertEquals(60.0, exercise.weightKg)
        assertEquals("Chest", exercise.category)
        assertFalse(exercise.isCompleted)
    }

    @Test
    fun `exercise can be marked as completed`() {
        val exercise =
            Exercise(
                exerciseName = "Pull Up",
                sets = 3,
                reps = 5,
                weightKg = 0.0,
                category = "Back",
            )

        exercise.isCompleted = true

        assertTrue(exercise.isCompleted)
    }
}
