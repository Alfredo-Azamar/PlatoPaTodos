package mx.mr.platopatodos.ui.reg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.mr.platopatodos.databinding.ActivityRegBinding
import mx.mr.platopatodos.model.QrManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scanQR()
        getVulSituations()
        uploadCustomer()
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

            viewModel.uploadCostumer(name, p_lastName, m_lastName, curp, bDate, gender, vulSituation) {success ->
                if (success) {
                    val token = viewModel.customerToken.value
                    if (!token.isNullOrBlank()){
                        val alert = AlertDialog.Builder(this)
                            .setTitle("T O K E N")
                            .setMessage("Se le proporciona el siguiente token: ${token}")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar"){_, _ ->
                                finish()
                            }
                        alert.show()
                    } else {
                        println("ERROR")
                    }
                }
            }
        }
    }

    private fun getCond(): Array<String> {
        val selectedCondition = adapter?.selectedConditions
        return selectedCondition!!.toTypedArray()
    }

    private fun scanQR() {
        binding.btnRegQR.setOnClickListener {
            QrManager.startQrCodeScanner(this, { result ->

                val regex = Regex("([A-Z0-9]+)\\|\\|([A-Z]+)\\|([A-Z]+)\\|([A-Z]+)\\|([A-Z]+)\\|(\\d{2}/\\d{2}/\\d{4})\\|([A-Z]+)\\|\\d+\\|")
                val matchResult = regex.find(result)

                if(matchResult != null) {
                    val (curp, lastP, lastM, name, gender,bDate) = matchResult.destructured
                    val dateParts = bDate.split("/")
                    val year = dateParts.last()
                    binding.etCurp.setText(curp)
                    binding.etPLastName.setText(lastP)
                    binding.etMLastName.setText(lastM)
                    binding.etName.setText(name)
                    if(gender == "HOMBRE") {
                        binding.spGender.setSelection(0)
                    } else {
                        binding.spGender.setSelection(1)
                    }
                    binding.etBDate.setText(year)
                } },

                { error ->
                    println(error.message) }
            )
        }
    }
}