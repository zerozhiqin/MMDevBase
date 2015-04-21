package dev.misono.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by zhiqin on 2015/4/20.
 */
public class AndroidSettingUtils {

    public static boolean isNetworkEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isGpsOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean openGpsSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException ex) {
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                context.startActivity(intent);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }


    public static void call(Context act, String tel) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
        act.startActivity(intent);
    }
}
