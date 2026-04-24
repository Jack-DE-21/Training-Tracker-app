package controllers

import models.Exercise
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
    inner class UpdateWorkout {

        @Test
        fun `updating a workout that exists returns true and updates details`() {
            val updatedWorkout = Workout(
                "Updated Push Day",
                "30-04-2026",
                "Hypertrophy",
                75,
                true
            )

            assertTrue(populatedWorkouts!!.updateWorkout(0, updatedWorkout))
            assertEquals("Updated Push Day", populatedWorkouts!!.findWorkout(0)!!.workoutName)
            assertEquals("30-04-2026", populatedWorkouts!!.findWorkout(0)!!.workoutDate)
            assertEquals("Hypertrophy", populatedWorkouts!!.findWorkout(0)!!.workoutType)
            assertEquals(75, populatedWorkouts!!.findWorkout(0)!!.durationMinutes)
            assertTrue(populatedWorkouts!!.findWorkout(0)!!.isCompleted)
        }

        @Test
        fun `updating a workout that does not exist returns false`() {
            val updatedWorkout = Workout(
                "Updated Workout",
                "30-04-2026",
                "Strength",
                45,
                false
            )

            assertFalse(emptyWorkouts!!.updateWorkout(0, updatedWorkout))
            assertFalse(populatedWorkouts!!.updateWorkout(-1, updatedWorkout))
            assertFalse(populatedWorkouts!!.updateWorkout(4, updatedWorkout))
        }
    }

    @Nested
    inner class CompleteWorkout {

        @Test
        fun `markWorkoutCompleted returns true when workout exists`() {
            assertFalse(populatedWorkouts!!.findWorkout(0)!!.isCompleted)

            assertTrue(populatedWorkouts!!.markWorkoutCompleted(0))

            assertTrue(populatedWorkouts!!.findWorkout(0)!!.isCompleted)
        }

        @Test
        fun `markWorkoutCompleted returns false when workout does not exist`() {
            assertFalse(emptyWorkouts!!.markWorkoutCompleted(0))
            assertFalse(populatedWorkouts!!.markWorkoutCompleted(-1))
            assertFalse(populatedWorkouts!!.markWorkoutCompleted(4))
        }
    }

    @Nested
    inner class WorkoutExercises {

        @Test
        fun `addExerciseToWorkout adds exercise when workout exists`() {
            val exercise = Exercise("Bench Press", 3, 10, 60.0, "Chest")

            assertTrue(populatedWorkouts!!.addExerciseToWorkout(0, exercise))
            assertEquals(1, populatedWorkouts!!.numberOfExercisesInWorkout(0))
            assertTrue(populatedWorkouts!!.listExercisesInWorkout(0).contains("Bench Press"))
        }

        @Test
        fun `addExerciseToWorkout returns false when workout does not exist`() {
            val exercise = Exercise("Bench Press", 3, 10, 60.0, "Chest")

            assertFalse(emptyWorkouts!!.addExerciseToWorkout(0, exercise))
            assertFalse(populatedWorkouts!!.addExerciseToWorkout(-1, exercise))
            assertFalse(populatedWorkouts!!.addExerciseToWorkout(4, exercise))
        }

        @Test
        fun `numberOfExercisesInWorkout returns correct number when workout exists`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")
            val shoulderPress = Exercise("Shoulder Press", 3, 8, 35.0, "Shoulders")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)
            populatedWorkouts!!.addExerciseToWorkout(0, shoulderPress)

            assertEquals(2, populatedWorkouts!!.numberOfExercisesInWorkout(0))
        }

        @Test
        fun `numberOfExercisesInWorkout returns minus one when workout does not exist`() {
            assertEquals(-1, emptyWorkouts!!.numberOfExercisesInWorkout(0))
            assertEquals(-1, populatedWorkouts!!.numberOfExercisesInWorkout(-1))
            assertEquals(-1, populatedWorkouts!!.numberOfExercisesInWorkout(4))
        }

        @Test
        fun `listExercisesInWorkout returns message when workout does not exist`() {
            assertEquals("Workout not found", emptyWorkouts!!.listExercisesInWorkout(0))
            assertEquals("Workout not found", populatedWorkouts!!.listExercisesInWorkout(-1))
            assertEquals("Workout not found", populatedWorkouts!!.listExercisesInWorkout(4))
        }

        @Test
        fun `listExercisesInWorkout returns message when workout has no exercises`() {
            assertEquals("No exercises stored in this workout", populatedWorkouts!!.listExercisesInWorkout(0))
        }

        @Test
        fun `listExercisesInWorkout returns exercises when workout has exercises`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")
            val tricepDip = Exercise("Tricep Dip", 3, 12, 0.0, "Arms")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)
            populatedWorkouts!!.addExerciseToWorkout(0, tricepDip)

            val exerciseList = populatedWorkouts!!.listExercisesInWorkout(0)

            assertTrue(exerciseList.contains("Bench Press"))
            assertTrue(exerciseList.contains("Tricep Dip"))
        }

        @Test
        fun `findExercise returns exercise when indexes are valid`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)

            assertEquals(benchPress, populatedWorkouts!!.findExercise(0, 0))
        }

        @Test
        fun `findExercise returns null when indexes are invalid`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)

            assertNull(populatedWorkouts!!.findExercise(0, -1))
            assertNull(populatedWorkouts!!.findExercise(0, 1))
            assertNull(populatedWorkouts!!.findExercise(-1, 0))
            assertNull(populatedWorkouts!!.findExercise(4, 0))
            assertNull(emptyWorkouts!!.findExercise(0, 0))
        }

        @Test
        fun `deleteExerciseFromWorkout deletes exercise when indexes are valid`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)

            assertEquals(1, populatedWorkouts!!.numberOfExercisesInWorkout(0))

            val deletedExercise = populatedWorkouts!!.deleteExerciseFromWorkout(0, 0)

            assertEquals(benchPress, deletedExercise)
            assertEquals(0, populatedWorkouts!!.numberOfExercisesInWorkout(0))
        }

        @Test
        fun `deleteExerciseFromWorkout returns null when indexes are invalid`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)

            assertNull(populatedWorkouts!!.deleteExerciseFromWorkout(0, -1))
            assertNull(populatedWorkouts!!.deleteExerciseFromWorkout(0, 1))
            assertNull(populatedWorkouts!!.deleteExerciseFromWorkout(-1, 0))
            assertNull(populatedWorkouts!!.deleteExerciseFromWorkout(4, 0))
            assertNull(emptyWorkouts!!.deleteExerciseFromWorkout(0, 0))
        }
    }

    @Nested
    inner class UpdateWorkoutExercises {

        @Test
        fun `updateExerciseInWorkout updates exercise when indexes are valid`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")
            val updatedExercise = Exercise("Incline Bench Press", 4, 8, 65.0, "Chest", true)

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)

            assertTrue(populatedWorkouts!!.updateExerciseInWorkout(0, 0, updatedExercise))

            val result = populatedWorkouts!!.findExercise(0, 0)

            assertEquals("Incline Bench Press", result!!.exerciseName)
            assertEquals(4, result.sets)
            assertEquals(8, result.reps)
            assertEquals(65.0, result.weightKg)
            assertEquals("Chest", result.category)
            assertTrue(result.isCompleted)
        }

        @Test
        fun `updateExerciseInWorkout returns false when indexes are invalid`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")
            val updatedExercise = Exercise("Incline Bench Press", 4, 8, 65.0, "Chest", true)

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)

            assertFalse(populatedWorkouts!!.updateExerciseInWorkout(0, -1, updatedExercise))
            assertFalse(populatedWorkouts!!.updateExerciseInWorkout(0, 1, updatedExercise))
            assertFalse(populatedWorkouts!!.updateExerciseInWorkout(-1, 0, updatedExercise))
            assertFalse(populatedWorkouts!!.updateExerciseInWorkout(4, 0, updatedExercise))
            assertFalse(emptyWorkouts!!.updateExerciseInWorkout(0, 0, updatedExercise))
        }

        @Test
        fun `markExerciseCompleted marks exercise completed when indexes are valid`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)

            assertFalse(populatedWorkouts!!.findExercise(0, 0)!!.isCompleted)

            assertTrue(populatedWorkouts!!.markExerciseCompleted(0, 0))

            assertTrue(populatedWorkouts!!.findExercise(0, 0)!!.isCompleted)
        }

        @Test
        fun `markExerciseCompleted returns false when indexes are invalid`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)

            assertFalse(populatedWorkouts!!.markExerciseCompleted(0, -1))
            assertFalse(populatedWorkouts!!.markExerciseCompleted(0, 1))
            assertFalse(populatedWorkouts!!.markExerciseCompleted(-1, 0))
            assertFalse(populatedWorkouts!!.markExerciseCompleted(4, 0))
            assertFalse(emptyWorkouts!!.markExerciseCompleted(0, 0))
        }

        @Test
        fun `searchExercisesByName returns matching exercises when workout exists`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")
            val shoulderPress = Exercise("Shoulder Press", 3, 8, 35.0, "Shoulders")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)
            populatedWorkouts!!.addExerciseToWorkout(0, shoulderPress)

            val searchResults = populatedWorkouts!!.searchExercisesByName(0, "Bench")

            assertTrue(searchResults.contains("Bench Press"))
            assertFalse(searchResults.contains("Shoulder Press"))
        }

        @Test
        fun `searchExercisesByName ignores case when searching`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)

            val searchResults = populatedWorkouts!!.searchExercisesByName(0, "bench")

            assertTrue(searchResults.contains("Bench Press"))
        }

        @Test
        fun `searchExercisesByName returns no exercises found when no match exists`() {
            val benchPress = Exercise("Bench Press", 3, 10, 60.0, "Chest")

            populatedWorkouts!!.addExerciseToWorkout(0, benchPress)

            assertEquals("No exercises found", populatedWorkouts!!.searchExercisesByName(0, "Squat"))
        }

        @Test
        fun `searchExercisesByName returns workout not found when workout index is invalid`() {
            assertEquals("Workout not found", emptyWorkouts!!.searchExercisesByName(0, "Bench"))
            assertEquals("Workout not found", populatedWorkouts!!.searchExercisesByName(-1, "Bench"))
            assertEquals("Workout not found", populatedWorkouts!!.searchExercisesByName(4, "Bench"))
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