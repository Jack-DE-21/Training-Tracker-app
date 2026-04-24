# Training Tracker App - Design Notes

## App Idea

The Training Tracker App is a Kotlin console app for storing workouts and the exercises inside each workout.

The app is designed for a user who wants to record training sessions, track exercises, update progress, and save the data between app runs.

## High-Level Features

The main features are:

- Add, list, update, delete, and complete workouts
- Search workouts by name
- Filter workouts by type
- Add exercises to a workout
- List, update, delete, complete, and search exercises
- Save and load workout data using persistence

## Menu Flow

The app uses a main menu for workout options.

Main menu:

- Add workout
- List workouts
- Update workout
- Delete workout
- Mark workout completed
- Search workouts by name
- List workouts by type
- Exercise menu
- Save workouts
- Load workouts
- Exit

The exercise options are kept inside a separate exercise menu to avoid making the main menu too crowded.

Exercise menu:

- Add exercise to workout
- List exercises in workout
- Update exercise
- Delete exercise
- Mark exercise completed
- Search exercises by name
- Return to main menu

## Data Models

The app uses two data models.

### Workout

A Workout stores the main training session details.

Fields:

- workoutName: String
- workoutDate: String
- workoutType: String
- durationMinutes: Int
- isCompleted: Boolean
- exercises: MutableSet<Exercise>

### Exercise

An Exercise stores the details of one exercise inside a workout.

Fields:

- exerciseName: String
- sets: Int
- reps: Int
- weightKg: Double
- category: String
- isCompleted: Boolean

## Model Relationship

The app uses a one-to-many relationship.

One workout can contain many exercises.

Example:

- Push Day
    - Bench Press
    - Shoulder Press
    - Tricep Dip

## Architecture

The app follows the same general structure used in the module labs.

Packages:

- models: stores data classes
- controllers: stores the WorkoutAPI controller
- persistence: stores serializer classes
- utils: stores reusable input and validation helper functions

Main.kt manages the console menu and user input.

WorkoutAPI manages the ArrayList of Workout objects and the exercise functions inside each workout.

## UX Rules

The app uses these UX rules:

- Keep workout options grouped in the main menu
- Keep exercise options in a separate submenu
- Show clear success and failure messages
- Check indexes before updating or deleting data
- Return useful messages when no data is stored
- Avoid crashing when the user enters an invalid index

## Sprint Plan

The app was built using small issues.

Sprints:

1. Create project structure
2. Add Workout and Exercise model classes
3. Add basic WorkoutAPI CRUD
4. Add workout search and filtering
5. Add workout update and completion options
6. Add exercises to workouts
7. Add exercise update and completion options
8. Add workout console menu
9. Add exercise menu
10. Add save and load
11. Add app design notes