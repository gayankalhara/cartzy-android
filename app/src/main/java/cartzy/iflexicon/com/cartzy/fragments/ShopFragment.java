package cartzy.iflexicon.com.cartzy.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cartzy.iflexicon.com.cartzy.Adapters.ShopsAdapter;
import cartzy.iflexicon.com.cartzy.DownloadUrl;
import cartzy.iflexicon.com.cartzy.Models.Shops;
import cartzy.iflexicon.com.cartzy.R;
import cartzy.iflexicon.com.cartzy.util.MyItemDecoration;
import cartzy.iflexicon.com.cartzy.util.RecyclerItemClickListener;


public class ShopFragment extends Fragment  implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    //private DatabaseReference mDatabase;
    private View view;
    String googlePlacesData;
    String url;

    private RecyclerView recyclerview;
    Context context;
    private ShopsAdapter sadpter;
    ArrayList<Shops> ShopList = new ArrayList<Shops>();
    //private OnFragmentInteractionListener mListener;
    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 5000;
    GoogleApiClient mGoogleApiClient;
    Location Location;


    LocationRequest mLocationRequest;

    public ShopFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerView2);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setHasFixedSize(true);
        recyclerview.addItemDecoration(new MyItemDecoration());
        // touch code
        recyclerview.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //ShopsList.get(position).getName().toString();
                        //Toast.makeText(getContext(), ShopList.get(position).getName(), Toast.LENGTH_SHORT).show();
                        //ShopDetailFragment ldf = new ShopDetailFragment();
                        Bundle args = new Bundle();
                        args.putString("ShopName",ShopList.get(position).getName());
                        args.putString("ShopVicinity", ShopList.get(position).getVicinity());
                        args.putDouble("ShopLat",ShopList.get(position).getLatitude());
                        args.putDouble("ShopLong",ShopList.get(position).getLongitude());
                        args.putString("ShopisOpen",ShopList.get(position).getIsOpen());
                        args.putString("ShopPhotoRef",ShopList.get(position).getPhotoRef());
                        args.putString("ShopID",ShopList.get(position).getId());

                        ShopDetailFragment nextFrag= new ShopDetailFragment();
                        nextFrag.setArguments(args);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_content, nextFrag)
                                .addToBackStack(null)
                                .commit();

                    }
                })
        );






        //--Swipe code
        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        // sadpter.removeAt(viewHolder.getAdapterPosition());
                        // sadpter.notifyItemRemoved(viewHolder.getAdapterPosition());


                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerview);
        //Swipe Code


        getAllShops getShops = new getAllShops();


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
        }
        else {
            Log.d("onCreate","Google Play Services available.");
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();

            }
        }
        else {
            buildGoogleApiClient();
        }
        String GoogleShop = "store";
        //String url = getUrl(latitude, longitude, GoogleShop);

        String url = getUrl(6.9146818, 79.9711401, GoogleShop);


        Object[] DataTransfer = new Object[2];
        DataTransfer[0] = null;
        DataTransfer[1] = url;



        if(isNetworkAvailable(getActivity()))
        {
            getShops.execute(DataTransfer);
        }
        else
        {
            TextView tv1 = (TextView) view.findViewById(R.id.no_internet);
            tv1.setText("No Internet Connection");
        }

        return view;
    }
    public boolean isNetworkAvailable(Context ctx)
    {
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()&& cm.getActiveNetworkInfo().isAvailable()&& cm.getActiveNetworkInfo().isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private  class getAllShops extends AsyncTask<Object, String, String> {


        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.d("GetNearbyPlacesData", "doInBackground entered");
                url = (String) params[1];
                DownloadUrl downloadUrl = new DownloadUrl();
                googlePlacesData = downloadUrl.readUrl(url);
                Log.d("GooglePlacesReadTask", "doInBackground Exit");
            } catch (Exception e) {
                Log.d("GooglePlacesReadTask", e.toString());
            }
            return googlePlacesData;
        }
        @Override
        protected void onPostExecute(String result) {
            try {
                //JSONArray jArray = new JSONArray(result);
                //JSONArray jArrayinside = new JSONArray(result);

                JSONObject jsonRootObject = new JSONObject(result);
                JSONArray places = jsonRootObject.getJSONArray("results");

                for(int i=0; i < places.length(); i++) {
                    Shops shop = new Shops();
                    JSONObject place = places.getJSONObject(i);
                    Log.d("Shops Found", place.toString());
                    //JSONObject jObject = jArray.getJSONObject(i);



                    String name = place.getString("name");
                    String icon = place.getString("icon");
                    String vicinity = place.getString("vicinity");
                    String isOpen = "";
                    try {
                        isOpen = place.getJSONObject("opening_hours").getString("open_now");
                    }
                    catch (JSONException e){

                    }

                    //if(isOpen!=null)
                    // isOpen = place.getJSONObject("opening_hours").getString("open_now");
                    double lat1 = Double.parseDouble(place.getJSONObject("geometry").getJSONObject("location").getString("lat"));
                    double long1 = Double.parseDouble(place.getJSONObject("geometry").getJSONObject("location").getString("lng"));
                    //String photoRef = place.getJSONArray("photos").getString("photo_reference");
                    String photoRef = null;
                    try {
                        photoRef = place.getJSONArray("photos").getJSONObject(0).getString("photo_reference");
                    }
                    catch (JSONException e){

                    }
                    String id = place.getString("place_id");

                    shop.setName(name);
                    shop.setIcon(icon);
                    shop.setVicinity(vicinity);
                    shop.setIsOpen(isOpen);
                    shop.setLatitude(lat1);
                    shop.setLongitude(long1);
                    shop.setPhotoRef(photoRef);
                    shop.setId(id);

                    ShopList.add(shop);
                    populateList();

                } // End Loop

            } catch (JSONException e) {
                Log.e("JSONException", "Error: " + e.toString());
            } // catch (JSONException e)
        }
        private void populateList() {
            sadpter = new ShopsAdapter(getContext(),ShopList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerview.setLayoutManager(mLayoutManager);
            recyclerview.setItemAnimator(new DefaultItemAnimator());
            recyclerview.setAdapter(sadpter);
        }
    }





    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    private String getUrl(double latitude, double longitude, String nearbyPlace) {


        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }
    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getContext());
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        0).show();
            }
            return false;
        }
        return true;
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        this.Location = location;


        latitude = location.getLatitude();
        longitude = location.getLongitude();

    }



}
