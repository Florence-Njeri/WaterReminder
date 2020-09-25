package com.florencenjeri.waterreminder.ui.view

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.florencenjeri.waterreminder.R
import com.florencenjeri.waterreminder.dialog.CongratsDialog
import com.florencenjeri.waterreminder.ui.viewModel.HomeViewModel
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.fragment_home.*
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
        homeViewModel.startReminder()
        homeViewModel.startDailyWaterDatabaseSync()
        homeViewModel.getUserSettingsData().observe(viewLifecycleOwner, Observer { settings ->
            //Initialize the global variables
            homeViewModel.dailyWaterConsumptionGoal = settings.numOfGlasses
            userId = settings.id
            welcomeTextView.text = String.format(getString(R.string.hello_user), settings.name)
            String.format(getString(R.string.notification_title), settings.name)

            val styledBottomText = Html.fromHtml(
                String.format(
                    getString(R.string.goal_progress),
                    homeViewModel.userProgress.toString()
                )
            )
            Log.d("GoalAttanedInGlasses", homeViewModel.userProgress.toString())
            arcProgress.bottomText = styledBottomText.toString()
            val firstLetter = settings.name.substring(0, 1).toUpperCase()
            val drawable = homeViewModel.generateProfileImage(firstLetter)
            myProfileImage.setImageDrawable(drawable)
            setGoalsTextView()
            setNumOfGlassesLeftText()
            setUpCircularSeekbar()
        })

        fab.setOnClickListener {
            setWaterDrankOnFabClick()
        }
        setNewUserOnBoarded()
        arcProgress.progress = 0

    }

    private fun setNumOfGlassesLeftText() {
        numOfGlassesLeft.text =
            String.format(
                getString(R.string.glasses_left),
                homeViewModel.getNumberOfGlassesLeft()
            )
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
        homeViewModel.incrementWaterIntake()
        if (homeViewModel.getNumberOfGlassesLeft() >= 0) {
            setUpCircularSeekbar()
            setNumOfGlassesLeftText()
            homeViewModel.setNumberOfGlassesDrank(homeViewModel.userProgress)
        }
        if (homeViewModel.getNumberOfGlassesLeft() == 0) {
            showGoalAchievedDialog()
            homeViewModel.stopReminder()
        }
    }

    private fun setGoalsTextView() {
        val goalText = String.format(
            getString(R.string.todays_goal),
            homeViewModel.dailyWaterConsumptionGoal
        )
        val styledText: CharSequence = Html.fromHtml(goalText)
        goalsTextView.text = styledText
    }

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
        val max = homeViewModel.dailyWaterConsumptionGoal.toFloat()
        val progress = homeViewModel.userProgress.toFloat()
        Log.d("UserP", max.toString())
        Log.d("UserP2", progress.toString())
        val percentage = progress / max * 100
        arcProgress.progress = percentage.toInt()
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

    private fun showGoalAchievedDialog() {
        CongratsDialog.newInstance(getString(R.string.goal_achieved_dialog_title))
            .show(parentFragmentManager, CongratsDialog.TAG)
    }
}