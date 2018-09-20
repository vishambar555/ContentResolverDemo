package com.example.vishambar.contentresolverdemo2018.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.vishambar.contentresolverdemo2018.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadWithThread(View view) {
        startActivity(new Intent(this, LoadUsingThreadActivity.class));
    }

    public void loadWithLoader(View view) {
        startActivity(new Intent(this, LoadUsingLoaderActivity.class));
    }
}
