package com.kdk96.common.ui

import android.support.v7.widget.Toolbar

interface HasDrawerToggle {
    fun setupDrawerToggle(toolbar: Toolbar)
    fun removeDrawerToggle()
}