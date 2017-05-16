package com.example.samad786.cyclone.Activities.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samad786.cyclone.Activities.DataProviers.FoodsCategories;
import com.example.samad786.cyclone.Activities.DataProviers.FoodsDataProvider;
import com.example.samad786.cyclone.Activities.DataProviers.RestutantsListDataProvider;
import com.example.samad786.cyclone.R;
import java.util.ArrayList;
/**
 * Created by DELL on 24-08-2016.
 */
public class FoodsCategoriesAdapter extends BaseAdapter {
    Context context;
    ArrayList<FoodsCategories> serviceManualArrayList;
    LayoutInflater inflater;
    public FoodsCategoriesAdapter(Context context, ArrayList<FoodsCategories> serviceManualArrayList) {
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
            view = inflater.inflate(R.layout.customvieew_foodscategories, viewGroup, false);
            holder.id = (TextView) view.findViewById(R.id.id);
            holder.title = (TextView) view.findViewById(R.id.title);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        FoodsCategories serviceManual = serviceManualArrayList.get(i);
        return view;
    }
    class Holder {
        TextView id,title;
    }
}