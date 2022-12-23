package com.example.sadnesslearn.classes.languages;

import android.os.StrictMode;

import com.example.sadnesslearn.classes.JsonStringHandler;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CompilerApi {
    private static final String clientId = "c0b0fe69991d61d0b7b63163584a6445";
    private static final String clientSecret = "6b95f0f97a26f0b7522c9355207e616bb8a19b69c88a2b264b1e6288f3a214ba";

    private static String executeCode(String script, String language, String versionIndex){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL url = new URL("https://api.jdoodle.com/v1/execute");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            Map<String, String> input = new HashMap<>();
            input.put("clientId", clientId);
            input.put("clientSecret", clientSecret);
            input.put("script", script);
            input.put("language", language);
            input.put("versionIndex", versionIndex);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(JsonStringHandler.serializeFromMap(input).getBytes());
            outputStream.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Please check your inputs : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            StringBuilder output = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
            connection.disconnect();
            return JsonStringHandler.deserializeToMap(output.toString()).get("output");
        } catch (IOException e){
            return e.getMessage();
        } catch (Exception e){
            return e.getMessage();
        }
    }


    public static String compileAndRun(String code, String language, String versionIndex){
        return executeCode(code, language, versionIndex);
    }
}
