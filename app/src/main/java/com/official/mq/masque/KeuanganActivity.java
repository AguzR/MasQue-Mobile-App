package com.official.mq.masque;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.official.mq.masque.adapter.KeuanganAdapter;
import com.official.mq.masque.model.Keuangan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;
import java.util.Map;

import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_BULAN;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_JUMLAH;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_KPEMASUKAN;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_KPENGELUARAN;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_PEMASUKAN;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_PENGELUARAN;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_SALDO;
import static com.official.mq.masque.adapter.KeuanganAdapter.EXTRA_TAHUN;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_ID;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_IMG;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_NAME;

public class KeuanganActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView nameMasjid, idMas, TglAwal, TglAkhir;
    private ImageView imgMasjid;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;
    private Button btnAwal, btnAkhir, Submit;

    private List<Keuangan> keuanganList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keuangan);

        nameMasjid = findViewById(R.id.nameMasjid);
        idMas = findViewById(R.id.idMasjid);
        imgMasjid= findViewById(R.id.imageMasjid);

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
        String nameMas = intent.getStringExtra(EXTRA_NAME);
        String imgMas = intent.getStringExtra(EXTRA_IMG);

        idMas.setText("" + id);
        idMas.setVisibility(View.GONE);
        nameMasjid.setText(nameMas);

        Glide.with(this)
                .load(imgMas)
                .into(imgMasjid);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        TglAwal = (TextView) findViewById(R.id.tglAwal);
        btnAwal = (Button) findViewById(R.id.Awal);
        btnAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogAwal();
            }
        });

        TglAkhir = (TextView) findViewById(R.id.tglAkhir);
        btnAkhir = (Button) findViewById(R.id.Akhir);
        btnAkhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialogAkhir();
            }
        });

        recyclerView = findViewById(R.id.recyleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        keuanganList = new ArrayList<>();

        Submit = (Button) findViewById(R.id.submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readKeuangan();
            }
        });

    }

    private void showDateDialogAkhir() {

        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TglAkhir.setText(simpleDateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    private void showDateDialogAwal() {

        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TglAwal.setText(simpleDateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void readKeuangan() {
        final String ID = idMas.getText().toString().trim();
        final String tglAw = TglAwal.getText().toString().trim();
        final String tglAk = TglAkhir.getText().toString().trim();
        String URL_READ = "http://192.168.43.69/masqueapp/android/masjid/read_keuangan.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                int intIDK = object.getInt("id_keuangan");
                                int intIDM = object.getInt("id_masjid");
                                String strBln = object.getString("bulan");
                                String strThn = object.getString("tahun");
                                String strSld = object.getString("saldo");
                                String strJns = object.getString("jenis_keuangan");
                                String strDna = object.getString("dana_keuangan");
                                String strKet = object.getString("ket_keuangan");
                                String strJml = object.getString("jumlah");
                                String strAt = object.getString("created_at");

                                keuanganList.add(new Keuangan(intIDK, intIDM, strBln, strThn, strSld, strJns, strDna, strKet,
                                        strJml, strAt));
                            }

                            KeuanganAdapter keuanganAdapter = new KeuanganAdapter(KeuanganActivity.this, keuanganList);
                            recyclerView.setAdapter(keuanganAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", ID);
                params.put("tglawal", tglAw);
                params.put("tglakhir", tglAk);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_masjid, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.menuSearch);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String cari) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String cari) {
//                keuanganAdapter.getFilter().filter(cari);
//                return false;
//            }
//        });
//
//        return true;
//    }

//    @Override
//    public void onItemClick(int position) {
//        Intent detailIntent = new Intent(this, DetailKeuanganActivity.class);
//
//        Keuangan clickedItem = keuanganList.get(position);
//        detailIntent.putExtra(EXTRA_BULAN, clickedItem.getBulan());
//        detailIntent.putExtra(EXTRA_TAHUN, clickedItem.getTahun());
//        detailIntent.putExtra(EXTRA_SALDO, clickedItem.getSaldo());
//        detailIntent.putExtra(EXTRA_PEMASUKAN, clickedItem.getPemasukan());
//        detailIntent.putExtra(EXTRA_KPEMASUKAN, clickedItem.getKet_pemasukan());
//        detailIntent.putExtra(EXTRA_PENGELUARAN, clickedItem.getPengeluaran());
//        detailIntent.putExtra(EXTRA_KPENGELUARAN, clickedItem.getKet_pengeluaran());
//        detailIntent.putExtra(EXTRA_JUMLAH, clickedItem.getJumlah());
//
//        startActivity(detailIntent);
//    }
}
