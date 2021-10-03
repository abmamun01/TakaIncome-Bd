package com.nooneprojects.takaincome_bd;

import android.app.Application;

import com.onesignal.OneSignal;

public class OneSignalClass extends Application {


        private static final String ONESIGNAL_APP_ID = "e91b5285-d6ef-46e7-b8af-4d9319cf6799";

        @Override
        public void onCreate() {
            super.onCreate();

            // Enable verbose OneSignal logging to debug issues if needed.
            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

            // OneSignal Initialization
            OneSignal.initWithContext(this);
            OneSignal.setAppId(ONESIGNAL_APP_ID);

        }
}
