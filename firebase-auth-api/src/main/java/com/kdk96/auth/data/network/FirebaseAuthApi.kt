package com.kdk96.auth.data.network

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.SignInMethodQueryResult
import com.kdk96.auth.data.repository.AuthApi
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
}