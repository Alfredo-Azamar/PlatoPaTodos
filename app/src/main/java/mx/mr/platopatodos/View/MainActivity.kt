package mx.mr.platopatodos.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import mx.mr.platopatodos.R
import mx.mr.platopatodos.databinding.ActivityMainBinding
import mx.mr.platopatodos.model.Prefs

/**
 * Main Activity with fragmentContainerView to display corresponding fragments.
 * This is the entry point of the application.
 *
 * @property binding View binding for the main activity layout.
 * @property navigationController Navigation controller for managing fragment navigation.
 * @property prefs An instance of the Preferences class for managing application preferences.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationController: NavController
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflating the layout for this Activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        prefs = Prefs(applicationContext)
        setContentView(binding.root)

        // Initialize the User Interface
        initUI()
    }

    /**
     * Initializes the UI components of the activity
     */
    private fun initUI() {
        val navigationHost = supportFragmentManager.findFragmentById(R.id.fc_main) as NavHostFragment
        navigationController = navigationHost.navController
        binding.bottomNavView.setupWithNavController(navigationController)
    }
}