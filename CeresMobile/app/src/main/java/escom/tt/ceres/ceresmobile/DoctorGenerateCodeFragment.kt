package escom.tt.ceres.ceresmobile

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DoctorGenerateCodeFragment : Fragment() {
  private var mListener: OnDoctorGenerateCodeInteraction? = null

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    var view = inflater!!.inflate(R.layout.doctor_generate_code_fragment, container, false)
    return view
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is OnDoctorGenerateCodeInteraction) {
      mListener = context
    } else {
      throw RuntimeException(context!!.toString() + " must implement OnDoctorGenerateCodeInteraction")
    }
  }

  override fun onAttach(activity: Activity?) {
    super.onAttach(activity)
    if (activity is OnDoctorGenerateCodeInteraction) {
      mListener = activity
    } else {
      throw RuntimeException(activity!!.toString() + " must implement OnDoctorGenerateCodeInteraction")
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnDoctorGenerateCodeInteraction

  companion object {
    fun newInstance(): DoctorGenerateCodeFragment {
      return DoctorGenerateCodeFragment()
    }
  }
}
