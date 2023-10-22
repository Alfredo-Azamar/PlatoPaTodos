package mx.mr.platopatodos.ui.incident

import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.MyDate
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.model.requests.ChgStatusDining
import mx.mr.platopatodos.model.requests.IncidentReq
import mx.mr.platopatodos.model.responses.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel for managing incidents and dining status.
 *
 * This ViewModel is responsible for managing incidents and the status of the dining location.
 *
 * @constructor Creates a new instance of [IncidentVM].
 * @property apiCall The Retrofit API service for making network requests.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class IncidentVM : ViewModel() {

    // Retrofit object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    /**
     * Inserts a new incident report.
     *
     * @param diningName The name of the dining location.
     * @param issue The type of issue or incident.
     * @param description The description of the incident.
     */
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

    /**
     * Updates the dining status to the specified status.
     *
     * @param diningName The name of the dining location.
     * @param diningStatus The new status of the dining location.
     */
    fun updateDinStatus(diningName: String, diningStatus: String) {

        val requestBody = ChgStatusDining(diningName, diningStatus)

        apiCall.updateDinStatus(requestBody).enqueue(object: Callback<StringResponse> {

            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
                if(response.isSuccessful) {
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