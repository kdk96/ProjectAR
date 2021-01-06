package com.kdk96.projectar.common.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.haroncode.gemini.StoreView
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseFragment<Action : Any, State : Any>(
    @LayoutRes contentLayoutId: Int = 0
) : Fragment(contentLayoutId), StoreView<Action, State> {

    private val actionChannel = Channel<Action>(Channel.BUFFERED)

    override val actionFlow: Flow<Action> = actionChannel.receiveAsFlow()

    override fun accept(value: State) = onStateChanged(value)

    abstract fun onStateChanged(state: State)

    fun postAction(action: Action) {
        lifecycleScope.launch {
            actionChannel.send(action)
        }
    }
}
