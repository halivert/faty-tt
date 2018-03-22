package escom.tt.ceres.ceresmobile.fragments

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import org.json.JSONObject

import escom.tt.ceres.ceresmobile.R

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
  internal var requestQueue: RequestQueue
  private var mListener: ComunicacionFMI? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle): View? {
    val view = inflater.inflate(R.layout.fragment_medico_inicio, container, false)
    val preferences = activity.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    val nombreUsuario = preferences.getString(NOMBRE, null)
    val apPaterno = preferences.getString(APELLIDO_PATERNO, null)
    val apMaterno = preferences.getString(APELLIDO_MATERNO, null)

    val imageView = view.findViewById<ImageView>(R.id.ivFruits)
    imageView.setImageResource(R.drawable.fruits)

    val textView = view.findViewById<TextView>(R.id.textNombre)
    if (textView != null)
      textView.text = nombreUsuario + ' '.toString() + apPaterno + ' '.toString() + apMaterno

    val btnCrearToken = view.findViewById<Button>(R.id.btnNuevoToken)
    btnCrearToken.setOnClickListener { crearToken() }
    requestQueue = Volley.newRequestQueue(activity)

    return view
  }

  override fun onAttach(activity: Activity) {
    super.onAttach(activity)
    if (activity is ComunicacionFMI) {
      mListener = activity
    } else {
      throw RuntimeException(getString(R.string.error))
    }
  }

  fun crearToken() {
    val act = activity
    val context = act.applicationContext
    val preferences = act.getSharedPreferences(LOGIN, Context.MODE_PRIVATE)
    val idMedico = preferences.getInt(ID_USUARIO, -1)
    // TODO: Agregar url de token
    val urlToken = "http://"
    // String urlToken = FuncionesPrincipales.urlTokenMedico(idMedico);
    val progressBar = act.findViewById<ProgressBar>(R.id.pbHeaderProgress)
    progressBar.progress = 0
    progressBar.visibility = View.VISIBLE

    val request = JsonObjectRequest(urlToken, null,
        Response.Listener { response ->
          try {
            var token = "Error"
            if (response.has(MENSAJE) && response.has(RESPUESTA)) {
              token = if (response.getString(RESPUESTA) == TOKEN) response.getString(MENSAJE) else ERROR
            }
            val textView = act.findViewById<TextView>(R.id.textTokenMedico)
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

    requestQueue.add(request)

    // GetRequest req = new GetRequest();
    // req.setProgressBar(pbHeaderProgress);
    // String resultado = req.execute(urlToken).get();
    // JSONObject respuesta = new JSONObject(resultado);
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  interface ComunicacionFMI
}
