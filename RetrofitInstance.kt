package app.bandhan.microbanking.network


import app.bandhan.microbanking.util.CONSTANT
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier


class RetrofitInstance {
    companion object {
        private val retrofitLogin by lazy {
            //val gson = GsonBuilder().disableHtmlEscaping().excludeFieldsWithModifiers(Modifier.TRANSIENT).create()
            //GsonBuilder().setExclusionStrategies()
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(CONSTANT.DEVELOPMENT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val loginApi by lazy {
            retrofitLogin.create(API::class.java)
        }


    }
}
