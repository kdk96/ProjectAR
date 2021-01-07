package com.kdk96.projectar.di.auth

import android.content.Context
import android.util.Log
import com.kdk96.projectar.R
import com.kdk96.projectar.auth.data.api.AuthApi
import com.kdk96.projectar.auth.data.api.FirebaseAuthApi
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.Json
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.host
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
object FirebaseAuthModule {
    private const val HOST = "identitytoolkit.googleapis.com/v1"

    //    private const val REFRESH_URL = "https://securetoken.googleapis.com/v1/"

    @Provides
    @Singleton
    fun provideFirebaseAuthApi(httpClient: HttpClient): AuthApi = FirebaseAuthApi(httpClient)

    @Provides
    @Singleton
    fun provideHttpClient(context: Context) = HttpClient(OkHttp) {

        defaultRequest {
            url.protocol = URLProtocol.HTTPS
//            url.encodedPath= ":"
            contentType(ContentType.Application.Json)
            host = HOST

            parameter("key", context.getString(R.string.api_key))
        }

        Json {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
            })
        }

        Logging {
            logger = object : Logger {

                override fun log(message: String) {
                    Log.i("ktor", message)
                }
            }
            level = LogLevel.ALL
        }


        engine {
            preconfigured = OkHttpClient.Builder().build()
        }
    }
}
