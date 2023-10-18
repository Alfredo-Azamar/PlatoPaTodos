package mx.mr.platopatodos.model

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Remote Server
 * @author Héctor González Sánchez
 */

object RetrofitManager {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://platopatodose3.ddns.net:8080/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    val apiService: ListaServiciosAPI by lazy {
        retrofit.create(ListaServiciosAPI::class.java)
    }
}
