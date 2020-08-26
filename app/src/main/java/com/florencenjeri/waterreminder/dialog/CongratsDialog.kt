package com.florencenjeri.waterreminder.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.florencenjeri.waterreminder.R
import kotlinx.android.synthetic.main.fragment_congrats_dialog.view.*

class CongratsDialog : DialogFragment() {
    companion object {

        const val TAG = "SimpleDialog"

        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(title: String): CongratsDialog {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            val fragment = CongratsDialog()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_congrats_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
        view.textViewCongratulations.text = arguments?.getString(KEY_TITLE)
    }

    private fun setupClickListeners(view: View) {
        view.buttonClaimBadge.setOnClickListener {
            // TODO: Do some task here
            dismiss()
        }
    }

}