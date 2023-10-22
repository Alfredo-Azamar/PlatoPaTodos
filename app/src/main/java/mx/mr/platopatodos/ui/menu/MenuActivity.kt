package mx.mr.platopatodos.ui.menu

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import mx.mr.platopatodos.R
import mx.mr.platopatodos.databinding.ActivityMenuBinding
import mx.mr.platopatodos.databinding.FragmentHomeBinding
import mx.mr.platopatodos.model.Prefs

/**
 * Menu View for managing and updating the dining menu.
 *
 * This activity allows the user to update the dining menu by entering various meal components,
 * such as soup, main course, carbs, water, and beans sauce.
 *
 * @constructor Creates a new instance of [MenuActivity].
 * @property viewModel The ViewModel responsible for managing the menu and UI interactions.
 * @property binding The ViewBinding instance for this activity.
 * @property prefs The preferences manager for storing user settings.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class MenuActivity : AppCompatActivity() {

    // Binding & ViewModel
    private val viewModel: MenuVM by viewModels()
    private lateinit var binding: ActivityMenuBinding
    private lateinit var prefs: Prefs

    /**
     * Called when the activity is created. Initializes the UI and sets up click listeners.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        prefs = Prefs(applicationContext)
        setContentView(binding.root)

        uploadMenu()
    }

    /**
     * Uploads the dining menu to the main database and updates dining status.
     */
    private fun uploadMenu() {
        binding.btnUploadMenu.setOnClickListener {

            val diningName = prefs.getLocation()
            val soup = binding.etSoup.text.toString()
            val mainCourse = binding.etMainCourse.text.toString()
            val carbs = binding.etCarbs.text.toString()
            val water = binding.etWater.text.toString()
            val beansSauce = binding.etBeansSauce.text.toString()


            if (soup != "" ||
                mainCourse != "" ||
                carbs != "" ||
                water != "" ||
                beansSauce != ""){

                viewModel.uploadMenu(diningName, soup, mainCourse, carbs, water, beansSauce)
                viewModel.updateDinStatus(diningName)
                Toast.makeText(this, "El menú se ha actualizado", Toast.LENGTH_SHORT).show()


                //Clickable Component for CardViews
                prefs.saveStautsCV(true)
                prefs.saveUpMenu(true)
                println("SSS ${prefs.getUpMenu()}")
                finish()
            } else {
                Toast.makeText(this, "Llena los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}