package escom.tt.ceres.ceresmobile.Fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.Tools.Functions.Strings.*


class PatientMainFragment : Fragment() {
  private var mListener: OnPatientMainInteraction? = null
  private var idUser: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments != null) {
      idUser = arguments.getInt(ARG1)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_paciente_inicio, container, false)

    val preferences = activity.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    val userName = preferences.getString(NOMBRE, null)
    val lastName = preferences.getString(APELLIDO_PATERNO, null)
    val mothersLastName = preferences.getString(APELLIDO_MATERNO, null)

    val textView = activity.findViewById<TextView>(R.id.userName)
    if (textView != null)
      textView.text = userName + ' '.toString() + lastName + ' '.toString() + mothersLastName

    return view
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is OnPatientMainInteraction) {
      mListener = context
    } else {
      throw RuntimeException(ERROR)
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface OnPatientMainInteraction

  companion object {
    private const val ARG1 = "ID_USER"

    fun newInstance(idUser: Int): PatientMainFragment {
      val fragment = PatientMainFragment()
      val args = Bundle()
      args.putInt(ARG1, idUser)
      fragment.arguments = args
      return fragment
    }
  }
}
