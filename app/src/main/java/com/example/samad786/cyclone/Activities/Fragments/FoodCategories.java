package com.example.samad786.cyclone.Activities.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.samad786.cyclone.Activities.Adapters.FoodsAdapter;
import com.example.samad786.cyclone.Activities.Adapters.FoodsCategoriesAdapter;
import com.example.samad786.cyclone.Activities.AppController;
import com.example.samad786.cyclone.Activities.DataProviers.FoodsCategories;
import com.example.samad786.cyclone.Activities.DataProviers.FoodsDataProvider;
import com.example.samad786.cyclone.Activities.Helper.Dialogs;
import com.example.samad786.cyclone.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mediapark on 16/05/2017.
 */

public class FoodCategories  extends Fragment {
    FoodsCategoriesAdapter adapter;
    ArrayList<FoodsCategories> araylist;
    ListView listView;
    Dialogs mydiaDialogs;
    SharedPreferences preferences;
    TextView nodatafound;
    ArrayList<String> f_id,f_title;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_categories, null, false);
        nodatafound=(TextView)view.findViewById(R.id.nodatafound);
        nodatafound.setVisibility(View.INVISIBLE);
        mydiaDialogs=new Dialogs(getActivity());
        preferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        listView=(ListView) view.findViewById(R.id.listview);
        araylist=new ArrayList<>();
        f_id=new ArrayList<>();
        f_title=new ArrayList<>();
        loadData("http://www.cyclonedelivery.com/cyclone_app/getFoods.php");
        return view;
    }
    private void loadFoods()
    {
        for(int i=0;i<f_title.size();i++)
        {
            araylist.add(new FoodsCategories(f_id.get(i),f_title.get(i)));
        }
        adapter=new FoodsCategoriesAdapter(getActivity(),araylist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView fid=(TextView)view.findViewById(R.id.id);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("f_id",fid.getText().toString());
                editor.apply();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction transcaction=manager.beginTransaction();
                transcaction.replace(R.id.containerView,new FoodFragment()).commit();
            }
        });
    }
    private void parseJson(String json)
    {
    try
    {
        JSONObject obj=new JSONObject(json);
        if (obj.getString("success").equalsIgnoreCase("true"))
        {
            JSONArray data=obj.getJSONArray("data");
            if (data.length()>0) {
                for (int i = 0; i < data.length(); i++) {
                    JSONObject d = data.getJSONObject(i);
                    f_id.add(d.getString("id"));
                    f_title.add(d.getString("item_name"));
                }
                loadFoods();
            }else {
                nodatafound.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
            }
        }else
        {nodatafound.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Responce Unsuccessfull", Toast.LENGTH_SHORT).show();
        }

    }catch (Exception ex)
    {
        Log.d("error", "parseJson: ");
    }
    }
    public void loadData(final String url) {
        mydiaDialogs.showProgress();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse:", response);
                        parseJson(response);
                        mydiaDialogs.hideProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.toString());
                        mydiaDialogs.hideProgress();
                        nodatafound.setVisibility(View.VISIBLE);
                        mydiaDialogs.showDialog("Error","Internal Problem occurred ! Pleae try again later");
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> params = new HashMap<String, String>();
                params.put("c_id","1");
                params.put("rcl_id",preferences.getString("r_id",""));
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