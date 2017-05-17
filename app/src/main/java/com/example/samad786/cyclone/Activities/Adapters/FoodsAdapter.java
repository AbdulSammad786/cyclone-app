package com.example.samad786.cyclone.Activities.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samad786.cyclone.Activities.DataProviers.FoodsDataProvider;
import com.example.samad786.cyclone.Activities.DataProviers.RestutantsListDataProvider;
import com.example.samad786.cyclone.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
/**
 * Created by DELL on 24-08-2016.
 */
public class FoodsAdapter extends BaseAdapter {
    Context context;
    ArrayList<FoodsDataProvider> serviceManualArrayList;
    LayoutInflater inflater;
    public FoodsAdapter(Context context, ArrayList<FoodsDataProvider> serviceManualArrayList) {
        this.context = context;
        this.serviceManualArrayList = serviceManualArrayList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return serviceManualArrayList.size();
    }
    @Override
    public Object getItem(int i) {
        return i;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            holder = new Holder();
            view = inflater.inflate(R.layout.curtom_view_foods, viewGroup, false);
            holder.logo = (ImageView) view.findViewById(R.id.image);
            holder.price = (TextView) view.findViewById(R.id.price);
            holder.id = (TextView) view.findViewById(R.id.id);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        FoodsDataProvider d = serviceManualArrayList.get(i);

        holder.id.setText(d.getId());
        holder.price.setText(d.getPrice());
        downloadTitlelImage d_image=new downloadTitlelImage(holder.logo);
        d_image.execute(d.getImage());
        return view;
    }
    class Holder {
        TextView id,price;
        ImageView logo;
    }
    private class downloadTitlelImage extends AsyncTask<String,String,Bitmap>
    {
        ImageView userimage;
        private downloadTitlelImage(ImageView userimage)
        {
            this.userimage=userimage;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            java.net.URL url = null;
            Log.d("urk"+params[0], "doInBackground: ");
            Log.i("url"+params[0], "doInBackground: ");
            try {
                url = new java.net.URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("error", "doInBackground: ");
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url
                        .openConnection();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("error", "doInBackground: ");
            }
            connection.setDoInput(true);
            try {
                connection.connect();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("error", "doInBackground: ");
            }
            InputStream input = null;
            try {
                input = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("error", "doInBackground: ");
            }
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            userimage.setImageBitmap(bitmap);
        }
    }
}