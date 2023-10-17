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
 * Main Activity con un fragmentContainerView para desplegar los fragmentos
 * correspondientes
 *
 * @author Héctor González Sánchez
 */

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationController: NavController
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        prefs = Prefs(applicationContext)
        setContentView(binding.root)
        initUI()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        prefs.wipe()
//    }

    private fun initUI() {
        val navigationHost = supportFragmentManager.findFragmentById(R.id.fc_main) as NavHostFragment
        // En esta madre estoy seteando el click en falso (hay que ver si se setea en otro lado)
        //prefs.saveStautsCV(false)
        navigationController = navigationHost.navController
        binding.bottomNavView.setupWithNavController(navigationController)
    }
}