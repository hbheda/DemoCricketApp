package com.harshit.sportsinteractivedemo.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.harshit.sportsinteractivedemo.BuildConfig
import com.harshit.sportsinteractivedemo.model.Matchdetail
import com.harshit.sportsinteractivedemo.model.ResponseData
import com.harshit.sportsinteractivedemo.utils.Constant
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiInterface {

    companion object {

        private fun hasNetwork(context: Context): Boolean {
            var isConnected: Boolean = false // Initial Value
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }

        private val logging: HttpLoggingInterceptor  =
            if(BuildConfig.DEBUG)
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY)
            else
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.NONE)

        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        var retrofitService: ApiInterface? = null

        //  private val client = OkHttpClient.Builder().cache(myCache).build()
        fun getInstance(): ApiInterface {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constant.PRODUCTION_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                retrofitService = retrofit.create(ApiInterface::class.java)
            }
            return retrofitService!!
        }
    }

    @GET("/{url}")//nzin01312019187360.json
    suspend fun matchData(@Path("url") url: String): Response<ResponseData>

}