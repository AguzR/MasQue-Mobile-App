package com.official.mq.masque;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView linkDaftar;
    private EditText phone, password;
    private Button btn_login;

    private static String URL_LOGIN = "http://192.168.43.69/masqueapp/android/authentication/cblogi.php";
//    private static String URL_LOGIN = "https://evcamp.site/masqueapp/android/authentication/cblogi.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        linkDaftar = findViewById(R.id.linkDaftar);
        phone = (EditText) findViewById(R.id.phone);
        btn_login = findViewById(R.id.btn_login);

        String tittle = getColoredSpanned("Belum ada akun?", "#00574B");
        String surtittle = getColoredSpanned("Daftar", "#000000FF");
        linkDaftar.setText(Html.fromHtml(tittle + " " + surtittle));

        linkDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkDaftar = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(linkDaftar);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPhone = phone.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if (mPhone.isEmpty()) {
                    phone.setError("Masukan nomor telepon anda");
                } else if (mPass.isEmpty()) {
                    password.setError("Masukan password anda");
                } else {
                    Login(mPhone, mPass);
                }
            }
        });
    }

    private void Login(final String phone, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu ... ");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){
                                for (int i = 0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String phone = object.getString("phone").trim();
                                    String id = object.getString("id").trim();

//                                    sessionManager.createSession(name, phone, password, id);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("name", name);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "No Telepon atau Password salah", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "No Telepon atau Password salah", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "No Telepon atau Password salah", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}
