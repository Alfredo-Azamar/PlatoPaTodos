package mx.mr.platopatodos.ui.reg

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.mr.platopatodos.databinding.ActivityRegBinding
import mx.mr.platopatodos.model.QrManager
import mx.mr.platopatodos.model.responses.vulCondItem
import mx.mr.platopatodos.model.vulCondAdapter

/**
 * Registro View
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
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

        // Adaptador vacío
        val arrVulCond = listOf<vulCondItem>()
        adapter = vulCondAdapter(this, arrVulCond.toTypedArray())
        binding.rvVulSituations.adapter = adapter

        scanQR()
        getVulSituations()
        uploadCustomer()
        getHelp()
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

        val minDate = 1920
        val maxDate = 2023

        binding.btnUploadCostumer.setOnClickListener {

            val name = binding.etName.text.toString()
            val pLastName = binding.etPLastName.text.toString()
            val mLastName = binding.etMLastName.text.toString()
            val curp = binding.etCurp.text.toString().trim()
            val bDateTxt = binding.etBDate.text.toString()
            val gender = binding.spGender.selectedItem.toString()
            val vulSituation: Array<String> = getCond()

            if (bDateTxt.isNotEmpty()) {
                val bDate = bDateTxt.toInt()
                if (curp.length == 18) {

                    if (bDate in minDate..maxDate) {
                            //Poner el campo de vulSituation en "No Aplica"
                            viewModel.uploadCostumer(
                                name, pLastName, mLastName, curp,
                                bDate, gender, vulSituation
                            ) { success ->
                                if (success) {
                                    val token = viewModel.customerToken.value
                                    if (!token.isNullOrBlank()) {
                                        val alert = AlertDialog.Builder(this)
                                            .setTitle("T O K E N")
                                            .setMessage("Se le proporciona el siguiente token: ${token}")
                                            .setCancelable(false)
                                            .setPositiveButton("Aceptar") { _, _ ->
                                                finish()
                                            }
                                        alert.show()
                                    }
                                }
                            }
                    } else {
                        Toast.makeText(this, "Año de Nacimiento inválido", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this, "CURP incorrecta", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Llena los campos", Toast.LENGTH_SHORT).show()
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

                val regex = """\|+""".toRegex()
                val regexRes = regex.split(result)
                println(result)
                println(regexRes.size)
                // No repetitions
                if(regexRes.size == 9) {
                    binding.etCurp.setText(regexRes[0])
                    binding.etPLastName.setText(regexRes[1])
                    binding.etMLastName.setText(regexRes[2])
                    binding.etName.setText(regexRes[3])
                    if(regexRes[4] == "HOMBRE") {
                        binding.spGender.setSelection(0)
                    } else {
                        binding.spGender.setSelection(1)
                    }
                    val dateParts = regexRes[5].split("/")
                    val year = dateParts.last()
                    binding.etBDate.setText(year)

                } else { // Repetitions
                    binding.etCurp.setText(regexRes[0])
                    binding.etPLastName.setText(regexRes[2])
                    binding.etMLastName.setText(regexRes[3])
                    binding.etName.setText(regexRes[4])
                    if(regexRes[5] == "HOMBRE") {
                        binding.spGender.setSelection(0)
                    } else {
                        binding.spGender.setSelection(1)
                    }
                    val dateParts = regexRes[6].split("/")
                    val year = dateParts.last()
                    binding.etBDate.setText(year)
                } },

                { error ->
                    println(error.message) }
            )
        }
    }

    private fun getHelp() {
        binding.tvHelpReg.setOnClickListener {
            val url = "https://www.gob.mx/curp/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}