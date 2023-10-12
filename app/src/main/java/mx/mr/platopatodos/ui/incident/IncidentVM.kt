package mx.mr.platopatodos.ui.incident

import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.MyDate
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.model.requests.IncidentReq
import mx.mr.platopatodos.model.responses.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Incident Frag ViewModel
 * @author Héctor González Sánchez
 */

class IncidentVM : ViewModel() {

    // Retrofit object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    fun insertIncident(diningName: String, issue: String, description: String) {

        val date = MyDate()
        val requestBody = IncidentReq(diningName, issue, description, date.getCurrentDate())

        apiCall.uploadIncidence(requestBody).enqueue(object: Callback<StringResponse>{

            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
                if(response.isSuccessful) {
                    println("Mensaje: ${response.body()}")
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