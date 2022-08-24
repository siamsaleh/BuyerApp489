package com.example.buyerapp489.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.buyerapp489.R;

public class RegisterActivity extends AppCompatActivity {

    //https://docs.google.com/forms/d/e/1FAIpQLSf_6ud66DKQHQ27dLA0T3hqx5k4rd4e_oNreyU_ubCvEhJxrg/viewform?usp=sf_link

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/siam.saleh.37"));
                startActivity(browserIntent);
            }
        });

        findViewById(R.id.btRegForm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSf_6ud66DKQHQ27dLA0T3hqx5k4rd4e_oNreyU_ubCvEhJxrg/viewform?usp=sf_link"));
                startActivity(browserIntent);
            }
        });

    }
}