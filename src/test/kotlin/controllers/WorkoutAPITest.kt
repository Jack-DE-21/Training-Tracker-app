package controllers

import models.Workout
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class WorkoutAPITest {

    private var pushDay: Workout? = null
    private var pullDay: Workout? = null
    private var legDay: Workout? = null
    private var cardioDay: Workout? = null
    private var populatedWorkouts: WorkoutAPI? = WorkoutAPI()
    private var emptyWorkouts: WorkoutAPI? = WorkoutAPI()

    @BeforeEach
    fun setup() {
        pushDay = Workout("Push Day", "24-04-2026", "Strength", 60, false)
        pullDay = Workout("Pull Day", "25-04-2026", "Strength", 55, false)
        legDay = Workout("Leg Day", "26-04-2026", "Strength", 70, true)
        cardioDay = Workout("Cardio Day", "27-04-2026", "Cardio", 30, false)

        populatedWorkouts!!.add(pushDay!!)
        populatedWorkouts!!.add(pullDay!!)
        populatedWorkouts!!.add(legDay!!)
        populatedWorkouts!!.add(cardioDay!!)
    }

    @AfterEach
    fun tearDown() {
        pushDay = null
        pullDay = null
        legDay = null
        cardioDay = null
        populatedWorkouts = null
        emptyWorkouts = null
    }

    @Nested
    inner class AddWorkouts {

        @Test
        fun `adding a workout to a populated list adds to ArrayList`() {
            val newWorkout = Workout("Mobility Day", "28-04-2026", "Mobility", 25, false)

            assertEquals(4, populatedWorkouts!!.numberOfWorkouts())
            assertTrue(populatedWorkouts!!.add(newWorkout))
            assertEquals(5, populatedWorkouts!!.numberOfWorkouts())
            assertEquals(newWorkout, populatedWorkouts!!.findWorkout(4))
        }

        @Test
        fun `adding a workout to an empty list adds to ArrayList`() {
            val newWorkout = Workout("Mobility Day", "28-04-2026", "Mobility", 25, false)

            assertEquals(0, emptyWorkouts!!.numberOfWorkouts())
            assertTrue(emptyWorkouts!!.add(newWorkout))
            assertEquals(1, emptyWorkouts!!.numberOfWorkouts())
            assertEquals(newWorkout, emptyWorkouts!!.findWorkout(0))
        }
    }

    @Nested
    inner class ListWorkouts {

        @Test
        fun `listAllWorkouts returns no workouts stored when ArrayList is empty`() {
            assertEquals("No workouts stored", emptyWorkouts!!.listAllWorkouts())
        }

        @Test
        fun `listAllWorkouts returns workouts when ArrayList has workouts stored`() {
            val workoutList = populatedWorkouts!!.listAllWorkouts()

            assertTrue(workoutList.contains("Push Day"))
            assertTrue(workoutList.contains("Pull Day"))
            assertTrue(workoutList.contains("Leg Day"))
            assertTrue(workoutList.contains("Cardio Day"))
        }

        @Test
        fun `listCompletedWorkouts returns no completed workouts when ArrayList is empty`() {
            assertEquals("No completed workouts stored", emptyWorkouts!!.listCompletedWorkouts())
        }

        @Test
        fun `listCompletedWorkouts returns completed workouts when they exist`() {
            val completedWorkouts = populatedWorkouts!!.listCompletedWorkouts()

            assertTrue(completedWorkouts.contains("Leg Day"))
            assertFalse(completedWorkouts.contains("Push Day"))
            assertFalse(completedWorkouts.contains("Pull Day"))
            assertFalse(completedWorkouts.contains("Cardio Day"))
        }

        @Test
        fun `listIncompleteWorkouts returns no incomplete workouts when ArrayList is empty`() {
            assertEquals("No incomplete workouts stored", emptyWorkouts!!.listIncompleteWorkouts())
        }

        @Test
        fun `listIncompleteWorkouts returns incomplete workouts when they exist`() {
            val incompleteWorkouts = populatedWorkouts!!.listIncompleteWorkouts()

            assertTrue(incompleteWorkouts.contains("Push Day"))
            assertTrue(incompleteWorkouts.contains("Pull Day"))
            assertTrue(incompleteWorkouts.contains("Cardio Day"))
            assertFalse(incompleteWorkouts.contains("Leg Day"))
        }

        @Test
        fun `listWorkoutsByType returns matching workouts when type exists`() {
            val strengthWorkouts = populatedWorkouts!!.listWorkoutsByType("Strength")

            assertTrue(strengthWorkouts.contains("Push Day"))
            assertTrue(strengthWorkouts.contains("Pull Day"))
            assertTrue(strengthWorkouts.contains("Leg Day"))
            assertFalse(strengthWorkouts.contains("Cardio Day"))
        }

        @Test
        fun `listWorkoutsByType ignores case when matching type`() {
            val cardioWorkouts = populatedWorkouts!!.listWorkoutsByType("cardio")

            assertTrue(cardioWorkouts.contains("Cardio Day"))
            assertFalse(cardioWorkouts.contains("Push Day"))
        }

        @Test
        fun `listWorkoutsByType returns message when type does not exist`() {
            assertEquals(
                "No workouts stored with type: Swimming",
                populatedWorkouts!!.listWorkoutsByType("Swimming")
            )
        }
    }

    @Nested
    inner class SearchWorkouts {

        @Test
        fun `searchWorkoutsByName returns matching workouts when name exists`() {
            val searchResults = populatedWorkouts!!.searchWorkoutsByName("Push")

            assertTrue(searchResults.contains("Push Day"))
            assertFalse(searchResults.contains("Pull Day"))
            assertFalse(searchResults.contains("Leg Day"))
            assertFalse(searchResults.contains("Cardio Day"))
        }

        @Test
        fun `searchWorkoutsByName ignores case when searching`() {
            val searchResults = populatedWorkouts!!.searchWorkoutsByName("push")

            assertTrue(searchResults.contains("Push Day"))
        }

        @Test
        fun `searchWorkoutsByName returns no workouts found when no match exists`() {
            assertEquals("No workouts found", populatedWorkouts!!.searchWorkoutsByName("Swim"))
        }

        @Test
        fun `searchWorkoutsByName returns no workouts found when ArrayList is empty`() {
            assertEquals("No workouts found", emptyWorkouts!!.searchWorkoutsByName("Push"))
        }
    }

    @Nested
    inner class NumberOfWorkouts {

        @Test
        fun `numberOfWorkouts returns zero when ArrayList is empty`() {
            assertEquals(0, emptyWorkouts!!.numberOfWorkouts())
        }

        @Test
        fun `numberOfWorkouts returns correct amount when ArrayList has workouts`() {
            assertEquals(4, populatedWorkouts!!.numberOfWorkouts())
        }
    }

    @Nested
    inner class FindWorkout {

        @Test
        fun `findWorkout returns workout when index is valid`() {
            assertEquals(pushDay, populatedWorkouts!!.findWorkout(0))
            assertEquals(pullDay, populatedWorkouts!!.findWorkout(1))
            assertEquals(legDay, populatedWorkouts!!.findWorkout(2))
            assertEquals(cardioDay, populatedWorkouts!!.findWorkout(3))
        }

        @Test
        fun `findWorkout returns null when index is invalid`() {
            assertNull(populatedWorkouts!!.findWorkout(-1))
            assertNull(populatedWorkouts!!.findWorkout(4))
            assertNull(emptyWorkouts!!.findWorkout(0))
        }
    }

    @Nested
    inner class DeleteWorkout {

        @Test
        fun `deleting a workout that exists removes and returns deleted object`() {
            assertEquals(4, populatedWorkouts!!.numberOfWorkouts())

            val deletedWorkout = populatedWorkouts!!.deleteWorkout(1)

            assertEquals(pullDay, deletedWorkout)
            assertEquals(3, populatedWorkouts!!.numberOfWorkouts())
            assertFalse(populatedWorkouts!!.listAllWorkouts().contains("Pull Day"))
        }

        @Test
        fun `deleting a workout that does not exist returns null`() {
            assertNull(emptyWorkouts!!.deleteWorkout(0))
            assertNull(populatedWorkouts!!.deleteWorkout(-1))
            assertNull(populatedWorkouts!!.deleteWorkout(4))
        }
    }
}