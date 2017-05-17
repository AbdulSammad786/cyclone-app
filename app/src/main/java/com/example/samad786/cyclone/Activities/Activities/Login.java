package com.example.samad786.cyclone.Activities.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.samad786.cyclone.Activities.AppController;
import com.example.samad786.cyclone.Activities.Helper.Dialogs;
import com.example.samad786.cyclone.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.intentfilter.androidpermissions.PermissionManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.singleton;

public class Login extends AppCompatActivity {
    EditText username,password;
    Dialogs mydialog;
    private CallbackManager callbackManager;
    private ImageView facebook_button;
    ProgressDialog progress;
    private String facebook_id,f_name, m_name, l_name, gender, profile_image, full_name, email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        mydialog=new Dialogs(this);
       String unique_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d(unique_id, "device Id: ");
        init_fb();
    }
    private void init_fb(){

        facebook_button=(ImageView) findViewById(R.id.fb_button);
        progress=new ProgressDialog(Login.this);
        progress.setMessage("Please wait,Facebook is loading for you!");
        progress.setIndeterminate(false);
        progress.setCancelable(false);
        facebook_id=f_name= m_name= l_name= gender= profile_image= full_name= email_id="";

        //for facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //register callback object for facebook result
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               // progress.show();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                    facebook_id=profile.getId();
                    f_name=profile.getFirstName();
                    m_name=profile.getMiddleName();
                    l_name=profile.getLastName();
                    full_name=profile.getName();
                    profile_image=profile.getProfilePictureUri(400, 400).toString();
                }
                //Toast.makeText(FacebookLogin.this,"Wait...",Toast.LENGTH_SHORT).show();
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.d("there", "onCompleted: ");
                                    email_id=object.getString("email");
                                    Toast.makeText(Login.this, "Logged In as: "+email_id, Toast.LENGTH_SHORT).show();
                                   /* gender=object.getString("gender");
                                    String profile_name=object.getString("name");
                                    long fb_id=object.getLong("id"); //use this for logout
                                    //Start new activity or use this info in your project.
                                    Intent i=new Intent(Login.this, Register.class);
                                    i.putExtra("type","facebook");
                                    i.putExtra("facebook_id",facebook_id);
                                    i.putExtra("f_name",f_name);
                                    i.putExtra("m_name",m_name);
                                    i.putExtra("l_name",l_name);
                                    i.putExtra("full_name",full_name);
                                    i.putExtra("profile_image",profile_image);
                                    i.putExtra("email_id",email_id);
                                    i.putExtra("gender",gender);
                                    progress.dismiss();
                                    startActivity(i);
                                    finish();*/
                                   startActivity(new Intent(Login.this,Home.class));
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    //  e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                Toast.makeText(Login.this,"Login cancelled",Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Login.this,"Login error",Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
        //facebook button click
        facebook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile", "user_friends", "email"));
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
