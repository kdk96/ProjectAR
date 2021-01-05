package com.kdk96.projectar.common.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.kdk96.projectar.common.R
import com.kdk96.projectar.common.unsafeLazy

abstract class FlowFragment(
    @LayoutRes contentLayoutId: Int = R.layout.layout_fragment_container
) : Fragment(contentLayoutId) {

    abstract val navigatorHolder: NavigatorHolder

    private val navigator: Navigator by unsafeLazy {
        AppNavigator(requireActivity(), R.id.fragmentContainerView, childFragmentManager)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
