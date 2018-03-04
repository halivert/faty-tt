package escom.tt.ceres.ceresmobile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static escom.tt.ceres.ceresmobile.Functions.Strings.DIETA;
import static escom.tt.ceres.ceresmobile.Functions.Strings.GLUCOSA;
import static escom.tt.ceres.ceresmobile.Functions.Strings.ID_USUARIO;
import static escom.tt.ceres.ceresmobile.Functions.Strings.INICIO;
import static escom.tt.ceres.ceresmobile.Functions.Strings.LOGIN;

public class ActividadPacientePrincipal extends AppCompatActivity
        implements PacienteInicioFragment.ComunicacionFPacienteI,
        RegistrarGlucosaFragment.OnRegistrarGlucosaListener,
        DietaFragment.OnDietaListener {

  final int[] texts = {
          R.id.tvInicio,
          R.id.tvDieta,
          R.id.tvRegistrarGlucosa,
          R.id.tvSalir
  };

  final int[] images = {
          R.id.ivInicio,
          R.id.ivDieta,
          R.id.ivRegistrarGlucosa,
          R.id.ivSalir
  };

  final int[] unselected = {
          R.drawable.fa_th_large,
          R.drawable.fa_hospital,
          R.drawable.fa_edit,
          R.drawable.fa_sign_out
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.actividad_principal_paciente);

    final Activity activity = this;

    Toolbar myToolbar = findViewById(R.id.appBar);
    setSupportActionBar(myToolbar);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayShowTitleEnabled(false);

    final int idUsuario = getIntent().getIntExtra(ID_USUARIO, -1);

    if (idUsuario >= 0) {
      FragmentTransaction ft = getFragmentManager().beginTransaction();
      PacienteInicioFragment fragment = PacienteInicioFragment.newInstance(idUsuario);
      ft.replace(R.id.frameFragment, fragment, INICIO);
      ft.commit();
    }

    ConstraintLayout clInicio = findViewById(R.id.clInicio);
    ConstraintLayout clDieta = findViewById(R.id.clDieta);
    ConstraintLayout clGlucosa = findViewById(R.id.clRegistrarGlucosa);
    ConstraintLayout clSalir = findViewById(R.id.clSalir);

    clInicio.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        TextView text = findViewById(R.id.tvInicio);
        ImageView imageView = findViewById(R.id.ivInicio);

        Functions.unselectElements(activity, texts, images, unselected);
        Functions.selectElement(activity, text, imageView, R.drawable.fa_th_large_selected);

        FragmentManager fm = getFragmentManager();
        PacienteInicioFragment fragment = (PacienteInicioFragment) fm.findFragmentByTag(INICIO);
        if (fragment == null || !fragment.isVisible()) {
          FragmentTransaction ft = fm.beginTransaction();
          fragment = PacienteInicioFragment.newInstance(idUsuario);
          ft.replace(R.id.frameFragment, fragment, INICIO);
          ft.addToBackStack(null);
          ft.commit();
        }
      }
    });

    clGlucosa.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        TextView registrarGlucosa = findViewById(R.id.tvRegistrarGlucosa);
        ImageView imageView = findViewById(R.id.ivRegistrarGlucosa);

        Functions.unselectElements(activity, texts, images, unselected);
        Functions.selectElement(activity, registrarGlucosa, imageView, R.drawable.fa_edit_selected);

        FragmentManager fm = getFragmentManager();
        RegistrarGlucosaFragment fragment = (RegistrarGlucosaFragment) fm.findFragmentByTag(GLUCOSA);
        if (fragment == null || !fragment.isVisible()) {
          FragmentTransaction ft = fm.beginTransaction();
          fragment = RegistrarGlucosaFragment.newInstance();
          ft.replace(R.id.frameFragment, fragment, GLUCOSA);
          ft.addToBackStack(null);
          ft.commit();
        }
      }
    });

    clDieta.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        TextView text = findViewById(R.id.tvDieta);
        ImageView imageView = findViewById(R.id.ivDieta);

        Functions.unselectElements(activity, texts, images, unselected);
        Functions.selectElement(activity, text, imageView, R.drawable.fa_hospital_selected);

        FragmentManager fm = getFragmentManager();
        DietaFragment fragment = (DietaFragment) fm.findFragmentByTag(DIETA);
        if (fragment == null || !fragment.isVisible()) {
          FragmentTransaction ft = fm.beginTransaction();
          fragment = DietaFragment.newInstance();
          ft.replace(R.id.frameFragment, fragment, DIETA);
          ft.addToBackStack(null);
          ft.commit();
        }
      }
    });

    clSalir.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        TextView text = findViewById(R.id.tvSalir);
        ImageView imageView = findViewById(R.id.ivSalir);

        Functions.unselectElements(activity, texts, images, unselected);
        Functions.selectElement(activity, text, imageView, R.drawable.fa_sign_out_selected);

        logOut(view);
      }
    });
  }

  @Override
  public void onBackPressed() {
    FragmentManager fm = getFragmentManager();
    if (fm.getBackStackEntryCount() > 0) {
      fm.popBackStackImmediate();
      activateFragmentIcon();
    } else {
      super.onBackPressed();
    }
  }

  public void activateFragmentIcon() {
    FragmentManager fm = getFragmentManager();
    Functions.unselectElements(this, texts, images, unselected);
    TextView text = findViewById(R.id.tvInicio);
    ImageView image = findViewById(R.id.ivInicio);
    int drawable = R.drawable.fa_th_large_selected;

    RegistrarGlucosaFragment glucosa = (RegistrarGlucosaFragment) fm.findFragmentByTag(GLUCOSA);
    DietaFragment dieta = (DietaFragment) fm.findFragmentByTag(DIETA);

    if (glucosa != null && glucosa.isVisible()) {
      text = findViewById(R.id.tvRegistrarGlucosa);
      image = findViewById(R.id.ivRegistrarGlucosa);
      drawable = R.drawable.fa_edit_selected;
    } else if (dieta != null && dieta.isVisible()) {
      text = findViewById(R.id.tvDieta);
      image = findViewById(R.id.ivDieta);
      drawable = R.drawable.fa_hospital_selected;
    }

    Functions.selectElement(this, text, image, drawable);
  }

  public void logOut(View view) {
    DialogInterface.OnClickListener pressNo = new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        activateFragmentIcon();
      }
    };

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
            .setNegativeButton(R.string.no, pressNo)
            .show();
  }

  @Override
  public int finRegistroGlucosa() {
    getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    ConstraintLayout clInicio = findViewById(R.id.clInicio);
    clInicio.callOnClick();
    return 0;
  }
}
