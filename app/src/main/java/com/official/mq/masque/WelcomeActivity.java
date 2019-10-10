package com.official.mq.masque;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class WelcomeActivity extends AppCompatActivity {

    private TextView PhoneNumber;
    private static int REQUEST_CODE = 99;
    private SessionManager sessionManager;
    private Menu action;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Menghapus title default
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sessionManager = new SessionManager(this);

        Button Masuk = (Button) findViewById(R.id.masuk);
        Button Daftar = (Button) findViewById(R.id.daftar);

        Masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage();
            }
        });

        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
            }
        });

        PhoneNumber = (TextView) findViewById(R.id.phone);
        PhoneNumber.setVisibility(View.INVISIBLE);
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
                            PhoneNumber.setText(String.format("" + account.getPhoneNumber() == null ? ""
                                    :account.getPhoneNumber().toString()));

                            signIn();
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {

                        }
                    });
            }
        }
    }

    private void signIn() {
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

                                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                                    intent.putExtra("name", name);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                progressDialog.dismiss();
                                AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
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
}
