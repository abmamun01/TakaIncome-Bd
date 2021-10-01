package com.nooneprojects.takaincome_bd.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nooneprojects.takaincome_bd.Common;
import com.nooneprojects.takaincome_bd.MainActivity;
import com.nooneprojects.takaincome_bd.Model.User;
import com.nooneprojects.takaincome_bd.QuizAcitivity;
import com.nooneprojects.takaincome_bd.R;
import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;


public class Home_Fragment extends Fragment {

    public Activity activity;
    private String GameId = "4365542";
    private String bannerAdsId = "Banner_Android";
    private String interstitialAdsId = "Interstitial_Android";
    private String rewardedAds = "Rewarded_Android";
    private boolean testMode = true;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    RelativeLayout quiz_button;

    ConstraintLayout watch_ads_btn;
    TextView userPoints, user_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_, container, false);


        watch_ads_btn = view.findViewById(R.id.watch_ads_id);
        userPoints = view.findViewById(R.id.user_points);
        user_name = view.findViewById(R.id.user_name);
        quiz_button = view.findViewById(R.id.quiz_button);


        getUserPoints();

        watch_ads_btn.setOnClickListener(view1 -> {
            displayRewardedVideoAd();
        });


        quiz_button.setOnClickListener(view1 -> {

            toastIconSuccess("Quiz!");
            startActivity(new Intent(getContext(), QuizAcitivity.class));
        });
        //================CHECKING IF VPN IS ACTIVE====================

/*        if (!isVpnConnectionActive()) {
            showCustomDialog();
        }*/
        //================CHECKING IF VPN IS ACTIVE====================


        //================UNITY ADS LISTENER====================
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
                        .collection(Common.COLLECTION_NAME)
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .update("coin", FieldValue.increment(10)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        // toastIconSuccess("10 Coins Added");

                        Toast.makeText(activity, "10 Coins Added", Toast.LENGTH_SHORT).show();
                    }

                });
            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {

                Log.d("UNITYADSLISTENER", "onUnityAdsError: ");

            }
        };
        UnityAds.setListener(unityAdsListener);
        //================UNITY ADS LISTENER====================


        return view;
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(v -> {

            Toast.makeText(getContext(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);


        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private boolean isVpnConnectionActive() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getNetworkInfo(ConnectivityManager.TYPE_VPN).isConnectedOrConnecting();
    }


    public void displayRewardedVideoAd() {
        if (UnityAds.isReady(rewardedAds)) {
            UnityAds.show((Activity) getContext(), rewardedAds);
        } else {

            toastIconError("Try Again Later!");

        }
    }


    private void getUserPoints() {


        firebaseFirestore.collection(Common.COLLECTION_NAME)
                .document(Common.UID).get()
                .addOnSuccessListener(documentSnapshot -> {

                    User user = documentSnapshot.toObject(User.class);
                    userPoints.setText(String.valueOf(user.getCoin()));
                    user_name.setText(String.valueOf(user.getName()));

                }).addOnFailureListener(e -> {

        });


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }


    private void toastIconSuccess(String text) {
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_LONG);


        View custom_view = activity.getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText(text);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_baseline_done_24);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.green_500));

        toast.setView(custom_view);
        toast.show();


    }

    private void toastIconError(String text) {
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        View custom_view = getLayoutInflater().inflate(R.layout.toast_icon_text, null);
        ((TextView) custom_view.findViewById(R.id.message)).setText(text);
        ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_baseline_close_24);
        ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(getResources().getColor(R.color.red_600));

        toast.setView(custom_view);
        toast.show();
    }


}