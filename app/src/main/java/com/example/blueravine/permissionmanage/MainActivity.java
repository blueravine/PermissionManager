package com.example.blueravine.permissionmanage;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.SearchManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.content.Intent;
import android.content.Context;
import android.content.pm.*;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Process;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.fabric.sdk.android.Fabric;
import com.crashlytics.android.Crashlytics;


import static android.app.ActivityManager.MOVE_TASK_WITH_HOME;
import static android.os.Process.myPid;
import static com.example.blueravine.permissionmanage.PermissionAdapter.appinstall;
//import static pm.ad.permissionmanager.ExecuteAsRootBase.canRunRootCommands;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> AppName = new ArrayList<>();
    public  static ArrayList<PackageInfo> PackageName = new ArrayList<PackageInfo>();
    ListView list;
//    CardView cardview;
    String[] dang;
    Switch simpleSwitchdev,simpleSwitchdev1,simpleSwitchdev2,simpleSwitchdev3;
    Button disable;
    List<ActivityManager.RunningAppProcessInfo> activityes;
    ArrayList<Integer> ToKill = new ArrayList<>();
    public static ArrayList<PackageVal> PackageValList = new ArrayList<>();

    public static TextView emptyTextUpcoming,emptyTextCompleted;
    PermissionAdapter permissionsAdapter;
    public static String lastSearchRequest;
    private RelativeLayout relativeLayout;
    private TextView Enable_Gps,Disable_Gps;
    private GoogleApiClient googleApiClient;
    Method dataMtd;
    final static int REQUEST_LOCATION = 199;
    TextView mEmptyView;
    Context context;
    String TAG;
    boolean mobileDataEnabled;
    TelephonyManager telephonyService;
    private final int REQUEST_ENABLE_BT = 1;
    private static final int  MY_PREMISSIONS_REQUEST_MODIFY_PHONE_STATE = 0;
    SearchView searchView;
    EditText editText ;
    Button ButtonOK;
    Button ButtonCancel;
    Dialog dialog;
    public int mState = 0; //at the top of the code
    int count;
//where you want to trigger the hide action
    // to hide or mState = 0; to show

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

        // set the custom dialog components - text, image and button
         editText = (EditText) findViewById(R.id.editText);
         ButtonOK = (Button) findViewById(R.id.ButtonOK);
        ButtonCancel = (Button) findViewById(R.id.ButtonCancel);
//        ButtonOK.setText("Android custom dialog example!");
        dang = getResources().getStringArray(R.array.dangarray); //String board with permissive permissions based on: https://developer.android.com/guide/topics/permissions/requesting.html#normal-dangerous
        list = (ListView) findViewById(R.id.listView1); //we set our application list
//        cardview = (CardView) findViewById(R.id.card_view);
//        simpleSwitchdev = (Switch) findViewById(R.id.SwitchLocdev);
//        simpleSwitchdev1 = (Switch) findViewById(R.id.SwitchCamdev);
//        simpleSwitchdev2 = (Switch) findViewById(R.id.SwitchMicdev);
//        simpleSwitchdev3 = (Switch) findViewById(R.id.SwitchCalldev);
//        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);



//        disable = (Button) findViewById(R.id.button4);
        PermissionsReturn();    //we run functions to search for permits
        permissionsAdapter = new PermissionAdapter(this, PackageValList); //object of our adapter
        list.setAdapter(permissionsAdapter);
//        count++;
//        invalidateOptionsMenu();
//        Collections.sort(AppName);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() //nacisniecie na element listy
//        {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
//            {
//                Intent in = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + PackageName.get(i).packageName.toString()));//uruchamiamy aktywnosc prowadzaca do ustawien danej aplikacji
//                in.addCategory(Intent.CATEGORY_DEFAULT);
//                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(in);
//
//
//            }
//
//
//        });
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() //nacisniecie na element listy
//        {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
//            {
//                Intent in = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + PackageName.get(i).packageName.toString()));//uruchamiamy aktywnosc prowadzaca do ustawien danej aplikacji
//                in.addCategory(Intent.CATEGORY_DEFAULT);
//                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(in);
//
//
//            }
//
//
//        });

