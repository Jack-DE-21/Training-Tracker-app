package controllers

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
            formatListString(workouts)
        }
    }

    fun listCompletedWorkouts(): String {
        val completedWorkouts = workouts.filter { workout -> workout.isCompleted }
        return if (completedWorkouts.isEmpty()) {
            "No completed workouts stored"
        } else {
            formatListString(completedWorkouts)
        }
    }

    fun listIncompleteWorkouts(): String {
        val incompleteWorkouts = workouts.filter { workout -> !workout.isCompleted }
        return if (incompleteWorkouts.isEmpty()) {
            "No incomplete workouts stored"
        } else {
            formatListString(incompleteWorkouts)
        }
    }

    fun listWorkoutsByType(workoutType: String): String {
        val matchingWorkouts = workouts.filter {
                workout -> workout.workoutType.equals(workoutType, ignoreCase = true)
        }

        return if (matchingWorkouts.isEmpty()) {
            "No workouts stored with type: $workoutType"
        } else {
            formatListString(matchingWorkouts)
        }
    }

    fun searchWorkoutsByName(searchString: String): String {
        val matchingWorkouts = workouts.filter {
                workout -> workout.workoutName.contains(searchString, ignoreCase = true)
        }

        return if (matchingWorkouts.isEmpty()) {
            "No workouts found"
        } else {
            formatListString(matchingWorkouts)
        }
    }

    fun findWorkout(index: Int): Workout? {
        return if (isValidIndex(index)) {
            workouts[index]
        } else {
            null
        }
    }

    fun deleteWorkout(indexToDelete: Int): Workout? {
        return if (isValidIndex(indexToDelete)) {
            workouts.removeAt(indexToDelete)
        } else {
            null
        }
    }

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, workouts)
    }

    private fun formatListString(workoutsToFormat: List<Workout>): String {
        return workoutsToFormat
            .mapIndexed { index, workout -> "$index: $workout" }
            .joinToString(separator = "\n")
    }
}