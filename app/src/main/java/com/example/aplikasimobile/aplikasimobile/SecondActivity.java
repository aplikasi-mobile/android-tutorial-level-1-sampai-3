package com.example.aplikasimobile.aplikasimobile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SecondActivity extends ActionBarActivity {
    TextView detail_name;
    TextView detail_descr;
    ImageView detail_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity_layout);


        detail_name = (TextView) findViewById(R.id.detail_name);

        //ambil putExtra
        String name = this.getIntent().getExtras().getString("name");
        detail_name.setText(name);

        detail_descr = (TextView) findViewById(R.id.detail_descr);

        //ambil putExtra
        String descr = this.getIntent().getExtras().getString("descr");
        detail_descr.setText(Html.fromHtml(descr));

        detail_pic = (ImageView) findViewById(R.id.detail_pic);
        String pics = this.getIntent().getExtras().getString("pics");

        // lihat apakah ada picturenya
        if (pics.length() > 0) {
            // pakai piccaso untuk load
            Picasso.with(this).load(pics).placeholder(R.mipmap.ic_launcher).into(detail_pic);
        }

    }
}
