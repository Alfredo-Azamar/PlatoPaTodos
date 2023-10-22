package mx.mr.platopatodos.ui.home

import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.model.requests.ChgStatusDining
import mx.mr.platopatodos.model.responses.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Home Frag ViewModel
 *
 * This ViewModel class is responsible for managing the logic related to the HomeFragment.
 * It primarily handles the update of the dining status to "Cerrado" (closed) and communicates with
 * the server to reflect this change.
 *
 * @property apiCall The Retrofit service object for making API calls.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class HomeVM : ViewModel() {

    // Retrofit object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    /**
     * Updates the dining status to "Cerrado" (closed) on the server.
     *
     * This function sends a request to the server to change the dining status to "Cerrado"
     * (closed). It handles the success and failure cases, printing messages accordingly.
     *
     * @param diningName The name of the dining location to be updated.
     */
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