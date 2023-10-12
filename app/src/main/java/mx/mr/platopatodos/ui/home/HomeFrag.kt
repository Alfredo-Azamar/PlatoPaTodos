package mx.mr.platopatodos.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import mx.mr.platopatodos.databinding.FragmentHomeBinding
import mx.mr.platopatodos.model.Prefs
import mx.mr.platopatodos.ui.assist.AssistActivity
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
        toRegister()
        toAssist()
        toMenu()
        getLocation()
    }

    private fun toAssist() {
        binding.cvAssist.setOnClickListener {
            println("Hizo click")
            val action = HomeFragDirections.actionHomeFragToAssistActivity()
            findNavController().navigate(action)
        }
    }

    private fun toRegister() {
        binding.cvRegister.setOnClickListener {
            println("Hizo click")
            val action = HomeFragDirections.actionHomeFragToRegActivity()
            findNavController().navigate(action)
        }
    }

    private fun toMenu() {
        binding.btnStatus.setOnClickListener {
            println("Hizo click")
            // TODO: Definir función en el modelo para controlar el estatus del comedor y enviar a menú
            val action = HomeFragDirections.actionHomeFragToMenuActivity()
            findNavController().navigate(action)
        }
    }

    private fun getLocation() {
        val location = prefs.getLocation()
        binding.etCurrentDining.setText("Estas en: ${location}")
    }


}