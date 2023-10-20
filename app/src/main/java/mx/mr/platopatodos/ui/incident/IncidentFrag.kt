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
 * Incident Frag View
 * @author Héctor González Sánchez
 */

class IncidentFrag : Fragment() {

    // Binding & ViewModel
    private lateinit var binding: FragmentIncidentBinding
    private val viewModel: IncidentVM by viewModels()
    private lateinit var prefs: Prefs

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
        changeStatus()
    }

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

    private fun insertIncident() {
        binding.btnUploadReport.setOnClickListener {

            val diningName = prefs.getLocation()
            val issue = binding.etIssue.text.toString()
            val description = binding.etDescription.text.toString()
            if (issue != ""){
                viewModel.insertIncident(diningName, issue, description)
                Toast.makeText(requireActivity(), "Incidencia reportada", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            } else{
                Toast.makeText(requireActivity(), "Llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changeStatus() {
        binding.swClosure.setOnClickListener {
            val diningName = prefs.getLocation()
            if (prefs.getStatus()) {
//                binding.swClosure.isChecked = prefs.getStatus()
                val alert = AlertDialog.Builder(requireActivity())
                    .setTitle("A V I S O")
                    .setMessage("¿Desea cambiar el estado del comedor?")
                    .setCancelable(false)
                    .setPositiveButton("Sí"){ dialogInterface: DialogInterface, i: Int ->
                        viewModel.updateDinStatus(diningName)
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
//                    binding.swClosure.isChecked = prefs.getStatus()
                    binding.swClosure.text = "El comedor está: Abierto"
                    viewModel.updateDinStatus(diningName)
                    prefs.saveStautsCV(true)
                }
//                binding.swClosure.isChecked = prefs.getStatus()
            }
        }
    }

}