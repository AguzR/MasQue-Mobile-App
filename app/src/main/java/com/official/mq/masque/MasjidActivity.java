package com.official.mq.masque;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.official.mq.masque.adapter.MasjidAdapter;
import com.official.mq.masque.model.Masjid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_ALAMAT;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_DES;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_ID;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_IDU;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_IMG;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_LATLONG;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_LUAS;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_NAME;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_NOTLP;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_SEJ;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_STATUS;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_THN;

public class MasjidActivity extends AppCompatActivity implements MasjidAdapter.OnItemClickListener{

    private Toolbar toolbar;
    private static final String URL_MASJID = "http://192.168.43.69/masqueapp/android/masjid/read_masjid.php";
//    private static final String URL_MASJID = "https://evcamp.site/masqueapp/android/masjid/read_masjid.php";
    List<Masjid> masjidList;
    RecyclerView recyclerView;
    MasjidAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masjid);

        recyclerView = findViewById(R.id.recyleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        masjidList = new ArrayList<>();
        loadMasjid();

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView)findViewById(R.id.title_bar);
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
                adapter.getFilter().filter(cari);
                return false;
            }
        });
    }

    private void loadMasjid() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_MASJID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject masjid = array.getJSONObject(i);
                                int idMasjid = masjid.getInt("id_masjid");
                                int idUsers = masjid.getInt("id_users");
                                String activated = masjid.getString("activated_masjid");
                                String nameMas = masjid.getString("nama_masjid");
                                String tahunMas = masjid.getString("tahun_berdiri_masjid");
                                String alamatMas = masjid.getString("alamat_masjid");
                                String jenisMas = masjid.getString("jenis_masjid");
                                String StatusT = masjid.getString("status_tanah");
                                String desMas = masjid.getString("deskripsi_masjid");
                                String strSejarah = masjid.getString("sejarah_masjid");
                                String noMas = masjid.getString("nomor_telepon_masjid");
                                String imgMas = masjid.getString("image_masjid");
                                String latlong = masjid.getString("latlong");

                                masjidList.add(new Masjid(idMasjid, idUsers, activated, nameMas, tahunMas, alamatMas,
                                        jenisMas, StatusT, desMas, strSejarah, noMas, imgMas, latlong));
                            }
                            adapter = new MasjidAdapter(MasjidActivity.this, masjidList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(MasjidActivity.this);
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
//                adapter.getFilter().filter(cari);
//                return false;
//            }
//        });
//        return true;
//    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailMasjidActivity.class);

        Masjid clickedItem = masjidList.get(position);
        detailIntent.putExtra(EXTRA_ID, clickedItem.getId_masjid());
        detailIntent.putExtra(EXTRA_IDU, clickedItem.getId_users());
        detailIntent.putExtra(EXTRA_IMG, clickedItem.getImage_masjid());
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getNama_masjid());
        detailIntent.putExtra(EXTRA_THN, clickedItem.getTahun_berdiri_masjid());
        detailIntent.putExtra(EXTRA_ALAMAT, clickedItem.getAlamat_masjid());
        detailIntent.putExtra(EXTRA_LUAS, clickedItem.getJenis_masjid());
        detailIntent.putExtra(EXTRA_STATUS, clickedItem.getStatus_tanah());
        detailIntent.putExtra(EXTRA_DES, clickedItem.getDeskripsi_masjid());
        detailIntent.putExtra(EXTRA_SEJ, clickedItem.getSejarah_masjid());
        detailIntent.putExtra(EXTRA_NOTLP, clickedItem.getNomor_telepon_masjid());
        detailIntent.putExtra(EXTRA_LATLONG, clickedItem.getLatlong());

        startActivity(detailIntent);
    }
}
