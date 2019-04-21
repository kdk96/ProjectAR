package com.kdk96.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar

class PermissionHelper {
    interface RequestPermissionsResultListener {
        fun onPermissionGranted(requestCode: Int)
        fun onNeverAskAgain(requestCode: Int, permissionDeniedMessageId: Int)
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
                listener?.onNeverAskAgain(requestCode, onPermissionsDeniedMessages.getValue(requestCode))
            }
        } else listener?.onPermissionGranted(requestCode)
    }
}

val Fragment.settingsIntent
    get() = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context!!.packageName, null)
    ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

fun Fragment.showOnNeverAskAgainSnackbar(messageResId: Int) {
    Snackbar.make(view!!, messageResId, Snackbar.LENGTH_LONG)
        .setAction(R.string.settings) { startActivity(settingsIntent) }
        .show()
}