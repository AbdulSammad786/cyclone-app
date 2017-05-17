package com.example.samad786.cyclone.Activities.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.samad786.cyclone.Activities.AppController;
import com.example.samad786.cyclone.Activities.DataProviers.FoodsDataProvider;
import com.example.samad786.cyclone.Activities.Helper.Dialogs;
import com.example.samad786.cyclone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by samad786 on 4/20/2017.
 */
public class FoodFragment  extends Fragment {
    ListView foodslistview;
    String[] items={"Breakfast","Smoothies","Salads"};
    ArrayList<FoodsDataProvider> arrayList;
    FoodsAdapter foodsadapter;
    ArrayList<String> fi_id,image_url,price;
    RelativeLayout ordeerlayout;
    TextView ordernow;
    Dialogs mydialogs;
    SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.foodframent, null, false);
        preferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        fi_id=new ArrayList<>();
        image_url=new ArrayList<>();
        price=new ArrayList<>();
        mydialogs=new Dialogs(getActivity());
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
        loadData("http://www.cyclonedelivery.com/cyclone_app/getFoodItems.php");
        return view;
    }

    private void loadFoods() {
        for(int i=0;i<fi_id.size();i++)
        {
            arrayList.add(new FoodsDataProvider(fi_id.get(i),price.get(i),image_url.get(i)));
        }
        foodsadapter=new FoodsAdapter(getActivity(),arrayList);
        foodslistview.setAdapter(foodsadapter);
    }
    private void parseJson(String  json)
    {
        try
        {
            JSONObject obj=new JSONObject(json);
            if (obj.getString("success").equalsIgnoreCase("true"))
            {
                JSONArray data=obj.getJSONArray("data");
                if (data.length()>0)
                {
                    for(int i=0;i<data.length();i++)
                    {
                     JSONObject d=data.getJSONObject(i);
                        fi_id.add(d.getString("id"));
                        image_url.add(d.getString("image"));
                        price.add(d.getString("price"));
                    }
                    loadFoods();
                }else
                {
                    Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }else
            {
                Toast.makeText(getActivity(), "Request Unsuccessfull", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex)
        {
            Log.d("error", "parseJson: ");
        }
    }
    public void loadData(final String url) {
        mydialogs.showProgress();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse:", response);
                        parseJson(response);
                        mydialogs.hideProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.toString());
                        mydialogs.hideProgress();
                        mydialogs.showDialog("Error","Internal Problem occurred ! Pleae try again later");
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> params = new HashMap<String, String>();
                params.put("f_id",preferences.getString("f_id",""));
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