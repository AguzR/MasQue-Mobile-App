package com.official.mq.masque;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.official.mq.masque.adapter.PesanAdapter;
import com.official.mq.masque.model.PesanMas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PesanMasActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar toolbar;
    SessionManager sessionManager;
    SwipeRefreshLayout swipeRefreshLayout;
    String getId;

    List<PesanMas> pesanMasList;
    RecyclerView recyclerView;
    PesanAdapter pesanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_mas);

        sessionManager = new SessionManager(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Menghapus title default
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Mengambil akses TextView yang ada di dalam Toolbar
        TextView mTitle = (TextView) toolbar.findViewById(R.id.title_bar);
        toolbar.setNavigationIcon(R.drawable.ic_back_dark);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        recyclerView = findViewById(R.id.recyleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pesanMasList = new ArrayList<>();

        pesanAdapter = new PesanAdapter(PesanMasActivity.this, pesanMasList);
        recyclerView.setAdapter(pesanAdapter);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBaseApp);
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                        pesanMasList.clear();
                        pesanAdapter.notifyDataSetChanged();
                        loadPesan();
                    }
                }
        );
//        loadPesan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bantuan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Help) {
            startActivity(new Intent(this, BantuanActivity.class));
        }
        return true;
    }

    private void loadPesan() {
        final String URL_READ = "http://192.168.43.69/masqueapp/android/users/read_pesan.php?id=" + getId;
//        final String URL_READ = "https://evcamp.site/masqueapp/android/users/read_pesan.php?id=" + getId;
//        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);
        pesanMasList.clear();
        pesanAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String strIdPesan = object.getString("id_pesan");
                                String strIdUsers = object.getString("id_users");
                                String strIdMasjid = object.getString("id_masjid");
                                String stridPengguna = object.getString("id_pengguna");
                                String strNameUsers = object.getString("dari");
                                String strNameMas = object.getString("kepada");
                                String strFoto = object.getString("foto_pm");
                                String strPesan = object.getString("pesan");
                                String strBalasan = object.getString("balasan");
                                String strDate = object.getString("tanggal");
                                String strAc = object.getString("ac_pm");

                                pesanMasList.add(new PesanMas(strIdPesan, strIdUsers, strIdMasjid, stridPengguna,
                                        strNameUsers, strNameMas, strFoto, strPesan, strBalasan, strDate,  strAc));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pesanAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onRefresh() {
        pesanMasList.clear();
        pesanAdapter.notifyDataSetChanged();
        loadPesan();
    }
}
