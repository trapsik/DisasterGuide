package com.example.disasterguide.api;// build.gradle -> dependencies -> implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpResult extends AsyncTask<String, Void, String> {
    private String content;
    @Override
    protected String doInBackground(String... params) {
        try {
            String temp = null;
            URL url = new URL("http://158.179.162.75:8080/"+params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuffer buffer = new StringBuffer();
            while ((temp = reader.readLine()) != null) buffer.append(temp);
            content = buffer.toString();
            reader.close();
        } catch (Exception e) {
            content = "서버 연결 실패";
        }

        return content;
    }
}
