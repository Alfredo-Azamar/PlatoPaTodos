package mx.mr.platopatodos.ui.incident

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.mr.platopatodos.databinding.FragmentIncidentBinding
import mx.mr.platopatodos.model.Prefs

/**
 * Fragment for reporting incidents and changing the dining status.
 *
 * This fragment allows users to report incidents and change the dining status.
 *
 * @constructor Creates a new instance of [IncidentFrag].
 * @property binding The data binding object for the fragment layout.
 * @property viewModel The ViewModel responsible for managing incidents and dining status.
 * @property prefs The shared preferences object for storing user preferences.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class IncidentFrag : Fragment() {

    // Binding & ViewModel
    private lateinit var binding: FragmentIncidentBinding
    private val viewModel: IncidentVM by viewModels()
    private lateinit var prefs: Prefs

    /**
     * Creates and inflates the fragment's view.
     *
     * This function is called when the fragment is created and is responsible for inflating the
     * layout defined in the `FragmentIncidentBinding`, initializing preferences, and returning the
     * root view.
     *
     * @param inflater The LayoutInflater used to inflate the layout.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState The saved instance state, if any.
     * @return The root view of the fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncidentBinding.inflate(layoutInflater)
        prefs = Prefs(requireActivity())
        return binding.root
    }

    /**
     * Called when the fragment's view has been created and can be accessed.
     *
     * In this function, we set up the behavior of the "Subir reporte" button and the "El comedor está"
     * switch, as well as handle the initial display of the dining status.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved instance state, if any.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializes incidents report and change status functions
        insertIncident()
        changeStatus()
    }

    /**
     * Handles the onResume lifecycle event.
     *
     * This method sets the text and state of the "Status" switch based on the dining status.
     */
    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        binding.swClosure.isChecked = prefs.getStatus()
        if (binding.swClosure.isChecked){
            binding.swClosure.text = "El comedor está: Abierto"
        } else {
            binding.swClosure.text = "El comedor está: Cerrado"
        }
    }

    /**
     * Inserts a new incident report.
     *
     * This function is called when the "Subir reporte" button is clicked. It collects user input
     * for the incident report and sends it to the ViewModel for processing.
     */
    private fun insertIncident() {
        binding.btnUploadReport.setOnClickListener {

            val diningName = prefs.getLocation()
            val issue = binding.etIssue.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (issue.isNotEmpty()){
                viewModel.insertIncident(diningName, issue, description)
                Toast.makeText(requireActivity(), "Incidencia reportada", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else{
                Toast.makeText(requireActivity(), "Llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Handles the change of dining status.
     *
     * This function is triggered when the "El comedor está" switch is toggled. It allows users
     * to change the dining status between "Abierto" and "Cerrado" with a confirmation dialog.
     */
    @SuppressLint("SetTextI18n")
    private fun changeStatus() {
        binding.swClosure.setOnClickListener {
            val diningName = prefs.getLocation()
            if (prefs.getStatus()) {
                val alert = AlertDialog.Builder(requireActivity())
                    .setTitle("A V I S O")
                    .setMessage("¿Desea cambiar el estado del comedor?")
                    .setCancelable(false)
                    .setPositiveButton("Sí"){ dialogInterface: DialogInterface, i: Int ->
                        viewModel.updateDinStatus(diningName, "Cerrado")
                        prefs.saveStautsCV(false)
                        binding.swClosure.isChecked = prefs.getStatus()
                        binding.swClosure.text = "El comedor está: Cerrado"
                    }
                    .setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->
                        binding.swClosure.isChecked = prefs.getStatus()
                    }
                alert.show()
            } else {
                if (prefs.getUpMenu()){
                    binding.swClosure.text = "El comedor está: Abierto"
                    viewModel.updateDinStatus(diningName, "Abierto")
                    prefs.saveStautsCV(true)
                }
                binding.swClosure.isChecked = prefs.getStatus()
            }
        }
    }
}