package com.florencenjeri.waterreminder.ui.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.florencenjeri.waterreminder.App
import com.florencenjeri.waterreminder.R
import com.florencenjeri.waterreminder.ui.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_second.*
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
//        homeViewModel.setUpReminder()
        return inflater.inflate(R.layout.fragment_second, container, false)
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
            Log.d("Settings", settings.toString())
            if (toGoal == 0) {
                String.format(getString(R.string.notification_title), settings.name)
                homeViewModel.stopReminder()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            Toast.makeText(App.getAppContext(), "Navigate!!", Toast.LENGTH_SHORT).show()
            navigateToSettingsScreen()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToSettingsScreen() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToSettingsFragment(userId)
        findNavController().navigate(action)
    }
}