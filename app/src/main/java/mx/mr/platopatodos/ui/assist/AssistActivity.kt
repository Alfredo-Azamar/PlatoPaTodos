package mx.mr.platopatodos.ui.assist

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
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

            binding.etServings.setText("0")

            val diningName = prefs.getLocation()
            val type = binding.spType.selectedItem.toString()
            val servings = binding.etServings.text.toString().toInt()
            val accessType = binding.etAccessType.text.toString()

            if (accessType != ""){
                viewModel.uploadAttendance(diningName, type, servings, accessType)
                Toast.makeText(this, "Se registró la asistencia", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
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