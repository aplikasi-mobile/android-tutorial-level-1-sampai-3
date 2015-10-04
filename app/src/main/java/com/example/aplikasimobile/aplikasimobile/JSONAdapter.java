package com.example.aplikasimobile.aplikasimobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONAdapter extends BaseAdapter {

    //1. inisiasi global variable
    //2. kontainer untuk Context
    Context mContext;
    //3. kontainer untuk inflater
    LayoutInflater mInflater;
    //4.kontainer untuk jsonArray
    JSONArray mJsonArray;

    //5. buat konstruktor
    public JSONAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    //6. metode yg harus dimplementasikan antara lain getCount, getItem, getItemId, dan getView
    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return mJsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateData(JSONArray jsonArray) {
        //20. update array data yang dipakai
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClubHolder holder;

        // 7. cek apakah view sudah ada
        // kalau ada tidak usah inflate lagi
        if (convertView == null) {

            // 8. kalau belum ada
            //inflate baru
            convertView = mInflater.inflate(R.layout.list_klub, null);

            //9. buat clubholder baru dengan inisiasi semua elemen view nya
            holder = new ClubHolder();
            holder.clubpicture = (ImageView) convertView.findViewById(R.id.picture);
            holder.clubname = (TextView) convertView.findViewById(R.id.name);
            holder.clubdescription = (TextView) convertView.findViewById(R.id.descr);

            //10. beri tag kepada view untuk penggunaan kedepan
            convertView.setTag(holder);
        } else {

            //11 panggil clubholder yg sudah diinflate menggunakan getTag
            holder = (ClubHolder) convertView.getTag();
        }
        //12 lebih banyak kode menyusul

        // 13. Ambil data dari objek aktual
        JSONObject jsonObject = (JSONObject) getItem(position);

        //14. cek apakah ada attribut picture pada data
        if (jsonObject.has("picture")) {

            // 15.Jika iya ambil dan simpan
            String imageURL = jsonObject.optString("picture");


            // 16. Pakai Picasso untuk memuat gambar
            // placeholder dipakai saat gambar loading pertama, atau lambat untuk loading
            Picasso.with(mContext).load(imageURL).placeholder(R.mipmap.ic_launcher).into(holder.clubpicture);
        } else {

            //17. Isi gambar default jika pada data tidak ada attribut picture
            holder.clubpicture.setImageResource(R.mipmap.ic_launcher);
        }

        //18. ambil nama club dan deskripsi
        String clubname = "";
        String clubdescr = "";

        if (jsonObject.has("name")) {
            clubname = jsonObject.optString("name");
        }

        if (jsonObject.has("description")) {
            clubdescr = jsonObject.optString("description");
        }

        //19. kirim data ke display
        holder.clubname.setText(clubname);
        holder.clubdescription.setText(clubdescr);


        return convertView;
    }


    private static class ClubHolder {
        public ImageView clubpicture;
        public TextView clubname;
        public TextView clubdescription;
    }
}
