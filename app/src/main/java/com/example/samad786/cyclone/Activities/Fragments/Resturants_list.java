package com.example.samad786.cyclone.Activities.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.samad786.cyclone.Activities.Adapters.ResturantsListAdapter;
import com.example.samad786.cyclone.Activities.DataProviers.RestutantsListDataProvider;
import com.example.samad786.cyclone.R;

import java.util.ArrayList;

/**
 * Created by samad786 on 4/19/2017.
 */

public class Resturants_list extends Fragment {

    ResturantsListAdapter adapter;
    ArrayList<RestutantsListDataProvider> arraylist;
    ListView listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_resturants, null, false);
        listview=(ListView)view.findViewById(R.id.listivew);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.containerView,new ResturantMenu()).commit();
            }
        });
        arraylist=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            arraylist.add(new RestutantsListDataProvider("1","name and locaion","details","30",R.drawable.rone,R.drawable.rthree));
        }
        adapter=new ResturantsListAdapter(getActivity(),arraylist);
        listview.setAdapter(adapter);
        return view;
    }

}
