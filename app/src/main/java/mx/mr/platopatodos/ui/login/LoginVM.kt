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

class LoginVM : ViewModel() {

    private val _navigateToNewAct = MutableLiveData<Boolean>()
    val navigateToNewAct: LiveData<Boolean>
        get() = _navigateToNewAct

    private val _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String>
        get() = _currentLocation

    // Retrofit Object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    fun userLogin(username: String, password: String) {
        val requestBody = LoginReq(username, password)

        apiCall.userLogin(requestBody).enqueue(object : Callback<LoginRes> {

            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                if (response.isSuccessful) {
                    val location = response.body()?.table?.get(0)?.Nombre
                    _currentLocation.postValue(location.toString())
                    _navigateToNewAct.postValue(true)
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()}")
                }

            }

            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }

        })
    }

    fun onNavigationHandled() {
        _navigateToNewAct.postValue(false)
    }

    //TODO: Possible logout delete prefs
}