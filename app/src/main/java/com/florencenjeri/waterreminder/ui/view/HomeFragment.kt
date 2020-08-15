package com.florencenjeri.waterreminder.ui.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.florencenjeri.waterreminder.R
import com.florencenjeri.waterreminder.ui.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {
    val homeViewModel: HomeViewModel by viewModel()
    private var userId: Long = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeViewModel.setUpReminder()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        homeViewModel.getUserSettingsData().observe(viewLifecycleOwner, Observer { settings ->
            userId = settings.id
            welcomeTextView.text = String.format(getString(R.string.hello_user), settings.name)
            val toGoal = settings.goal.toInt() - settings.cupMeasurements.toInt()
            val numberOfReminders = settings.goal.toDouble() / settings.cupMeasurements.toInt()
            goalsTextView.text =
                String.format(getString(R.string.water_consumption_goal), settings.goal)
            String.format(getString(R.string.notification_title), settings.name)
            if (toGoal == 0) {
                homeViewModel.stopReminder()
            }

            val firstLetter = settings.name.substring(0, 1).toUpperCase()
            generateProfileImage(firstLetter)
        })
        fab.setOnClickListener {
            //TODO
        }
    }

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
}