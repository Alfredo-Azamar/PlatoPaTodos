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

class AsistenciaVM : ViewModel() {

    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    fun uploadAttendance(diningName: String, type: String, servings: Int,
                         accessType: String) {

        val date = MyDate()
        val requestBody = AssistReq(diningName, type, servings, date.getCurrentDate(), accessType)

        apiCall.uploadAttendance(requestBody).enqueue(object: Callback<StringResponse> {

            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
                if(response.isSuccessful) {
                    println("Mensaje: ${response.body()}")
                } else {
                    println("Falla: ${response.code()}")
                    println("Date: ${date.getCurrentDate()}")
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