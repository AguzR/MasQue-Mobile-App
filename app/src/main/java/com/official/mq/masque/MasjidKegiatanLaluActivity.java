package com.official.mq.masque;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.official.mq.masque.adapter.KegiatanAdapter;
import com.official.mq.masque.model.Kegiatan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_DARIWAK;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_DESCIPTION;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_IMG;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_IMGMASK;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_LATLONG;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_NAMEM;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_POSTED;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_SAMPAIWAK;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_TANGGAL;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_TEMPAT;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_ID;

public class MasjidKegiatanLaluActivity extends AppCompatActivity implements KegiatanAdapter.OnItemClickListener {

    private Toolbar toolbar;
    private TextView getId;

    List<Kegiatan> kegiatanList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masjid_kegiatan_lalu);

        getId = findViewById(R.id.idMasjid);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = toolbar.findViewById(R.id.title_bar);

        toolbar.setNavigationIcon(R.drawable.ic_back_dark);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        int ID_MASJID = intent.getIntExtra(EXTRA_ID, 0);
        getId.setText("" + ID_MASJID);
        getId.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kegiatanList = new ArrayList<>();
        readKegiatan();
    }

    private void readKegiatan() {
        final String getID = getId.getText().toString();
        final String URL_READ = "http://192.168.43.69/masqueapp/android/masjid/read_kegiatanlalu.php?id=" + getID;
//        final String URL_READ = "https://evcamp.site/masqueapp/android/masjid/read_kegiatanlalu.php?id=" + getID;
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject kegiatan = jsonArray.getJSONObject(i);
                                int idJadwal = kegiatan.getInt("id_jadwal");
                                int idMasjid = kegiatan.getInt("id_masjid");
                                String nameKegiatan = kegiatan.getString("nama_jadwal");
                                String ketKegiatan = kegiatan.getString("deskripsi_jadwal");
                                String tempatKegiatan = kegiatan.getString("tempat");
                                String tanggalKegiatan = kegiatan.getString("tanggal");
                                String strDari = kegiatan.getString("dari_jam");
                                String strSampai = kegiatan.getString("sampai_jam");
                                String imageKegiatan = kegiatan.getString("foto");
                                String strPosted = kegiatan.getString("posted");
                                String latlong = kegiatan.getString("latlong");
                                String strName = kegiatan.getString("nama_masjid");
                                String strImage = kegiatan.getString("image_masjid");

                                kegiatanList.add(new Kegiatan(idJadwal, idMasjid, nameKegiatan,
                                        ketKegiatan, tempatKegiatan, tanggalKegiatan, strDari, strSampai,
                                        imageKegiatan, strPosted, latlong, strName, strImage));
                            }

                            KegiatanAdapter kegiatanAdapter = new KegiatanAdapter(MasjidKegiatanLaluActivity.this, kegiatanList);
                            recyclerView.setAdapter(kegiatanAdapter);
                            kegiatanAdapter.setOnItemClickListener(MasjidKegiatanLaluActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailKegiatanActivity.class);

        Kegiatan clickedItem = kegiatanList.get(position);
        detailIntent.putExtra(KegiatanAdapter.EXTRA_NAME, clickedItem.getNama_jadwal());
        detailIntent.putExtra(EXTRA_DESCIPTION, clickedItem.getDeskripsi_jadwal());
        detailIntent.putExtra(EXTRA_TEMPAT, clickedItem.getTempat());
        detailIntent.putExtra(EXTRA_TANGGAL, clickedItem.getTanggal());
        detailIntent.putExtra(EXTRA_DARIWAK, clickedItem.getDari());
        detailIntent.putExtra(EXTRA_SAMPAIWAK, clickedItem.getSampai());
        detailIntent.putExtra(EXTRA_IMG, clickedItem.getFoto());
        detailIntent.putExtra(EXTRA_POSTED, clickedItem.getPosted());
        detailIntent.putExtra(EXTRA_LATLONG, clickedItem.getLatlong());
        detailIntent.putExtra(EXTRA_NAMEM, clickedItem.getName());
        detailIntent.putExtra(EXTRA_IMGMASK, clickedItem.getImage());

        startActivity(detailIntent);
    }
}