//        mState = 0;
//        invalidateOptionsMenu();
//        handletoggleclicks();




    }

//    public void handletoggleclicks(){
//
//        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        final LocationManager loc = (LocationManager) MainActivity.this.getSystemService( Context.LOCATION_SERVICE );
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//                    simpleSwitchdev.setChecked(true);
//
//
//                }
//                else{
//                    simpleSwitchdev.setChecked(false);
//                }
//            }
//        }, 200);
//
//        // Capture ToggleButton clicks
//        @SuppressLint("WifiManagerLeak")
//        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//        Handler handlerwifi = new Handler();
//        handlerwifi.postDelayed(new Runnable() {
//            public void run() {
//        if(wifiManager.isWifiEnabled()){
//            simpleSwitchdev1.setChecked(true);
//        }
//        else
//        {
//            simpleSwitchdev1.setChecked(false);
//        }
//            }
//        }, 200);
//        Handler handlerbluetooth = new Handler();
//        handlerbluetooth.postDelayed(new Runnable() {
//            public void run() {
//        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//
//        if(mBluetoothAdapter.isEnabled()){
//            simpleSwitchdev2.setChecked(true);
//        }
//        else
//        {
//            simpleSwitchdev2.setChecked(false);
//        }
//    }
//}, 200);
//        mobileDataEnabled = getMobileDataState();
//        Handler handlerdata = new Handler();
//        handlerdata.postDelayed(new Runnable() {
//            public void run() {
//        if (mobileDataEnabled)
//        {
////                setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
//            simpleSwitchdev3.setChecked(true);
//        }
//        else {
//            simpleSwitchdev3.setChecked(false);
//        }
//        }
//        }, 200);
//        simpleSwitchdev.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                if (simpleSwitchdev.isChecked()) {
//                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(MainActivity.this)) {
//
//                        Snackbar snackbar = Snackbar
//                                .make(relativeLayout, "Gps enabled", Snackbar.LENGTH_LONG);
//
//                        snackbar.show();
////                        Toast.makeText(MainActivity.this,"Gps not enabled",Toast.LENGTH_SHORT).show();
//                        enableLoc();
//                    }else{
//                        Snackbar snackbar = Snackbar
//                                .make(relativeLayout, "Gps already enabled", Snackbar.LENGTH_LONG);
//
//                        snackbar.show();
////                        Toast.makeText(MainActivity.this,"Gps already enabled",Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                else if( loc.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER ) )
//                {
//
//                    Snackbar snackbar = Snackbar
//                            .make(relativeLayout, "Please disable locatiion/GPS in the following screen and navigate back ", Snackbar.LENGTH_LONG)
//                            .setAction("OK", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
//                                    startActivity(myIntent);
//                                    Snackbar snackbar = Snackbar
//                                            .make(relativeLayout, "GPS off", Snackbar.LENGTH_LONG);
//
//                                    snackbar.show();
//                                }
//                            });
//                    // Changing message text color
//                    snackbar.setActionTextColor(Color.RED);
//
//// Changing action button text color
//                    View sbView = snackbar.getView();
//                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//                    textView.setTextColor(Color.YELLOW);
//                    snackbar.show();
//
//                }
//
//            }
//
//        });
//
//        simpleSwitchdev.setChecked(false);
//
////        WifiManager to control the Wifi Service
//
//        simpleSwitchdev1.setOnClickListener(new View.OnClickListener() {
//            //                @SuppressLint("WifiManagerLeak")
////                final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//            @Override
//            public void onClick(View view) {
////
//                if (simpleSwitchdev1.isChecked()) {
//                    // Switch On Wifi
//                    wifiManager.setWifiEnabled(true);
//                    Snackbar snackbar = Snackbar
//                            .make(relativeLayout, "Wifi Turned ON", Snackbar.LENGTH_LONG);
//
//                    snackbar.show();
//                }
//                else {
//                    // Switch Off Wifi
//                    wifiManager.setWifiEnabled(false);
//                    Snackbar snackbar = Snackbar
//                            .make(relativeLayout, "Wifi Turned OFF", Snackbar.LENGTH_LONG);
//
//                    snackbar.show();
//                }
//            }
//        });
////        simpleSwitchdev1.setChecked(false);
//
//
//        simpleSwitchdev2.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
////
//                if (simpleSwitchdev2.isChecked()) {
//                    // Switch On bluetooth
//                    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
//                    adapter.enable();
//                    Snackbar snackbar = Snackbar
//                            .make(relativeLayout, "Bluetooth ON", Snackbar.LENGTH_LONG);
//
//                    snackbar.show();
//                }
//                else {
//                    // Switch Off bluetooth
//                    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
//                    adapter.disable();
//                    Snackbar snackbar = Snackbar
//                            .make(relativeLayout, "Bluetooth OFF", Snackbar.LENGTH_LONG);
//
//                    snackbar.show();
//                }
//            }
//        });
////        simpleSwitchdev2.setChecked(false);
////        }
//
//        simpleSwitchdev3.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("NewApi")
//            @Override
//            public void onClick(View view) {
////                if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.MODIFY_PHONE_STATE)==PackageManager.PERMISSION_GRANTED){
//                if (simpleSwitchdev3.isChecked()) {
//
////                        setMobileDataState(true);
////                    Intent intent = new Intent();
////                    intent.setComponent(new ComponentName("com.android.settings","com.android.settings.Settings$DataUsageSummaryActivity"));
//////                        intent.addCategory(Intent.CATEGORY_DEFAULT);
////                    startActivity(intent);
//
//                    Snackbar snackbar = Snackbar
//                            .make(relativeLayout, "Please enable 'Cellular Data' or 'Mobile Data' in the following screen and navigate back ", Snackbar.LENGTH_LONG)
//                            .setAction("OK", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent = new Intent();
//                                    intent.setComponent(new ComponentName("com.android.settings","com.android.settings.Settings$DataUsageSummaryActivity"));
////                        intent.addCategory(Intent.CATEGORY_DEFAULT);
//                                    startActivity(intent);
//                                    Snackbar snackbar = Snackbar
//                                            .make(relativeLayout, "Mobile Data turned ON", Snackbar.LENGTH_LONG);
//
//                                    snackbar.show();
//                                }
//                            });
//                    // Changing message text color
//                    snackbar.setActionTextColor(Color.RED);
//
//// Changing action button text color
//                    View sbView = snackbar.getView();
//                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//                    textView.setTextColor(Color.YELLOW);
//                    snackbar.show();
//                }
//                else {
//
////                        setMobileDataState(false);
////                    Intent intent = new Intent();
////                    intent.setComponent(new ComponentName("com.android.settings","com.android.settings.Settings$DataUsageSummaryActivity"));
//////                        intent.addCategory(Intent.CATEGORY_DEFAULT);
////                    startActivity(intent);
//                    Snackbar snackbar = Snackbar
//                            .make(relativeLayout, "Please disable 'Cellular Data' or 'Mobile Data' in the following screen and navigate back ", Snackbar.LENGTH_LONG)
//                            .setAction("OK", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent = new Intent();
//                                    intent.setComponent(new ComponentName("com.android.settings","com.android.settings.Settings$DataUsageSummaryActivity"));
////                        intent.addCategory(Intent.CATEGORY_DEFAULT);
//                                    startActivity(intent);
//                                    Snackbar snackbar = Snackbar
//                                            .make(relativeLayout, "Mobile Data turned OFF", Snackbar.LENGTH_LONG);
//
//                                    snackbar.show();
//                                }
//                            });
//                    // Changing message text color
//                    snackbar.setActionTextColor(Color.RED);
//
//// Changing action button text color
//                    View sbView = snackbar.getView();
//                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//                    textView.setTextColor(Color.YELLOW);
//                    snackbar.show();
//
//                }
//
//
//
//            }
//        });
//    }
    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(MainActivity.this, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
        list.setEmptyView(mEmptyView);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_CANCELED: {
                        // The user was asked to change settings, but chose not to
                        finish();
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu to add items to action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
//        menu.removeItem(R.id.share_icon);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setQueryHint(getString(R.string.search_hint));

        MenuItem feedbackitem = menu.findItem(R.id.feedback);
//        MenuItem itemSearch = menu.findItem(R.id.search);
        final MenuItem itemSetting = menu.findItem(R.id.filter);
//        if (count % 2 == 0)
//        {
//            itemSetting.setVisible(false);
//        }
//        else
//        {
//            itemSetting.setVisible(true);
//        }

//        searchView.setSearchableInfo(searchManager.getSearchableInfo(
//                new ComponentName(this, MainActivity.class)));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                permissionsAdapter.getFilter().filter("Q#$".concat(query));
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                permissionsAdapter.getFilter().filter("Q#$".concat(newText));
//                itemSetting.setVisible(false);
//                invalidateOptionsMenu();

                return true;
            }


        });

        feedbackitem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // custom dialog
                 dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.custom);
                dialog.setTitle("FeedBack Form...");
                dialog.show();

