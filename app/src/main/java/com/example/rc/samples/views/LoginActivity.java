package com.example.rc.samples.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.rc.samples.models.LoginModel;
import com.example.rc.samples.R;
import com.example.rc.samples.services.UserService;
import com.example.rc.samples.views.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email)
    protected EditText email;

    @BindView(R.id.password)
    protected EditText password;

    @BindView(R.id.loader)
    protected ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit)
    public void sbumitForm() {
        loader.setVisibility(View.VISIBLE);
        new AsyncTask<LoginModel, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(LoginModel... loginModels) {
                return UserService.getInstance().login(loginModels[0]);
            }

            @Override
            protected void onPostExecute(Boolean isLogged) {
                if (isLogged) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showConnectionProblemsAlert();
                }
                loader.setVisibility(View.GONE);

            }
        }.execute(new LoginModel(email.getText().toString(), password.getText().toString()));
    }

    private void showConnectionProblemsAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Ojojoj")
                .setMessage("Zły login lub hasło... :(")
                .setCancelable(true)
                .setNegativeButton("Ustawienia", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setPositiveButton("Rozumiem", null)
                .show();
    }

    @OnClick(R.id.register_link)
    public void goToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
