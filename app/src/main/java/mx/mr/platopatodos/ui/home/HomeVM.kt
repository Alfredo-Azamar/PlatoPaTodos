package mx.mr.platopatodos.ui.home

import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.model.requests.ChgStatusDining
import mx.mr.platopatodos.model.responses.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeVM : ViewModel() {

    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    fun updateDinStatus(diningName: String){
        val diningStatus = "Cerrado"
        val requestBody = ChgStatusDining(diningName, diningStatus)

        apiCall.updateDinStatus(requestBody).enqueue(object: Callback<StringResponse> {

            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
                if (response.isSuccessful) {
                    println("Mensaje: El estatus ha cambiado")
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
            }

        })
    }
}