//                ButtonCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
//                // if button is clicked, close the custom dialog
//                dialogButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//            }
//        });
                return false;
            }
        });
//        if (mState == 1) {//1 is true, 0 is false
//            invalidateOptionsMenu();
//            menu.getItem(2).setVisible(false);
//    }
//        this.invalidateOptionsMenu();
//        MenuItem item = menu.findItem(R.id.filter);
//        if (item != null){
//            item.setVisible(false);
//        }
//        else {
//            item.setVisible(true);
//        }
//searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//    @Override
//    public boolean onClose() {
//        AllAppsList();
//        return false;
//    }
//});
//        searchView.setOnKeyListener(new TextView.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL))) {
//
//                }
//                return false;
//            }
//        });







        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        this.invalidateOptionsMenu();
//        MenuItem item = menu.findItem(R.id.filter);
//        item.setVisible(false);
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        searchView.setQuery("",false);
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_allapps:
                // search action
                AllAppsList();
                return true;
            case R.id.action_loc:
                // search action
                AllAppsList();
                LocationList();
                return true;
            case R.id.action_cam:
                AllAppsList();
                CameraList();
                return true;
            case R.id.action_mic:
                AllAppsList();
                MicList();
                // refresh
                return true;
            case R.id.action_conts:
                AllAppsList();
                ContactsList();
                // help action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu){
