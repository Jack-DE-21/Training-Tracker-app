import controllers.WorkoutAPI
import models.Exercise
import models.Workout
import persistence.XMLSerializer
import utils.readNextInt
import utils.readNextLine
import java.io.File
import kotlin.system.exitProcess

private val workoutAPI = WorkoutAPI(XMLSerializer(File("workouts.xml")))

fun main() {
    runMenu()
}

fun mainMenu(): Int {
    print(
        """
            
        ----------------------------------
        |      TRAINING TRACKER APP      |
        ----------------------------------
        | WORKOUT MENU                   |
        |  1) Add workout                |
        |  2) List workouts              |
        |  3) Update workout             |
        |  4) Delete workout             |
        |  5) Mark workout completed     |
        |  6) Search workouts by name    |
        |  7) List workouts by type      |
        ----------------------------------
        |  8) Exercise menu              |
        ----------------------------------
        | STORAGE MENU                   |
        | 20) Save workouts              |
        | 21) Load workouts              |
        ----------------------------------
        |  0) Exit                       |
        ----------------------------------
        ==>> 
        """.trimIndent()
    )

    return readNextInt("")
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addWorkout()
            2 -> listWorkouts()
            3 -> updateWorkout()
            4 -> deleteWorkout()
            5 -> markWorkoutCompleted()
            6 -> searchWorkouts()
            7 -> listWorkoutsByType()
            8 -> exerciseMenu()
            20 -> save()
            21 -> load()
            0 -> exitApp()
            else -> println("Invalid menu option: $option")
        }
    } while (true)
}

fun exerciseMenu() {
    do {
        val option = readNextInt(
            """
                
            ----------------------------------
            | EXERCISE MENU                  |
            ----------------------------------
            |  1) Add exercise to workout    |
            |  2) List exercises in workout  |
            |  3) Update exercise            |
            |  4) Delete exercise            |
            |  5) Mark exercise completed    |
            |  6) Search exercises by name   |
            |  0) Return to main menu        |
            ----------------------------------
            ==>> 
            """.trimIndent()
        )

        when (option) {
            1 -> addExerciseToWorkout()
            2 -> listExercisesInWorkout()
            3 -> updateExerciseInWorkout()
            4 -> deleteExerciseFromWorkout()
            5 -> markExerciseCompleted()
            6 -> searchExercisesByName()
            0 -> println("Returning to main menu")
            else -> println("Invalid exercise menu option: $option")
        }
    } while (option != 0)
}

fun addWorkout() {
    val workoutName = readNextLine("Enter workout name: ")
    val workoutDate = readNextLine("Enter workout date: ")
    val workoutType = readNextLine("Enter workout type: ")
    val durationMinutes = readNextInt("Enter duration in minutes: ")

    val isAdded = workoutAPI.add(
        Workout(
            workoutName = workoutName,
            workoutDate = workoutDate,
            workoutType = workoutType,
            durationMinutes = durationMinutes,
            isCompleted = false
        )
    )

    if (isAdded) {
        println("Workout added successfully")
    } else {
        println("Add failed")
    }
}

fun listWorkouts() {
    if (workoutAPI.numberOfWorkouts() > 0) {
        val option = readNextInt(
            """
            
            ----------------------------------
            | LIST WORKOUTS                  |
            ----------------------------------
            |  1) List all workouts          |
            |  2) List completed workouts    |
            |  3) List incomplete workouts   |
            ----------------------------------
            ==>> 
            """.trimIndent()
        )

        when (option) {
            1 -> println(workoutAPI.listAllWorkouts())
            2 -> println(workoutAPI.listCompletedWorkouts())
            3 -> println(workoutAPI.listIncompleteWorkouts())
            else -> println("Invalid list option")
        }
    } else {
        println("No workouts stored")
    }
}

fun updateWorkout() {
    println(workoutAPI.listAllWorkouts())

    if (workoutAPI.numberOfWorkouts() > 0) {
        val indexToUpdate = readNextInt("Enter the index of the workout to update: ")

        if (workoutAPI.isValidIndex(indexToUpdate)) {
            val workoutName = readNextLine("Enter updated workout name: ")
            val workoutDate = readNextLine("Enter updated workout date: ")
            val workoutType = readNextLine("Enter updated workout type: ")
            val durationMinutes = readNextInt("Enter updated duration in minutes: ")

            val isUpdated = workoutAPI.updateWorkout(
                indexToUpdate,
                Workout(
                    workoutName = workoutName,
                    workoutDate = workoutDate,
                    workoutType = workoutType,
                    durationMinutes = durationMinutes,
                    isCompleted = false
                )
            )

            if (isUpdated) {
                println("Workout updated successfully")
            } else {
                println("Update failed")
            }
        } else {
            println("There is no workout at this index")
        }
    }
}

fun deleteWorkout() {
    println(workoutAPI.listAllWorkouts())

    if (workoutAPI.numberOfWorkouts() > 0) {
        val indexToDelete = readNextInt("Enter the index of the workout to delete: ")
        val deletedWorkout = workoutAPI.deleteWorkout(indexToDelete)

        if (deletedWorkout != null) {
            println("Deleted workout: ${deletedWorkout.workoutName}")
        } else {
            println("Delete failed")
        }
    }
}

