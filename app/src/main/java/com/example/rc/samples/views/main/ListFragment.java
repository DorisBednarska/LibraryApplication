package com.example.rc.samples.views.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rc.samples.AppConfig;
import com.example.rc.samples.R;
import com.example.rc.samples.ServiceUtils;
import com.example.rc.samples.models.BookModel;
import com.example.rc.samples.models.ResponseBookModel;
import com.example.rc.samples.services.BookService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by RaVxp on 25.04.2017.
 */

public class ListFragment extends Fragment {

    private static final String IS_MINE = "isMine";

    private BookService service = ServiceUtils.retrofit.create(BookService.class);

    @BindView(R.id.list)
    protected ListView listView;

    @BindView(R.id.empty_list_text)
    protected View empty;

    private ProgressDialog loader;

    private BooksAdapter adapter;

    private Callback callback = new Callback<ResponseBookModel>() {
        @Override
        public void onResponse(Call<ResponseBookModel> call, Response<ResponseBookModel> response) {
            adapter = new BooksAdapter(getActivity(), response.body().getEmbedded().getData());
            listView.setAdapter(adapter);
            loader.dismiss();
        }

        @Override
        public void onFailure(Call<ResponseBookModel> call, Throwable t) {
            loader.dismiss();
            new AlertDialog.Builder(getActivity())
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
    };

    public static ListFragment newInstance(boolean isMine) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_MINE, isMine);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, rootView);
        listView.setEmptyView(empty);

        adapter = new BooksAdapter(getActivity(), new ArrayList<BookModel>());
        listView.setAdapter(adapter);

        load();

        return rootView;
    }

    public void load() {
        loader = ProgressDialog.show(getActivity(), "Czekaj...", "", true);

        String token = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(AppConfig.TOKEN, "");
        Long id = PreferenceManager.getDefaultSharedPreferences(getActivity()).getLong(AppConfig.ID, -1);

        if (getArguments().getBoolean(IS_MINE)) {
            service.getMyBooks(id, token).enqueue(callback);
        } else {
            service.getBooks().enqueue(callback);
        }
    }
}
