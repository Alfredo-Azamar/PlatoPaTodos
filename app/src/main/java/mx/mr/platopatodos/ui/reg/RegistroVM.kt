package mx.mr.platopatodos.ui.reg

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.model.requests.RegisterReq
import mx.mr.platopatodos.model.responses.RegisterRes
import mx.mr.platopatodos.model.responses.vulCondItem
import mx.mr.platopatodos.model.responses.vulCondRes
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
    val vulCondList = MutableLiveData<List<vulCondItem>>()
    var customerToken = MutableLiveData<String>()

    fun uploadCostumer(name: String, p_lastName: String, m_lastName: String,
                       curp: String, bDate: String, gender: String,
                       vulSituation: Array<String>, callback: (Boolean) -> Unit) {

        val requestBody = RegisterReq(name, p_lastName, m_lastName, curp, bDate, gender, vulSituation)

        apiCall.uploadCustomer(requestBody).enqueue(object : Callback<RegisterRes> {

            override fun onResponse(call: Call<RegisterRes>, response: Response<RegisterRes>) {
                if(response.isSuccessful) {
                    println("Mensaje: ${response.body()}")
                    customerToken.value = response.body()?.token.toString()
                    callback(true)
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()?.string()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<RegisterRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
                callback(false)
            }

        })
    }

    fun getVulSituations() {
        val call = apiCall.getVulSituations()
        call.enqueue(object: Callback<vulCondRes> {
            override fun onResponse(call: Call<vulCondRes>, response: Response<vulCondRes>) {
                if(response.isSuccessful) {
                    val vulConRes = response.body()
                    vulConRes?.let {
                        vulCondList.value = it.table
                    }
                    println(vulCondList)
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<vulCondRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
            }
        })
    }
}