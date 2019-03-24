package com.kdk96.questinfo.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import com.kdk96.questinfo.R

class CancelParticipationDialog : DialogFragment() {
    companion object {
        fun newInstance(fragment: Fragment) = CancelParticipationDialog().apply {
            setTargetFragment(fragment, 0)
        }
    }

    interface CancelConfirmedListener {
        fun onCancelConfirmed()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = targetFragment as CancelConfirmedListener
        return AlertDialog.Builder(context!!)
                .setMessage(R.string.cancel_participation_message)
                .setPositiveButton(android.R.string.ok) { _, _ -> listener.onCancelConfirmed() }
                .setNegativeButton(android.R.string.cancel, null)
                .create()
    }
}