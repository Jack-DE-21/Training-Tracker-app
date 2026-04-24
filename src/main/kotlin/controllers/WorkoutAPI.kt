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
            workouts
                .mapIndexed { index, workout -> "$index: $workout" }
                .joinToString(separator = "\n")
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
}