fun markWorkoutCompleted() {
    println(workoutAPI.listAllWorkouts())

    if (workoutAPI.numberOfWorkouts() > 0) {
        val indexToComplete = readNextInt("Enter the index of the workout to mark completed: ")

        if (workoutAPI.markWorkoutCompleted(indexToComplete)) {
            println("Workout marked as completed")
        } else {
            println("No workout found at this index")
        }
    }
}

fun searchWorkouts() {
    val searchName = readNextLine("Enter workout name to search by: ")
    val searchResults = workoutAPI.searchWorkoutsByName(searchName)

    println(searchResults)
}

fun listWorkoutsByType() {
    val workoutType = readNextLine("Enter workout type to filter by: ")
    val filteredWorkouts = workoutAPI.listWorkoutsByType(workoutType)

    println(filteredWorkouts)
}

fun addExerciseToWorkout() {
    println(workoutAPI.listAllWorkouts())

    if (workoutAPI.numberOfWorkouts() > 0) {
        val workoutIndex = readNextInt("Enter the workout index to add an exercise to: ")

        if (workoutAPI.isValidIndex(workoutIndex)) {
            val exerciseName = readNextLine("Enter exercise name: ")
            val sets = readNextInt("Enter number of sets: ")
            val reps = readNextInt("Enter number of reps: ")
            val weightKg = readNextLine("Enter weight in kg: ").toDouble()
            val category = readNextLine("Enter exercise category: ")

            val isAdded = workoutAPI.addExerciseToWorkout(
                workoutIndex,
                Exercise(
                    exerciseName = exerciseName,
                    sets = sets,
                    reps = reps,
                    weightKg = weightKg,
                    category = category,
                    isCompleted = false
                )
            )

            if (isAdded) {
                println("Exercise added successfully")
            } else {
                println("Exercise add failed")
            }
        } else {
            println("There is no workout at this index")
        }
    }
}

fun listExercisesInWorkout() {
    println(workoutAPI.listAllWorkouts())

    if (workoutAPI.numberOfWorkouts() > 0) {
        val workoutIndex = readNextInt("Enter the workout index to list exercises from: ")

        println(workoutAPI.listExercisesInWorkout(workoutIndex))
    }
}

fun updateExerciseInWorkout() {
    println(workoutAPI.listAllWorkouts())

    if (workoutAPI.numberOfWorkouts() > 0) {
        val workoutIndex = readNextInt("Enter the workout index: ")

        println(workoutAPI.listExercisesInWorkout(workoutIndex))

        val exerciseIndex = readNextInt("Enter the exercise index to update: ")

        val exerciseName = readNextLine("Enter updated exercise name: ")
        val sets = readNextInt("Enter updated number of sets: ")
        val reps = readNextInt("Enter updated number of reps: ")
        val weightKg = readNextLine("Enter updated weight in kg: ").toDouble()
        val category = readNextLine("Enter updated exercise category: ")
        val isCompleted = readNextLine("Is the exercise completed? yes/no: ").equals("yes", ignoreCase = true)

        val isUpdated = workoutAPI.updateExerciseInWorkout(
            workoutIndex,
            exerciseIndex,
            Exercise(
                exerciseName = exerciseName,
                sets = sets,
                reps = reps,
                weightKg = weightKg,
                category = category,
                isCompleted = isCompleted
            )
        )

        if (isUpdated) {
            println("Exercise updated successfully")
        } else {
            println("Exercise update failed")
        }
    }
}

fun deleteExerciseFromWorkout() {
    println(workoutAPI.listAllWorkouts())

    if (workoutAPI.numberOfWorkouts() > 0) {
        val workoutIndex = readNextInt("Enter the workout index: ")

        println(workoutAPI.listExercisesInWorkout(workoutIndex))

        val exerciseIndex = readNextInt("Enter the exercise index to delete: ")

        val deletedExercise = workoutAPI.deleteExerciseFromWorkout(workoutIndex, exerciseIndex)

        if (deletedExercise != null) {
            println("Deleted exercise: ${deletedExercise.exerciseName}")
        } else {
            println("Exercise delete failed")
        }
    }
}

fun markExerciseCompleted() {
    println(workoutAPI.listAllWorkouts())

    if (workoutAPI.numberOfWorkouts() > 0) {
        val workoutIndex = readNextInt("Enter the workout index: ")

        println(workoutAPI.listExercisesInWorkout(workoutIndex))

        val exerciseIndex = readNextInt("Enter the exercise index to mark completed: ")

        if (workoutAPI.markExerciseCompleted(workoutIndex, exerciseIndex)) {
            println("Exercise marked as completed")
        } else {
            println("No exercise found at this index")
        }
    }
}

fun searchExercisesByName() {
    println(workoutAPI.listAllWorkouts())

    if (workoutAPI.numberOfWorkouts() > 0) {
        val workoutIndex = readNextInt("Enter the workout index to search exercises in: ")
        val searchName = readNextLine("Enter exercise name to search by: ")

        println(workoutAPI.searchExercisesByName(workoutIndex, searchName))
    }
}

fun save() {
    try {
        workoutAPI.store()
        println("Workouts saved successfully")
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        workoutAPI.load()
        println("Workouts loaded successfully")
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp() {
    println("Exiting app")
    exitProcess(0)
}

