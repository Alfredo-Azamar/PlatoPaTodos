package mx.mr.platopatodos.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import mx.mr.platopatodos.R
import mx.mr.platopatodos.databinding.VulSituationBinding
import mx.mr.platopatodos.model.responses.vulCondItem

class vulCondAdapter(private val context: Context, var arrVulCond: Array<vulCondItem>)
    : RecyclerView.Adapter<vulCondAdapter.vulCondCheck>()
{
    class vulCondCheck(var checkBoxView: View): RecyclerView.ViewHolder(checkBoxView)
    {
        fun set(conditionName: vulCondItem) {
            checkBoxView.findViewById<CheckBox>(R.id.cb_vulCondition).text = conditionName.Nombre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vulCondCheck {
        val binding = VulSituationBinding.inflate(LayoutInflater.from(context))
        return vulCondCheck(binding.root)
    }

    override fun getItemCount(): Int {
        return arrVulCond.size
    }

    override fun onBindViewHolder(holder: vulCondCheck, position: Int) {
        val conditionName = arrVulCond[position]
        holder.set(conditionName)
    }

}