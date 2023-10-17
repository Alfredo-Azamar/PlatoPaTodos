package mx.mr.platopatodos.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import mx.mr.platopatodos.View.MainActivity
import mx.mr.platopatodos.databinding.FragmentHomeBinding
import mx.mr.platopatodos.model.Prefs
import mx.mr.platopatodos.ui.assist.AssistActivity
import mx.mr.platopatodos.ui.login.LoginActivity
import mx.mr.platopatodos.ui.reg.RegActivity

class HomeFrag : Fragment() {

    // Binding & ViewModel
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeVM by viewModels()
    private lateinit var prefs: Prefs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        prefs = Prefs(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        toRegister()
//        toAssist()

        toAssit_Register()

        toMenu()
        getLocation()
        logout()
    }

    override fun onResume() {
        super.onResume()
        toAssit_Register()
    }


    private fun toAssit_Register() {
        val isClickable = prefs.getStatus()

        println("COSO STATUS: ${isClickable}")

        binding.cvRegister.setOnClickListener {
            println("Hizo click")
            if (isClickable){
                val action = HomeFragDirections.actionHomeFragToRegActivity()
                findNavController().navigate(action)
            } else {
                val alert = AlertDialog.Builder(requireActivity())
                    .setTitle("A V I S O")
                    .setMessage("Recuerda subir Menú")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", null)
                alert.show()
            }
        }

        binding.cvAssist.setOnClickListener {
            println("Hizo click")
            if (isClickable){
                val action = HomeFragDirections.actionHomeFragToAssistActivity()
                findNavController().navigate(action)
            } else {
                val alert = AlertDialog.Builder(requireActivity())
                    .setTitle("A V I S O")
                    .setMessage("Recuerda subir Menú")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", null)
                alert.show()
            }
        }
    }

    private fun toMenu() {
        binding.btnStatus.setOnClickListener {
            println("Hizo click")
            val action = HomeFragDirections.actionHomeFragToMenuActivity()
            findNavController().navigate(action)
        }
    }

    private fun getLocation() {
        val location = prefs.getLocation()
        binding.etCurrentDining.setText("Estas en: ${location}")
    }

    private fun logout() {
        binding.etLogout.setOnClickListener{
            println("Hizo click")
            prefs.wipe()

            val diningName = prefs.getLocation()
            viewModel.updateDinStatus(diningName)

            // Log-out
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

            val cardsViewClickable = false
            prefs.saveStautsCV(cardsViewClickable)
            println("COSO STATUS LOGOUT: ${cardsViewClickable}")
        }
    }
}