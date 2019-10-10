package com.official.mq.masque;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.official.mq.masque.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_ID;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_IDU;
import static com.official.mq.masque.adapter.MasjidAdapter.EXTRA_NAME;

public class PesanActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView idUser, idPengguna, nameUsers, idMas, nameMasjid, bFoto, pesan;
    private Button send;
    private ImageView imgPsn;
    SessionManager sessionManager;
    String getId, getName, Pesan;

    Bitmap bitmap, decoded;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    private static final String TAG = PesanActivity.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private String KEY_IMAGE = "image";
    private String KEY_IDPESAN = "id";
    int success;

    String tag_json_obj = "json_obj_req";

    private static String URL_SEND = "http://192.168.43.69/masqueapp/android/masjid/send_pesan.php";
//    private static String URL_SEND = "https://evcamp.site/masqueapp/android/masjid/send_pesan.php";
//    private static String URL_SEND = "http://192.168.43.69/API/upfoto.php";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        
        sessionManager = new SessionManager(this);
//        sessionManager.checkLoginPesan();
        
        idUser = findViewById(R.id.idUsers);
        nameUsers = findViewById(R.id.name);
        idMas = findViewById(R.id.idMasjid);
        nameMasjid = findViewById(R.id.to);
        idPengguna = findViewById(R.id.idPengguna);
        imgPsn = findViewById(R.id.imagePsn);
        bFoto = findViewById(R.id.btn_foto);
        pesan = findViewById(R.id.pesanDev);
        send = findViewById(R.id.btn_send);
        
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

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);
        getName = user.get(sessionManager.NAME);
        
        idPengguna.setText(getId);
        idPengguna.setVisibility(View.GONE);
        nameUsers.setText(getName);
        nameUsers.setVisibility(View.GONE);

        Intent intent = getIntent();
        int getID = intent.getIntExtra(EXTRA_ID, 0);
        int getIDU = intent.getIntExtra(EXTRA_IDU, 0);
        String getNameMas = intent.getStringExtra(EXTRA_NAME);
        idMas.setText("" + getID);
        idMas.setVisibility(View.GONE);
        idUser.setText(""+ getIDU);
        idUser.setVisibility(View.GONE);
        nameMasjid.setText(getNameMas);
        nameMasjid.setVisibility(View.GONE);

        bFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterChooser();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pesan = pesan.getText().toString().trim();
//                String ImageP = imgPsn.getText().toString();

                if (Pesan.isEmpty()) {
                    pesan.setError("Pesan tidak boleh kosong, Masukan pesan anda !");
                }  else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PesanActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("Apakah kamu yakin mengirimkan pesan ini ?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (imgPsn.getDrawable() == null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(PesanActivity.this);
                                builder1.setCancelable(false);
                                builder1.setMessage("Anda belum memasukan foto");
                                builder1.setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                AlertDialog alertDialog = builder1.create();
                                alertDialog.show();
                            } else {
                                sendPesan(Pesan);
                            }
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }
        });
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

    private void showFilterChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void kosong() {
        imgPsn.setImageResource(0);
    }

    private void setToImageView(Bitmap bmp) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        imgPsn.setImageBitmap(decoded);
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void sendPesan(final String Pesan) {
        final String idPengguna = getId;
        final String idUsers = idUser.getText().toString();
        final String From = nameUsers.getText().toString().trim();
        final String idMasjid = idMas.getText().toString();
        final String To = nameMasjid.getText().toString().trim();
//        final ProgressDialog loading = ProgressDialog.show(this, "Mengirim...", "Tunggu...", false, false);
        final ProgressDialog loading = new ProgressDialog(this);
        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SEND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

//                                Toast.makeText(PesanActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                kosong();

                                showCustomDialog();

                            } else {
                                Toast.makeText(PesanActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();

//                        Toast.makeText(PesanActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id_users", idUsers);
                params.put("id_masjid", idMasjid);
                params.put("id_pengguna", idPengguna);
                params.put("dari", From);
                params.put("kepada", To);
                params.put(KEY_IMAGE, getStringImage(decoded));
                params.put("pesan", Pesan);
                Log.e(TAG, "" + params);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_success, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
