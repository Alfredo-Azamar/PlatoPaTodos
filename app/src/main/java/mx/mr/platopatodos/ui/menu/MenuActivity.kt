package mx.mr.platopatodos.ui.menu

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import mx.mr.platopatodos.R
import mx.mr.platopatodos.databinding.ActivityMenuBinding
import mx.mr.platopatodos.databinding.FragmentHomeBinding
import mx.mr.platopatodos.model.Prefs

/**
 * Menu View
 * @author Héctor González Sánchez
 */


class MenuActivity : AppCompatActivity() {

    // Binding & ViewModel
    private val viewModel: MenuVM by viewModels()
    private lateinit var binding: ActivityMenuBinding
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        prefs = Prefs(applicationContext)
        setContentView(binding.root)

        uploadMenu()
//        setupListeners()
    }

    private fun uploadMenu() {
        binding.btnUploadMenu.setOnClickListener {

            val diningName = prefs.getLocation()
            val soup = binding.etSoup.text.toString()
            val mainCourse = binding.etMainCourse.text.toString()
            val carbs = binding.etCarbs.text.toString()
            val water = binding.etWater.text.toString()
            val beansSauce = binding.etBeansSauce.text.toString()

            viewModel.uploadMenu(diningName, soup, mainCourse, carbs, water, beansSauce)
            viewModel.updateDinStatus(diningName)
            Toast.makeText(this, "El menú se ha actualizado", Toast.LENGTH_SHORT).show()


            //Clickable Component for CardViews
            val cardsViewClickable = true
            prefs.saveStautsCV(cardsViewClickable)
            finish()
        }
    }

//    private fun setupListeners() {
//        viewModel.currentStatus.observe(this) {status ->
//            prefs.saveStautsCV(status)
//            println(status)
//        }
//    }
}