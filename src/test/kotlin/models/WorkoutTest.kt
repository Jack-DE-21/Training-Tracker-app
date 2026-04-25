package models

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class WorkoutTest {
    @Test
    fun `workout stores correct details`() {
        val workout =
            Workout(
                workoutName = "Push Day",
                workoutDate = "24-04-2026",
                workoutType = "Strength",
                durationMinutes = 60,
            )

        assertEquals("Push Day", workout.workoutName)
        assertEquals("24-04-2026", workout.workoutDate)
        assertEquals("Strength", workout.workoutType)
        assertEquals(60, workout.durationMinutes)
        assertFalse(workout.isCompleted)
        assertEquals(0, workout.exercises.size)
    }

    @Test
    fun `workout can be marked as completed`() {
        val workout =
            Workout(
                workoutName = "Pull Day",
                workoutDate = "25-04-2026",
                workoutType = "Strength",
                durationMinutes = 55,
            )

        workout.isCompleted = true

        assertTrue(workout.isCompleted)
    }

    @Test
    fun `exercise can be added to workout`() {
        val workout =
            Workout(
                workoutName = "Leg Day",
                workoutDate = "26-04-2026",
                workoutType = "Strength",
                durationMinutes = 70,
            )

        val exercise =
            Exercise(
                exerciseName = "Squat",
                sets = 4,
                reps = 8,
                weightKg = 80.0,
                category = "Legs",
            )

        workout.exercises.add(exercise)

        assertEquals(1, workout.exercises.size)
        assertTrue(workout.exercises.contains(exercise))
    }
}
