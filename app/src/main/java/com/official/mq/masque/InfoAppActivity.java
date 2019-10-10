package com.official.mq.masque;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class InfoAppActivity extends AppCompatActivity {

    private TextView Mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_app);

        Mail = findViewById(R.id.mail);

        Mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"masqueapps.official@gmail.com"});

                try {
                    startActivity(Intent.createChooser(intent, "Ingin Mengirim Email ?"));
                } catch (android.content.ActivityNotFoundException ex) {

                }
            }
        });
    }
}