//
//        if (Utils.checkNetworkStatus(MainActivity.this)) {
//            menu.findItem(R.id.filter).setVisible(true);
////            menu.findItem(R.id.delete).setVisible(true);
//        }else {
//            menu.findItem(R.id.filter).setVisible(false);
////            menu.findItem(R.id.delete).setVisible(false);
//        }
//        return true;
//    }
    public void setMobileDataState(boolean mobileDataEnabled)
    {

                try
        {
            TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Method setMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            if (null != setMobileDataEnabledMethod)
            {
                setMobileDataEnabledMethod.invoke(telephonyService, mobileDataEnabled);
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Network " +mobileDataEnabled, Snackbar.LENGTH_LONG);

                snackbar.show();
            }
        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error setting mobile data state", ex);
        }
    }
    public boolean getMobileDataState()
    {
        try
        {
            TelephonyManager telephonyService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            Method getMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod)
            {
                boolean mobileDataEnabled = (Boolean) getMobileDataEnabledMethod.invoke(telephonyService);
                return mobileDataEnabled;
            }
        }
        catch (Exception ex)
        { Log.e(TAG, "Error getting mobile data state", ex);
        }
        return false;
    }
    /**
     * Launching new activity
     * */
    private void AllAppsList() {
        permissionsAdapter = new PermissionAdapter(this, PackageValList); //object of our adapter
        list.setAdapter(permissionsAdapter);

    }

    private void LocationList() {
            permissionsAdapter.getFilter().filter("L#$");

    }
    private void CameraList() {
        permissionsAdapter.getFilter().filter("C#$");

    }
    private void MicList() {
        permissionsAdapter.getFilter().filter("M#$");

    }
    private void ContactsList() {
        permissionsAdapter.getFilter().filter("P#$");

    }

    /*
A method that checks whether an application is a system application. We reject such a walk and consider it safe */
    private static boolean isThisASystemPackage(PackageInfo pkgInfo)
    {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1;
    }


    protected void PermissionsReturn()
    {
        PackageManager pm = this.getPackageManager();
        List<PackageInfo> appinstall = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS); //Thanks to the GET_PERMISSIONS flag, we collect information about permissions in these packages.
