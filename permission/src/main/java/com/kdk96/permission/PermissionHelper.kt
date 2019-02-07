package com.kdk96.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat

class PermissionHelper {
    interface RequestPermissionsResultListener {
        fun onPermissionGranted(requestCode: Int)
        fun onPermissionDenied(requestCode: Int) = Unit
    }

    var listener: RequestPermissionsResultListener? = null

    fun doActionWithCheckPermissions(
            fragment: Fragment,
            permissions: Array<out String>,
            requestCode: Int,
            rationaleMessageResId: Int
    ) {
        if (hasNotGrantedPermission(permissions, fragment.context!!)) {
            requestPermissions(fragment, permissions, requestCode, rationaleMessageResId)
        } else listener?.onPermissionGranted(requestCode)
    }


    private fun hasNotGrantedPermission(
            permissions: Array<out String>,
            context: Context
    ): Boolean = permissions.any {
        ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions(
            fragment: Fragment,
            permissions: Array<out String>,
            requestCode: Int,
            rationaleMessageResId: Int
    ) {
        if (shouldShowRationaleDialog(permissions.asIterable(), fragment)) {
            showRationaleDialog(fragment, rationaleMessageResId, permissions, requestCode)
        } else fragment.requestPermissions(permissions, requestCode)
    }

    private fun shouldShowRationaleDialog(
            permissions: Iterable<String>,
            fragment: Fragment
    ): Boolean = permissions.any { fragment.shouldShowRequestPermissionRationale(it) }

    private fun showRationaleDialog(
            fragment: Fragment,
            rationaleMessageResId: Int,
            permissions: Array<out String>,
            requestCode: Int
    ) {
        if (isRationaleDialogNotShown(fragment.fragmentManager!!)) {
            RationaleDialog.newInstance(fragment, rationaleMessageResId, permissions, requestCode)
                    .show(fragment.fragmentManager, RationaleDialog.TAG)
        }
    }

    private fun isRationaleDialogNotShown(fragmentManager: FragmentManager): Boolean =
            fragmentManager.findFragmentByTag(RationaleDialog.TAG) == null

    fun onRequestPermissionsResult(
            fragment: Fragment,
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray,
            onPermissionsDeniedMessages: Map<Int, Int>
    ) {
        val deniedPermissions = permissions.filterIndexed { index, _ ->
            grantResults[index] == PackageManager.PERMISSION_DENIED
        }
        val denied = deniedPermissions.isNotEmpty()
        if (denied) {
            if (shouldShowRationaleDialog(deniedPermissions, fragment)) {
                showRationaleDialog(
                        fragment,
                        onPermissionsDeniedMessages.getValue(requestCode),
                        deniedPermissions.toTypedArray(),
                        requestCode
                )
            } else {
                listener?.onPermissionDenied(requestCode)
                val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", fragment.context!!.packageName, null)
                ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
                Snackbar.make(fragment.view!!, onPermissionsDeniedMessages.getValue(requestCode), Snackbar.LENGTH_LONG)
                        .setAction(R.string.settings) { fragment.startActivity(intent) }
                        .show()
            }
        } else listener?.onPermissionGranted(requestCode)
    }
}