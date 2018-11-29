package com.blueravine.permissionmanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueravine.permissionmanager.R;
import com.google.android.gms.location.LocationListener;

import java.util.ArrayList;

import static com.blueravine.permissionmanager.MainActivity.PackageValList;


/**
 * Created by blue rabbit on 2018-01-27.
 */

public class PermissionAdapter extends ArrayAdapter<PackageVal> implements Filterable //Adapter sluzy do stworzenia naszej listy
{
    private Activity context ;
    private AppFilter appFilter;
    public PackageManager packageManager=getContext().getPackageManager();
    public static ArrayList<PackageVal> appinstall;
    public static TextView simpleSwitch,simpleSwitch1,simpleSwitch2,simpleSwitch3;
    LocationListener locationListener;
    private static CardView cardview;
    private static final int  PERMISSIONS_REQUEST_COARSE_LOCATION =0;
    public static PermissionListener permissionListener;
    MainActivity activity = new MainActivity();
//    Button simpleSwitch;
    public PermissionAdapter(Activity context, ArrayList<PackageVal> appinstall) //Konstruktor
    {
        super(context, R.layout.row, appinstall);
        this.context = context;
        //this.permissions = permissions;
        this.appinstall = appinstall;

        locationListener = null;

//        getFilter();
    }
    @Override
    public int getCount() {
        return appinstall.size();
    }

    /**
     * Get specific item from user list
     * @param i item index
     * @return list item
     */
    @Override
    public PackageVal getItem(int i) {
        return appinstall.get(i);
    }

