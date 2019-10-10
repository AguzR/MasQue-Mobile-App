package com.official.mq.masque;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.official.mq.masque.adapter.StructureAdapter;
import com.official.mq.masque.model.Structure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_ID;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_IMG;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_NAME;

public class StructureActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView imgMasjid;
    private TextView idMasjid, nameMasjid;
    private List<Structure> structureList;
    private RecyclerView recyclerView;
    private StructureAdapter structureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structure);

        imgMasjid = findViewById(R.id.imageMasjid);
        idMasjid = findViewById(R.id.idMasjid);
        nameMasjid = findViewById(R.id.nameMasjid);

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

        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_ID, 0);
        String name = intent.getStringExtra(EXTRA_NAME);
        String img = intent.getStringExtra(EXTRA_IMG);

        idMasjid.setText("" + id);
        nameMasjid.setText(name);

        Glide.with(this)
                .load(img)
                .into(imgMasjid);

        recyclerView = findViewById(R.id.recyleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        structureList = new ArrayList<>();
        getDataStucture();
    }

    public void getDataStucture() {
        final String getID = idMasjid.getText().toString();
        final String URL_READ = "http://192.168.43.69/masqueapp/android/masjid/read_structure.php?id=" + getID;
//        final String URL_READ = "https://evcamp.site/masqueapp/android/masjid/read_structure.php?id=" + getID;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                int intId_struktur = object.getInt("id_struktur");
                                int intId_masjid = object.getInt("id_masjid");
                                String strNama = object.getString("nama_anggota").trim();
                                String strJabatan = object.getString("jabatan").trim();
                                String strTahun = object.getString("tahun_kerja").trim();

                                structureList.add(new Structure(intId_struktur, intId_masjid, strNama,
                                        strJabatan, strTahun));

                            }

                            structureAdapter = new StructureAdapter(StructureActivity.this, structureList);
                            recyclerView.setAdapter(structureAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}