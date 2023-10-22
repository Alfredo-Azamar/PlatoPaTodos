package mx.mr.platopatodos.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import mx.mr.platopatodos.View.MainActivity
import mx.mr.platopatodos.databinding.ActivityLoginBinding
import mx.mr.platopatodos.model.Prefs

/**
 * Login Activity for user authentication.
 *
 * This activity allows users to log in by entering their username and password.
 *
 * @constructor Creates a new instance of [LoginActivity].
 * @property viewModel The ViewModel responsible for user authentication and UI interactions.
 * @property binding The ViewBinding instance for this activity.
 * @property prefs The preferences manager for storing user settings.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class LoginActivity : AppCompatActivity() {

    // Bindind & ViewModel
    private val viewModel: LoginVM by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefs: Prefs

    /**
     * Called when the activity is created. Initializes the UI, checks for saved login, and sets up click listeners.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        prefs = Prefs(applicationContext)
        setContentView(binding.root)

       getLogkey()
       login()
       setupListeners()
    }

    /**
     * Set up listeners for LiveData updates and navigation.
     */
    private fun setupListeners() {
        viewModel.navigateToNewAct.observe(this, Observer { shouldNavigate ->
            if(shouldNavigate) {
                prefs.saveLogin(true)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                viewModel.onNavigationHandled()
            }
        })

        viewModel.currentLocation.observe(this) { location ->
            prefs.saveLocation(location)
        }

//        viewModel.responseAPI.observe(this) { itslogged ->
//            if (!itslogged) {
//                binding.tipUser.error = "Usuario incorrecto"
//                binding.tipPassword.error = "Contraseña incorrecta"
//            }
//        }
    }

    /**
     * Perform user login when the login button is clicked.
     */
    private fun login() {
        binding.btnLogin.setOnClickListener {

            val user = binding.etUser.text.toString()
            val password = binding.etPassworrd.text.toString()

            if (user != "" && password != "" ){
                viewModel.userLogin(user, password) {success ->
                    if (!success){
                        binding.tipUser.error = "Usuario incorrecto"
                        binding.tipPassword.error = "Contraseña incorrecta"
                    }
                }
            } else {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.etUser.doOnTextChanged { text, start, before, count ->
            if(text!!.length == 0) {
                binding.tipUser.error = null
            }
        }

        binding.etPassworrd.doOnTextChanged { text, start, before, count ->
            if(text!!.length == 0) {
                binding.tipPassword.error = null
            }
        }
    }

    /**
     * Check for saved login and navigate to the main activity if logged in.
     */
    private fun getLogkey() {
        if(prefs.getLogin()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            viewModel.onNavigationHandled()
        }
    }
}