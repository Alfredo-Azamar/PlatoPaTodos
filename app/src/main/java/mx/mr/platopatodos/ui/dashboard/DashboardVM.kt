package mx.mr.platopatodos.ui.dashboard

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.databinding.FragmentDashboardBinding
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.MyDate
import mx.mr.platopatodos.model.Prefs
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.model.responses.DashboardCompRes
import mx.mr.platopatodos.model.responses.DashboardRes
import mx.mr.platopatodos.model.responses.LoginRes
import mx.mr.platopatodos.ui.incident.IncidentVM
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Dashboard Frag ViewModel
 * @author Héctor González Sánchez
 */

class DashboardVM() : ViewModel() {

    val dashBDPt1 = MutableLiveData<String?>()
    val dashBDPt2 = MutableLiveData<String?>()
    val dashBDPt3 = MutableLiveData<String?>()
    val dashBDPt4 = MutableLiveData<String?>()

    // Retrofit object
    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService


    fun getDashboardInfo(diningName: String) {
        val date = MyDate().getCurrentDate()
        //val date = "2023-09-17" // Change

        apiCall.getDashboardInfo(diningName, date).enqueue(object: Callback<DashboardRes> {

            override fun onResponse(call: Call<DashboardRes>, response: Response<DashboardRes>) {
                if (response.isSuccessful) {
                    println(response.body())
                    response.body()?.table?.let { table ->
                        table[0].Valor.let { data1 ->
                            dashBDPt1.value = data1
                        }
                        table[1].Valor.let {data2 ->
                            dashBDPt2.value = data2
                        }
                        table[2].Valor.let {data3 ->
                            dashBDPt3.value = data3
                        }
                        table[3].Valor.let {data4 ->
                            dashBDPt4.value = "$$data4"
                        }
                    }
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<DashboardRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }
        })
    }

    fun getDashboardCompInfo(diningName: String) {

        //val date = MyDate().getCurrentDate()
        val date = "2023-09-17" // Change

        apiCall.getDashboardCompInfo(diningName, date).enqueue(object: Callback<DashboardCompRes> {
            override fun onResponse(call: Call<DashboardCompRes>, response: Response<DashboardCompRes>) {
                if (response.isSuccessful){
                    for (i in 0..4) {
                        //println(i)

                        val nameDining = response.body()?.table?.get(i)?.Nombre
                        val serPaid = response.body()?.table?.get(i)?.R_Pagadas
                        val serDonated = response.body()?.table?.get(i)?.R_Donadas
                        val totalVisits = response.body()?.table?.get(i)?.TotalVisitas
                        val money = response.body()?.table?.get(i)?.MontoRecaudado
                        println(nameDining)
                        println(serPaid)
                        println(serDonated)
                        println(totalVisits)
                        println(money)

                    }
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<DashboardCompRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }

        })
    }
}