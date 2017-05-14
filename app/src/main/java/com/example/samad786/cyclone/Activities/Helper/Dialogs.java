package com.example.samad786.cyclone.Activities.Helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ABDUL Samad on 5/13/2017.
 */

public class Dialogs {
    Context context;
    AlertDialog.Builder alertDialog;
    ProgressDialog progressDialog;
    public Dialogs(Context context)
    {
        alertDialog=new AlertDialog.Builder(context);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    public  void showProgress()
    {
      //  androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        //        exclude group: 'com.android.support', module: 'support-annotations'
    //})
        progressDialog.show();
    }
    public void hideProgress()
    {
    progressDialog.cancel();
    }
    public  void showDialog(String title,String message)
    {
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }

}
