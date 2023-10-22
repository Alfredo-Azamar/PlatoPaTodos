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
 *
 * This activity allows users to record attendance for dining events. Users can scan a QR code, enter
 * details, and upload attendance information to the server. It also provides a help option with a
 * link to the official website for CURP (Clave Única de Registro de Población) information.
 *
 * @property viewModel The ViewModel for handling attendance-related data and actions.
 * @property binding The data binding object for the activity's layout.
 * @property prefs A utility class for handling shared preferences.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class AssistActivity : AppCompatActivity() {

    // ViewModel, binding & shared preferences
    private val viewModel: AsistenciaVM by viewModels()
    private lateinit var binding: ActivityAssistBinding
    private lateinit var prefs: Prefs

    /**
     * Called when the activity is created. Initializes the activity's layout, preferences, and sets
     * up click listeners for attendance upload, QR code scanning, and accessing help information.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssistBinding.inflate(layoutInflater)
        prefs = Prefs(applicationContext)
        setContentView(binding.root)

        // Initialize the QR code scanner, attendance upload, and help functions
        scanQR()
        uploadAttendance()
        getHelp()
    }

    /**
     * Uploads attendance information to the main database.
     *
     * This function sends attendance data to the databae, including the dining location name, type
     * of attendance, number of servings, and the access type.
     *
     * @param diningName The name of the dining location.
     * @param type The type of attendance.
     * @param servings The number of servings.
     * @param accessType The type of access.
     * @param callback A callback function to handle the result (success or failure).
     */
    private fun uploadAttendance() {
        binding.btnUploadAtten.setOnClickListener {

            val diningName = prefs.getLocation()
            val type = binding.spType.selectedItem.toString()
            val servingsTxt = binding.etServings.text.toString()
            val accessType = binding.etAccessType.text.toString().trim()
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

    /**
     * Initiates QR code scanning for the access type field.
     */
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

    /**
     * Provides a link to help information about CURP (Clave Única de Registro de Población).
     */
    private fun getHelp() {
        binding.tvAssistHelp.setOnClickListener {
            val url = "https://www.gob.mx/curp/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}