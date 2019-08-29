package com.dartmedia.nuansakucing.Nearby;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dartmedia.nuansakucing.APIHelper.BaseAPIService;
import com.dartmedia.nuansakucing.APIHelper.UtilsAPI;
import com.dartmedia.nuansakucing.Adapter.ListViewAdapter;
import com.dartmedia.nuansakucing.LoginActivity;
import com.dartmedia.nuansakucing.Model.Model;
import com.dartmedia.nuansakucing.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends AppCompatActivity implements LocationListener {

    //Map
    IMapController mapController;
    MapView map = null;
    GeoPoint startPoint;
    Marker startMarker;



    //Location
    private ProgressDialog progress;
    LocationManager locationManager;
    private boolean isItStart = true;

    //Lati Longi
    public static double longi;
    public static double lati;


    //Button
    ImageButton btnCenter;




    //JSONParse


    //BottomSheet
    private BottomSheetBehavior mBehavior, mBehave_swipeUp;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    LinearLayout swipeup_sheet;


    //APIService
    BaseAPIService mAPIService;

    // List View
    private ListView listPetshop;
    private List<Model> model_listView = new ArrayList<>();
    private ListViewAdapter adapter_listView;


    //Formater
    DecimalFormat df = new DecimalFormat("0.00");

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LoadingProgress();
        getLocation();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // New Map
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));


        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(true);

        mapController = map.getController();
        mapController.setZoom(18);
        map.setMaxZoomLevel(21.0);
        map.setMinZoomLevel(7.0);

        mapController.setCenter(startPoint);
        startMarker = new Marker(map);
        map.invalidate();
        //End Map Code


        btnCenter = (ImageButton) findViewById(R.id.btn_centerMap);
        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapController.setCenter(startPoint);
                mapController.setZoom(20.0);
            }
        });


        //Toolbar
        Toolbar t = findViewById(R.id.ToolbarNearby);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //API SERVICE
        mAPIService = UtilsAPI.getAPIService();
//        APIGETCheck();

        //Swipe Up
        swipeup_sheet = (LinearLayout) findViewById(R.id.bottom_sheet_main); //swipeup_sheet
        mBehave_swipeUp = BottomSheetBehavior.from(swipeup_sheet); //swipeup_sheet


        //BottomSheet
        bottom_sheet = findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


    }

    //Toolbar section
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //API Section
    private void APIGETCheck(){

        Log.i("debug", "onResponse: masukAPI");
       mAPIService.getPetshop().enqueue(new Callback<ResponseBody>() {

           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if (response.isSuccessful()){
                   Log.i("debug", "onResponse:BERHASIL " + response.body().toString());
                   try {
                       JSONObject jsonRESULTS = new JSONObject(response.body().string());
                       JSONArray jsonArray = jsonRESULTS.getJSONArray("dataPet");
                       Log.i("debug", "onResponse: masuk"+jsonRESULTS);
                        if ((jsonRESULTS.getString("status").equals("true"))){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject dataPetshopJSON = jsonArray.getJSONObject(i);
                                String own = dataPetshopJSON.getString("owner");
                                double longi = dataPetshopJSON.getDouble("longitude");
                                double latit = dataPetshopJSON.getDouble("latitude");
                                String petshopName = dataPetshopJSON.getString("petshop_name");
                                String notel = dataPetshopJSON.getString("noTelp");
                                String photo = dataPetshopJSON.getString("photo");
                                GeoPoint GPJson = new GeoPoint(latit, longi);
                                distanceMeasure(latit,longi);
                                Log.i("debug", "onResponse: masuk beada " + longi + latit + own + notel + photo);
//                                if (distance < 10000) {
                                    addMarker(GPJson, petshopName, own, notel);
                                    populateView(petshopName, own, GPJson,photo);
//                                }




                            }
                            progress.dismiss();
                            Log.i("debug", "onResponse: Success");
                        }else{
                            progress.dismiss();
                            Log.i("debug", "onResponse: error");
                            String error_message = jsonRESULTS.getString("error_msg");
                            Toast.makeText(MapsActivity.this, error_message, Toast.LENGTH_SHORT).show();
                        }
                   } catch (JSONException e) {
                       progress.dismiss();
                       Log.i("debug", "onResponse: Json " + e);
                       e.printStackTrace();
                   } catch (IOException e) {
                       progress.dismiss();
                       Log.i("debug", "onResponse: IO" + e);
                       e.printStackTrace();
                   }
               }else{
                   progress.dismiss();
                   Log.i("debug", "onResponse: No Connection");
                   Intent dir = new Intent(MapsActivity.this,LoginActivity.class);
                   startActivity(dir);
                   Toast.makeText(MapsActivity.this, "No Connection or Server Close", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
               progress.dismiss();
               Log.i("debug", "onFailure: ERROR > " + t.getMessage());
               Intent dir = new Intent(MapsActivity.this,LoginActivity.class);
               startActivity(dir);
               Toast.makeText(MapsActivity.this, "Fail to get Connection", Toast.LENGTH_SHORT).show();
           }
       });
    }



    //Listview
    private void  populateView(String nPetsho, String owne, final GeoPoint GPCard, String photo){

        Log.i("debug", "populateView: "+nPetsho+" "+owne);
        listPetshop = (ListView) findViewById(R.id.listView_BS);
        distanceMeasure(GPCard.getLatitude(),GPCard.getLongitude());
        model_listView.add(new Model(photo,nPetsho,owne,String.valueOf(df.format(distance)),GPCard));

        adapter_listView = new ListViewAdapter(this,R.layout.list_item_nearby,model_listView);
        listPetshop.setAdapter(adapter_listView);
        checkSwipeDown();

    }

    private void  populateView(String nPetsho, String owne, final GeoPoint GPCard){

        Log.i("debug", "populateView: "+nPetsho+" "+owne);
        listPetshop = (ListView) findViewById(R.id.listView_BS);
        distanceMeasure(GPCard.getLatitude(),GPCard.getLongitude());
        model_listView.add(new Model(nPetsho,owne,String.valueOf(df.format(distance)),GPCard));

        adapter_listView = new ListViewAdapter(this,R.layout.list_item_nearby,model_listView);
        listPetshop.setAdapter(adapter_listView);
        checkSwipeDown();

    }


    //DistanceMeasure
    public double distance = 0;
    private void distanceMeasure(double latM, double longM){
        double latA = -6.197821;
        double lngA = 106.761363;

        Location crntLocation = new Location("crntlocation");
        crntLocation.setLatitude(lati);
        crntLocation.setLongitude(longi);

        Location newLocation = new Location("newlocation");
        newLocation.setLatitude(latM);
        newLocation.setLongitude(longM);

        distance = crntLocation.distanceTo(newLocation) / 1000; // in km
    }



    public void addMarker(final GeoPoint GP, final String Title, final String owne, final String notelp) {
        final Marker Mark = new Marker(map);
        Mark.setPosition(GP);

        Mark.setIcon(getResources().getDrawable(R.drawable.ic_edit2));
        Mark.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        Mark.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                mBehave_swipeUp.setState(BottomSheetBehavior.STATE_COLLAPSED);
                showBottomSheetDialog(GP, Title, owne, notelp);

                return true;
            }
        });
        map.getOverlays().add(Mark);
    }
    public void onResume() {
        super.onResume();
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }
    public void onPause() {
        super.onPause();

        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    //Bottomsheet dialog
    private void showBottomSheetDialog(final GeoPoint longlat, final String petshopName, String own, String notelp) {

        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            Toast.makeText(MapsActivity.this, "Expanded",Toast.LENGTH_LONG).show();
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }


        final View view = getLayoutInflater().inflate(R.layout.sheet_layout, null);
        Button btDir = (Button) view.findViewById(R.id.directionBtn);
        Button btnShare = (Button) view.findViewById(R.id.shareBtn);
        TextView shopName = view.findViewById(R.id.PetshopNameL);
        TextView alamat = view.findViewById(R.id.alamatL);
        TextView notel = view.findViewById(R.id.noHp);
        TextView kilometer = view.findViewById(R.id.angkaKM);

        distanceMeasure(longlat.getLatitude(),longlat.getLongitude());
        DecimalFormat df = new DecimalFormat("0.00");
        shopName.setText(petshopName);
        alamat.setText(own);
        notel.setText(notelp);
        kilometer.setText(String.valueOf(df.format(distance)));

        btDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                double distance;
                Toast.makeText(MapsActivity.this, "Jarak  " + distance + " KM",Toast.LENGTH_LONG).show();
                System.out.println(longlat);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + longlat.getLatitude() + "," + longlat.getLongitude() + ""));
