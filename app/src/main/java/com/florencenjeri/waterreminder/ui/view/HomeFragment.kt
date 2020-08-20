package com.florencenjeri.waterreminder.ui.view

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.florencenjeri.waterreminder.R
import com.florencenjeri.waterreminder.ui.viewModel.HomeViewModel
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.fragment_home.*
import me.tankery.lib.circularseekbar.CircularSeekBar
import org.koin.android.viewmodel.ext.android.viewModel
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.PromptStateChangeListener


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {
    val homeViewModel: HomeViewModel by viewModel()
    private var userId: Long = 1

    /**
     * TODO: Remove all the logic of calculating the glasses of water into the VIewModel
     * Get the num of remaining glasses from the View Model
     * Update the Circular Seekbar from data obtained in the ViewModel
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zTransitionFromProfileSettingsFragment()
        zTransitionToUserProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        dailyProgressButton.isSelected = true
        homeViewModel.getUserSettingsData().observe(viewLifecycleOwner, Observer { settings ->
            //Initialize the global variables
            homeViewModel.totalNumOfGlasses = settings.numOfGlasses
            homeViewModel.dailyGoal = settings.numOfGlasses
            userId = settings.id
            welcomeTextView.text = String.format(getString(R.string.hello_user), settings.name)
            String.format(getString(R.string.notification_title), settings.name)
            Log.d("Settings", settings.toString())
            if (checkWaterConsumptionGoalAchieved()) {
                homeViewModel.stopReminder()
            }

            val firstLetter = settings.name.substring(0, 1).toUpperCase()
            val drawable = homeViewModel.generateProfileImage(firstLetter)
            myProfileImage.setImageDrawable(drawable)
            val goalText = String.format(
                getString(R.string.water_consumption_goal),
                homeViewModel.totalNumOfGlasses - homeViewModel.userProgress
            )
            val styledText: CharSequence = Html.fromHtml(goalText)
            goalsTextView.text = styledText
        })

        fab.setOnClickListener {
            homeViewModel.incrementWaterIntake()
            setWaterDrankOnFabClick()
        }
        setNewUserOnBoarded()

    }

    private fun setNewUserOnBoarded() {
        if (!homeViewModel.checkIfUserIsOnboarded()) {
            MaterialTapTargetPrompt.Builder(requireActivity())
                .setTarget(fab)
                .setPrimaryText("Track your water intake")
                .setSecondaryText(getString(R.string.track_water))
                .setPromptStateChangeListener(PromptStateChangeListener { prompt, state ->
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                        homeViewModel.setUserOnboardedToTrue()
                    }
                })
                .setBackgroundColour(resources.getColor(R.color.colorPrimary))
                .show()
        }
    }

    private fun setWaterDrankOnFabClick() {
        //TODO : Increase the capacity of water consumed that day

        //TODO : Fix this bug
        if (homeViewModel.totalNumOfGlasses - homeViewModel.userProgress >= 0) {
            setUpCircularSeekbar()
            val goalText = String.format(
                getString(R.string.water_consumption_goal),
                homeViewModel.totalNumOfGlasses - homeViewModel.userProgress
            )
            val styledText: CharSequence = Html.fromHtml(goalText)
            goalsTextView.text = styledText
        }
        if (homeViewModel.totalNumOfGlasses - homeViewModel.userProgress == 0) {
            goalsTextView.visibility = GONE
            textViewGoalComplete.visibility = VISIBLE
        }
    }

    fun checkWaterConsumptionGoalAchieved(): Boolean =
        homeViewModel.userProgress == homeViewModel.totalNumOfGlasses

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.userProfileFragment) {
            navigateToSettingsScreen()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToSettingsScreen() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToSettingsFragment(userId)
        findNavController().navigate(action)
    }

    private fun setUpCircularSeekbar() {
        circularSeekBar.max = homeViewModel.dailyGoal.toFloat()
        circularSeekBar.progress = homeViewModel.userProgress.toFloat()

        circularSeekBar.setOnSeekBarChangeListener(object :
            CircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar?,
                progress: Float,
                fromUser: Boolean
            ) {
                //On fab clicked, update the progress bar
                circularSeekBar?.progress = homeViewModel.userProgress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {
                TODO("Not yet implemented")
            }


        })
    }

    private fun zTransitionFromProfileSettingsFragment() {
        //From Profile to Home Fragment
        val forward = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        enterTransition = forward
        //Back to the ProfileSettings Fragment
        val backward = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        returnTransition = backward
    }

    private fun zTransitionToUserProfileFragment() {
        //UserProfile back to Home Fragment Navigation
        val backward = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        reenterTransition = backward
        //Home to UserProfile navigation
        val forward = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = forward
    }

}