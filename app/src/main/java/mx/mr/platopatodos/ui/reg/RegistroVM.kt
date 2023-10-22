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
 * ViewModel for registration functionality.
 * This ViewModel manages customer registration and vulnerability condition data.
 *
 * @constructor Creates a new instance of [RegistroVM].
 * @property apiCall Retrofit service for API calls.
 * @property vulCondList MutableLiveData for storing the list of vulnerability conditions.
 * @property customerToken MutableLiveData for storing the customer token after registration.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */


class RegistroVM : ViewModel() {
    // Retrofit object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService
    val vulCondList = MutableLiveData<List<vulCondItem>>()
    var customerToken = MutableLiveData<String>()

    /**
     * Uploads customer information to the main database.
     *
     * @param name The customer's name.
     * @param p_lastName The customer's primary last name.
     * @param m_lastName The customer's secondary last name.
     * @param curp The CURP (Unique Population Registry Code) of the customer.
     * @param bDate The customer's birth year.
     * @param gender The customer's gender.
     * @param vulSituation An array of selected vulnerability conditions.
     * @param callback A callback function that is called after the upload operation is completed.
     */
    fun uploadCostumer(
        name: String, p_lastName: String, m_lastName: String,
        curp: String, bDate: Int, gender: String,
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

    /**
     * Retrieves vulnerability conditions from the API and updates [vulCondList].
     */
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