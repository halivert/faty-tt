package escom.tt.ceres.ceresmobile.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import escom.tt.ceres.ceresmobile.DoctorGenerateCodeFragment
import escom.tt.ceres.ceresmobile.R
import escom.tt.ceres.ceresmobile.fragments.DoctorMainFragment
import escom.tt.ceres.ceresmobile.fragments.DoctorPatientsFragment
import escom.tt.ceres.ceresmobile.tools.Functions
import escom.tt.ceres.ceresmobile.tools.Functions.Strings.LOGIN

class DoctorMainActivity :
    AppCompatActivity(),
    DoctorMainFragment.OnDoctorMainInteraction,
    DoctorGenerateCodeFragment.OnDoctorGenerateCodeInteraction,
    DoctorPatientsFragment.OnDoctorPatientsInteraction {

  private val texts = intArrayOf(
      R.id.tvInicio,
      R.id.tvPacientes,
      R.id.tvGenerarCodigo,
      R.id.tvSalir
  )

  private val images = intArrayOf(
      R.id.ivInicio,
      R.id.ivPacientes,
      R.id.ivGenerarCodigo,
      R.id.ivSalir
  )

  private val unselected = intArrayOf(
      R.drawable.fa_th_large,
      R.drawable.fa_address_book,
      R.drawable.fa_user_md,
      R.drawable.fa_sign_out
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.doctor_main_activity)
    setSupportActionBar(findViewById(R.id.appBar))
    supportActionBar!!.setDisplayShowTitleEnabled(false)

    val idUser = intent.getIntExtra(Functions.Strings.ID_USUARIO, -1)

    if (idUser >= 0) {
      val fragmentTransaction = fragmentManager.beginTransaction()
      val fragment = DoctorMainFragment.newInstance(idUser)
      fragmentTransaction.replace(R.id.frameFragment, fragment, Functions.Strings.INICIO)
      fragmentTransaction.commit()
    }

    val activity = this
    val constraintLayoutMain = findViewById<ConstraintLayout>(R.id.clInicio)
    val constraintLayoutPatients = findViewById<ConstraintLayout>(R.id.clPacientes)
    val constraintLayoutGenerateCode = findViewById<ConstraintLayout>(R.id.clGenerarCodigo)
    val constraintLayoutExit = findViewById<ConstraintLayout>(R.id.clSalir)

    constraintLayoutMain.setOnClickListener {
      val text = findViewById<TextView>(R.id.tvInicio)
      val imageView = findViewById<ImageView>(R.id.ivInicio)

      Functions.deselectElement(activity, texts, images, unselected)
      Functions.selectElement(activity, text, imageView, R.drawable.fa_th_large_selected)

      var fragment = fragmentManager.findFragmentByTag(Functions.Strings.INICIO) as DoctorMainFragment?
      val fragmentVisible = fragment?.isVisible ?: false

      if (!fragmentVisible) {
        val ft = fragmentManager.beginTransaction()
        fragment = DoctorMainFragment.newInstance(idUser)
        ft.replace(R.id.frameFragment, fragment, Functions.Strings.INICIO)
        ft.addToBackStack(null)
        ft.commit()
      }
    }

    constraintLayoutGenerateCode.setOnClickListener {
      val sugarRegistration = findViewById<TextView>(R.id.tvGenerarCodigo)
      val imageView = findViewById<ImageView>(R.id.ivGenerarCodigo)

      Functions.deselectElement(activity, texts, images, unselected)
      Functions.selectElement(activity, sugarRegistration, imageView, R.drawable.fa_user_md_selected)

      var fragment = fragmentManager.findFragmentByTag(Functions.Strings.GENERAR_CODIGO) as DoctorGenerateCodeFragment?
      val fragmentVisible = fragment?.isVisible ?: false

      if (!fragmentVisible) {
        val ft = fragmentManager.beginTransaction()
        fragment = DoctorGenerateCodeFragment.newInstance()
        ft.replace(R.id.frameFragment, fragment, Functions.Strings.GENERAR_CODIGO)
        ft.addToBackStack(null)
        ft.commit()
      }
    }

    constraintLayoutPatients.setOnClickListener {
      val text = findViewById<TextView>(R.id.tvPacientes)
      val imageView = findViewById<ImageView>(R.id.ivPacientes)

      Functions.deselectElement(activity, texts, images, unselected)
      Functions.selectElement(activity, text, imageView, R.drawable.fa_address_book_selected)

      var fragment =
          fragmentManager.findFragmentByTag(Functions.Strings.PACIENTES) as DoctorPatientsFragment?
      val fragmentVisible = fragment?.isVisible ?: false

      if (!fragmentVisible) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragment = DoctorPatientsFragment.newInstance()
        fragmentTransaction.replace(R.id.frameFragment, fragment, Functions.Strings.PACIENTES)
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
    } else {
      super.onBackPressed()
    }
  }

  private fun activateFragmentIcon() {
    Functions.deselectElement(this, texts, images, unselected)
    var text = findViewById<TextView>(R.id.tvInicio)
    var image = findViewById<ImageView>(R.id.ivInicio)
    var drawable = R.drawable.fa_th_large_selected

    val patients =
        fragmentManager.findFragmentByTag(Functions.Strings.PACIENTES) as DoctorPatientsFragment?
    val generateCode =
        fragmentManager.findFragmentByTag(Functions.Strings.GENERAR_CODIGO) as DoctorGenerateCodeFragment?

    val patientsVisible = patients?.isVisible ?: false
    val generateCodeVisible = generateCode?.isVisible ?: false

    if (patientsVisible) {
      text = findViewById(R.id.tvPacientes)
      image = findViewById(R.id.ivPacientes)
      drawable = R.drawable.fa_address_book_selected
    } else if (generateCodeVisible) {
      text = findViewById(R.id.tvGenerarCodigo)
      image = findViewById(R.id.ivGenerarCodigo)
      drawable = R.drawable.fa_user_md_selected
    }

    Functions.selectElement(this, text, image, drawable)
  }

  fun logOut(view: View) {
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
        .setNegativeButton(R.string.no, null)
        .show()
  }
}