package com.example.samad786.cyclone.Activities.Fragments;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v4.widget.TextViewCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.samad786.cyclone.Activities.Adapters.ResturantsListAdapter;
import com.example.samad786.cyclone.Activities.AppController;
import com.example.samad786.cyclone.Activities.DataProviers.RestutantsListDataProvider;
import com.example.samad786.cyclone.Activities.Helper.ConnectionDetector;
import com.example.samad786.cyclone.Activities.Helper.Dialogs;
import com.example.samad786.cyclone.Activities.Helper.GPSTracker;
import com.example.samad786.cyclone.Activities.Helper.GooglePlaces;
import com.example.samad786.cyclone.Activities.Helper.Place;
import com.example.samad786.cyclone.Activities.Helper.PlaceList;
import com.example.samad786.cyclone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by samad786 on 4/19/2017.
 */
public class Resturants_list extends Fragment {
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Dialogs mydialog;
    GooglePlaces googlePlaces;
    PlaceList nearPlaces;
    GPSTracker gps;
    ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
    public static String KEY_REFERENCE = "reference";
    public static String KEY_NAME = "restaurant";
    public static String KEY_VICINITY = "vicinity";
    ResturantsListAdapter adapter;
    ArrayList<RestutantsListDataProvider> arraylist;
    ListView listview;
    TextView nodatafound;
    ArrayList<String> names,id,name,description,logo,titleimage,deliverytime;
    SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_resturants, null, false);
        preferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        listview=(ListView)view.findViewById(R.id.listivew);
        nodatafound=(TextView)view.findViewById(R.id.nodatafound);
        names=new ArrayList<>();
        id=new ArrayList<>();
        name=new ArrayList<>();
        logo=new ArrayList<>();
        description=new ArrayList<>();
        titleimage=new ArrayList<>();
        deliverytime=new ArrayList<>();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView r_id=(TextView)view.findViewById(R.id.id);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("r_id",r_id.getText().toString());
                editor.apply();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.containerView,new ResturantMenu()).commit();
            }
        });
        mydialog=new Dialogs(getActivity());
        arraylist=new ArrayList<>();
        //starting locations search on google maps

        cd = new ConnectionDetector(getActivity());

        // Check if Internet present
        isInternetPresent = cd.isConnectedToInternet();
        if (isInternetPresent) {
// creating GPS Class object
            gps = new GPSTracker(getActivity());

            // check if GPS location can get
            if (gps.canGetLocation()) {
                Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
                new LoadPlaces().execute();
            } else {
                // Can't get user's current location
                mydialog.showDialog("GPS Status",
                        "Couldn't get location information. Please enable GPS");
            }
        }else {
            // Internet Connection is not present
            mydialog.showDialog("Error","Internet Connect Problem");
            // stop executing code by return
        }
        return view;
    }
    class LoadPlaces extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            // creating Places class object
            googlePlaces = new GooglePlaces();
            try {
                // Separeate your place types by PIPE symbol "|"
                // If you want all types places make it as null
                // Check list of types supported by google
                //
                String types = preferences.getString("list",""); // Listing places only cafes, restaurants
                // Radius in meters - increase this value if you don't find any places
                double radius = 8000; // 1000 meters
                // get nearest places
                nearPlaces = googlePlaces.search(gps.getLatitude(),
                        gps.getLongitude(), radius, types);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        /**
         * After completing background task Dismiss the progress dialog
         * and show the data in UI
         * Always use runOnUiThread(new Runnable()) to update UI from background
         * thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            /**
             * Updating parsed Places into LISTVIEW
             * */
            // Get json response status
            String status = nearPlaces.status;
            // Check for all possible status
            if (status.equals("OK")) {
                if (nearPlaces.results != null)
                {
                    nodatafound.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "results="+nearPlaces.results.size(), Toast.LENGTH_SHORT).show();
                    for (Place p : nearPlaces.results) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_REFERENCE, p.reference);
                        map.put(KEY_NAME, p.name);
                        names.add(p.name);
                        Log.d(p.name, "Restaurant: ");
                        placesListItems.add(map);
                    }
                    loadServerData("http://www.cyclonedelivery.com/cyclone_app/getRestaurants.php","restaurants");
                }else
                {
                    nodatafound.setVisibility(View.VISIBLE);
                }
            } else if (status.equals("ZERO_RESULTS")) {
                // Zero results found
                nodatafound.setVisibility(View.VISIBLE);
                mydialog.showDialog("Near Places","Sorry no places found. Try to change the types of places");
            } else if (status.equals("UNKNOWN_ERROR")) {
                nodatafound.setVisibility(View.VISIBLE);
                mydialog.showDialog("Places Error", "Sorry unknown error occured.");
            } else if (status.equals("OVER_QUERY_LIMIT")) {
                nodatafound.setVisibility(View.VISIBLE);
                mydialog.showDialog("Places Error", "Sorry query limit to google places is reached");
            } else if (status.equals("REQUEST_DENIED")) {
                nodatafound.setVisibility(View.VISIBLE);
                mydialog.showDialog("Places Error", "Sorry error occured. Request is denied");
            } else if (status.equals("INVALID_REQUEST")) {
                nodatafound.setVisibility(View.VISIBLE);
                mydialog.showDialog("Places Error", "Sorry error occured. Invalid Request");
            } else {
                nodatafound.setVisibility(View.VISIBLE);
                mydialog.showDialog("Places Error", "Sorry error occured.");
            }
        }
    }
    private void parseJson(String json) {
        try
        {
            JSONObject obj=new JSONObject(json);
            if (obj.getString("success").equalsIgnoreCase("true"))
            {
                JSONArray data = obj.getJSONArray("data");
                for (int i = 0; i < data.length(); i++)
                {
                    JSONObject mydata=data.getJSONObject(i);
                    for(int j=0;j<names.size();j++)
                    {
                        if (names.get(j).equalsIgnoreCase(mydata.getString("name"))) {
                            id.add(mydata.getString("id"));
                            name.add(mydata.getString("name"));
                            deliverytime.add(mydata.getString("delivery_time"));
                            logo.add(mydata.getString("logo_url"));
                            titleimage.add(mydata.getString("title_image_url"));
                            description.add(mydata.getString("description"));
                        }
                    }
                }
                if (name.size()>0)
                {
                    loadData();
                }else {
                    mydialog.showDialog("Sorry", "No Restaurants found nearby within 5 km radius");
                    nodatafound.setVisibility(View.VISIBLE);
                }
            }else
            {
                nodatafound.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex)
        {
            Log.d("error", "parseJson: ");
        }
    }
    public void loadServerData(final String url,final String data_id) {
        mydialog.showProgress();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse:", response);
                        parseJson(response);
                        mydialog.hideProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error",error.toString());
                        mydialog.hideProgress();
                        nodatafound.setVisibility(View.VISIBLE);
                        mydialog.showDialog("Error","Internal Problem occurred ! Pleae try again later");
                    }
                }) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(request, "srarequest");
    }
    private void loadData(){
        for(int i=0;i<name.size();i++)
        {
            arraylist.add(new RestutantsListDataProvider(id.get(i),name.get(i),description.get(i),deliverytime.get(i),logo.get(i),titleimage.get(i)));
        }
        adapter=new ResturantsListAdapter(getActivity(),arraylist);
        listview.setAdapter(adapter);
    }
}
