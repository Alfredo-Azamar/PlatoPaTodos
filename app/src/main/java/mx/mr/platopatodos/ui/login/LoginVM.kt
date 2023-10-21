package mx.mr.platopatodos.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.requests.LoginReq
import mx.mr.platopatodos.model.responses.LoginRes
import mx.mr.platopatodos.model.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Login ViewModel
 * @author Héctor González Sánchez
 */

class LoginVM : ViewModel() {

    private val _navigateToNewAct = MutableLiveData<Boolean>()
    val navigateToNewAct: LiveData<Boolean>
        get() = _navigateToNewAct

    private val _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String>
        get() = _currentLocation

    private val _responseAPI = MutableLiveData<Boolean>()

    val responseAPI: LiveData<Boolean>
        get() = _responseAPI

    // Retrofit Object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    fun userLogin(username: String, password: String, callback: (Boolean) -> Unit) {
        val requestBody = LoginReq(username, password)

        apiCall.userLogin(requestBody).enqueue(object: Callback<LoginRes> {
            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                if (response.isSuccessful) {
                    val location = response.body()?.table?.get(0)?.Nombre
                    _currentLocation.postValue(location.toString())
                    _navigateToNewAct.postValue(true)
                    callback(true)
//                    _responseAPI.postValue(true)
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()}")
                    callback(false)
//                    _responseAPI.postValue(false)
                }

            }

            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                callback(false)
            }

        })
    }

    fun onNavigationHandled() {
        _navigateToNewAct.postValue(false)
    }

    //TODO: Possible logout delete prefs
}