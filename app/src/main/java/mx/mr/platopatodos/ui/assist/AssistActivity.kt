package mx.mr.platopatodos.ui.assist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
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
    }

    private fun uploadAttendance() {
        binding.btnUploadAtten.setOnClickListener {

            val diningName = prefs.getLocation()
            val type = binding.spType.selectedItem.toString()
            val servings = binding.etServings.text.toString().toInt()
            val accessType = binding.etAccessType.text.toString()

            viewModel.uploadAttendance(diningName, type, servings, accessType)
            finish()
        }
    }

    private fun scanQR() {
        binding.btnAssistQR.setOnClickListener {
            QrManager.startQrCodeScanner(this, { result ->

                val regex = Regex("([A-Z0-9]+)\\|\\|([A-Z]+)\\|([A-Z]+)\\|([A-Z]+)\\|([A-Z]+)\\|(\\d{2}/\\d{2}/\\d{4})\\|([A-Z]+)\\|\\d+\\|")
                val matchResult = regex.find(result)

                if(matchResult != null) {
                    val (curp) = matchResult.destructured
                    binding.etAccessType.setText(curp)
                } },

                { error ->
                    println(error.message) }
            )
        }
    }
}