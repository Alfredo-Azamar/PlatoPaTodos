package mx.mr.platopatodos.ui.assist

import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.MyDate
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.model.requests.AssistReq
import mx.mr.platopatodos.model.responses.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Attendance ViewModel
 *
 * This ViewModel is responsible for handling attendance-related data and actions. It provides
 * the functionality to upload attendance records to the server.
 *
 * @property apiCall The Retrofit service for making API calls to interact with attendance data.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class AsistenciaVM : ViewModel() {

    // Retrofit object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    /**
     * Uploads attendance information to the server.
     *
     * @param diningName The name of the dining location.
     * @param type The type of attendance (e.g., breakfast, lunch, dinner).
     * @param servings The number of servings.
     * @param accessType The type of access (e.g., regular, special).
     * @param callback A callback function to handle the result (success or failure).
     */
    fun uploadAttendance(diningName: String, type: String, servings: Int,
                         accessType: String, callback: (Boolean) -> Unit) {

        val date = MyDate()
        val requestBody = AssistReq(diningName, type, servings, date.getCurrentDate(), accessType)

        apiCall.uploadAttendance(requestBody).enqueue(object: Callback<StringResponse> {

            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
                if(response.isSuccessful) {
                    println("Mensaje: ${response.body()}")
                    callback(true)
                } else {
                    println("Falla: ${response.code()}")
                    println("Date: ${date.getCurrentDate()}")
                    println("Error: ${response.errorBody()?.string()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
                callback(false)
            }
        })
    }
}