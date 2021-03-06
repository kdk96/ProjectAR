package com.kdk96.permission

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class RationaleDialog : DialogFragment() {
    interface CancelListener {
        fun onRationaleDialogCancel(requestCode: Int)
    }

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
                isCancelable = false
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val messageId = arguments!!.getInt(ARG_MESSAGE_ID)
        val permissions = arguments!!.getStringArray(ARG_PERMISSIONS)!!
        val requestCode = arguments!!.getInt(ARG_REQUEST_CODE)
        return AlertDialog.Builder(context!!)
            .setMessage(messageId)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                targetFragment!!.requestPermissions(permissions, requestCode)
            }
            .setNegativeButton(android.R.string.no) { _, _ ->
                (targetFragment as? CancelListener)?.onRationaleDialogCancel(requestCode)
            }
            .create()
    }
}