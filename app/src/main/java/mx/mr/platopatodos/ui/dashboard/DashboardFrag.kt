package mx.mr.platopatodos.ui.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import mx.mr.platopatodos.R
import mx.mr.platopatodos.databinding.FragmentDashboardBinding
import mx.mr.platopatodos.model.Prefs


/**
 * Dashboard Frag View
 * @author Héctor González Sánchez
 */


class DashboardFrag : Fragment() {

    // Binding & ViewModel
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardVM by viewModels()
    private lateinit var prefs: Prefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        prefs = Prefs(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        viewModel.dashBDPt1.observe(viewLifecycleOwner) { value ->
            binding.etDashPt1.text = value.toString()
        }

        viewModel.dashBDPt2.observe(viewLifecycleOwner) { value ->
            binding.etDashPt2.text = value.toString()
        }

        viewModel.dashBDPt3.observe(viewLifecycleOwner) { value ->
            binding.etDashPt3.text = value.toString()
        }

        viewModel.dashBDPt4.observe(viewLifecycleOwner) { value ->
            binding.etDashPt4.text = value.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        val diningName = prefs.getLocation()
        viewModel.getDashboardInfo(diningName)
    }
}