//
                startActivity(intent);
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "*"+ petshopName+"*"+ "\n" + "http://maps.google.com/maps?daddr=" + longlat.getLatitude() + "," + longlat.getLongitude() + "");
//                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Application of social rating share with your friend");
                try {
                    Objects.requireNonNull(view.getContext()).startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
                }
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(view);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }


    //check for 1st listview
    public boolean listIsAtTop()   {
        if(listPetshop.getChildCount() == 0) return true;
        return (listPetshop.getChildAt(0).getTop() == 0 && listPetshop.getFirstVisiblePosition() ==0);
    }

    //Check when swiping up
    public void checkSwipeDown(){
        final TextView swipeText = (TextView) findViewById(R.id.textSwipeUp);
        final View line =(View) findViewById(R.id.line_swipe);
        mBehave_swipeUp.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (i == BottomSheetBehavior.STATE_DRAGGING && !listIsAtTop()){
                    mBehave_swipeUp.setState(BottomSheetBehavior.STATE_EXPANDED);
                    btnCenter.setVisibility(View.GONE);
                }else if (i==BottomSheetBehavior.STATE_EXPANDED){
                    swipeText.setText("Nearby Petshop");
                    line.setVisibility(View.INVISIBLE);
                    btnCenter.setVisibility(View.GONE);
                } else if(i==BottomSheetBehavior.STATE_HIDDEN || i==BottomSheetBehavior.STATE_COLLAPSED){
                    swipeText.setText("Swipe Up for Nearby Petshop");
                    line.setVisibility(View.VISIBLE);
                    btnCenter.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

















    //Loading Section
    private void LoadingProgress(){
        progress = new ProgressDialog(this);
        progress.setMessage("Loading maps..");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        longi = location.getLongitude();
        lati = location.getLatitude();
        if (isItStart){
            NewLocation();
            APIGETCheck();
//            progress.dismiss();

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider!" + provider,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MapsActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    private void NewLocation() {
        isItStart = false;
        startPoint = new GeoPoint(lati, longi);
        mapController.setCenter(startPoint);

        startMarker.setPosition(startPoint);
        map.getOverlays().add(startMarker);
        startMarker.setIcon(getResources().getDrawable(R.drawable.ic_location));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startPoint = new GeoPoint(lati, longi);
                startMarker.setPosition(startPoint);
                map.getOverlays().add(startMarker);
                startMarker.setIcon(getResources().getDrawable(R.drawable.ic_location));
                map.invalidate();
                handler.postDelayed(this, 2000);
            }
        }, 500);  //the time is in miliseconds
    }

}
