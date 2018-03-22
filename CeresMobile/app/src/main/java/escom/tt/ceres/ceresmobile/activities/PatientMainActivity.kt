package escom.tt.ceres.ceresmobile.activities

import android.app.AlertDialog
import android.app.FragmentManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.PatientDietFragment
import escom.tt.ceres.ceresmobile.fragments.PatientMainFragment
import escom.tt.ceres.ceresmobile.fragments.PatientSugarRegisterFragment
import escom.tt.ceres.ceresmobile.tools.Functions
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.DIETA
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.GLUCOSA
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.ID_USUARIO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.INICIO
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.LOGIN

class PatientMainActivity : AppCompatActivity(),
    PatientMainFragment.OnPatientMainInteraction,
    PatientSugarRegisterFragment.OnSugarRegisterListener,
    PatientDietFragment.OnDietListener {

  private val texts = intArrayOf(
      R.id.tvInicio,
      R.id.tvDieta,
      R.id.tvRegistrarGlucosa,
      R.id.tvSalir
  )

  private val images = intArrayOf(
      R.id.ivInicio,
      R.id.ivDieta,
      R.id.ivRegistrarGlucosa,
      R.id.ivSalir
  )

  private val unselected = intArrayOf(
      R.drawable.fa_th_large,
      R.drawable.fa_hospital,
      R.drawable.fa_edit,
      R.drawable.fa_sign_out
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.actividad_principal_paciente)
    setSupportActionBar(findViewById(R.id.appBar))
    supportActionBar!!.setDisplayShowTitleEnabled(false)

    val idUser = intent.getIntExtra(ID_USUARIO, -1)

    if (idUser >= 0) {
      val fragmentTransaction = fragmentManager.beginTransaction()
      val fragment = PatientMainFragment.newInstance(idUser)
      fragmentTransaction.replace(R.id.frameFragment, fragment, INICIO)
      fragmentTransaction.commit()
    }

    val activity = this
    val constraintLayoutMain = findViewById<ConstraintLayout>(R.id.clInicio)
    val constraintLayoutDiet = findViewById<ConstraintLayout>(R.id.clDieta)
    val constraintLayoutSugar = findViewById<ConstraintLayout>(R.id.clRegistrarGlucosa)
    val constraintLayoutExit = findViewById<ConstraintLayout>(R.id.clSalir)

    constraintLayoutMain.setOnClickListener {
      val text = findViewById<TextView>(R.id.tvInicio)
      val imageView = findViewById<ImageView>(R.id.ivInicio)

      Functions.deselectElement(activity, texts, images, unselected)
      Functions.selectElement(activity, text, imageView, R.drawable.fa_th_large_selected)

      var fragment = fragmentManager.findFragmentByTag(INICIO) as PatientMainFragment?
      val fragmentVisible = fragment?.isVisible ?: false

      if (fragmentVisible) {
        val ft = fragmentManager.beginTransaction()
        fragment = PatientMainFragment.newInstance(idUser)
        ft.replace(R.id.frameFragment, fragment, INICIO)
        ft.addToBackStack(null)
        ft.commit()
      }
    }

    constraintLayoutSugar.setOnClickListener {
      val sugarRegistration = findViewById<TextView>(R.id.tvRegistrarGlucosa)
      val imageView = findViewById<ImageView>(R.id.ivRegistrarGlucosa)

      Functions.deselectElement(activity, texts, images, unselected)
      Functions.selectElement(activity, sugarRegistration, imageView, R.drawable.fa_edit_selected)

      var fragment = fragmentManager.findFragmentByTag(GLUCOSA) as PatientSugarRegisterFragment?
      val fragmentVisible = fragment?.isVisible ?: false

      if (!fragmentVisible) {
        val ft = fragmentManager.beginTransaction()
        fragment = PatientSugarRegisterFragment.newInstance()
        ft.replace(R.id.frameFragment, fragment, GLUCOSA)
        ft.addToBackStack(null)
        ft.commit()
      }
    }

    constraintLayoutDiet.setOnClickListener {
      val text = findViewById<TextView>(R.id.tvDieta)
      val imageView = findViewById<ImageView>(R.id.ivDieta)

      Functions.deselectElement(activity, texts, images, unselected)
      Functions.selectElement(activity, text, imageView, R.drawable.fa_hospital_selected)

      var fragment = fragmentManager.findFragmentByTag(DIETA) as PatientDietFragment?
      val fragmentVisible = fragment?.isVisible ?: false

      if (!fragmentVisible) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragment = PatientDietFragment.newInstance()
        fragmentTransaction.replace(R.id.frameFragment, fragment, DIETA)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
      }
    }

    constraintLayoutExit
        .setOnClickListener {
          val text = findViewById<TextView>(R.id.tvSalir)
          val imageView = findViewById<ImageView>(R.id.ivSalir)

          Functions.deselectElement(activity, texts, images, unselected)
          Functions.selectElement(activity, text, imageView, R.drawable.fa_sign_out_selected)

          logOut(constraintLayoutExit)
        }
  }

  override fun onBackPressed() {
    if (fragmentManager.backStackEntryCount > 0) {
      fragmentManager.popBackStackImmediate()
      activateFragmentIcon()
    } else
      super.onBackPressed()
  }

  private fun activateFragmentIcon() {
    Functions.deselectElement(this, texts, images, unselected)
    var text = findViewById<TextView>(R.id.tvInicio)
    var image = findViewById<ImageView>(R.id.ivInicio)
    var drawable = R.drawable.fa_th_large_selected

    val sugar = fragmentManager.findFragmentByTag(GLUCOSA) as PatientSugarRegisterFragment?
    val diet = fragmentManager.findFragmentByTag(DIETA) as PatientDietFragment?
    val sugarVisible = sugar?.isVisible ?: false
    val dietVisible = diet?.isVisible ?: false

    if (sugarVisible) {
      text = findViewById(R.id.tvRegistrarGlucosa)
      image = findViewById(R.id.ivRegistrarGlucosa)
      drawable = R.drawable.fa_edit_selected
    } else if (dietVisible) {
      text = findViewById(R.id.tvDieta)
      image = findViewById(R.id.ivDieta)
      drawable = R.drawable.fa_hospital_selected
    }

    Functions.selectElement(this, text, image, drawable)
  }

  private fun logOut(view: View) {
    val pressNo = DialogInterface.OnClickListener { _, _ -> activateFragmentIcon() }

    AlertDialog.Builder(this)
        .setTitle(R.string.cerrando_sesion)
        .setMessage(R.string.confirmar_cerrar_sesion)
        .setPositiveButton(R.string.si) { _, _ ->
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

    val constraintLayoutMain = findViewById<ConstraintLayout>(R.id.clInicio)
    constraintLayoutMain.callOnClick()
    return 0
  }
}
