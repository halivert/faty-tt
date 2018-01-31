package escom.tt.ceres.ceresmobile;

/**
 * Created by hali on 26/10/17.
 */

public class FuncionesPrincipales {
  public static String qString(String s) {
    return "\"" + s + "\"";
  }

  public static String urlTokenMedico(int idMedico) {
    return "http://35.188.191.232/tt-escom-diabetes/ceres/medico/" + String.valueOf(idMedico) + "/token/";
  }
}
