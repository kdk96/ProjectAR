package com.kdk96.projectar.auth.presentation.signin

import com.haroncode.gemini.element.EventProducer
import com.haroncode.gemini.element.Middleware
import com.haroncode.gemini.element.Reducer
import com.haroncode.gemini.store.BaseStore
import com.kdk96.projectar.auth.domain.AuthDataValidator
import com.kdk96.projectar.auth.domain.AuthRepository
import com.kdk96.projectar.auth.presentation.signin.SignInStore.Action
import com.kdk96.projectar.auth.presentation.signin.SignInStore.Effect
import com.kdk96.projectar.auth.presentation.signin.SignInStore.Event
import com.kdk96.projectar.auth.presentation.signin.SignInStore.State
import com.kdk96.projectar.common.domain.ErrorMessageProvider
import com.kdk96.projectar.common.domain.result.Result
import com.kdk96.projectar.common.domain.result.asResult
import com.kdk96.projectar.common.domain.validation.Unknown
import com.kdk96.projectar.common.domain.validation.Valid
import com.kdk96.projectar.common.domain.validation.VerifiableValue
import com.kdk96.projectar.common.orEmpty
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignInStore @Inject constructor(
    authDataValidator: AuthDataValidator,
    authRepository: AuthRepository,
    errorMessageProvider: ErrorMessageProvider
) : BaseStore<Action, State, Event, Effect>(
    initialState = State(),
    reducer = ReducerImpl(),
    middleware = MiddlewareImpl(authDataValidator, authRepository),
    eventProducer = EventProducerImpl(errorMessageProvider)
) {

    data class State(
        val isLoading: Boolean = false,
        val error: Throwable? = null,
        val email: VerifiableValue<String> = VerifiableValue("", Unknown),
        val password: VerifiableValue<String> = VerifiableValue("", Unknown)
    )

    sealed class Action {
        data class ChangeUsername(
            val username: String
        ) : Action()

        data class ChangePassword(
            val password: String
        ) : Action()

        object SignIn : Action()
    }

    sealed class Event {
        data class Error(val message: String) : Event()
    }

    sealed class Effect {
        data class UsernameChanged(
            val username: VerifiableValue<String>
        ) : Effect()

        data class UsernameChecked(
            val usernameCheckResult: Result<VerifiableValue<String>>
        ) : Effect()

        data class PasswordChanged(
            val password: VerifiableValue<String>
        ) : Effect()

        object SignedIn : Effect()
    }

    class MiddlewareImpl(
        private val authDataValidator: AuthDataValidator,
        private val authRepository: AuthRepository
    ) : Middleware<Action, State, Effect> {

        override fun execute(action: Action, state: State) = when (action) {
            Action.SignIn -> signIn(state)
            is Action.ChangeUsername -> changeUsername(action, state)
            is Action.ChangePassword -> TODO()
        }

        private fun signIn(state: State) = flow {
            val email = state.email.value
            val verifiable = authDataValidator.validateEmail(email)

            if (verifiable == Valid) {
                val checkResultFlow = authRepository.checkEmail(email)
                    .map { state.email.copy(verifiable = it) }
                    .asResult()
                    .map(Effect::UsernameChecked)
                emitAll(checkResultFlow)
            } else {
                emit(Effect.UsernameChanged(state.email.copy(verifiable = verifiable)))
            }
        }

        private fun changeUsername(action: Action.ChangeUsername, state: State) =
            action.username.takeIf { it != state.email.value }
                ?.let {
                    flowOf(Effect.UsernameChanged(VerifiableValue(it, Unknown)))
                }
                .orEmpty()
    }

    class ReducerImpl : Reducer<State, Effect> {

        override fun reduce(state: State, effect: Effect) = when (effect) {
            is Effect.UsernameChanged -> state.copy(email = effect.username)
            is Effect.PasswordChanged -> state.copy(password = effect.password)
            is Effect.UsernameChecked -> when (val result = effect.usernameCheckResult) {
                Result.Loading -> state.copy(isLoading = true)
                is Result.Data -> state.copy(isLoading = false, email = result.value)
                is Result.Error -> state.copy(isLoading = false)
            }
            Effect.SignedIn -> TODO()
        }
    }

    class EventProducerImpl(
        private val errorMessageProvider: ErrorMessageProvider
    ) : EventProducer<State, Effect, Event> {

        override fun produce(state: State, effect: Effect): Event? {
            return when (effect) {
                is Effect.UsernameChecked -> {
                    (effect.usernameCheckResult as? Result.Error)?.let {
                        Event.Error(errorMessageProvider.provide(it.throwable))
                    }
                }
                else -> null
            }
        }
    }
}
