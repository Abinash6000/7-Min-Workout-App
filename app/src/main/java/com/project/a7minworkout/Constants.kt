package com.project.a7minworkout

import com.project.a7minworkout.model.ExerciseModel

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks_2
        )

        exerciseList.add(jumpingJacks)

        val wallSit = ExerciseModel(
            2,
            "Wall Sit",
            R.drawable.ic_wall_sit_2
        )

        exerciseList.add(wallSit)

        val pushUp = ExerciseModel(
            3,
            "Push Ups",
            R.drawable.ic_push_up_2
        )

        exerciseList.add(pushUp)

        val abdominalCrunch = ExerciseModel(
            4,
            "Abdominal Crunch",
            R.drawable.ic_abdominal_crunch_2
        )

        exerciseList.add(abdominalCrunch)

        val stepUpOnChair = ExerciseModel(
            5,
            "Step-Up on Chair",
            R.drawable.ic_step_up_on_chair_2
        )

        exerciseList.add(stepUpOnChair)

        val squat = ExerciseModel(
            6,
            "Squats",
            R.drawable.ic_squat_2
        )

        exerciseList.add(squat)

        val tricepsDipOnChair = ExerciseModel(
            7,
            "Triceps Dip On Chair",
            R.drawable.ic_tricep_dip_on_chair_2
        )

        exerciseList.add(tricepsDipOnChair)

        val plank = ExerciseModel(
            8,
            "Plank",
            R.drawable.ic_plank_2
        )

        exerciseList.add(plank)

        val highKneesRunningInPlace = ExerciseModel(
            9,
            "High Knees Running In Place",
            R.drawable.ic_high_knees_running_in_place_2
        )

        exerciseList.add(highKneesRunningInPlace)

        val lunges = ExerciseModel(
            10,
            "Lunges",
            R.drawable.ic_lunge_2
        )

        exerciseList.add(lunges)

        val pushUpAndRotation = ExerciseModel(
            11,
            "Push Up and Rotation",
            R.drawable.ic_push_up_and_rotation_2
        )

        exerciseList.add(pushUpAndRotation)

        val sidePlank = ExerciseModel(
            12,
            "Side Plank",
            R.drawable.ic_side_plank_2
        )

        exerciseList.add(sidePlank)

        return exerciseList
    }
}