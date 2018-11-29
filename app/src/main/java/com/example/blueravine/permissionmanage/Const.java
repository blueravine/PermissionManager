package com.example.blueravine.permissionmanage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by santhoshG on 27/10/16.
 */
public class Const {

    private static String BASE_URL = "https://interface.blueravine.in/feedback";

    public static final String FeedBack_url = BASE_URL + "/feedback/register";


//    Context context;
//
//    // http://localhost:8080/TaskManagerApp/tasks/useradd
//    public final Typeface montserrat_custom_font = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Montserrat-Regular.otf");
//    ProgressDialog progressDialog;
//
//    public static final class FontHelper {
//
//        private final Map<String, Typeface> TYPEFACES = new HashMap<>();
//
//        public Typeface get(Context context, String fontFileName){
//            Typeface typeface = TYPEFACES.get(fontFileName);
//            if(typeface == null){
//                typeface = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Montserrat-Regular.otf");
//                TYPEFACES.put(fontFileName, typeface);
//            }
//            return typeface;
//        }
//    }

}
