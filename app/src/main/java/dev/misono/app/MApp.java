package dev.misono.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import dev.misono.util.PRNGFixes;
import roboguice.RoboGuice;

public class MApp extends Application {

    private static MApp instance;

    public static MApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.setUseAnnotationDatabases(false);
        PRNGFixes.apply();
        instance = this;

//        ActivityLifecycleCallbacks
    }
}
