package escom.tt.ceres.ceresmobile;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static escom.tt.ceres.ceresmobile.Vars.Strings.LOGIN;

public class ActividadMedicoPrincipal extends AppCompatActivity
        implements FragmentoMedicoInicio.ComunicacionFMI {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.actividad_principal_medico);

    Toolbar myToolbar = findViewById(R.id.appBar);
    setSupportActionBar(myToolbar);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowTitleEnabled(false);

    FragmentTransaction ft = getFragmentManager().beginTransaction();
    FragmentoMedicoInicio fragment = new FragmentoMedicoInicio();
    ft.replace(R.id.frameFragment, fragment);
    ft.commit();
  }

  @Override
  public void onBackPressed() {
    if (getFragmentManager().getBackStackEntryCount() > 0) {
      getFragmentManager().popBackStack();
    } else {
      super.onBackPressed();
    }
  }

  public void logOut(View view) {
    new AlertDialog.Builder(this)
            .setTitle(R.string.cerrando_sesion)
            .setMessage(R.string.confirmar_cerrar_sesion)
            .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = getSharedPreferences(LOGIN, MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), ActividadPrincipal.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
              }
            })
            .setNegativeButton(R.string.no, null)
            .show();
  }
}