package com.florencenjeri.waterreminder.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.florencenjeri.waterreminder.R
import com.florencenjeri.waterreminder.ui.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_second.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HomeFragment : Fragment() {
    val homeViewModel: HomeViewModel by viewModel()
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
            welcomeTextView.text = String.format(getString(R.string.hello_user), settings.name)
            val toGoal = settings.goal.toInt() - settings.cupMeasurements.toInt()
            val numberOfReminders = settings.goal.toDouble() / settings.cupMeasurements.toInt()
            goalsTextView.text =
                String.format(getString(R.string.water_consumption_goal), settings.goal)
            String.format(getString(R.string.notification_title), settings.name)
//            if (toGoal == 0) {
//                homeViewModel.stopReminder()
//            }
        })
    }

}