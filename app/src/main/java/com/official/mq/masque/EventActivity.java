package com.official.mq.masque;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.official.mq.masque.adapter.RutinAdapter;
import com.official.mq.masque.model.Rutin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_ID;

public class EventActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView getId;

    private List<Rutin> rutinList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

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
        rutinList = new ArrayList<>();
        readRutin();
    }

    private void readRutin() {
        String ID = getId.getText().toString();
        final String URL_READ = "http://192.168.43.69/masqueapp/android/masjid/read_rutin.php?id=" + ID;
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject rutin = jsonArray.getJSONObject(i);
                                int intIdJadwal = rutin.getInt("id_jadwal");
                                int intIdMasjid = rutin.getInt("id_masjid");
                                String strName = rutin.getString("nama_jadwal");
                                String strDesc = rutin.getString("deskripsi_jadwal");
                                String strTempat = rutin.getString("tempat");
                                String strDay = rutin.getString("hari");
                                String strDari = rutin.getString("dari_jam");
                                String strSampai = rutin.getString("sampai_jam");
                                String strFoto = rutin.getString("foto");
                                String strPosted = rutin.getString("posted");

                                rutinList.add(new Rutin(intIdJadwal, intIdMasjid, strName, strDesc, strTempat, strDay,
                                        strDari, strSampai, strFoto, strPosted));
                            }

                            RutinAdapter rutinAdapter = new RutinAdapter(EventActivity.this, rutinList);
                            recyclerView.setAdapter(rutinAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EventActivity.this, "ERROR CONNECTION", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EventActivity.this, "ERROR CONNECTION", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
