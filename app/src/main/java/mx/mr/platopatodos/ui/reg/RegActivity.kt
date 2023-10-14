package mx.mr.platopatodos.ui.reg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.mr.platopatodos.databinding.ActivityRegBinding
import mx.mr.platopatodos.model.vulCondAdapter

/**
 * Registro View
 * @author Héctor González Sánchez
 */

class RegActivity : AppCompatActivity() {

    // Binding, ViewModel & Adaptator
    private val viewModel: RegistroVM by viewModels()
    private lateinit var binding: ActivityRegBinding
    private var adapter: vulCondAdapter? = null

    val selectedConditions = listOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getVulSituations()
        uploadCustomer()
        //getSelectedConditions()
    }

    override fun onStart() {
        super.onStart()
        viewModel.getVulSituations()
    }

    private fun getVulSituations() {
        val layout = GridLayoutManager(this, 2)
        binding.rvVulSituations.layoutManager = layout

        viewModel.vulCondList.observe(this) {vulCondList ->
            val arrVulCond = vulCondList.toTypedArray()
            adapter = vulCondAdapter(this, arrVulCond)
            binding.rvVulSituations.adapter = adapter

            // Nuevo a partir de aquí
            adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    val selectedConditions = adapter?.selectedConditions
                    println("Condiciones seleccionads: $selectedConditions")
                }
            })
        }
    }

    private fun uploadCustomer() {
        binding.btnUploadCostumer.setOnClickListener {

            val name = binding.etName.text.toString()
            val p_lastName = binding.etPLastName.text.toString()
            val m_lastName = binding.etMLastName.text.toString()
            val curp = binding.etCurp.text.toString()
            val bDate = binding.etBDate.text.toString()
            val gender = binding.spGender.selectedItem.toString()
            val vulSituation:Array<String> = getCond()

            viewModel.uploadCostumer(name, p_lastName, m_lastName, curp, bDate, gender, vulSituation)
        }
    }

    fun getCond(): Array<String> {
        val selectedCondition = adapter?.selectedConditions
        return selectedCondition!!.toTypedArray()
    }

}