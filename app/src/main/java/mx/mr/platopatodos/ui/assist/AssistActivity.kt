package mx.mr.platopatodos.ui.assist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import mx.mr.platopatodos.databinding.ActivityAssistBinding
import mx.mr.platopatodos.model.Prefs


class AssistActivity : AppCompatActivity() {

    private val viewModel: AsistenciaVM by viewModels()
    private lateinit var binding: ActivityAssistBinding
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssistBinding.inflate(layoutInflater)
        prefs = Prefs(applicationContext)
        setContentView(binding.root)

        uploadAttendance()
    }

    private fun uploadAttendance() {
        binding.btnUploadAtten.setOnClickListener {

            val diningName = prefs.getLocation()
            val type = binding.spType.selectedItem.toString()
            val servings = binding.etServings.text.toString().toInt()
            val accessType = binding.etAccessType.text.toString()

            viewModel.uploadAttendance(diningName, type, servings, accessType)
        }
    }
}