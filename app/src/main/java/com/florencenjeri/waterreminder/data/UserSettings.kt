package com.florencenjeri.waterreminder.data

data class UserSettings(
    val name: String,
    val goal: Int,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val weight: Int,
    val height: Int,
    val measurementUnits: String
)