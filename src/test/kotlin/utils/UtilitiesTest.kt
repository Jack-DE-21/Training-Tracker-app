package utils

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UtilitiesTest {
    @Test
    fun `isValidWorkoutType returns true when workout type exists`() {
        assertTrue(isValidWorkoutType("Strength"))
        assertTrue(isValidWorkoutType("cardio"))
    }

    @Test
    fun `isValidWorkoutType returns false when workout type does not exist`() {
        assertFalse(isValidWorkoutType("Swimming"))
    }

    @Test
    fun `isValidExerciseCategory returns true when exercise category exists`() {
        assertTrue(isValidExerciseCategory("Chest"))
        assertTrue(isValidExerciseCategory("legs"))
    }

    @Test
    fun `isValidExerciseCategory returns false when exercise category does not exist`() {
        assertFalse(isValidExerciseCategory("Random"))
    }
}
