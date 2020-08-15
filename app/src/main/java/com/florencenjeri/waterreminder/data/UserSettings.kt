package com.florencenjeri.waterreminder.data

data class UserSettings(
    val name: String,
    val goal: String,
    val cupMeasurements: String,
    val startTime: String,
    val endTime: String,
    val gender: String,
    val weight: String,
    val height: String,
    val measurementUnits: String
)