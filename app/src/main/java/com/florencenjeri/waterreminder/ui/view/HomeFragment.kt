package com.florencenjeri.waterreminder.ui.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.florencenjeri.waterreminder.R
import com.florencenjeri.waterreminder.ui.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import me.tankery.lib.circularseekbar.CircularSeekBar
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {
    val homeViewModel: HomeViewModel by viewModel()
    private var userId: Long = 1
    private lateinit var dailyGoal: String
    var dailyProgressAchieved: Int = 0
    lateinit var glassCapacity: String
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
        homeViewModel.setUpReminder()
        homeViewModel.getUserSettingsData().observe(viewLifecycleOwner, Observer { settings ->
            //Initialize the global variables
            dailyGoal = settings.goal
            glassCapacity = settings.cupMeasurements
            userId = settings.id
            welcomeTextView.text = String.format(getString(R.string.hello_user), settings.name)
            String.format(getString(R.string.notification_title), settings.name)
            val numberOfReminders = settings.goal.toDouble() / settings.cupMeasurements.toInt()
            Log.d("Settings", settings.toString())
            if (checkWaterConsumptionGoalAchieved()) {
                homeViewModel.stopReminder()
            }

            val firstLetter = settings.name.substring(0, 1).toUpperCase()
            generateProfileImage(firstLetter)
            setUpCircularSeekbar()
            goalsTextView.text =
                String.format(
                    getString(R.string.water_consumption_goal),
                    dailyGoal.toInt() - dailyProgressAchieved
                )
        })

        fab.setOnClickListener {
            //TODO : Increase the capacity of water consumed that day
            dailyProgressAchieved += glassCapacity.toInt()
            Log.d("Progress", dailyProgressAchieved.toString())
            setUpCircularSeekbar()
            goalsTextView.text =
                String.format(
                    getString(R.string.water_consumption_goal),
                    dailyGoal.toInt() - dailyProgressAchieved
                )
        }
    }

    fun checkWaterConsumptionGoalAchieved(): Boolean = dailyProgressAchieved == dailyGoal.toInt()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            navigateToSettingsScreen()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToSettingsScreen() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToSettingsFragment(userId)
        findNavController().navigate(action)
    }

    private fun generateProfileImage(firstLetter: String?) {

        //        binding.myProfileImage.setImageDrawable(drawable)
        var generator: ColorGenerator = ColorGenerator.MATERIAL // or use DEFAULT
        // generate random color
        val color1: Int = generator.randomColor
        val drawable = TextDrawable.builder()
            .beginConfig()
            .textColor(Color.WHITE)
            .useFont(Typeface.DEFAULT)
            .fontSize(60) /* size in px */
            .bold()
            .toUpperCase()
            .endConfig()
            .buildRoundRect(firstLetter, color1, 100)
        myProfileImage.setImageDrawable(drawable)
    }

    private fun setUpCircularSeekbar() {
        circularSeekBar.max = dailyGoal.toFloat()
        circularSeekBar.progress = dailyProgressAchieved.toFloat()

        circularSeekBar.setOnSeekBarChangeListener(object :
            CircularSeekBar.OnCircularSeekBarChangeListener {
            override fun onProgressChanged(
                circularSeekBar: CircularSeekBar?,
                progress: Float,
                fromUser: Boolean
            ) {
                //On fab clicked, update the progress bar
                circularSeekBar?.progress = dailyProgressAchieved.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: CircularSeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: CircularSeekBar?) {
                TODO("Not yet implemented")
            }


        })
    }
}