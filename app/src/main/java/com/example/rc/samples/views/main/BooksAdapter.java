package com.example.rc.samples.views.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rc.samples.R;
import com.example.rc.samples.models.BookModel;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RaVxp on 25.04.2017.
 */

public class BooksAdapter extends ArrayAdapter<BookModel> {

    private final Context context;

    @BindView(R.id.title)
    protected TextView title;

    @BindView(R.id.author)
    protected TextView author;

    @BindView(R.id.isbn)
    protected TextView isbn;

    @BindView(R.id.borrowButton)
    protected Button button;

    @BindView(R.id.image)
    protected ImageView imageView;

    public BooksAdapter(Context context, List<BookModel> elements) {
        super(context, 0, elements);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final BookModel model = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_row, parent, false);
        }
        ButterKnife.bind(this, convertView);

        title.setText(model.getTitle());
        author.setText(model.getAuthor());
        isbn.setText(model.getIsbn());

        if (StringUtils.isNotEmpty(model.getUrl())) {
            Picasso
                    .with(context)
                    .load(model.getUrl())
                    .placeholder(R.drawable.icon_book_open_page_variant)
                    .error(R.drawable.icon_book_open_page_variant)
                    .into(imageView);
        }

        return convertView;
    }
}
