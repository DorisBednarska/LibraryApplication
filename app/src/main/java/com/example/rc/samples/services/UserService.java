package com.example.rc.samples.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;

import com.example.rc.samples.AppConfig;
import com.example.rc.samples.MyApp;
import com.example.rc.samples.models.ErrorModel;
import com.example.rc.samples.models.LoginModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class UserService {

    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();

    private static final UserService ourInstance = new UserService();

    private SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApp.context);

    public static UserService getInstance() {
        return ourInstance;
    }

    private UserService() {
    }

    public Boolean login(LoginModel loginModels) {

        boolean isLogIn = false;
        Pair<Integer, String> result = makePOSTCall(AppConfig.HOST + LOGIN, gson.toJson(loginModels));

        if (result.first == HttpsURLConnection.HTTP_OK) {
            isLogIn = true;
            LoginModel loginModel = gson.fromJson(result.second, LoginModel.class);
            prefs.edit()
                    .putString(AppConfig.TOKEN, loginModel.getToken())
                    .putLong(AppConfig.ID, loginModel.getId())
                    .commit();
        }


        return isLogIn;
    }

    public Pair<Integer, String> makePOSTCall(String requestURL, String model) {

        URL url;
        Pair<Integer, String> response = null;
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            Log.i("TAG", model);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(model);

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = new Pair(responseCode, IOUtils.toString(in, "UTF-8"));

            } else {
                InputStream in = new BufferedInputStream(conn.getErrorStream());
                response = new Pair(responseCode, IOUtils.toString(in, "UTF-8"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public List<ErrorModel> register(LoginModel model) {

        if (model.getSurname().length() == 0) {
            model.setSurname(null);
        }
        List<ErrorModel> errorModels = null;
        Pair<Integer, String> result = makePOSTCall(AppConfig.HOST + REGISTER, gson.toJson(model));

        if (result.first == HttpsURLConnection.HTTP_OK) {
            LoginModel loginModel = gson.fromJson(result.second, LoginModel.class);
            prefs.edit()
                    .putString(AppConfig.TOKEN, loginModel.getToken())
                    .putLong(AppConfig.ID, loginModel.getId())
                    .commit();
        } else {
            errorModels = gson.fromJson(result.second, new TypeToken<List<ErrorModel>>() {
            }.getType());
        }


        return errorModels;
    }
}
