package poran.cse.mockapiresponse.data.mockApi

import okhttp3.OkHttpClient
import poran.cse.mockapiresponse.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiProvider {
    //Return a lazy instance of OkHttp client

    private val myClient: OkHttpClient by lazy {
        val  client = OkHttpClient()
            .newBuilder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
        if (BuildConfig.DEBUG){
            client.addInterceptor(StudyDataInterceptor())
        }
        client.build()
    }

    fun getApiService(): ApiService = retrofitInstance.create(ApiService::class.java)

    //Return a lazy Retrofit instance
    private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(myClient)
            .build()
    }
}