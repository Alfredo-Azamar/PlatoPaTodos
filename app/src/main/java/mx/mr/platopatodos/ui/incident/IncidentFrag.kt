package mx.mr.platopatodos.ui.incident

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import mx.mr.platopatodos.R
import mx.mr.platopatodos.databinding.FragmentIncidentBinding
import mx.mr.platopatodos.model.MyDate
import mx.mr.platopatodos.model.Prefs
import mx.mr.platopatodos.ui.incident.IncidentVM

/**
 * Incident Frag View
 * @author Héctor González Sánchez
 */

class IncidentFrag : Fragment() {

    // Binding & ViewModel
    private lateinit var binding: FragmentIncidentBinding
    private val viewModel: IncidentVM by viewModels()
    private lateinit var prefs: Prefs
    private lateinit var date: MyDate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncidentBinding.inflate(layoutInflater)
        prefs = Prefs(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insertIncident()
    }

    private fun insertIncident() {
        binding.btnUploadReport.setOnClickListener {

            val diningName = prefs.getLocation()
            val issue = binding.etIssue.text.toString()
            val description = binding.etDescription.text.toString()

            viewModel.insertIncident(diningName, issue, description)

        }
    }

}