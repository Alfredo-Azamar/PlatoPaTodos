package mx.mr.platopatodos.ui.home

import android.content.DialogInterface
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

/**
 * Home Frag View
 *
 * This fragment is responsible for the main home screen, which provides options to navigate to
 * registration, assistance, and menu activities, as well as displaying the current dining location
 * and allowing the user to log out.
 *
 * @property binding The view binding for the fragment.
 * @property viewModel The ViewModel associated with this fragment.
 * @property prefs The preferences manager for storing app-related data.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class HomeFrag : Fragment() {

    // Binding & ViewModel
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeVM by viewModels()
    private lateinit var prefs: Prefs

    /**
     * Creates and inflates the fragment's view.
     *
     * This function is called when the fragment is created and is responsible for inflating the
     * layout defined in the `FragmentHomeBinding`, initializing preferences, and returning the
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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        prefs = Prefs(requireActivity())
        return binding.root
    }

    /**
     * Called when the fragment's view has been created and can be accessed.
     *
     * In this function, we set up click listeners for navigating to registration, assistance, and
     * menu activities, retrieve and display the current dining location, and handle the log-out
     * functionality.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState The saved instance state, if any.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toAssit_Register()
        toMenu()
        getLocation()
        logout()
    }

    /**
     * Called when the fragment resumes.
     *
     * In this function, we refresh the ability to navigate to the registration and assistance
     * activities based on the dining status.
     */
    override fun onResume() {
        super.onResume()
        toAssit_Register()
    }

    /**
     * Navigates to registration or assistance activities.
     *
     * This function sets up click listeners for the "Registro" (registration) and "Asistencia"
     * (assistance) CardViews, allowing the user to navigate to the corresponding activities if the
     * dining location is open. If the dining location is closed, it displays an alert message
     * reminding the user to upload the menu.
     */
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
                    .setMessage("Recuerda subir el menú")
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
                    .setMessage("Recuerda subir el menú")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", null)
                alert.show()
            }
        }
    }

    /**
     * Navigates to the menu activity.
     *
     * This function sets up a click listener for the "Estatus" (status) button, allowing the user
     * to navigate to the menu activity.
     */
    private fun toMenu() {
        binding.btnStatus.setOnClickListener {
            println("Hizo click")
            val action = HomeFragDirections.actionHomeFragToMenuActivity()
            findNavController().navigate(action)
        }
    }

    /**
     * Displays the current dining location.
     *
     * This function retrieves and displays the current dining location stored in preferences, showing
     * it in the text field on the home screen.
     */
    private fun getLocation() {
        val location = prefs.getLocation()
        binding.etCurrentDining.setText("Estas en: ${location}")
    }

    /**
     * Logs out of the app and returns to the login screen.
     *
     * This function displays a confirmation dialog to the user, asking if they want to log out of the app.
     * If confirmed, it updates the dining status, clears preferences, and navigates back to the login screen.
     */
    private fun logout() {
        binding.etLogout.setOnClickListener{
            val diningName = prefs.getLocation()

            val alert = AlertDialog.Builder(requireActivity())
                .setTitle("A V I S O")
                .setMessage("¿Desea salir de la app?")
                .setCancelable(false)
                .setPositiveButton("Sí"){ dialogInterface: DialogInterface, i: Int ->
                    viewModel.updateDinStatus(diningName)
                    prefs.saveStautsCV(false)
                    prefs.wipe()

                    //Log-out
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("No", null)
            alert.show()
        }
    }
}