package escom.tt.ceres.ceresmobile;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static escom.tt.ceres.ceresmobile.Vars.Strings.CODIGO_ERROR;
import static escom.tt.ceres.ceresmobile.Vars.Strings.ERROR_GENERAL;

/**
 * Created by hali on 26/10/17.
 */

public class GetReq extends AsyncTask<String, Integer, String> {
  ProgressBar bar = null;

  public void setProgressBar(ProgressBar progressBar) {
    bar = progressBar;
    bar.setProgress(0);
  }

  @Override
  protected void onPostExecute(String s) {
    super.onPostExecute(s);
    if (bar != null) {
      bar.setVisibility(View.GONE);
    }
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    if (bar != null) {
      bar.setVisibility(View.VISIBLE);
    }
  }

  @Override
  protected void onProgressUpdate(Integer... values) {
    super.onProgressUpdate(values);
    if (bar != null) {
      bar.setProgress(values[0]);
    }
  }

  @Override
  protected String doInBackground(String... params) {
    String urlString;
    JSONObject jsonObject = new JSONObject();
    if (params.length > 0) {
      urlString = params[0];

      HttpURLConnection urlConnection;
      try {
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.addRequestProperty("Accept", "application/json");
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);

        int status = urlConnection.getResponseCode();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder result = new StringBuilder();
        String line;
        line = bufferedReader.readLine();
        result.append(line);
        bufferedReader.close();

        if (status == 200)
          return new JSONObject(result.toString()).toString();

      } catch (Exception e) {
        try {
          jsonObject.put(CODIGO_ERROR, ERROR_GENERAL);
          return jsonObject.toString();
        } catch (Exception jsonE) {
        }
      }
      return jsonObject.toString();
    }
    return jsonObject.toString();
  }
}
