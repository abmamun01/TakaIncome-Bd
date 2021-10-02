package com.nooneprojects.takaincome_bd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nooneprojects.takaincome_bd.Fragments.Home_Fragment;
import com.nooneprojects.takaincome_bd.Fragments.LeaderBoard_Fragments;
import com.nooneprojects.takaincome_bd.Fragments.Post_Feed_Fragment;
import com.nooneprojects.takaincome_bd.Fragments.WithDrawFragment;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;


public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private String GameId = "4365542";
    private String bannerAdsId = "Banner_Android";
    private String interstitialAdsId = "Interstitial_Android";
    private String rewardedAds = "Rewarded_Android";
    private boolean testMode = false;
    int cash;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigation = findViewById(R.id.bottom_navigation_id);



        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_newsfeed));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.settingsicon));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_withdraw));


        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment fragment = null;

                switch (item.getId()) {
                    case 1:
                        fragment = new LeaderBoard_Fragments();

                        break;

                    case 2:
                        fragment = new Home_Fragment();
                        break;

                    case 3:
                        fragment = new WithDrawFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerID,fragment).commit();

            }
        });

        bottomNavigation.setCount(1,"5");
        bottomNavigation.show(2,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

               // Toast.makeText(getApplicationContext(), "Clicked"+item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {


            }
        });




        UnityAds.initialize(MainActivity.this, GameId, testMode);


        //=========================================================================Interstitial Ads =============================================================================

        IUnityAdsListener unityAdsListener = new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String s) {

                Log.d("UNITYADSLISTENER", "onUnityAdsReady: ");
            }

            @Override
            public void onUnityAdsStart(String s) {

                Log.d("UNITYADSLISTENER", "onUnityAdsStart: ");

            }

            @Override
            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
                Log.d("UNITYADSLISTENER", "onUnityAdsFinish: ");

                firebaseFirestore
                        .collection("Users")
                        .document("tIzIeeCOsVqg7Frh5UsD")
                        .update("coins", FieldValue.increment(10)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "10" + " Coins added in account.", Toast.LENGTH_SHORT).show();
                        displayRewardedVideoAd();

                    }

                });
            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {

                Log.d("UNITYADSLISTENER", "onUnityAdsError: ");

            }
        };

        UnityAds.setListener(unityAdsListener);

        //=========================================================================Interstitial Ads =============================================================================


        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerID, new Home_Fragment()).commit();

    }



    private void displayInterstitialAds() {
        if (UnityAds.isReady(interstitialAdsId)) {
            UnityAds.show(MainActivity.this, interstitialAdsId);
        }
    }

    public void displayRewardedVideoAd() {
        if (UnityAds.isReady(rewardedAds)) {
            UnityAds.show(this, rewardedAds);
        }
    }


    // Implement the IUnityAdsListener interface methods:
    private class UnityAdsListener implements IUnityAdsListener {

        public void onUnityAdsReady(String adUnitId) {
            // Implement functionality for an ad being ready to show.
        }

        @Override
        public void onUnityAdsStart(String adUnitId) {
            // Implement functionality for a user starting to watch an ad.
        }

        @Override
        public void onUnityAdsFinish(String adUnitId, UnityAds.FinishState finishState) {
            // Implement conditional logic for each ad completion status:
            if (finishState.equals(UnityAds.FinishState.COMPLETED)) {
                // Reward the user for watching the ad to completion.
            } else if (finishState == UnityAds.FinishState.SKIPPED) {
                // Do not reward the user for skipping the ad.
            } else if (finishState == UnityAds.FinishState.ERROR) {
                // Log an error.
            }
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError error, String message) {
            // Implement functionality for a Unity Ads service error occurring.
        }


    }


    private boolean isVpnConnectionActive() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getNetworkInfo(ConnectivityManager.TYPE_VPN).isConnectedOrConnecting();
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i("TAG", "***** IP=" + ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("TAG", ex.toString());
        }
        return null;
    }
}