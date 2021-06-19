package mak.onelinecoding.qrcodepdfapp

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object WebApiFactory {

    private const val BASE_URL = "https://api.wexpom.com/"

    private val authInterceptor = Interceptor { chain ->
        val newRequest = chain.request()
            .newBuilder()
            .header("deviceId", App.DEVICE_ID)
            .build()

        chain.proceed(newRequest)
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()


    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()


    val webApi: WebApi = retrofit.create(WebApi::class.java)


    private val moshi = Moshi.Builder().build()
    private val listType = Types.newParameterizedType(List::class.java, PdfModel::class.java)
    val adapter: JsonAdapter<List<PdfModel>> = moshi.adapter(listType)


}