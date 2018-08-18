package com.kdk96.common.ui

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.kdk96.common.R

class ProgressDialog : DialogFragment() {
    companion object {
        private const val ARG_RES_ID = "res id argument"
        fun newInstance(resId: Int? = null) = ProgressDialog().apply {
            resId?.let { arguments = Bundle().apply { putInt(ARG_RES_ID, it) } }
        }
    }

    private var resId = R.string.loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.ProgressDialogTheme)
        arguments?.let { resId = it.getInt(ARG_RES_ID) }
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.dialog_progress, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<TextView>(R.id.messageTV)?.setText(resId)
    }
}