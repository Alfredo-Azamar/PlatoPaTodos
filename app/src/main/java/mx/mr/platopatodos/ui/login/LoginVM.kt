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
 * ViewModel for user login and authentication.
 *
 * This ViewModel is responsible for user login and authentication, handling the API requests and navigation.
 *
 * @constructor Creates a new instance of [LoginVM].
 * @property apiCall The Retrofit API service for making network requests.
 * @property _navigateToNewAct A private MutableLiveData for navigating to a new activity.
 * @property navigateToNewAct A LiveData to observe navigation events.
 * @property _currentLocation A private MutableLiveData for storing the current user location.
 * @property currentLocation A LiveData to observe the current location.
 * @property _responseAPI A private MutableLiveData for observing API response status.
 * @property responseAPI A LiveData to observe the API response status.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
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

    /**
     * Attempt to log in the user with the provided username and password.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @param callback A callback function to handle the result of the login attempt.
     */
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

    /**
     * Handle the completion of navigation to a new activity.
     */
    fun onNavigationHandled() {
        _navigateToNewAct.postValue(false)
    }
}