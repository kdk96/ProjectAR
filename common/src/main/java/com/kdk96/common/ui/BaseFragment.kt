package com.kdk96.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kdk96.common.di.ComponentManager
import com.kdk96.common.di.DaggerComponent

abstract class BaseFragment : MvpAppCompatFragment() {
    companion object {
        private const val TAG_PROGRESS = "progress"
    }

    protected abstract val layoutRes: Int
    protected lateinit var componentBuilder: () -> DaggerComponent
    private var instanceStateSaved: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(layoutRes, container, false)

    protected inline fun <reified T : DaggerComponent> getComponent(): T =
            ComponentManager.getOrPutComponent(this.javaClass.simpleName, componentBuilder) as T

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
    }

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needClearComponent()) {
            ComponentManager.clearComponent(this.javaClass.simpleName)
        }
    }

    private fun needClearComponent(): Boolean = when {
        activity?.isChangingConfigurations == true -> false
        activity?.isFinishing == true -> true
        else -> isRealRemoving()
    }

    private fun isRealRemoving(): Boolean = (isRemoving && !instanceStateSaved)
            || ((parentFragment as? BaseFragment)?.isRealRemoving() ?: false)

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

    open fun onBackPressed() {}
}