package com.example.samad786.cyclone.Activities.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.samad786.cyclone.R;

public class CodeSuccessfully extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_successfully);
    }
    public void homescreen(View view)
    {
        finish();
        startActivity(new Intent(this,Home.class));
    }
}