//        Collections.sort(appinstall, new ApplicationInfo.DisplayNameComparator(pm));
//        Collections.sort(appinstall, new Comparator<PackageInfo>(){
//            public int compare(PackageInfo o1, PackageInfo o2) {
//                return o1.packageName.compareTo(o2.packageName);
//            }
//        });
                PackageValList.clear();
                PackageName.clear();
        for (PackageInfo pInfo : appinstall)
        {

            if (!(isThisASystemPackage(pInfo))) //We check if it is a system application
            {
                String[] reqPermission = pInfo.requestedPermissions; //We download to the table all permissions requested by this application

                if (!(reqPermission == (null)))
                {
                    PackageVal packageVal = new PackageVal();
                    PackageName.add(pInfo); //We add to packages lists that will help us to "kill" the application

                    for (int i = 0; i < reqPermission.length; i++)
                    {
                        for (int j = 0; j < dang.length; j++)
                        {
                            if ((reqPermission[i].compareTo(dang[j])) == 0) ;
                            {
                                Log.d("Installed Applications", pInfo.applicationInfo.loadLabel(pm).toString());
                                Log.d("packegename", pInfo.packageName.toString());
                                Log.d("permission list", " " + dang[j]);
                                packageVal.name = pInfo.applicationInfo.loadLabel(pm).toString();
                                packageVal.packageName = pInfo.packageName.toString();
                                packageVal.permissions.add(reqPermission[i]);


                            }
                        }


                    }
                    PackageValList.add(packageVal);
                }

            }
        }

        Collections.sort(PackageValList, new Comparator<PackageVal>(){
            public int compare(PackageVal o1, PackageVal o2) {
                return o1.name.compareToIgnoreCase(o2.name);
            }
        });

    }


//    protected void killProcesses(ArrayList<Integer> toKill)
//    {
//
//        int mypid = myPid();
//        for (int iCnt = 0; iCnt < AppName.size(); iCnt++)
//        {
//
//            //android.os.Process.sendSignal(toKill.get(iCnt), Process.SIGNAL_KILL);
//            // android.os.Process.killProcess(toKill.get(iCnt));
//            ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Activity.ACTIVITY_SERVICE);
//            manager.killBackgroundProcesses(AppName.get(iCnt));
//
//        }
//
//    }
//    protected ArrayList<Integer> comparator()
//    {
//        ActivityManager manager =  (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
//        activityes = manager.getRunningAppProcesses();
//        System.out.println(activityes.size());
//        System.out.println(PackageName.size());
//
//        for( int i = 1; i < activityes.size() - 1 ; i++)
//            for( int j = 0; j < PackageName.size() - 1; j++)
//            {
//
//                if( PackageName.get(j).packageName.compareTo((activityes.get(i).processName)) == 0)
//                {
//                    ToKill.add(activityes.get(i).pid);
//                    AppName.add(PackageName.get(j).packageName);
////                    Collections.sort(AppName);
////                    {
////                        @Override
////                        public int compare(String text1, String text2)
////                        {
////                            return text1.compareToIgnoreCase(text2);
////                        }
////                    };
//                }
//            }
//        return ToKill;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

    }
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            list.setAdapter(permissionsAdapter);
//            handletoggleclicks();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
//        permissionsAdapter = new PermissionAdapter(this, PackageValList); //object of our adapter
        list.setAdapter(permissionsAdapter);
//        handletoggleclicks();
//        Collections.sort(AppName);
    }


}