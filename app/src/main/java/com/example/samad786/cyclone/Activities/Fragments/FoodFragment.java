package com.example.samad786.cyclone.Activities.Fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.samad786.cyclone.Activities.Adapters.FoodsAdapter;
import com.example.samad786.cyclone.Activities.DataProviers.FoodsDataProvider;
import com.example.samad786.cyclone.R;
import java.util.ArrayList;
/**
 * Created by samad786 on 4/20/2017.
 */
public class FoodFragment  extends Fragment {
    ListView foodslistview;
    String[] items={"Breakfast","Smoothies","Salads"};
    ArrayList<FoodsDataProvider> arrayList;
    FoodsAdapter foodsadapter;
    ArrayList<String> id,itemname;
    RelativeLayout ordeerlayout;
    TextView ordernow;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foodframent, null, false);
        ordernow=(TextView)view.findViewById(R.id.ordernow);
        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.containerView,new OrderFragment()).commit();
            }
        });
         ordeerlayout=(RelativeLayout)view.findViewById(R.id.orderlayout);
        foodslistview=(ListView)view.findViewById(R.id.foodslistview);
        arrayList=new ArrayList<>();
        loadFoods();

        return view;
    }
    private void loadFoods()
    {
        for(int i=0;i<10;i++)
        {
            arrayList.add(new FoodsDataProvider("1","232",R.drawable.lb));
        }
        foodsadapter=new FoodsAdapter(getActivity(),arrayList);
        foodslistview.setAdapter(foodsadapter);
    }

}