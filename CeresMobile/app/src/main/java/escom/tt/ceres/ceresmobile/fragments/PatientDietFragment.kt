package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import escom.tt.ceres.ceresmobile.R

class PatientDietFragment : Fragment() {
  private var mListener: OnDietListener? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.patient_diet_fragment, container, false)
    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnDietListener) {
      mListener = context
    } else {
      throw RuntimeException(context.toString() + " must implement OnDietListener")
    }
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is OnDietListener) {
      mListener = activity
    } else {
      throw RuntimeException(activity.toString() + " must implement OnDietListener")
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnDietListener

  companion object {
    fun newInstance(): PatientDietFragment {
      return PatientDietFragment()
    }
  }
}