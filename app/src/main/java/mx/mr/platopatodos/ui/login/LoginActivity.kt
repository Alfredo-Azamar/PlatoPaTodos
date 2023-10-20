package mx.mr.platopatodos.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import mx.mr.platopatodos.R
import mx.mr.platopatodos.View.MainActivity
import mx.mr.platopatodos.databinding.ActivityLoginBinding
import mx.mr.platopatodos.model.Prefs
import mx.mr.platopatodos.model.QrManager

class LoginActivity : AppCompatActivity() {

    // Bindind & ViewModel
    private val viewModel: LoginVM by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        prefs = Prefs(applicationContext)
        setContentView(binding.root)

       getLogkey()
       login()
       setupListeners()
    }

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

        viewModel.responseAPI.observe(this) { itslogged ->
            if (!itslogged) {
                binding.tipUser.error = "Usuario incorrecto"
                binding.tipPassword.error = "Contraseña incorrecta"
            }
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {

            val user = binding.etUser.text.toString()
            val password = binding.etPassworrd.text.toString()

            if (user != "" && password != "" ){
                viewModel.userLogin(user, password)
            } else {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLogkey() {
        if(prefs.getLogin()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            viewModel.onNavigationHandled()
        }
    }
}