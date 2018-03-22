package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.single.CeresRequestQueue
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.APELLIDO_MATERNO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.APELLIDO_PATERNO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ERROR
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.LOGIN
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.MENSAJE
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.NOMBRE
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.RESPUESTA
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.TOKEN

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
    val preferences = activity.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    val userName = preferences.getString(NOMBRE, null)
    val lastName = preferences.getString(APELLIDO_PATERNO, null)
    val mothersLastName = preferences.getString(APELLIDO_MATERNO, null)

    val imageView = view.findViewById<ImageView>(R.id.ivFruits)
    imageView.setImageResource(R.drawable.fruits)

    var textView = view.findViewById<TextView>(R.id.textNombre)
    if (textView != null) {
      textView.text = "$userName $lastName $mothersLastName"
    }

    textView = activity.findViewById(R.id.userName)
    if (textView != null) {
      textView.text = "$userName $lastName $mothersLastName"
    }

    return view
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnDoctorMainInteraction)
      mListener = context
    else
      throw RuntimeException(getString(R.string.error))
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is OnDoctorMainInteraction)
      mListener = activity
    else
      throw RuntimeException(getString(R.string.error))
  }

  private fun createToken() {
    val context = activity.applicationContext
    val preferences = activity.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    val idDoctor = preferences.getInt(ID_USUARIO, -1)
    // TODO: Agregar url de token
    val urlToken = "http://"
    // String urlToken = FuncionesPrincipales.urlTokenMedico(idDoctor);
    val progressBar = activity.findViewById<ProgressBar>(R.id.pbHeaderProgress)
    progressBar.progress = 0
    progressBar.visibility = View.VISIBLE

    val request = JsonObjectRequest(urlToken, null,
        Response.Listener { response ->
          try {
            var token = "Error"
            if (response.has(MENSAJE) && response.has(RESPUESTA)) {
              token = if (response.getString(RESPUESTA) == TOKEN) response.getString(MENSAJE) else ERROR
            }
            val textView = activity.findViewById<TextView>(R.id.textTokenMedico)
            textView.text = token
          } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
          }

          progressBar.visibility = View.GONE
        },
        Response.ErrorListener {
          Toast.makeText(context, ERROR, Toast.LENGTH_SHORT).show()
          progressBar.visibility = View.GONE
        }
    )

    CeresRequestQueue.getInstance(context).addToRequestQueue(request)

    // GetRequest req = new GetRequest();
    // req.setProgressBar(pbHeaderProgress);
    // String resultado = req.execute(urlToken).get();
    // JSONObject respuesta = new JSONObject(resultado);
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  companion object {
    private const val ARG1 = "ID_USER"

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