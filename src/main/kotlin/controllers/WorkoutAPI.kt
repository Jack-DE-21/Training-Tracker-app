package controllers

import models.Exercise
import models.Workout
import utils.isValidListIndex

class WorkoutAPI {

    private var workouts = ArrayList<Workout>()

    fun add(workout: Workout): Boolean {
        return workouts.add(workout)
    }

    fun numberOfWorkouts(): Int {
        return workouts.size
    }

    fun listAllWorkouts(): String {
        return if (workouts.isEmpty()) {
            "No workouts stored"
        } else {
            formatWorkoutListString(workouts)
        }
    }

    fun listCompletedWorkouts(): String {
        val completedWorkouts = workouts.filter { workout -> workout.isCompleted }

        return if (completedWorkouts.isEmpty()) {
            "No completed workouts stored"
        } else {
            formatWorkoutListString(completedWorkouts)
        }
    }

    fun listIncompleteWorkouts(): String {
        val incompleteWorkouts = workouts.filter { workout -> !workout.isCompleted }

        return if (incompleteWorkouts.isEmpty()) {
            "No incomplete workouts stored"
        } else {
            formatWorkoutListString(incompleteWorkouts)
        }
    }

    fun listWorkoutsByType(workoutType: String): String {
        val matchingWorkouts = workouts.filter { workout ->
            workout.workoutType.equals(workoutType, ignoreCase = true)
        }

        return if (matchingWorkouts.isEmpty()) {
            "No workouts stored with type: $workoutType"
        } else {
            formatWorkoutListString(matchingWorkouts)
        }
    }

    fun searchWorkoutsByName(searchString: String): String {
        val matchingWorkouts = workouts.filter { workout ->
            workout.workoutName.contains(searchString, ignoreCase = true)
        }

        return if (matchingWorkouts.isEmpty()) {
            "No workouts found"
        } else {
            formatWorkoutListString(matchingWorkouts)
        }
    }

    fun findWorkout(index: Int): Workout? {
        return if (isValidIndex(index)) {
            workouts[index]
        } else {
            null
        }
    }

    fun updateWorkout(indexToUpdate: Int, workout: Workout): Boolean {
        val foundWorkout = findWorkout(indexToUpdate)

        return if (foundWorkout != null) {
            foundWorkout.workoutName = workout.workoutName
            foundWorkout.workoutDate = workout.workoutDate
            foundWorkout.workoutType = workout.workoutType
            foundWorkout.durationMinutes = workout.durationMinutes
            foundWorkout.isCompleted = workout.isCompleted
            true
        } else {
            false
        }
    }

    fun markWorkoutCompleted(indexToComplete: Int): Boolean {
        val foundWorkout = findWorkout(indexToComplete)

        return if (foundWorkout != null) {
            foundWorkout.isCompleted = true
            true
        } else {
            false
        }
    }

    fun deleteWorkout(indexToDelete: Int): Workout? {
        return if (isValidIndex(indexToDelete)) {
            workouts.removeAt(indexToDelete)
        } else {
            null
        }
    }

    fun addExerciseToWorkout(workoutIndex: Int, exercise: Exercise): Boolean {
        val foundWorkout = findWorkout(workoutIndex)

        return if (foundWorkout != null) {
            foundWorkout.exercises.add(exercise)
        } else {
            false
        }
    }

    fun numberOfExercisesInWorkout(workoutIndex: Int): Int {
        val foundWorkout = findWorkout(workoutIndex)

        return foundWorkout?.exercises?.size ?: -1
    }

    fun listExercisesInWorkout(workoutIndex: Int): String {
        val foundWorkout = findWorkout(workoutIndex)

        return if (foundWorkout == null) {
            "Workout not found"
        } else if (foundWorkout.exercises.isEmpty()) {
            "No exercises stored in this workout"
        } else {
            formatExerciseListString(foundWorkout.exercises.toList())
        }
    }

    fun findExercise(workoutIndex: Int, exerciseIndex: Int): Exercise? {
        val foundWorkout = findWorkout(workoutIndex)

        return if (
            foundWorkout != null &&
            isValidListIndex(exerciseIndex, foundWorkout.exercises.toList())
        ) {
            foundWorkout.exercises.elementAt(exerciseIndex)
        } else {
            null
        }
    }

    fun deleteExerciseFromWorkout(workoutIndex: Int, exerciseIndex: Int): Exercise? {
        val foundWorkout = findWorkout(workoutIndex)
        val foundExercise = findExercise(workoutIndex, exerciseIndex)

        return if (foundWorkout != null && foundExercise != null) {
            foundWorkout.exercises.remove(foundExercise)
            foundExercise
        } else {
            null
        }
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, workouts)
    }

    private fun formatWorkoutListString(workoutsToFormat: List<Workout>): String {
        return workoutsToFormat
            .mapIndexed { index, workout -> "$index: $workout" }
            .joinToString(separator = "\n")
    }

    private fun formatExerciseListString(exercisesToFormat: List<Exercise>): String {
        return exercisesToFormat
            .mapIndexed { index, exercise -> "$index: $exercise" }
            .joinToString(separator = "\n")
    }
}