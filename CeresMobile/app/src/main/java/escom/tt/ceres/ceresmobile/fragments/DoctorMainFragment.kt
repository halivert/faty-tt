package escom.tt.ceres.ceresmobile.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.activities.DoctorMainActivity
import escom.tt.ceres.ceresmobile.tools.Constants.Strings
import escom.tt.ceres.ceresmobile.tools.Constants.Strings.NAME

class DoctorMainFragment : Fragment() {
  private var mListener: OnDoctorMainInteraction? = null
  private var idUser: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments != null) {
      idUser = arguments.getInt(PatientMainFragment.ARG1)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.doctor_main_fragment, container, false)

    val preferences = activity.getSharedPreferences(Strings.LOGIN, Context.MODE_PRIVATE)
    val userName = preferences.getString(NAME, null)
    val lastName = preferences.getString(Strings.APELLIDO_PATERNO, null)
    val mothersLastName = preferences.getString(Strings.APELLIDO_MATERNO, null)

    val textView = view.findViewById<TextView>(R.id.userName)
    if (textView != null) {
      textView.text = "$userName $lastName $mothersLastName"
    }

    val patientsNumber = view.findViewById<TextView>(R.id.tvPatients)
    patientsNumber.text = DoctorMainActivity.patients.size.toString()

    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnDoctorMainInteraction)
      mListener = context
    else
      throw RuntimeException(getString(R.string.error))
  }

  private fun createToken() {
    /*
    val context = activity.applicationContext
    val preferences = activity.getSharedPreferences(Strings.LOGIN, Context.MODE_PRIVATE)
    val idDoctor = preferences.getInt(Strings.ID_USUARIO, -1)
    // TODO: Agregar url de token
    val urlToken = "http://"
    // String urlToken = FuncionesPrincipales.urlTokenMedico(idDoctor);
    // val progressBar = activity.findViewById<ProgressBar>(R.id.pbHeaderProgress)
    // progressBar.progress = 0
    // progressBar.visibility = View.VISIBLE

    val request = JsonObjectRequest(urlToken, null,
        Response.Listener { response ->
          try {
            var token = "Error"
            if (response.has(Strings.MENSAJE) && response.has(Strings.RESPUESTA)) {
              token = if (response.getString(Strings.RESPUESTA) == Strings.TOKEN)
                response.getString(Strings.MENSAJE)
              else
                Strings.ERROR
            }
            // val textView = activity.findViewById<TextView>(R.id.textTokenMedico)
            // textView.text = token
          } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
          }

          // progressBar.visibility = View.GONE
        },
        Response.ErrorListener {
          Toast.makeText(context, Strings.ERROR, Toast.LENGTH_SHORT).show()
          // progressBar.visibility = View.GONE
        }
    )

    CeresRequestQueue.getInstance(context).addToRequestQueue(request)

    // GetRequest req = new GetRequest();
    // req.setProgressBar(pbHeaderProgress);
    // String resultado = req.execute(urlToken).get();
    // JSONObject respuesta = new JSONObject(resultado);
    */
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  companion object {
    private const val ARG1 = "ID_USER"
    const val TAG = "DOCTOR_MAIN_FRAGMENT_TAG"

    fun newInstance(idUser: Int): DoctorMainFragment {
      val fragment = DoctorMainFragment()
      val args = Bundle()
      args.putInt(ARG1, idUser)
      fragment.arguments = args
      return fragment
    }
  }

  interface OnDoctorMainInteraction
}