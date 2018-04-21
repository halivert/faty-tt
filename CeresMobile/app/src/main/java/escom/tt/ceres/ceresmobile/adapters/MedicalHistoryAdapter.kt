package escom.tt.ceres.ceresmobile.adapters

import android.content.Context


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.models.MedicalHistory

class MedicalHistoryAdapter(private var context: Context, histories: List<MedicalHistory>)
  : RecyclerView.Adapter<MedicalHistoryAdapter.ViewHolder>() {

  private var medicalHistories: List<MedicalHistory> = histories

  override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
    val history = medicalHistories[position]

    var textView = holder!!.patientWeight
    textView.text = history.weight.toString()

    textView = holder.patientSize
    textView.text = history.size.toString()

    textView = holder.medicalHistoryDate
    textView.text = history.date

    textView = holder.patientSugar
    textView.text = history.sugar.toString()
  }

  override fun getItemCount(): Int = medicalHistories.size

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
    val context = parent?.context
    val inflater = LayoutInflater.from(context)
    val contactView = inflater.inflate(R.layout.medical_history_row_layout, parent, false)
    return ViewHolder(contactView)
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var patientWeight: TextView = itemView.findViewById(R.id.patient_weight)
    var patientSize: TextView = itemView.findViewById(R.id.patient_size)
    var medicalHistoryDate: TextView = itemView.findViewById(R.id.medical_history_date)
    var patientSugar: TextView = itemView.findViewById(R.id.patient_sugar)
  }
}
