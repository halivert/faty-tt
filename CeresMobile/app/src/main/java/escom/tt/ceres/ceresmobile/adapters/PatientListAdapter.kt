package escom.tt.ceres.ceresmobile.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.models.Patient
import escom.tt.ceres.ceresmobile.tools.Functions.calculateAge
import java.text.SimpleDateFormat

class PatientListAdapter(private var activity: Activity, private var items: MutableList<Patient>) : BaseAdapter() {
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

    val df = SimpleDateFormat("yyyy-mm-dd")
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
}