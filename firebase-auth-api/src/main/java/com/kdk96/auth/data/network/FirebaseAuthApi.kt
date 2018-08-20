package com.kdk96.auth.data.network

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.kdk96.auth.data.repository.AuthApi
import com.kdk96.auth.domain.AccountCollisionException
import com.kdk96.auth.domain.FieldName
import com.kdk96.auth.domain.InvalidFieldException
import com.kdk96.auth.domain.NoSuchAccountException
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import java.io.IOException

class FirebaseAuthApi : AuthApi {
    override fun checkEmail(email: String) = Completable.create { emitter ->
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
                .addOnSuccessListener { onFetchMethodsForEmailSuccess(emitter, it) }
                .addOnFailureListener { onFetchMethodsForEmailFailure(emitter, it) }
    }

    private fun onFetchMethodsForEmailSuccess(emitter: CompletableEmitter, result: SignInMethodQueryResult) {
        if (result.signInMethods!!.isEmpty())
            emitter.tryOnError(NoSuchAccountException())
        else emitter.onComplete()
    }

    private fun onFetchMethodsForEmailFailure(emitter: CompletableEmitter, exception: Exception) {
        when (exception) {
            is FirebaseAuthInvalidCredentialsException ->
                emitter.tryOnError(InvalidFieldException(setOf(FieldName.EMAIL)))
            is FirebaseNetworkException -> emitter.tryOnError(IOException())
        }
    }

    override fun authorize(email: String, password: String) = Completable.create { emitter ->
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { onSignInFailure(emitter, it) }
    }

    private fun onSignInFailure(emitter: CompletableEmitter, exception: Exception) {
        when (exception) {
            is FirebaseAuthInvalidUserException -> emitter.tryOnError(NoSuchAccountException())
            is FirebaseAuthInvalidCredentialsException ->
                emitter.tryOnError(InvalidFieldException(setOf(FieldName.PASSWORD)))
            is FirebaseNetworkException -> emitter.tryOnError(IOException())
        }
    }

    override fun register(email: String, password: String, name: String) = Completable.create { emitter ->
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .continueWith { updateName(it.result.user, name) }
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { onRegisterFailure(emitter, it) }
    }

    private fun updateName(user: FirebaseUser, name: String) =
            user.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())

    private fun onRegisterFailure(emitter: CompletableEmitter, exception: Exception) {
        when (exception) {
            is FirebaseNetworkException -> emitter.tryOnError(IOException())
            is FirebaseAuthInvalidCredentialsException ->
                emitter.tryOnError(InvalidFieldException(setOf(FieldName.EMAIL)))
            is FirebaseAuthWeakPasswordException ->
                emitter.tryOnError(InvalidFieldException(setOf(FieldName.PASSWORD)))
            is FirebaseAuthUserCollisionException -> emitter.tryOnError(AccountCollisionException())
        }
    }
}