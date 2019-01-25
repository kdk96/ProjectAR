package com.kdk96.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

class PermissionHelper {
    interface RequestPermissionsResultListener {
        fun onPermissionGranted(requestCode: Int)
        fun onPermissionDenied(requestCode: Int) = Unit
    }

    var listener: RequestPermissionsResultListener? = null

    fun requestPermissions(fragment: Fragment,
                           permissions: Array<out String>,
                           requestCode: Int,
                           rationaleMessageResId: Int) =
            if (permissions.any { ContextCompat.checkSelfPermission(fragment.context!!, it) != PackageManager.PERMISSION_GRANTED }) {
                if (permissions.any { fragment.shouldShowRequestPermissionRationale(it) }) {
                    RationaleDialog.newInstance(fragment, rationaleMessageResId, permissions, requestCode)
                            .show(fragment.fragmentManager, "rationale dialog")
                } else fragment.requestPermissions(permissions, requestCode)
                false
            } else true

    fun onRequestPermissionsResult(fragment: Fragment,
                                   requestCode: Int,
                                   permissions: Array<out String>,
                                   grantResults: IntArray,
                                   onPermissionsDeniedMessages: Map<Int, Int>) {
        var denied = false
        val deniedPermissions = mutableListOf<String>()
        grantResults.forEachIndexed { index, result ->
            if (result == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permissions[index])
                denied = true
            }
        }
        if (denied) {
            if (deniedPermissions.any { fragment.shouldShowRequestPermissionRationale(it) }) {
                RationaleDialog.newInstance(fragment, onPermissionsDeniedMessages[requestCode]!!, deniedPermissions.toTypedArray(), requestCode)
                        .show(fragment.fragmentManager, "rationale dialog")
            } else {
                listener?.onPermissionDenied(requestCode)
                val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", fragment.context!!.packageName, null)
                ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
                Snackbar.make(fragment.view!!, onPermissionsDeniedMessages[requestCode]!!, Snackbar.LENGTH_LONG)
                        .setAction("Settings") { fragment.startActivity(intent) }
                        .show()
            }
        } else listener?.onPermissionGranted(requestCode)
    }
}