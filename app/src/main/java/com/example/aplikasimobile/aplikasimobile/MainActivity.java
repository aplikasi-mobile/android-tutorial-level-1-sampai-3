package com.example.aplikasimobile.aplikasimobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    //tambahkan global variable
    ArrayList mArrayList;
//    ArrayAdapter mAdapter;
    ListView mListView;
    ProgressDialog mDialog;

    //JSONAdapter
    JSONAdapter mJSONAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        //1.inisiasi listview
        mListView = (ListView) findViewById(R.id.list1);



        /*
        //2.inisiasi ArrayList sebagai wadah dari List kita
        mArrayList = new ArrayList();

        //3.Inisiasi Adapter
        mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mArrayList);

        //4.Set Adapter
        mListView.setAdapter(mAdapter);

        //5.Coba isi data
        mArrayList.add("Apple");
        mArrayList.add("Windows");
        mArrayList.add("Linux");
        mArrayList.add("Android");
        mArrayList.add("iOS");
        */

        // 18. Tambahkan mJSONAdapter
        mJSONAdapter = new JSONAdapter(this, getLayoutInflater());

        // 19. Set ListView untuk memakai JSONAdapter
        mListView.setAdapter(mJSONAdapter);

        //6. Listener untuk Item Click
        mListView.setOnItemClickListener(this);

        //7. Tambahan untuk Progress dialog
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Sedang Mencari Klub");
        mDialog.setCancelable(false);

        query_data();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        /*
        Intent intent = new Intent(this,SecondActivity.class);
        String txt = (String) mArrayList.get(position);
        intent.putExtra("name", txt);

        startActivity(intent);*/

        // 21. ambil data gambar
        JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(position);
        String pics = jsonObject.optString("picture","");

        // 22. buat Intent untuk berpindah ke SecondActivity
        Intent detailIntent = new Intent(this, SecondActivity.class);

        // 23. Isi putExtra
        detailIntent.putExtra("pics", pics);

        String name = jsonObject.optString("name","");
        detailIntent.putExtra("name", name);

        String description_long = jsonObject.optString("description_long","");
        detailIntent.putExtra("descr", description_long);

        // start the next Activity using your prepared Intent
        startActivity(detailIntent);
    }


    public void query_data(){

        //8.url web service yang dipakai , 1 pada bagian belakang menunjukan nomor halaman
        String url = "http://aplikasi-mobile.com/id/dummy/json/1";

        //9. Bikin Klien
        AsyncHttpClient client = new AsyncHttpClient();

        //10. Keluarkan Progress Dialoh
        mDialog.show();

        //11. Panggilan dijalankan
        client.get(url,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // 12. Begitu Sukses hilangkan progress dialog
                        mDialog.dismiss();

                        //13. Keluarkan toast
                        Toast.makeText(getApplicationContext(), "Sukses!", Toast.LENGTH_SHORT).show();

                        //14. Untuk sekarang JSON hanya kita keluarkan lewat log
                        Log.d("aplikasi-mobile.com", jsonObject.toString());

                        //20.update data kita
                        mJSONAdapter.updateData(jsonObject.optJSONArray("data"));

                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        // 15. Dismiss the ProgressDialog
                        mDialog.dismiss();

                        //16. Keluarkan toast
                        Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();

                        //17. Print Log
                        Log.e("aplikasi-mobile.com", statusCode + " " + throwable.getMessage());

                    }
                });
    }
}
