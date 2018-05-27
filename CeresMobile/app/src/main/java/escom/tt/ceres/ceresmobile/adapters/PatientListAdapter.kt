package escom.tt.ceres.ceresmobile.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.models.Patient
import escom.tt.ceres.ceresmobile.tools.Functions.calculateAge
import java.text.SimpleDateFormat

class PatientListAdapter(
    private val patients: MutableList<Patient>,
    private var mListener: PatientItemInteraction?) : RecyclerView.Adapter<PatientListAdapter.ViewHolder>(), Filterable {

  private var patientsListFiltered = mutableListOf<Patient>()

  override fun getFilter(): Filter {
    return object : Filter() {
      override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
        val charString = charSequence.toString()
        if (charString.isEmpty()) {
          patientsListFiltered = patients
        } else {
          val filteredList = mutableListOf<Patient>()
          for (row in patients) {
            if (row.name.toLowerCase().contains(charString.toLowerCase()) || row.lastName.contains(charSequence)) {
              filteredList.add(row)
            }
          }

          patientsListFiltered = filteredList
        }

        val filterResults = Filter.FilterResults()
        filterResults.values = patientsListFiltered
        return filterResults
      }

      override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
        patientsListFiltered = filterResults.values as MutableList<Patient>

        notifyDataSetChanged()
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)

    val patientView = inflater.inflate(R.layout.patient_row_layout, parent, false)

    return ViewHolder(patientView)
  }

  override fun getItemCount(): Int {
    return patients.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val patient = patients[position]
    val df = SimpleDateFormat("yyyy-MM-dd")
    val birthdate = df.parse(patient.birthDate)
    holder.patientName.text = patient.name
    holder.patientAge.text = calculateAge(birthdate).toString()
    holder.patientBirthDate.text = patient.birthDate
    holder.patientSex.text = patient.sex
    holder.patientEmail.text = patient.email

    holder.itemView!!.setOnClickListener({
      if (mListener != null) {
        mListener!!.onPatientItemClick(position)
      }
    })
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var patientName: TextView = view.findViewById(R.id.patient_name)
    var patientAge: TextView = view.findViewById(R.id.patient_age)
    var patientBirthDate: TextView = view.findViewById(R.id.patient_birth_date)
    var patientSex: TextView = view.findViewById(R.id.patient_sex)
    var patientEmail: TextView = view.findViewById(R.id.patient_email)
  }

  interface PatientItemInteraction {
    fun onPatientItemClick(position: Int)
  }
}