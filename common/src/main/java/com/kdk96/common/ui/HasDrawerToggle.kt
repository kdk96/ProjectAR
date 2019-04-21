package com.kdk96.common.ui

import androidx.appcompat.widget.Toolbar

interface HasDrawerToggle {
    fun setupDrawerToggle(toolbar: Toolbar)
    fun removeDrawerToggle()
}