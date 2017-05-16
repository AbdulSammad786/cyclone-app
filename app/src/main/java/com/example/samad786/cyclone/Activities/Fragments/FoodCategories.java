package com.example.samad786.cyclone.Activities.Fragments;

import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.samad786.cyclone.Activities.Adapters.FoodsAdapter;
import com.example.samad786.cyclone.Activities.Adapters.FoodsCategoriesAdapter;
import com.example.samad786.cyclone.Activities.DataProviers.FoodsCategories;
import com.example.samad786.cyclone.Activities.DataProviers.FoodsDataProvider;
import com.example.samad786.cyclone.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by mediapark on 16/05/2017.
 */

public class FoodCategories  extends Fragment {
    FoodsCategoriesAdapter adapter;
    ArrayList<FoodsCategories> araylist;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_categories, null, false);
        listView=(ListView) view.findViewById(R.id.listview);
        araylist=new ArrayList<>();
        loadFoods();
        return view;
    }
    private void loadFoods()
    {
        for(int i=0;i<10;i++)
        {
            araylist.add(new FoodsCategories("1","Breakfast"));
        }
        adapter=new FoodsCategoriesAdapter(getActivity(),araylist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction transcaction=manager.beginTransaction();
                transcaction.replace(R.id.containerView,new FoodFragment()).commit();
            }
        });
    }
}