    /**
     * Get user list item id
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }
    @SuppressLint({"MissingPermission", "NewApi"})
    public View getView(final int position, View convertView, ViewGroup parent) //Funkcja ustawiajaca pojedynczy element
    {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.row, null, true);
        simpleSwitch = (TextView) rowView.findViewById(R.id.SwitchLoc);
        simpleSwitch1 = (TextView) rowView.findViewById(R.id.SwitchCam);
        simpleSwitch2 = (TextView) rowView.findViewById(R.id.SwitchMic);
        simpleSwitch3 = (TextView) rowView.findViewById(R.id.SwitchCall);
        cardview = (CardView) rowView.findViewById(R.id.card_view);
        final RelativeLayout relativeLayout = (RelativeLayout) rowView.findViewById(R.id.rowlayout);
        TextView title = (TextView) rowView.findViewById(R.id.Title);
//        TextView row = (TextView) rowView.findViewById(R.id.Row);
        title.setText(appinstall.get(position).name); //Nazwa aplikacji



//        row.setText((appinstall.get(position).permissions.get(1) + "..."));
        cardview.setOnClickListener(new AdapterView.OnClickListener() //nacisniecie na element listy
        {
            @Override
            public void onClick(View view) {
//                Snackbar snackbar = Snackbar
//                        .make(relativeLayout, "app postion " +appinstall.get(position).packageName.toString(), Snackbar.LENGTH_LONG);
//
//                snackbar.show();
                Intent in = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.parse("package:" + appinstall.get(position).packageName.toString());
                in.setData(uri);
                in.addCategory(Intent.CATEGORY_DEFAULT);
//                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(in);

//                appinstall.get(1).packageName.contains(lastSearchRequest);
//                Log.e("Permission ", String.valueOf(appinstall.get(1).packageName.contains(lastSearchRequest)));
            }


        });

        PermissionCheck(position);

        return rowView;
    }

////     Filter Class
//    public void filter(String text) {
//
//        ArrayList<PackageVal> tempList = new ArrayList<PackageVal>();
//
//        // search content in friend list
//        for (PackageVal appnames : appinstall) {
//            if (appnames.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
//                tempList.add(appnames);
//            }
//        }
//
//        filterResults.count = tempList.size();
//        filterResults.values = tempList;
//    }




    @Override
    public Filter getFilter() {
        if (appFilter == null) {
            appFilter = new AppFilter();
        }

        return appFilter;
    }
    private class AppFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>=3) {
                ArrayList<PackageVal> tempList = new ArrayList<PackageVal>();

                switch(constraint.charAt(0)){
                    case 'Q':

                // search content in PackageVal list
                        for (PackageVal appnames : PackageValList) {
                            if (appnames.getName().toLowerCase().contains(constraint.subSequence(3,constraint.length()).toString().toLowerCase())) {
                                tempList.add(appnames);
                            }
                        }

                        filterResults.count = tempList.size();
                        filterResults.values = tempList;
                    break;

                    case 'L':

                        // search content in friend list
                        for (PackageVal appnames : PackageValList) {
                            if (appnames.getLocationpermis()) {
                                tempList.add(appnames);
                            }
                        }

                        filterResults.count = tempList.size();
                        filterResults.values = tempList;
                        break;

                    case 'C':

                        // search content in friend list
                        for (PackageVal appnames : PackageValList) {
                            if (appnames.getCamerapermis()) {
                                tempList.add(appnames);
                            }
                        }

                        filterResults.count = tempList.size();
                        filterResults.values = tempList;
                        break;

                    case 'M':

                        // search content in friend list
                        for (PackageVal appnames : PackageValList) {
                            if (appnames.getMicpermis()) {
                                tempList.add(appnames);
                            }
                        }

                        filterResults.count = tempList.size();
                        filterResults.values = tempList;
                        break;

                    case 'P':

                        // search content in friend list
                        for (PackageVal appnames : PackageValList) {
                            if (appnames.getContactspermis()) {
                                tempList.add(appnames);
                            }
                        }

                        filterResults.count = tempList.size();
                        filterResults.values = tempList;
                        break;

                    default:
                        break;
                }
            } else {
                filterResults.count = PackageValList.size();
                filterResults.values = PackageValList;
            }

            return filterResults;
            }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            appinstall = (ArrayList<PackageVal>) results.values;
            notifyDataSetChanged();
        }
    }


    public  void PermissionCheck(int position){
//    final PackageManager packageManager=getContext().getPackageManager();
    if((packageManager.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, appinstall.get(position).packageName)==PackageManager.PERMISSION_GRANTED)
            ||(packageManager.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, appinstall.get(position).packageName)==PackageManager.PERMISSION_GRANTED)){
//
        simpleSwitch.setText("Granted");
        simpleSwitch.setTextColor(Color.parseColor("#2e8d3d"));
        appinstall.get(position).setLocationpermis(true);


    }else
    {
        simpleSwitch.setText("Denied");
        simpleSwitch.setTextColor(Color.parseColor("#a3a3a3"));
        appinstall.get(position).setLocationpermis(false);
    }

    if ((packageManager.checkPermission(Manifest.permission.CAMERA, appinstall.get(position).packageName)==PackageManager.PERMISSION_GRANTED)){

        simpleSwitch1.setText("Granted");
        simpleSwitch1.setTextColor(Color.parseColor("#2e8d3d"));
        appinstall.get(position).setCamerapermis(true);

    }else
    {
        simpleSwitch1.setText("Denied");
        simpleSwitch1.setTextColor(Color.parseColor("#a3a3a3"));
        appinstall.get(position).setCamerapermis(false);
    }

    if ((packageManager.checkPermission(Manifest.permission.RECORD_AUDIO, appinstall.get(position).packageName)==PackageManager.PERMISSION_GRANTED)){

        simpleSwitch2.setText("Granted");
        simpleSwitch2.setTextColor(Color.parseColor("#2e8d3d"));
        appinstall.get(position).setMicpermis(true);

    }
    else
    {
        simpleSwitch2.setText("Denied");
        simpleSwitch2.setTextColor(Color.parseColor("#a3a3a3"));
        appinstall.get(position).setMicpermis(false);
    }

    if ((packageManager.checkPermission(Manifest.permission.READ_CONTACTS, appinstall.get(position).packageName)==PackageManager.PERMISSION_GRANTED)
            ||(packageManager.checkPermission(Manifest.permission.WRITE_CONTACTS, appinstall.get(position).packageName)==PackageManager.PERMISSION_GRANTED)
            ||(packageManager.checkPermission(Manifest.permission.GET_ACCOUNTS, appinstall.get(position).packageName)==PackageManager.PERMISSION_GRANTED)){

        simpleSwitch3.setText("Granted");
        simpleSwitch3.setTextColor(Color.parseColor("#2e8d3d"));
        appinstall.get(position).setContactspermis(true);
    }
    else
    {
        simpleSwitch3.setText("Denied");
        simpleSwitch3.setTextColor(Color.parseColor("#a3a3a3"));
        appinstall.get(position).setContactspermis(false);

    }
}

// //check if is 6.0 or higher
//    public boolean isMNC_Or_Higher(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            return true;
//        }
//        return false;
//    }
}

