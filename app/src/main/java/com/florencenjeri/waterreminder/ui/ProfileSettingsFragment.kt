package com.florencenjeri.waterreminder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.florencenjeri.waterreminder.R
import kotlinx.android.synthetic.main.profile_settings_fragment.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProfileSettingsFragment : Fragment() {

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
}