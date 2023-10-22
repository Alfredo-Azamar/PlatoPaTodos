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
 *
 * This fragment displays the dashboard for a dining location, showing relevant information
 * such as the number of diners, completed menus, pending menus, and more.
 *
 * @property binding The binding object associated with the fragment's layout.
 * @property viewModel The ViewModel responsible for managing the dashboard data.
 * @property prefs An instance of shared preferences for storing and retrieving user data.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class DashboardFrag : Fragment() {

    // Binding & ViewModel
    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardVM by viewModels()
    private lateinit var prefs: Prefs

    /**
     * Called to create and return the view hierarchy associated with the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *     saved state as given here.
     * @return The root view for this fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        prefs = Prefs(requireActivity())
        return binding.root
    }

    /**
     * Called immediately after `onCreateView`. This is where UI components are set up and
     * initialized. LiveData is observed from the ViewModel.
     *
     * @param view The root view returned by `onCreateView`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *     saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    /**
     * Sets up listeners for LiveData updates from the ViewModel to update the UI.
     */
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

    /**
     * Called when the fragment is visible to the user. Fetches important info related
     * to dashboard view.
     */
    override fun onStart() {
        super.onStart()
        val diningName = prefs.getLocation()
        viewModel.getDashboardInfo(diningName)
    }
}