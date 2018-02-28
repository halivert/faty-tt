package escom.tt.ceres.ceresmobile;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import static escom.tt.ceres.ceresmobile.Vars.Strings.CODIGO_ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR_CONEXION;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR_GENERAL;
import static escom.tt.ceres.ceresmobile.Vars.qString;

/**
 * Created by hali on 26/10/17.
 */

public class PostJSONReq extends AsyncTask<String, Integer, String> {
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

    try {
      URL url = new URL(urlString);

      HttpURLConnection urlConnection;
      urlConnection = (HttpURLConnection) url.openConnection();

      urlConnection.addRequestProperty("Accept", "application/json");
      urlConnection.addRequestProperty("Content-Type", "application/json");
      urlConnection.setRequestMethod("POST");
      urlConnection.setDoInput(true);
      urlConnection.setDoOutput(true);

      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
      bw.write(data);
      bw.flush();
      bw.close();

      int status = urlConnection.getResponseCode();
      if (status == 200) {
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        line = bufferedReader.readLine();
        result.append(line);
        bufferedReader.close();

        return new JSONObject(result.toString()).toString();
      }
    } catch (ConnectException ce) {
      return "{" + qString(ERROR) + ":" + qString(ERROR_CONEXION) + "," +
              qString(CODIGO_ERROR) + ":" + Vars.Ints.ERROR_CONEXION + "}";
    } catch (Exception e) {
      return "{" + qString(ERROR) + ":" + qString(ERROR_GENERAL) + "," +
              qString(CODIGO_ERROR) + ":" + Vars.Ints.ERROR_GENERAL + "}";
    }
    return "{" + qString(ERROR) + ":" + qString(ERROR_GENERAL) + "," +
            qString(CODIGO_ERROR) + ":" + Vars.Ints.ERROR_GENERAL + "}";
  }
}
