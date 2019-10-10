package com.official.mq.masque;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textName, textUname;
    private CardView linkPM;
    SessionManager sessionManager;
    String getId;

//    private static String URL_READ = "https://masqueapps.000webhostapp.com/API/users/read_detail.php";
    private static String URL_READ = "http://192.168.43.69/masqueapp/android/users/read_detail.php";
//    private static String URL_READ = "https://evcamp.site/masqueapp/android/users/read_detail.php";
//    private static String URL_EDIT = "https://masqueapps.000webhostapp.com/API/users/edit_detail.php";
//    private static String URL_EDIT = "http://192.168.43.69/masqueapp/android/users/edit_detail.php";
//    private static String URL_EDIT = "https://evcamp.site/masqueapp/android/users/edit_detail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(this);

        textName = findViewById(R.id.textName);
        textUname = findViewById(R.id.textUserN);
        linkPM = findViewById(R.id.cardPm);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Menghapus title default
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Mengambil akses TextView yang ada di dalam Toolbar
        TextView mTitle = (TextView) toolbar.findViewById(R.id.title_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
        String uName = user.get(sessionManager.UNAME);
        String Name = user.get(sessionManager.NAME);

        textName.setText(Name);
        textUname.setText(uName);

        linkPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, PesanMasActivity.class));
            }
        });
    }
}
