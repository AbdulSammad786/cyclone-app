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
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;
    // Alert Dialog Manager
    Dialogs mydialog;
    // Google Places
    GooglePlaces googlePlaces;
    // Places List
    PlaceList nearPlaces;
    // GPS Location
    GPSTracker gps;
    // Button
    Button btnShowOnMap;
    // Progress dialog
    ProgressDialog pDialog;
    // Places Listview
    ListView lv;
    // ListItems data
    ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
    public static String KEY_REFERENCE = "reference"; // id of the place
    public static String KEY_NAME = "restaurant"; // name of the place
    public static String KEY_VICINITY = "vicinity"; // Place area name
    ResturantsListAdapter adapter;
    ArrayList<RestutantsListDataProvider> arraylist;
    ListView listview;
    ArrayList<String> names,id,name,description,logo,titleimage,deliverytime;
    SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_resturants, null, false);
        preferences=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        listview=(ListView)view.findViewById(R.id.listivew);
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
               // new LoadPlaces().execute();
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
        loadServerData("http://www.cyclonedelivery.com/cyclone_app/getRestaurants.php","restaurants");
        return view;
    }
    /**
     * Background Async Task to Load Google places
     * */
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
                String types = "cafe|restaurant"; // Listing places only cafes, restaurants
                // Radius in meters - increase this value if you don't find any places
                double radius = 5000; // 1000 meters
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
                // Successfully got places details
                Log.d("result OK", "onPostExecute: ");
                if (nearPlaces.results != null) {
                    // loop through each place
                    Log.d("Array list not null", "onPostExecute: ");
                    for (Place p : nearPlaces.results) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        // Place reference won't display in listview - it will be hidden
                        // Place reference is used to get "place full details"
                        map.put(KEY_REFERENCE, p.reference);
                        // Place name
                        map.put(KEY_NAME, p.name);
                        Log.d("place name= ", p.name);
                        names.add(p.name);
                        // adding HashMap to ArrayList
                        placesListItems.add(map);
                    }
                    loadServerData("http://www.cyclonedelivery.com/cyclone_app/getRestaurants.php","restaurants");
                }
            } else if (status.equals("ZERO_RESULTS")) {
                // Zero results found
                mydialog.showDialog("Near Places","Sorry no places found. Try to change the types of places");
            } else if (status.equals("UNKNOWN_ERROR")) {
                mydialog.showDialog("Places Error", "Sorry unknown error occured.");
            } else if (status.equals("OVER_QUERY_LIMIT")) {
                mydialog.showDialog("Places Error", "Sorry query limit to google places is reached");
            } else if (status.equals("REQUEST_DENIED")) {
                mydialog.showDialog("Places Error", "Sorry error occured. Request is denied");
            } else if (status.equals("INVALID_REQUEST")) {
                mydialog.showDialog("Places Error", "Sorry error occured. Invalid Request");
            } else {
                mydialog.showDialog("Places Error", "Sorry error occured.");
            }
        }
    }
    private void parseJson(String json)
    {
        try
        {
            JSONObject obj=new JSONObject(json);
            if (obj.getString("success").equalsIgnoreCase("true")) {
                JSONArray data = obj.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject mydata=data.getJSONObject(i);
                  //  for(int j=0;j<names.size();j++) {
                      //  if (names.get(j).equalsIgnoreCase(mydata.getString("name"))) {
                            id.add(mydata.getString("id"));
                            name.add(mydata.getString("name"));
                            deliverytime.add(mydata.getString("delivery_time"));
                            logo.add(mydata.getString("logo_url"));
                            titleimage.add(mydata.getString("title_image_url"));
                            description.add(mydata.getString("description"));
                       // }
                   // }
                }
                if (name.size()>0)
                {
                    loadData();
                }else {
                    mydialog.showDialog("Sorry", "No Restaurants found nearby within 5 km radius");
                }
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
