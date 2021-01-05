package com.kdk96.projectar.common.ui

import androidx.appcompat.widget.Toolbar

interface HasDrawerToggle {
    fun setupDrawerToggle(toolbar: Toolbar)
    fun removeDrawerToggle()
}