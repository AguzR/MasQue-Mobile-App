package com.official.mq.masque;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView name, uname;
    private TextView VerifyId, PhoneNumber, Uname, WarningName, WarningUname;

    private static int REQUEST_CODE = 99;
    private String UsernamePath = "[a-z0-9]";
    public static final String USERNAME = "uname";

    private SessionManager sessionManager;

//    private static String URL_REGIST = "https://masqueapps.000webhostapp.com/API/authentication/register.php";
    private static String URL_REGIST = "http://192.168.43.69/masqueapp/android/authentication/register.php";
//    private static String URL_REGIST = "https://evcamp.site/masqueapp/android/authentication/cbregist.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sessionManager = new SessionManager(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.title_bar);
        toolbar.setNavigationIcon(R.drawable.ic_back_dark);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        name = (TextView) findViewById(R.id.name);
        uname = (TextView) findViewById(R.id.uname);
        WarningName = (TextView) findViewById(R.id.warningname);
        WarningUname = (TextView) findViewById(R.id.warninguname);
        WarningName.setVisibility(View.INVISIBLE);
        WarningUname.setVisibility(View.INVISIBLE);
        Button Lanjut = (Button) findViewById(R.id.lanjut);
        Lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().trim();
                String Uname = uname.getText().toString().trim();

                if (Name.isEmpty()) {
                    name.setError("Tidak boleh kosong !");
                } else if (Name.length() >= 50) {
                    name.setError("Maksimal 50 karakter !");
                } else if (Uname.isEmpty()) {
                    uname.setError("Username tidak boleh kosong !");
                } else if (!Uname.matches(UsernamePath)) {
                    uname.setError("Username tidak sesuai, Cth: namalengkap");
                } else {
                    Regist(Name, Uname);
                }
            }
        });

        Uname = (TextView) findViewById(R.id.unameget);
        VerifyId = (TextView) findViewById(R.id.verify_id);
        PhoneNumber = (TextView) findViewById(R.id.phone);
        Uname.setVisibility(View.INVISIBLE);
        VerifyId.setVisibility(View.INVISIBLE);
        PhoneNumber.setVisibility(View.INVISIBLE);
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

    private void Regist(final String Name, final String Uname) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu ... ");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
//                                finish();
                                startLoginPage();
//                                Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setCancelable(false);
                                builder.setMessage(jsonObject.getString("message"));
                                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
//                                Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
//                            Toast.makeText(RegisterActivity.this, "Daftar Gagal, perikasa jaringan atau mungkin nomor sudah terdaftar", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
//                        Toast.makeText(RegisterActivity.this, "Daftar Gagal, perikasa jaringan atau mungkin nomor sudah terdaftar", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", Name);
                params.put("uname", Uname);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void startLoginPage() {
        Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (result.getError() != null) {
                Toast.makeText(this, ""+ result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
                return;
            } else if (result.wasCancelled()) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if(result.getAccessToken() != null)

                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(Account account) {
                            VerifyId.setText(String.format("" + account.getId()));
                            PhoneNumber.setText(String.format("" + account.getPhoneNumber() == null ? ""
                                    :account.getPhoneNumber().toString()));

                            upData();
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {

                        }
                    });
            }
        }
    }

    private void upData() {
        final String userName = uname.getText().toString().trim();
        final String idVerif = VerifyId.getText().toString().trim();
        final String phoneNum = PhoneNumber.getText().toString().trim();

        final String URL_UP = "http://192.168.43.69/masqueapp/android/authentication/up.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                iN();
                            } else {
                                Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(RegisterActivity.this, .getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(RegisterActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uname", userName);
                params.put("verify_id", idVerif);
                params.put("phone", phoneNum);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(stringRequest);
    }

    private void iN() {
        final String iNphone = PhoneNumber.getText().toString().trim();
        final String URL_IN = "http://192.168.43.69/masqueapp/android/authentication/login.php";

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Tunggu ...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_IN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {
                                for (int i = 0; i<jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String user_id = object.getString("user_id").trim();
                                    String name = object.getString("name").trim();
                                    String uname = object.getString("uname").trim();
                                    String phone = object.getString("phone").trim();

                                    sessionManager.createSession(name, uname, phone, user_id);

                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.putExtra("name", name);
                                    startActivity(intent);
                                    finish();
                                }
                            }
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
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", iNphone);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
