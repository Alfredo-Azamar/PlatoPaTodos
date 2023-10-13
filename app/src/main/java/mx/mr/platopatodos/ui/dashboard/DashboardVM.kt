package mx.mr.platopatodos.ui.dashboard

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import mx.mr.platopatodos.databinding.FragmentDashboardBinding
import mx.mr.platopatodos.model.ListaServiciosAPI
import mx.mr.platopatodos.model.MyDate
import mx.mr.platopatodos.model.Prefs
import mx.mr.platopatodos.model.RetrofitManager
import mx.mr.platopatodos.ui.incident.IncidentVM


/**
 * Dashboard Frag ViewModel
 * @author Héctor González Sánchez
 */

class DashboardVM : ViewModel() {

    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService
    private lateinit var prefs: Prefs

    val diningName = prefs.getLocation()
    val date = MyDate().getCurrentDate()
    //fun getDashboardInfo(diningName: String, date: String) {}
}