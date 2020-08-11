package com.florencenjeri.waterreminder.ui.view

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.florencenjeri.waterreminder.R
import com.florencenjeri.waterreminder.database.UserSettingsEntity
import com.florencenjeri.waterreminder.ui.viewModel.UserSettingsViewModel
import kotlinx.android.synthetic.main.profile_settings_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class ProfileSettingsFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(UserSettingsViewModel::class.java) }
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
        startTimeButton.setOnClickListener {
            showTimePicker(startTimeButton)
        }
        endTimeButton.setOnClickListener {
            showTimePicker(endTimeButton)
        }
        saveButton.setOnClickListener {
            val settings = UserSettingsEntity(
                0,
                editTextName.text.toString(),
                editTextGoal.text.toString(),
                startTimeButton.text.toString(),
                endTimeButton.text.toString(),
                getSelectedGender(),
                editTextHeight.text.toString(),
                editTextWeight.text.toString(),
                getSelectedMeasurements()
            )
            viewModel.saveUserSettings(settings)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
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
}