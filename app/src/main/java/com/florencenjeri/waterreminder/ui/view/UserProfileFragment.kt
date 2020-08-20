package com.florencenjeri.waterreminder.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.florencenjeri.waterreminder.R
import com.florencenjeri.waterreminder.ui.viewModel.HomeViewModel
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.fragment_user_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserProfileFragment : Fragment() {
    val homeViewModel: HomeViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zTransitionFromHomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val safeArgs =
                UserProfileFragmentArgs.fromBundle(
                    it
                )
            homeViewModel.getUserById(safeArgs.userId)
                .observe(viewLifecycleOwner, Observer { settings ->
                    val firstLetter = settings.name.substring(0, 1).toUpperCase()
                    val profileDrawable = homeViewModel.generateProfileImage(firstLetter)
                    userProfileImage.setImageDrawable(profileDrawable)
                    userName.text = settings.name
                    goal.text = settings.goal
                    glassSize.text = settings.cupMeasurements
                    sleepingTime.text = settings.startTime
                    wakeUpTime.text = settings.endTime
                    weight.text = settings.weight
                    height.text = settings.height
                    if (settings.gender == "Male") {
                        maleButton.isSelected = true
                    } else {
                        femaleButtonSettings.isSelected = true
                    }
                    if (settings.measurementUnits == "Kgs | Ml") {
                        kgsUnits.isSelected = true
                    } else {
                        poundsUnitsSettings.isSelected = true
                    }
                    waterIntakeText.text = settings.numOfGlasses.toString()
                })

        }
    }

    private fun zTransitionFromHomeFragment() {
        //From Home to UserProfile Fragment
        val forward = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        enterTransition = forward
        //Back to the Home Fragment
        val backward = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        returnTransition = backward
    }
}