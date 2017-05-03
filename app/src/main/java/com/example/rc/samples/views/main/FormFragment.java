package com.example.rc.samples.views.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rc.samples.R;
import com.example.rc.samples.ServiceUtils;
import com.example.rc.samples.models.BookModel;
import com.example.rc.samples.services.BookService;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RaVxp on 25.04.2017.
 */

public class FormFragment extends Fragment {

    @BindView(R.id.title)
    protected EditText title;

    @BindView(R.id.author)
    protected EditText author;

    @BindView(R.id.isbn)
    protected EditText isbn;


    private BookService service = ServiceUtils.retrofit.create(BookService.class);


    public static FormFragment newInstance() {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_panel, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.submit)
    public void submit() {
        if (StringUtils.isEmpty(title.getText())) {
            title.requestFocus();
            title.setError(getString(R.string.field_required));
        } else if (StringUtils.isEmpty(author.getText())) {
            author.requestFocus();
            author.setError(getString(R.string.field_required));
        } else if (StringUtils.isEmpty(isbn.getText())) {
            isbn.requestFocus();
            isbn.setError(getString(R.string.field_required));
        } else {
            create();
        }
    }

    private void create() {
        service.createBook(
                new BookModel(
                        title.getText().toString(),
                        author.getText().toString(),
                        isbn.getText().toString()
                )
        ).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    title.getText().clear();
                    author.getText().clear();
                    isbn.getText().clear();

                    Toast.makeText(getActivity(), "Book created!", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Ojojoj")
                                .setMessage(response.errorBody().string())
                                .setCancelable(true)
                                .setNegativeButton("Ustawienia", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                    }
                                })
                                .setPositiveButton("Rozumiem", null)
                                .show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Ojojoj")
                        .setMessage(t.getMessage())
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
        });
    }
}
