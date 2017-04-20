package com.example.samad786.cyclone.Activities.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.samad786.cyclone.Activities.DataProviers.RestutantsListDataProvider;
import com.example.samad786.cyclone.R;
import java.util.ArrayList;
/**
 * Created by DELL on 24-08-2016.
 */
public class ResturantsListAdapter extends BaseAdapter {
    Context context;
    ArrayList<RestutantsListDataProvider> serviceManualArrayList;
    LayoutInflater inflater;
    public ResturantsListAdapter(Context context, ArrayList<RestutantsListDataProvider> serviceManualArrayList) {
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
            view = inflater.inflate(R.layout.custom_view_resturantslist, viewGroup, false);
            holder.details = (TextView) view.findViewById(R.id.details);
            holder.namelocation = (TextView) view.findViewById(R.id.nameandlocation);
            holder.time = (TextView) view.findViewById(R.id.time);
            holder.logo = (ImageView) view.findViewById(R.id.logo);
            holder.hotelimage = (ImageView) view.findViewById(R.id.resturantimage);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        RestutantsListDataProvider serviceManual = serviceManualArrayList.get(i);
        return view;
    }
    class Holder {
        TextView id,details,namelocation,time;
        ImageView logo,hotelimage;
    }
}