package com.florencenjeri.waterreminder.ui.view

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.florencenjeri.waterreminder.R
import com.florencenjeri.waterreminder.database.UserSettingsEntity
import com.florencenjeri.waterreminder.ui.viewModel.CredentialsValidationState
import com.florencenjeri.waterreminder.ui.viewModel.ProfileSettingsViewModel
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.profile_settings_fragment.*
import org.koin.androidx.scope.lifecycleScope
import java.text.SimpleDateFormat
import java.util.*

class ProfileSettingsFragment : Fragment() {
    private val profileSettingsViewModel: ProfileSettingsViewModel by lifecycleScope.inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zTransitionToHomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        maleButton.isSelected = true
        kgsUnits.isSelected = true
        setSelectedGender()
        setSelectedMeasurements()
        setClickListeners()
        susbscribeToData()
        profileSettingsViewModel.checkIfCredentialsAreSet()
    }

    private fun setClickListeners() {
        startTimeButton.setOnClickListener {
            showTimePicker(startTimeButton)
        }
        endTimeButton.setOnClickListener {
            showTimePicker(endTimeButton)
        }
        saveButton.setOnClickListener {
            val name = editTextName.text.trim().toString()
            val goal = editTextGoal.text.trim().toString()
            val cupMeasurement = editTextGlassSize.text.trim().toString()
            val startTime = startTimeButton.text.toString()
            val endTime = endTimeButton.text.toString()
            val height = editTextHeight.text.trim().toString()
            val weight = editTextWeight.text.trim().toString()
            val numOfGlasses =
                profileSettingsViewModel.getTotalNumOfGlasses(goal.toInt(), cupMeasurement.toInt())
            val hoursAwake = getNumOfHoursAwake(startTime, endTime)
            val delayTime = profileSettingsViewModel.getScheduledNotificationsDelayTime(
                hoursAwake,
                numOfGlasses
            )
            profileSettingsViewModel.checkCredentials(
                name,
                goal,
                cupMeasurement,
                startTime,
                endTime,
                height,
                weight
            )
            val settings = UserSettingsEntity(
                0,
                name,
                goal,
                cupMeasurement,
                startTime,
                endTime,
                getSelectedGender(),
                weight,
                height,
                getSelectedMeasurements(),
                numOfGlasses,
                delayTime
            )
            profileSettingsViewModel.saveUserSettings(settings)
            profileSettingsViewModel.storeDelayTime(delayTime.toLong())
        }
    }

    private fun getNumOfHoursAwake(
        startTime: String,
        endTime: String
    ): Int {
        /**
         * startTime - e.g 21:00
         * endTime -  e.g 04:00
         * expected to return 17
         */
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        val dateMax: Date = simpleDateFormat.parse(startTime)
        val dateMin: Date = simpleDateFormat.parse(endTime)
        val startDate = simpleDateFormat.parse(startTime)
        val endDate = simpleDateFormat.parse(endTime)

        var difference = dateMax.time - dateMin.time
        if (difference < 0) {
            difference = (dateMax.time - startDate.time) + (endDate.time - dateMin.time)

        }
        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
        val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60))
        return hours.toInt()
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_ProfileSettingsFragment_to_HomeFragment)
    }

    fun susbscribeToData() {
        profileSettingsViewModel.getCredentialsValidationState().observe(viewLifecycleOwner,
            androidx.lifecycle.Observer { credentialsValidationState ->
                onProfileCredentialsChange(credentialsValidationState)
            })
    }

    private fun onProfileCredentialsChange(credentialsValidationState: CredentialsValidationState) =
        when (credentialsValidationState) {
            CredentialsValidationState.UserSettingsConfigured -> navigateToHomeScreen()
            CredentialsValidationState.NameInvalid -> setNameError()
            CredentialsValidationState.NameValid -> removeNameError()
            CredentialsValidationState.ConsumptionGoalInvalid -> setConsumptionGoalError()
            CredentialsValidationState.ConsumptionGoalValid -> removeConsumptionGoalError()
            CredentialsValidationState.HeightInvalid -> setHeightError()
            CredentialsValidationState.HeightValid -> removeHeightError()
            CredentialsValidationState.WeightInvalid -> setWeightError()
            CredentialsValidationState.WeightValid -> removeWeightError()
            CredentialsValidationState.SleepingTimeInvalid -> setSleepingTimeError()
            CredentialsValidationState.SleepingTimeValid -> removeSleepingTimeError()
            CredentialsValidationState.WakeUpTimeInvalid -> setWakeUpTimeError()
            CredentialsValidationState.WakeUpTimeValid -> removeWakeUpTimeError()
            CredentialsValidationState.CupMeasurementValid -> setCupMeasurementError()
            CredentialsValidationState.CupMeasurementInvalid -> removeCupMeasurementError()
        }

    private fun removeCupMeasurementError() {
        editTextGlassSize.error = "Set a valid glass size"
    }

    private fun setCupMeasurementError() {
        editTextGlassSize.error = null
    }

    fun setNameError() {
        editTextName.error = "Set a valid name"
    }

    fun removeNameError() {
        editTextName.error = null
    }

    fun setConsumptionGoalError() {
        editTextGoal.error = "Set a valid goal"
    }

    fun removeConsumptionGoalError() {
        editTextGoal.error = null
    }

    fun setHeightError() {
        editTextHeight.error = "Set a valid height value"
    }

    fun removeHeightError() {
        editTextHeight.error = null
    }

    fun setWeightError() {
        editTextWeight.error = "Set a valid weight value"
    }

    fun removeWeightError() {
        editTextWeight.error = null
    }

    fun setSleepingTimeError() {
        startTimeButton.error = "Select the time you get to bed"
    }

    fun removeSleepingTimeError() {
        startTimeButton.error = null
    }

    fun setWakeUpTimeError() {
        endTimeButton.error = "Select the time you wake up"
    }

    fun removeWakeUpTimeError() {
        endTimeButton.error = null
    }

    fun setSelectedGender() {
        maleButton.setOnClickListener {
            maleButton.isSelected = true
            femaleButton.isSelected = false
        }
        femaleButton.setOnClickListener {
            maleButton.isSelected = false
            femaleButton.isSelected = true
        }
    }

    private fun getSelectedGender(): String {
        return if (maleButton.isSelected) {
            "Male"
        } else {
            "Female"
        }
    }

    fun setSelectedMeasurements() {
        kgsUnits.setOnClickListener {
            kgsUnits.isSelected = true
            poundsUnits.isSelected = false
        }
        poundsUnits.setOnClickListener {
            kgsUnits.isSelected = false
            poundsUnits.isSelected = true
        }
    }

    fun getSelectedMeasurements(): String {
        return if (kgsUnits.isSelected) {
            "Kgs | Ml"
        } else {
            "Lbs | oz"
        }
    }

    fun showTimePicker(view: Button) {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfDay ->
            c.set(Calendar.HOUR_OF_DAY, hourOfDay)
            c.set(Calendar.MINUTE, minuteOfDay)
            val time = SimpleDateFormat("HH:mm").format(c.time)
            view.text = time
        }
        TimePickerDialog(
            activity,
            timeSetListener,
            hour,
            minute,
            DateFormat.is24HourFormat(activity)
        ).show()
    }

    private fun zTransitionToHomeFragment() {
        //HomeFragment to ProfileSettings Fragment Navigation
        val backward = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        reenterTransition = backward
        //ProfileSettings to HomeFragment navigation
        val forward = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = forward
    }
}