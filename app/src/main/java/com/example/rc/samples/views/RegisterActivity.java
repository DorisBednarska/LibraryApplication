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

import com.example.rc.samples.pickers.DatePickerFragment;
import com.example.rc.samples.models.ErrorModel;
import com.example.rc.samples.models.LoginModel;
import com.example.rc.samples.R;
import com.example.rc.samples.services.UserService;
import com.example.rc.samples.views.main.MainActivity;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.email)
    protected EditText email;

    @BindView(R.id.password)
    protected EditText password;

    @BindView(R.id.name)
    protected EditText name;

    @BindView(R.id.surname)
    protected EditText surname;

    @BindView(R.id.birthday)
    protected EditText birthday;

    @BindView(R.id.loader)
    protected ProgressBar loader;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);


        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment pickder = new DatePickerFragment();
                pickder.setBirthdayButton(birthday);
                pickder.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }

    @OnClick(R.id.submit)
    public void submitForm() {
        if (StringUtils.isEmpty(email.getText())) {
            email.requestFocus();
            email.setError(getString(R.string.field_required));
        } else if (StringUtils.isEmpty(password.getText())) {
            password.requestFocus();
            password.setError(getString(R.string.field_required));
        } else if (StringUtils.isEmpty(name.getText())) {
            name.requestFocus();
            name.setError(getString(R.string.field_required));
        } else if (StringUtils.isEmpty(birthday.getText())) {
            birthday.requestFocus();
            birthday.setError(getString(R.string.field_required));
        } else {
            register();
        }
    }

    private void register() {
        loader.setVisibility(View.VISIBLE);
        try {
            new AsyncTask<LoginModel, Void, List<ErrorModel>>() {
                @Override
                protected List<ErrorModel> doInBackground(LoginModel... loginModels) {
                    return UserService.getInstance().register(loginModels[0]);
                }

                @Override
                protected void onPostExecute(List<ErrorModel> errorModels) {
                    if (errorModels == null) {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        for (ErrorModel model : errorModels) {
                            switch (model.getCode()) {
                                case "EMAIL_NOT_UNIQ": {
                                    email.requestFocus();
                                    email.setError("Taki email już istnieje...");
                                    break;
                                }
                                case "EMAIL_NOT_VALID": {
                                    email.requestFocus();
                                    email.setError("Taki email nie jest poprawny...");
                                    break;
                                }
                                default: {
                                    new AlertDialog.Builder(RegisterActivity.this)
                                            .setTitle("Ojojoj")
                                            .setMessage(model.getMessage())
                                            .setCancelable(true)
                                            .setPositiveButton("Rozumiem", null)
                                            .show();
                                }
                            }
                        }
                    }
                    loader.setVisibility(View.GONE);

                }
            }.execute(
                    new LoginModel(
                            email.getText().toString(),
                            password.getText().toString(),
                            name.getText().toString(),
                            surname.getText().toString(),
                            simpleDateFormat.parse(birthday.getText().toString())
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
}
