package escom.tt.ceres.ceresmobile.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.models.Diet
import java.text.SimpleDateFormat
import java.util.*

class DietListAdapter(
    private val diets: MutableList<Diet>,
    private var mListener: DietItemInteraction?) : RecyclerView.Adapter<DietListAdapter.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)

    val dietView = inflater.inflate(R.layout.patient_diet_row_layout, parent, false)

    return ViewHolder(dietView)
  }

  override fun getItemCount(): Int {
    return diets.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val diet = diets[position]
    holder.dietDescription.text = diet.description
    val date = Date(diet.assignDate.time)
    holder.dietAssignDate.text =
        SimpleDateFormat("yyyy-MM-dd HH:mm").format(date)

    holder.itemView!!.setOnClickListener {
      if (mListener != null) {
        mListener!!.onDietItemClick(diets[position].idDiet)
      }
    }
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var dietDescription: TextView = view.findViewById(R.id.diet_description)
    var dietAssignDate: TextView = view.findViewById(R.id.diet_assign_date)
  }

  interface DietItemInteraction {
    fun onDietItemClick(idDiet: Int)
  }
}