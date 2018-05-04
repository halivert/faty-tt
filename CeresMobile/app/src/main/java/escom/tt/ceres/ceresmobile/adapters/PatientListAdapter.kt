package escom.tt.ceres.ceresmobile.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.models.Patient
import escom.tt.ceres.ceresmobile.tools.Functions.calculateAge
import java.text.SimpleDateFormat

/*
class PatientListAdapter(
    private var activity: Activity,
    private var items: MutableList<Patient>,
    private var mListener: PatientItemInteraction?) : BaseAdapter() {
  private class ViewHolder(row: View?) {
    var patientName: TextView? = null
    var patientAge: TextView? = null
    var patientBirthDate: TextView? = null
    var patientSex: TextView? = null
    var patientEmail: TextView? = null

    init {
      this.patientName = row?.findViewById(R.id.patient_name)
      this.patientAge = row?.findViewById(R.id.patient_age)
      this.patientBirthDate = row?.findViewById(R.id.patient_birth_date)
      this.patientSex = row?.findViewById(R.id.patient_sex)
      this.patientEmail = row?.findViewById(R.id.patient_email)
    }
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view: View?
    val viewHolder: ViewHolder
    if (convertView == null) {
      val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      view = inflater.inflate(R.layout.patient_row_layout, null)
      viewHolder = ViewHolder(view)

      view?.tag = viewHolder
    } else {
      view = convertView
      viewHolder = view.tag as ViewHolder
    }

    var patient = items[position]

    val df = SimpleDateFormat("yyyy-MM-dd")
    val birthdate = df.parse(patient.birthDate)
    viewHolder.patientName?.text = patient.name
    viewHolder.patientAge?.text = calculateAge(birthdate).toString()
    viewHolder.patientBirthDate?.text = patient.birthDate
    viewHolder.patientSex?.text = patient.sex
    viewHolder.patientEmail?.text = patient.email

    return view as View
  }

  override fun getItem(i: Int): Patient {
    return items[i]
  }

  override fun getItemId(i: Int): Long {
    return i.toLong()
  }

  override fun getCount(): Int {
    return items.size
  }

  interface PatientItemInteraction {
    fun onPatientItemClick(patient: Patient)
  }
}
*/

class PatientListAdapter(
    private val patients: MutableList<Patient>,
    private var mListener: PatientItemInteraction?) : RecyclerView.Adapter<PatientListAdapter.ViewHolder>() {

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