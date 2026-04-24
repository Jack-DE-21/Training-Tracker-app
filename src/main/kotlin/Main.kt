import controllers.WorkoutAPI
import models.Workout
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess

private val workoutAPI = WorkoutAPI()

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
            0 -> exitApp()
            else -> println("Invalid menu option: $option")
        }
    } while (true)
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

fun exitApp() {
    println("Exiting app")
    exitProcess(0)
}