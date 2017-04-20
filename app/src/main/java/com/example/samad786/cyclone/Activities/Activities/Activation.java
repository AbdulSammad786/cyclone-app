package com.example.samad786.cyclone.Activities.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.samad786.cyclone.R;

public class Activation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
    }
    public void activation(View view)
    {
        finish();
        startActivity(new Intent(this,CodeSuccessfully.class));
    }
}
