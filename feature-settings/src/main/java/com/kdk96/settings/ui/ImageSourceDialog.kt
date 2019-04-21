package com.kdk96.settings.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
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