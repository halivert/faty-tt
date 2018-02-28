package escom.tt.ceres.ceresmobile;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import static escom.tt.ceres.ceresmobile.Vars.Strings.CODIGO_ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR_CONEXION;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR_GENERAL;
import static escom.tt.ceres.ceresmobile.Vars.qString;

/**
 * Created by hali on 30/10/17.
 */

public class PostReq extends AsyncTask<String, Integer, String> {
  @Override
  protected String doInBackground(String... params) {
    String urlString = null;
    String data = null;
    if (params.length > 0)
      urlString = params[0];
    if (params.length > 1)
      data = params[1];
    if (urlString == null || data == null)
      return "Faltan parámetros";

    OutputStream out = null;
    HttpURLConnection urlConnection = null;
    String error = null;
    JSONObject jsonError = new JSONObject();

    try {
      URL url = new URL(urlString);

      urlConnection = (HttpURLConnection) url.openConnection();

      urlConnection.addRequestProperty("Accept", "application/json");
      urlConnection.setRequestMethod("POST");
      urlConnection.setDoInput(true);
      urlConnection.setDoOutput(true);

      /*
      DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
      wr.writeBytes(data);
      wr.flush();
      wr.close();
      */

      int status = urlConnection.getResponseCode();
      if (status != 500) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        line = bufferedReader.readLine();
        result.append(line);
        bufferedReader.close();

        if (status == 200)
          return new JSONObject(result.toString()).toString();
      }
    } catch (ConnectException ce) {
      try {
        jsonError.put(ERROR, ERROR_CONEXION);
        jsonError.put(CODIGO_ERROR, Vars.Ints.ERROR_CONEXION);
        return jsonError.toString();
      } catch (Exception jsne) {
      }
    } catch (Exception e) {
      try {
        jsonError.put(ERROR, ERROR_GENERAL);
        jsonError.put(CODIGO_ERROR, Vars.Ints.ERROR_GENERAL);
        return jsonError.toString();
      } catch (Exception jsne) {
      }
    }
    return "{" + qString(ERROR) + ":" + qString(ERROR_GENERAL) + "," +
            qString(CODIGO_ERROR) + ":" + Vars.Ints.ERROR_GENERAL + "}";
  }
}

