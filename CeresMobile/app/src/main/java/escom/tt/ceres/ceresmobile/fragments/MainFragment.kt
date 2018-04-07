package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.activities.SignInActivity
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.MEDICO
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.NULL
import escom.tt.ceres.ceresmobile.tools.Constants.Ints.PACIENTE
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.ERROR

class MainFragment : Fragment() {
  private var mListener: OnMainInteraction? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.main_fragment, container, false)

    val onClickListener = View.OnClickListener { view -> goToMain(view) }

    var textView = view.findViewById<TextView>(R.id.soyPaciente)
    textView.setOnClickListener(onClickListener)
    textView = view.findViewById(R.id.soyMedico)
    textView.setOnClickListener(onClickListener)

    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnMainInteraction) {
      mListener = context
    } else {
      throw RuntimeException(ERROR)
    }
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is OnMainInteraction) {
      mListener = activity
    } else {
      throw RuntimeException(ERROR)
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  private fun goToMain(view: View) {
    var user = NULL
    if (view.tag.toString() == getString(R.string.im_patient))
      user = PACIENTE
    else if (view.tag.toString() == getString(R.string.im_doctor))
      user = MEDICO

    val rolUser = user == MEDICO || user == PACIENTE
    if (mListener != null && rolUser) {
      mListener!!.userSelection(user)
    }
  }

  interface OnMainInteraction {
    fun userSelection(user: Int)
  }

  companion object {
    fun newInstance(): MainFragment {
      return MainFragment()
    }
  }
}
