package mx.mr.platopatodos.ui.reg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import mx.mr.platopatodos.R
import mx.mr.platopatodos.databinding.ActivityMenuBinding
import mx.mr.platopatodos.databinding.ActivityRegBinding
import mx.mr.platopatodos.ui.menu.MenuVM

/**
 * Registro View
 * @author Héctor González Sánchez
 */

class RegActivity : AppCompatActivity() {

    // Binding & ViewModel
    private val viewModel: RegistroVM by viewModels()
    private lateinit var binding: ActivityRegBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uploadCustomer()
    }

    private fun uploadCustomer() {
        binding.btnUploadCostumer.setOnClickListener {

            val name = binding.etName.text.toString()
            val p_lastName = binding.etPLastName.text.toString()
            val m_lastName = binding.etMLastName.text.toString()
            val curp = binding.etCurp.text.toString()
            val bDate = binding.etBDate.text.toString().toInt()
            val gender = binding.spGender.selectedItem.toString()
            val vulSituation:Array<String> = arrayOf("No Aplica", "Ciego")

            viewModel.uploadCostumer(name, p_lastName, m_lastName, curp, bDate, gender, vulSituation)
        }
    }
}