package mx.mr.platopatodos.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.model.MyDate
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.requests.MenuReq
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.model.requests.ChgStatusDining
import mx.mr.platopatodos.model.responses.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Menu ViewModel
 * @author Héctor González Sánchez
 */


class MenuVM : ViewModel() {

    // Preferences (Status)
//    private val _currentStatus = MutableLiveData<Boolean>()

//    val currentStatus: LiveData<Boolean>
//        get() = _currentStatus

    // Retrofit object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    fun uploadMenu(diningName: String, soup: String, mainCourse: String,
                   carbs: String, water: String, beansSauce: String) {

        val date = MyDate()
        val requestBody = MenuReq(diningName, soup, mainCourse, carbs, water, beansSauce, date.getCurrentDate())

        apiCall.uploadMenu(requestBody).enqueue(object : Callback<StringResponse> {

            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
                if(response.isSuccessful) {
                    println("Mensaje: ${response.body()}")
//                    _currentStatus.postValue(true)
                } else {
                    println("Mensaje: ${requestBody}")
                }
            }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
            }
        })
    }

    fun updateDinStatus(diningName: String) {

        val diningStatus = "Abierto"
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