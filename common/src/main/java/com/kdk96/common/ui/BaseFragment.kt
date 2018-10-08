package com.kdk96.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.kdk96.common.di.*

abstract class BaseFragment : MvpAppCompatFragment(), HasComponent {
    companion object {
        private const val TAG_PROGRESS = "progress"
    }

    protected abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(layoutRes, container, false)

    protected inline fun <reified T : Component> getComponent(
            componentBuilder: () -> Component = ::buildComponent
    ) = getComponent<T>(findComponentManager(), componentBuilder)

    protected fun findComponentManager() = findForInjection<ComponentManager>()

    protected abstract fun buildComponent(): Component

    protected fun showProgressDialog(show: Boolean, resId: Int? = null) {
        if (!isAdded) return
        var progressDialog = childFragmentManager.findFragmentByTag(TAG_PROGRESS)
        if (progressDialog == null && show) {
            progressDialog = ProgressDialog.newInstance(resId)
            progressDialog.show(childFragmentManager, TAG_PROGRESS)
            childFragmentManager.executePendingTransactions()
        } else if (progressDialog != null && !show) {
            (progressDialog as ProgressDialog).dismissAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        }
    }

    protected fun clearComponentsOnDestroy(vararg components: Class<out Component>) {
        if (activity!!.isFinishing) clearComponents(components)
        if (isStateSaved) return
        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }
        if (isRemoving || anyParentIsRemoving) clearComponents(components)
    }

    private fun clearComponents(componentsForRemove: Array<out Class<out Component>>) {
        val components = findComponentManager().components
        componentsForRemove.forEach { components.remove(it) }
    }

    abstract fun onBackPressed()
}