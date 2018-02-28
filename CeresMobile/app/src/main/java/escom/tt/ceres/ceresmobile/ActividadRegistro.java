package escom.tt.ceres.ceresmobile;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static escom.tt.ceres.ceresmobile.Vars.Ints.MEDICO;
import static escom.tt.ceres.ceresmobile.Vars.Ints.PACIENTE;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ID_USUARIO;

public class ActividadRegistro extends AppCompatActivity
        implements InicioFragment.EnSeleccionUsuario,
        FragmentoPacienteRegistro.CFPacienteRegistro,
        FragmentoMedicoRegistro.CFMedicoRegistro,
        LoginFragment.OnLoginInteraction {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.actividad_registro);

    Toolbar myToolbar = findViewById(R.id.appBar);
    setSupportActionBar(myToolbar);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    FragmentTransaction ft = getFragmentManager().beginTransaction();
    InicioFragment fragment = new InicioFragment();
    ft.replace(R.id.frameFragment, fragment);
    ft.commit();
  }

  @Override
  public void onBackPressed() {
    if (getFragmentManager().getBackStackEntryCount() > 0) {
      getFragmentManager().popBackStack();
    } else {
      super.onBackPressed();
      finish();
    }
  }

  @Override
  public void seleccionUsuario(int usuario) {
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    if (usuario == PACIENTE) {
      FragmentoPacienteRegistro fragment = new FragmentoPacienteRegistro();
      ft.replace(R.id.frameFragment, fragment);
    } else if (usuario == MEDICO) {
      FragmentoMedicoRegistro fragment = new FragmentoMedicoRegistro();
      ft.replace(R.id.frameFragment, fragment);
    } else {
      Toast.makeText(this, R.string.tipo_usuario_incorrecto, Toast.LENGTH_SHORT).show();
      return;
    }
    ft.addToBackStack(null);
    ft.commit();
  }

  @Override
  public void registroExitoso(String email, String keyword) {
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    LoginFragment fragment = LoginFragment.newInstance(email, keyword);
    ft.replace(R.id.frameFragment, fragment);
    ft.commit();
  }

  @Override
  public void accesoExitoso(int idUsuario, int rolUsuario) {
    Intent intent = null;
    if (rolUsuario == PACIENTE) {
      intent = new Intent(this, ActividadPacientePrincipal.class);
    } else if (rolUsuario == MEDICO) {
      intent = new Intent(this, MedicoPrincipalActivity.class);
    }
    intent.putExtra(ID_USUARIO, idUsuario);
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }
}
