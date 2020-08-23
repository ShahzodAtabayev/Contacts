package uz.xsoft.restapi.networks

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.xsoft.restapi.apps.App
import uz.xsoft.restapi.data.local.storage.LocalStorage

object ApiClient {
    private const val BASE_URL = "http://161.35.73.101:99/"
    private val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(ChuckInterceptor(App.instance))
        .addInterceptor {
            val requestOld = it.request()
            val pref = LocalStorage.instance
            val request = requestOld.newBuilder()
                .removeHeader("token")
                .addHeader("token", pref.token)
                .method(requestOld.method, requestOld.body)
                .build()
            val response = it.proceed(request)
            response
        }
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}