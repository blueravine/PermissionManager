package com.blueravine.permissionmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.blueravine.permissionmanager.R;

/**
 * Created by blue rabbit on 2018-01-28.
 */

public class SingleItem extends Activity //Klasa sluzy do wyswietlenia wylaczonych procesow
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item);
        TextView app = (TextView)findViewById(R.id.apps);
        String nm = getIntent().getExtras().get("tokill").toString();
        app.setText(nm);

    }

}
