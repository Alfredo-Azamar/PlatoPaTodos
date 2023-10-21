package mx.mr.platopatodos.ui.assist

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import mx.mr.platopatodos.databinding.ActivityAssistBinding
import mx.mr.platopatodos.model.Prefs
import mx.mr.platopatodos.model.QrManager

/**
 * Attendance View
 * @author Héctor González Sánchez
 */

class AssistActivity : AppCompatActivity() {

    private val viewModel: AsistenciaVM by viewModels()
    private lateinit var binding: ActivityAssistBinding
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssistBinding.inflate(layoutInflater)
        prefs = Prefs(applicationContext)
        setContentView(binding.root)

        scanQR()
        uploadAttendance()
        getHelp()
    }

    private fun uploadAttendance() {
        binding.btnUploadAtten.setOnClickListener {

            val diningName = prefs.getLocation()
            val type = binding.spType.selectedItem.toString()
            val servingsTxt = binding.etServings.text.toString()
            val accessType = binding.etAccessType.text.toString().trim()

//            if (servings == "" || servings == "."){
//                binding.etServings.setText("0")
//                println(servings)
//            }

            val servings = servingsTxt.toDoubleOrNull() ?:0.0

            if (accessType.length == 5 || accessType.length == 18) {
                if (servings.toInt() in 1 .. 5) {
                    viewModel.uploadAttendance(diningName, type, servings.toInt(), accessType) { success ->
                        if (success) {
                            Toast.makeText(this, "Se registró la asistencia", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "El Comensal no está registrado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "El número de Raciones es incorrecto", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "El Token o CURP son incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun scanQR() {
        binding.btnAssistQR.setOnClickListener {
            QrManager.startQrCodeScanner(this, { result ->

                val regex = """\|+""".toRegex()
                val regexRes = regex.split(result)
                binding.etAccessType.setText(regexRes[0]) },

                { error ->
                    println(error.message) }
            )
        }
    }

    private fun getHelp() {
        binding.tvAssistHelp.setOnClickListener {
            val url = "https://www.gob.mx/curp/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}