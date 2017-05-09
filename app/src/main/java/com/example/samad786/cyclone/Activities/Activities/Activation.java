package com.example.samad786.cyclone.Activities.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.samad786.cyclone.R;

public class Activation extends AppCompatActivity {

    EditText n1,n2,n3,n4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);
        init();
        handleCodeTyping();
    }
    private void init()
    {
        n1=(EditText)findViewById(R.id.n1);
        n2=(EditText)findViewById(R.id.n2);
        n3=(EditText)findViewById(R.id.n3);
        n4=(EditText)findViewById(R.id.n4);
    }
    private void handleCodeTyping()
    {
        n1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (n1.getText().length()==1)
                {
                    n2.setFocusableInTouchMode(true);
                    n2.requestFocus();
                }
            }
        });
        n2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (n2.getText().length()==1)
                {
                    n3.setFocusableInTouchMode(true);
                    n3.requestFocus();
                }
            }
        });
        n3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (n3.getText().length()==1)
                {
                    n4.setFocusableInTouchMode(true);
                    n4.requestFocus();
                }
            }
        });
        n4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (n4.getText().length()==1)
                {
                    authenticateCode();
                }
            }
        });

    }
    private void authenticateCode()
    {
        Log.d("authentication", "authenticateCode: ");
    }
    public void activation(View view)
    {
        finish();
        startActivity(new Intent(this,CodeSuccessfully.class));
    }
}