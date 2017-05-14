package com.example.samad786.cyclone.Activities.Activities;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.samad786.cyclone.Activities.AppController;
import com.example.samad786.cyclone.Activities.Helper.Dialogs;
import com.example.samad786.cyclone.R;
import com.intentfilter.androidpermissions.PermissionManager;

import org.json.JSONObject;

import java.util.HashMap;

import static java.util.Collections.singleton;

public class Login extends AppCompatActivity {
    EditText username,password;
    Dialogs mydialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        mydialog=new Dialogs(this);
    }
    public void register(View view)
    {
     finish();
        startActivity(new Intent(this,Register.class));
    }
    public void login(View view)
    {
        if (username.getText().length()>0&&password.getText().length()>0)
        {
            requestLogin("http://www.cyclonedelivery.com/cyclone_app/login.php",username.getText().toString(),password.getText().toString());
        }
    }
    private void parseJson(String json)
    {
        try{
            JSONObject obj=new JSONObject(json);
            if (obj.getString("success").equalsIgnoreCase("true"))
            {
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this,Home.class));
            }else
            {


                mydialog.showDialog("Error","Invalid Username or Password ! Pleae try again later");
            }

        }catch (Exception ex)
        {
            Log.d("error", "parseJson: ");
        }
    }
    public void requestLogin(final String url,final String username,final  String password) {

        mydialog.showProgress();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse:", response);
                        parseJson(response);
                        mydialog.hideProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.toString());
                        mydialog.hideProgress();
                        mydialog.showDialog("Error","Internal Problem occurred ! Pleae try again later");
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> params = new HashMap<String, String>();
                params.put("username",username );
                params.put("password", password);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(request, "srarequest");
    }
}
