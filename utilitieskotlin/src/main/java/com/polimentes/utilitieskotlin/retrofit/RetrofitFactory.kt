package com.polimentes.utilitieskotlin.retrofit

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.util.Base64
import com.polimentes.utilitieskotlin.SharedPreferencesManager
import com.polimentes.utilitieskotlin.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Luis L. Polimentes
 *         Description:
 *         created on 02/03/2018
 */
object RetrofitFactory {


    @SuppressLint("MissingPermission")
    private fun interceptHeader(context: Context): Interceptor {
        return Interceptor { chain ->
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            val hasNetwork = networkInfo != null && networkInfo.isConnectedOrConnecting
            if (!hasNetwork) {
                throw NoInternetException()
            } else {
                val token: String = SharedPreferencesManager[context, Constants.SPM_ACCESS_TOKEN, Constants.SPM_DEFAULT_STRING]
                val originalRequest: Request
                originalRequest = if (token == Constants.SPM_DEFAULT_STRING) {
                    chain.request()
                } else {
                    val builderToCall: Request.Builder = chain.request().newBuilder()
                    builderToCall.header(Constants.AUTH, Constants.TYPE_TOKEN + " " + token)
                    builderToCall.header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    builderToCall.build()
                }
                chain.proceed(originalRequest)
            }

        }
    }

    @SuppressLint("MissingPermission")
    private fun interceptHeader(clientId: String, clientSecret: String, context: Context): Interceptor {
        return Interceptor { chain ->
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            val hasNetwork = networkInfo != null && networkInfo.isConnectedOrConnecting
            if (!hasNetwork) {
                throw NoInternetException()
            } else {
                val builder: Request.Builder = chain.request().newBuilder()
                builder.addHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_XWWFORM)
                val credentials = "$clientId:$clientSecret"
                val auth = Constants.BASIC_AUTH + " " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
                builder.addHeader(Constants.AUTH, auth)
                val originalRequest: Request = builder.build()
                chain.proceed(originalRequest)
            }
        }
    }

    private fun httpClient(context: Context): OkHttpClient {
        val okHttp = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(interceptHeader(context))
                .readTimeout(Constants.TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .writeTimeout(Constants.TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .connectTimeout(Constants.TIMEOUT_MS, TimeUnit.MILLISECONDS)
        return okHttp.build()
    }

    private fun httpClient(clientId: String, clientSecret: String, context: Context): OkHttpClient {
        val okHttp = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(interceptHeader(clientId, clientSecret, context))
                .readTimeout(Constants.TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .writeTimeout(Constants.TIMEOUT_MS, TimeUnit.MILLISECONDS)
                .connectTimeout(Constants.TIMEOUT_MS, TimeUnit.MILLISECONDS)
        return okHttp.build()
    }

    fun <T> makeService(urlBase: String, serviceClass: Class<T>, context: Context): T {
        val builder = Retrofit.Builder()
                .baseUrl(urlBase)
                .client(httpClient(context))
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return builder.create(serviceClass)
    }

    fun <T> makeService(urlBase: String, serviceClass: Class<T>, clientId: String, clientSecret: String, context: Context): T {
        val builder = Retrofit.Builder()
                .baseUrl(urlBase)
                .client(httpClient(clientId, clientSecret, context))
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return builder.create(serviceClass)
    }
}