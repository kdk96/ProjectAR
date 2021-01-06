package com.kdk96.projectar.common.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.kdk96.projectar.common.R
import com.kdk96.projectar.common.unsafeLazy
import com.kdk96.tanto.android.inject
import javax.inject.Inject

abstract class FlowFragment(
    @LayoutRes contentLayoutId: Int = R.layout.layout_fragment_container
) : Fragment(contentLayoutId) {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    protected val navigator: Navigator by unsafeLazy {
        AppNavigator(requireActivity(), R.id.fragmentContainerView, childFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
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
