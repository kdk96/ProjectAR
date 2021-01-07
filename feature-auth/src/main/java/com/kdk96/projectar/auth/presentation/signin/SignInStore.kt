package com.kdk96.projectar.auth.presentation.signin

import com.haroncode.gemini.element.EventProducer
import com.haroncode.gemini.element.Middleware
import com.haroncode.gemini.element.Reducer
import com.haroncode.gemini.store.BaseStore
import com.kdk96.projectar.auth.domain.AuthDataValidator
import com.kdk96.projectar.auth.domain.AuthErrorMessageProvider
import com.kdk96.projectar.auth.domain.AuthRepository
import com.kdk96.projectar.auth.domain.InvalidCredentialsException
import com.kdk96.projectar.auth.presentation.signin.SignInStore.Action
import com.kdk96.projectar.auth.presentation.signin.SignInStore.Effect
import com.kdk96.projectar.auth.presentation.signin.SignInStore.Event
import com.kdk96.projectar.auth.presentation.signin.SignInStore.State
import com.kdk96.projectar.common.domain.ErrorMessageProvider
import com.kdk96.projectar.common.domain.result.Result
import com.kdk96.projectar.common.domain.result.UnitResult
import com.kdk96.projectar.common.domain.result.asError
import com.kdk96.projectar.common.domain.result.asResult
import com.kdk96.projectar.common.domain.validation.Unknown
import com.kdk96.projectar.common.domain.validation.Valid
import com.kdk96.projectar.common.domain.validation.VerifiableValue
import com.kdk96.projectar.common.domain.validation.Violation
import com.kdk96.projectar.common.orEmpty
import com.kdk96.projectar.common.unitFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SignInStore @Inject constructor(
    authDataValidator: AuthDataValidator,
    authRepository: AuthRepository,
    errorMessageProvider: ErrorMessageProvider,
    authErrorMessageProvider: AuthErrorMessageProvider
) : BaseStore<Action, State, Event, Effect>(
    initialState = State(),
    reducer = ReducerImpl(),
    middleware = MiddlewareImpl(authDataValidator, authRepository, authErrorMessageProvider),
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

        data class SignedIn(
            val result: UnitResult
        ) : Effect()
    }

    class MiddlewareImpl(
        private val authDataValidator: AuthDataValidator,
        private val authRepository: AuthRepository,
        private val authErrorMessageProvider: AuthErrorMessageProvider
    ) : Middleware<Action, State, Effect> {

        override fun execute(action: Action, state: State) = when (action) {
            is Action.ChangeUsername -> changeUsername(action, state)
            is Action.ChangePassword -> changePassword(action, state)
            Action.SignIn -> signIn(state)
        }

        private fun changeUsername(action: Action.ChangeUsername, state: State) =
            action.username.takeIf { it != state.email.value }
                ?.let {
                    flowOf(Effect.UsernameChanged(VerifiableValue(it, Unknown)))
                }
                .orEmpty()

        private fun changePassword(action: Action.ChangePassword, state: State) =
            action.password.takeIf { it != state.password.value }
                ?.let {
                    flowOf(Effect.PasswordChanged(VerifiableValue(it, Unknown)))
                }
                .orEmpty()

        private fun signIn(state: State): Flow<Effect> {
            val email = state.email
            return if (email.verifiable == Valid) {
                signIn(email, state.password)
            } else {
                checkEmail(email)
            }
        }

        private fun signIn(
            email: VerifiableValue<String>,
            password: VerifiableValue<String>
        ): Flow<Effect> = if (password.value.isNotBlank()) {
            unitFlow {
                authRepository.signIn(email = email.value, password = password.value)
            }
                .asResult()
                .map<UnitResult, Effect>(Effect::SignedIn)
                .onStart { emit(Effect.PasswordChanged(password.copy(verifiable = Unknown))) }
        } else {
            flowOf(
                Effect.PasswordChanged(
                    password.copy(verifiable = Violation(authErrorMessageProvider.emptyPassword))
                )
            )
        }

        private fun checkEmail(email: VerifiableValue<String>): Flow<Effect> {
            val emailVerifiable = authDataValidator.validateEmail(email.value)
            return if (emailVerifiable == Valid) {
                authRepository.checkEmail(email.value)
                    .map { email.copy(verifiable = it) }
                    .asResult()
                    .map(Effect::UsernameChecked)
            } else {
                flowOf(Effect.UsernameChanged(email.copy(verifiable = emailVerifiable)))
            }
        }
    }

    class ReducerImpl : Reducer<State, Effect> {

        override fun reduce(state: State, effect: Effect) = when (effect) {
            is Effect.UsernameChanged -> state.copy(
                email = effect.username,
                password = VerifiableValue("", Unknown)
            )
            is Effect.PasswordChanged -> state.copy(password = effect.password)
            is Effect.UsernameChecked -> when (val result = effect.usernameCheckResult) {
                Result.Loading -> state.copy(isLoading = true)
                is Result.Data -> state.copy(isLoading = false, email = result.value)
                is Result.Error -> state.copy(isLoading = false)
            }
            is Effect.SignedIn -> when (val result = effect.result) {
                Result.Loading -> state.copy(isLoading = true)
                is Result.Data -> state.copy(isLoading = false)
                is Result.Error -> {
                    val invalidCredentialsException = result.throwable as? InvalidCredentialsException
                    state.copy(
                        isLoading = false,
                        email = invalidCredentialsException?.emailViolation?.let { state.email.copy(verifiable = it) } ?: state.email,
                        password = invalidCredentialsException?.passwordViolation?.let { state.password.copy(verifiable = it) }
                            ?: state.password
                    )
                }
            }

        }
    }

    class EventProducerImpl(
        private val errorMessageProvider: ErrorMessageProvider
    ) : EventProducer<State, Effect, Event> {

        override fun produce(state: State, effect: Effect): Event? {
            return when (effect) {
                is Effect.UsernameChecked -> {
                    effect.usernameCheckResult.asError()?.let {
                        Event.Error(errorMessageProvider.provide(it.throwable))
                    }
                }
                is Effect.SignedIn -> {
                    effect.result.asError()
                        ?.throwable
                        ?.takeIf { it !is InvalidCredentialsException }
                        ?.let { Event.Error(errorMessageProvider.provide(it)) }
                }
                else -> null
            }
        }
    }
}
