package escom.tt.ceres.ceresmobile.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import escom.tt.ceres.ceresmobile.fragments.DietFragment
import escom.tt.ceres.ceresmobile.tools.Functions
import escom.tt.ceres.ceresmobile.fragments.PatientMainFragment
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.SugarRegisterFragment

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.DIETA
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.GLUCOSA
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.INICIO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.LOGIN

class PatientMainActivity : AppCompatActivity(), PatientMainFragment.OnPatientMainInteraction, SugarRegisterFragment.OnSugarRegisterListener, DietFragment.OnDietListener {

  internal val texts = intArrayOf(R.id.tvInicio, R.id.tvDieta, R.id.tvRegistrarGlucosa, R.id.tvSalir)

  internal val images = intArrayOf(R.id.ivInicio, R.id.ivDieta, R.id.ivRegistrarGlucosa, R.id.ivSalir)

  internal val unselected = intArrayOf(R.drawable.fa_th_large, R.drawable.fa_hospital, R.drawable.fa_edit, R.drawable.fa_sign_out)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.actividad_principal_paciente)

    val activity = this

    val myToolbar = findViewById<Toolbar>(R.id.appBar)
    setSupportActionBar(myToolbar)
    val actionBar = supportActionBar
    actionBar!!.setDisplayShowTitleEnabled(false)

    val idUsuario = intent.getIntExtra(ID_USUARIO, -1)

    if (idUsuario >= 0) {
      val ft = fragmentManager.beginTransaction()
      val fragment = PatientMainFragment.newInstance(idUsuario)
      ft.replace(R.id.frameFragment, fragment, INICIO)
      ft.commit()
    }

    val clInicio = findViewById<ConstraintLayout>(R.id.clInicio)
    val clDieta = findViewById<ConstraintLayout>(R.id.clDieta)
    val clGlucosa = findViewById<ConstraintLayout>(R.id.clRegistrarGlucosa)
    val clSalir = findViewById<ConstraintLayout>(R.id.clSalir)

    clInicio.setOnClickListener {
      val text = findViewById<TextView>(R.id.tvInicio)
      val imageView = findViewById<ImageView>(R.id.ivInicio)

      Functions.deselectElement(activity, texts, images, unselected)
      Functions.selectElement(activity, text, imageView, R.drawable.fa_th_large_selected)

      val fm = fragmentManager
      var fragment: PatientMainFragment? = fm.findFragmentByTag(INICIO) as PatientMainFragment
      if (fragment == null || !fragment.isVisible) {
        val ft = fm.beginTransaction()
        fragment = PatientMainFragment.newInstance(idUsuario)
        ft.replace(R.id.frameFragment, fragment, INICIO)
        ft.addToBackStack(null)
        ft.commit()
      }
    }

    clGlucosa.setOnClickListener {
      val registrarGlucosa = findViewById<TextView>(R.id.tvRegistrarGlucosa)
      val imageView = findViewById<ImageView>(R.id.ivRegistrarGlucosa)

      Functions.deselectElement(activity, texts, images, unselected)
      Functions.selectElement(activity, registrarGlucosa, imageView, R.drawable.fa_edit_selected)

      val fm = fragmentManager
      var fragment: SugarRegisterFragment? = fm.findFragmentByTag(GLUCOSA) as SugarRegisterFragment
      if (fragment == null || !fragment!!.isVisible) {
        val ft = fm.beginTransaction()
        fragment = SugarRegisterFragment.newInstance()
        ft.replace(R.id.frameFragment, fragment, GLUCOSA)
        ft.addToBackStack(null)
        ft.commit()
      }
    }

    clDieta.setOnClickListener {
      val text = findViewById<TextView>(R.id.tvDieta)
      val imageView = findViewById<ImageView>(R.id.ivDieta)

      Functions.deselectElement(activity, texts, images, unselected)
      Functions.selectElement(activity, text, imageView, R.drawable.fa_hospital_selected)

      val fm = fragmentManager
      var fragment: DietFragment? = fm.findFragmentByTag(DIETA) as DietFragment
      if (fragment == null || !fragment!!.isVisible) {
        val ft = fm.beginTransaction()
        fragment = DietFragment.newInstance()
        ft.replace(R.id.frameFragment, fragment, DIETA)
        ft.addToBackStack(null)
        ft.commit()
      }
    }

    clSalir.setOnClickListener { view ->
      val text = findViewById<TextView>(R.id.tvSalir)
      val imageView = findViewById<ImageView>(R.id.ivSalir)

      Functions.deselectElement(activity, texts, images, unselected)
      Functions.selectElement(activity, text, imageView, R.drawable.fa_sign_out_selected)

      logOut(view)
    }
  }

  override fun onBackPressed() {
    val fm = fragmentManager
    if (fm.backStackEntryCount > 0) {
      fm.popBackStackImmediate()
      activateFragmentIcon()
    } else {
      super.onBackPressed()
    }
  }

  fun activateFragmentIcon() {
    val fm = fragmentManager
    Functions.deselectElement(this, texts, images, unselected)
    var text = findViewById<TextView>(R.id.tvInicio)
    var image = findViewById<ImageView>(R.id.ivInicio)
    var drawable = R.drawable.fa_th_large_selected

    val glucosa = fm.findFragmentByTag(GLUCOSA) as SugarRegisterFragment
    val dieta = fm.findFragmentByTag(DIETA) as DietFragment

    if (glucosa != null && glucosa.isVisible) {
      text = findViewById(R.id.tvRegistrarGlucosa)
      image = findViewById(R.id.ivRegistrarGlucosa)
      drawable = R.drawable.fa_edit_selected
    } else if (dieta != null && dieta.isVisible) {
      text = findViewById(R.id.tvDieta)
      image = findViewById(R.id.ivDieta)
      drawable = R.drawable.fa_hospital_selected
    }

    Functions.selectElement(this, text, image, drawable)
  }

  fun logOut(view: View) {
    val pressNo = DialogInterface.OnClickListener { dialogInterface, i -> activateFragmentIcon() }

    AlertDialog.Builder(this)
        .setTitle(R.string.cerrando_sesion)
        .setMessage(R.string.confirmar_cerrar_sesion)
        .setPositiveButton(R.string.si) { dialog, which ->
          val editor = getSharedPreferences(LOGIN, Context.MODE_PRIVATE).edit()
          editor.clear()
          editor.apply()

          val intent = Intent(applicationContext, MainActivity::class.java)
          intent.addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
          startActivity(intent)
          finish()
        }
        .setNegativeButton(R.string.no, pressNo)
        .show()
  }

  override fun endSugarRegister(): Int {
    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

    val clInicio = findViewById<ConstraintLayout>(R.id.clInicio)
    clInicio.callOnClick()
    return 0
  }
}
