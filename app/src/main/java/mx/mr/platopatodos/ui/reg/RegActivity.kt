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
 * Registration view for collecting customer information.
 *
 * This Activity allow users to enter customer details, scan CURP QR codes, select
 * vulnerability condition and upload those data to the server.
 *
 * @constructor Creates a new instance of [RegActivity].
 * @property viewModel The ViewModel responsible for managing registration data.
 * @property binding The ViewBinding instance for this activity.
 * @property adapter The adapter for vulnerability conditions.
 * @property minDate The minimum birth date allowed for customers.
 * @property maxDate The maximum birth date allowed for customers.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

class RegActivity : AppCompatActivity() {

    // Binding, ViewModel & Adapter
    private val viewModel: RegistroVM by viewModels()
    private lateinit var binding: ActivityRegBinding
    private var adapter: vulCondAdapter? = null
    private val minDate = 1920
    private val maxDate = 2023

    /**
     * Called when the activity is created. Initializes the UI and sets up click listeners.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize an empty adapter
        val arrVulCond = listOf<vulCondItem>()
        adapter = vulCondAdapter(this, arrVulCond.toTypedArray())
        binding.rvVulSituations.adapter = adapter

        scanQR()
        getVulSituations()
        uploadCustomer()
        getHelp()
    }

    /**
     * Called when the activity is started. Fetches vulnerability conditions.
     */
    override fun onStart() {
        super.onStart()
        viewModel.getVulSituations()
    }

    /**
     * Fetches and displays vulnerability conditions in a grid layout.
     */
    private fun getVulSituations() {
        val layout = GridLayoutManager(this, 2)
        binding.rvVulSituations.layoutManager = layout

        viewModel.vulCondList.observe(this) {vulCondList ->
            val arrVulCond = vulCondList.toTypedArray()
            adapter = vulCondAdapter(this, arrVulCond)
            binding.rvVulSituations.adapter = adapter

            adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    val selectedConditions = adapter?.selectedConditions
                    println("Condiciones seleccionads: $selectedConditions")
                }
            })
        }
    }

    /**
     * Uploads customer information to the main database after validating user input.
     */
    private fun uploadCustomer() {

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
                        if(adapter?.selectedConditions?.isEmpty() == true) {
                            adapter?.selectedConditions?.add("No Aplica")
                        }
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

    /**
     * Gets the selected vulnerability conditions from the adapter.
     *
     * @return An array of selected vulnerability conditions.
     */
    private fun getCond(): Array<String> {
        val selectedCondition = adapter?.selectedConditions
        return selectedCondition!!.toTypedArray()
    }

    /**
     * Initiates QR code scanning and populates customer information fields from the scanned data.
     */
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

    /**
     * Opens a web page for help and information about CURP.
     */
    private fun getHelp() {
        binding.tvHelpReg.setOnClickListener {
            val url = "https://www.gob.mx/curp/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}