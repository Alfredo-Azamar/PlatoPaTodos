package mx.mr.platopatodos.ui.reg

import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.model.requests.RegisterReq
import mx.mr.platopatodos.model.responses.RegisterRes
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

/**
 * Registro ViewModel
 * @author Héctor González Sánchez
 */

class RegistroVM : ViewModel() {
    // Retrofit object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    fun uploadCostumer(name: String, p_lastName: String, m_lastName: String,
                       curp: String, bDate: Int, gender: String, vulSituation: Array<String>) {

        val requestBody = RegisterReq(name, p_lastName, m_lastName, curp, bDate, gender, vulSituation)

        apiCall.uploadCustomer(requestBody).enqueue(object : Callback<RegisterRes> {

            override fun onResponse(call: Call<RegisterRes>, response: Response<RegisterRes>) {
                if(response.isSuccessful) {
                    println("Mensaje: ${response.body()}")
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RegisterRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
            }

        })
    }

}