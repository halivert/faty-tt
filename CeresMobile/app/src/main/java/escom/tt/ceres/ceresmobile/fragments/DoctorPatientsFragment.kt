package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import escom.tt.ceres.ceresmobile.R

class DoctorPatientsFragment : Fragment() {

  private var mListener: OnDoctorPatientsInteraction? = null

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    var view = inflater!!.inflate(R.layout.doctor_patients_fragment, container, false)
    return view
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is OnDoctorPatientsInteraction) {
      mListener = context
    } else {
      throw RuntimeException("Must implement OnDoctorPatientsInteraction")
    }
  }

  override fun onAttach(activity: Activity?) {
    super.onAttach(activity)
    if (activity is OnDoctorPatientsInteraction) {
      mListener = activity
    } else {
      throw RuntimeException("Must implement OnDoctorPatientsInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnDoctorPatientsInteraction

  companion object {
    fun newInstance(): DoctorPatientsFragment {
      return DoctorPatientsFragment()
    }
  }
}