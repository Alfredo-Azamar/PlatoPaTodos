package mx.mr.platopatodos.ui.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.model.MyDate
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.Prefs
import mx.mr.platopatodos.model.requests.MenuReq
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.model.requests.ChgStatusDining
import mx.mr.platopatodos.model.responses.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ViewModel for managing dining menu and status.
 *
 * This ViewModel is responsible for managing the dining menu updates and the status of the dining location.
 *
 * @constructor Creates a new instance of [MenuVM].
 * @property apiCall The Retrofit API service for making network requests.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class MenuVM : ViewModel() {

    // Retrofit object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    /**
     * Uploads the dining menu to the main database.
     *
     * @param diningName The name of the dining location.
     * @param soup The description of the soup.
     * @param mainCourse The description of the main course.
     * @param carbs The description of the carbs.
     * @param water The description of the water.
     * @param beansSauce The description of the beans sauce.
     */
    fun uploadMenu(diningName: String, soup: String, mainCourse: String,
                   carbs: String, water: String, beansSauce: String) {

        val date = MyDate()
        val requestBody = MenuReq(diningName, soup, mainCourse, carbs, water, beansSauce, date.getCurrentDate())

        apiCall.uploadMenu(requestBody).enqueue(object : Callback<StringResponse> {

            override fun onResponse(call: Call<StringResponse>, response: Response<StringResponse>) {
                if(response.isSuccessful) {
                    println("Mensaje: ${response.body()}")
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

    /**
     * Updates the dining status to "Abierto" (Open).
     *
     * @param diningName The name of the dining location.
     */
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