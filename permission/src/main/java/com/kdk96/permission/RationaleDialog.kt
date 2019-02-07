package com.kdk96.permission

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog

class RationaleDialog : DialogFragment() {
    companion object {
        const val TAG = "rationale dialog"
        private const val ARG_MESSAGE_ID = "arg message"
        private const val ARG_PERMISSIONS = "arg permissions"
        private const val ARG_REQUEST_CODE = "arg request code"

        fun newInstance(
                fragment: Fragment,
                messageId: Int,
                permissions: Array<out String>,
                requestCode: Int
        ): DialogFragment {
            val arguments = Bundle().apply {
                putInt(ARG_MESSAGE_ID, messageId)
                putStringArray(ARG_PERMISSIONS, permissions)
                putInt(ARG_REQUEST_CODE, requestCode)
            }
            return RationaleDialog().apply {
                setTargetFragment(fragment, 1)
                setArguments(arguments)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val messageId = arguments!!.getInt(ARG_MESSAGE_ID)
        val permissions = arguments!!.getStringArray(ARG_PERMISSIONS)
        val requestCode = arguments!!.getInt(ARG_REQUEST_CODE)
        return AlertDialog.Builder(context!!)
                .setMessage(messageId)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    targetFragment!!.requestPermissions(permissions, requestCode)
                }
                .setNegativeButton(android.R.string.no, null)
                .create()
    }
}