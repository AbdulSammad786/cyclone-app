package com.example.samad786.cyclone.Activities.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.samad786.cyclone.Activities.Adapters.FoodsAdapter;
import com.example.samad786.cyclone.Activities.DataProviers.FoodsDataProvider;
import com.example.samad786.cyclone.R;

import java.util.ArrayList;

/**
 * Created by samad786 on 4/20/2017.
 */
public class DrinkFragment  extends Fragment {
    ListView listview,foodslistview;
    String[] items={"CocCola","Pepsi","Sting","Fanta"};
    ArrayList<FoodsDataProvider> arrayList;
    FoodsAdapter foodsadapter;
    RelativeLayout ordeerlayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drink_fragment, null, false);
        listview=(ListView)view.findViewById(R.id.listview);
        ordeerlayout=(RelativeLayout)view.findViewById(R.id.orderlayout);
        ordeerlayout.setVisibility(View.INVISIBLE);
        foodslistview=(ListView)view.findViewById(R.id.foodslistview);
        foodslistview.setVisibility(View.INVISIBLE);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listview.setVisibility(View.INVISIBLE);
                foodslistview.setVisibility(View.VISIBLE);
                ordeerlayout.setVisibility(View.VISIBLE);
            }
        });
        arrayList=new ArrayList<>();

        for(int i=0;i<10;i++)
        {
            arrayList.add(new FoodsDataProvider("1","232",R.drawable.lb));
        }
        foodsadapter=new FoodsAdapter(getActivity(),arrayList);
        foodslistview.setAdapter(foodsadapter);
        return view;
    }
}
