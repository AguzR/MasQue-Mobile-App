package com.official.mq.masque;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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
import java.util.Map;

import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_DARIWAK;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_DESCIPTION;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_IMG;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_IMGMASK;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_LATLONG;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_NAME;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_NAMEM;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_POSTED;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_SAMPAIWAK;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_TANGGAL;
import static com.official.mq.masque.adapter.KegiatanAdapter.EXTRA_TEMPAT;

public class KegiatanActivity extends AppCompatActivity implements KegiatanAdapter.OnItemClickListener{

    private Toolbar toolbar;
    private static final String URL_READ = "http://192.168.43.69/masqueapp/android/kegiatan/read_kegiatan.php";
//    private static final String URL_READ = "https://evcamp.site/masqueapp/android/kegiatan/read_kegiatan.php";
    List<Kegiatan> kegiatanList;
    RecyclerView recyclerView;
    KegiatanAdapter kegiatanAdapter;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kegiatan);

        recyclerView = findViewById(R.id.recyleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kegiatanList = new ArrayList<>();
        loadKegiatan();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back_dark);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setFocusable(false);

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String cari) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String cari) {
                kegiatanAdapter.getFilter().filter(cari);
                return false;
            }
        });

    }

    private void loadKegiatan() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
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

                            kegiatanAdapter = new KegiatanAdapter(KegiatanActivity.this, kegiatanList);
                            recyclerView.setAdapter(kegiatanAdapter);
                            kegiatanAdapter.setOnItemClickListener(KegiatanActivity.this);

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
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getNama_jadwal());
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