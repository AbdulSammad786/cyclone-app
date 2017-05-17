package com.example.samad786.cyclone.Activities.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.samad786.cyclone.R;

/**
 * Created by samad786 on 4/20/2017.
 */

public class MainMenu  extends Fragment {
    LinearLayout restaurants,liquor;
    SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_fragment, null, false);
        preferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        restaurants=(LinearLayout)view.findViewById(R.id.restaurntslayout);
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("list","restaurant");
                editor.apply();
                FragmentManager  manager=getActivity().getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.containerView,new Resturants_list()).commit();
            }
        });
        liquor=(LinearLayout)view.findViewById(R.id.liquor);
        liquor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("list","liquor_store");
                editor.apply();
                FragmentManager  manager=getActivity().getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.containerView,new Resturants_list()).commit();
            }
        });
        return view;
    }

}
