package com.kdk96.settings.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import com.kdk96.settings.R

class ImageSourceDialog : DialogFragment() {
    companion object {
        fun newInstance(fragment: Fragment) = ImageSourceDialog().apply {
            setTargetFragment(fragment, 0)
        }
    }

    interface OnItemSelectListener {
        fun onTakePhoto()
        fun onPickFromGallery()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = targetFragment as OnItemSelectListener
        return AlertDialog.Builder(context!!)
                .setItems(R.array.image_source_array) { _, which ->
                    when (which) {
                        0 -> listener.onTakePhoto()
                        1 -> listener.onPickFromGallery()
                    }
                }.create()
    }
}