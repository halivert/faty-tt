package escom.tt.ceres.ceresmobile;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static escom.tt.ceres.ceresmobile.Vars.Ints.MEDICO;
import static escom.tt.ceres.ceresmobile.Vars.Ints.PACIENTE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ID_INDIVIDUO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ID_USUARIO;
import static escom.tt.ceres.ceresmobile.Vars.Strings.LOGIN;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ROL;

public class ActividadPrincipal extends AppCompatActivity
        implements FragmentoLogin.OnLoginInteraction {

  @Override
  public void onResume() {
    SharedPreferences preferences = getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
    int idUsuario = preferences.getInt(ID_INDIVIDUO, -1);
    int rol = preferences.getInt(ROL, -1);

    if (idUsuario > -1 && rol > -1) {
      accesoExitoso(idUsuario, rol);
    }
    super.onResume();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.actividad_principal);

    SharedPreferences preferences = getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
    final int idUsuario = preferences.getInt(ID_INDIVIDUO, -1);
    final int rol = preferences.getInt(ROL, -1);

    if (idUsuario > -1 && rol > -1) {
      accesoExitoso(idUsuario, rol);
    } else {
      Toolbar myToolbar = findViewById(R.id.appBar);
      setSupportActionBar(myToolbar);
      ActionBar actionBar = getSupportActionBar();
      actionBar.setDisplayShowTitleEnabled(false);

      FragmentTransaction ft = getFragmentManager().beginTransaction();
      FragmentoLogin fragment = new FragmentoLogin();
      ft.replace(R.id.frameFragment, fragment);
      ft.commit();
    }
  }

  @Override
  public void onBackPressed() {
    if (getFragmentManager().getBackStackEntryCount() > 0) {
      getFragmentManager().popBackStack();
    } else {
      super.onBackPressed();
    }
  }

  public void irARegistro(View view) {
    Intent intent = new Intent(this, ActividadRegistro.class);
    startActivityForResult(intent, 611);
  }

  @Override
  public void accesoExitoso(int idUsuario, int rolUsuario) {
    Intent intent = null;
    if (rolUsuario == PACIENTE) {
      intent = new Intent(this, ActividadPacientePrincipal.class);
    } else if (rolUsuario == MEDICO) {
      intent = new Intent(this, ActividadMedicoPrincipal.class);
    }
    intent.putExtra(ID_USUARIO, idUsuario);
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }
}
