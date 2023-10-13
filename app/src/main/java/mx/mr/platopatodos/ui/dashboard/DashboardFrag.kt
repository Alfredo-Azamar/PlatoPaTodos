package mx.mr.platopatodos.ui.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import mx.mr.platopatodos.R
import mx.mr.platopatodos.databinding.FragmentDashboardBinding


/**
 * Dashboard Frag View
 * @author Héctor González Sánchez
 */


class DashboardFrag : Fragment() {

    // Binding & ViewModel
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}