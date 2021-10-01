package com.nooneprojects.takaincome_bd.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.nooneprojects.takaincome_bd.Model.User;
import com.nooneprojects.takaincome_bd.Model.WithdrawModel;
import com.nooneprojects.takaincome_bd.R;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class WithDrawFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    public Activity activity;
    TextView total_balance, paymentStatus;
    Button fourtyTakaBtn, eightyTakaBtn, oneHundredSixtyTakaBtn, threeHundredTwentyTakaBtn;
    EditText withdraw_phone_numberET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_with_draw, container, false);


        firebaseFirestore = FirebaseFirestore.getInstance();
        withdraw_phone_numberET = view.findViewById(R.id.withdraw_phone_number_id);
        total_balance = view.findViewById(R.id.total_balance);
        fourtyTakaBtn = view.findViewById(R.id.fourty_taka_btn);
        eightyTakaBtn = view.findViewById(R.id.eightyTakaBtn);
        paymentStatus = view.findViewById(R.id.paymentStatus);
        oneHundredSixtyTakaBtn = view.findViewById(R.id.oneHundredSixtyTakaBtn);
        threeHundredTwentyTakaBtn = view.findViewById(R.id.threeHundredTwentyTakaBtn);


        fourtyTakaBtn.setOnClickListener(view1 ->
        {


            fourtyTakaBtn.setEnabled(false);


            firebaseFirestore.collection(Common.COLLECTION_NAME).document(Common.UID)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);

                    if (!withdraw_phone_numberET.getText().toString().isEmpty()) {

                        assert user != null;
                        if (user.getCoin() > 5000) {

                            WithdrawModel withdrawModel = new WithdrawModel();
                            withdrawModel.setUid(FirebaseAuth.getInstance().getUid());
                            withdrawModel.setUserName(user.getName());
                            withdrawModel.setUserPhoneNumber(withdraw_phone_numberET.getText().toString());
                            withdrawModel.setTotalBalance(user.getCoin() + "");
                            withdrawModel.setDemand("40 Taka");

                            firebaseFirestore.collection(Common.COLLECTION_WITHDRAW)
                                    .document(user.getName() + "    " + FirebaseAuth.getInstance().getUid())
                                    .set(withdrawModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    decrementUserCoins(5000);

                                    SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText("Transaction Pending!");
                                    pDialog.setContentText("আপনার পেমেন্ট রিকুয়েস্ট আমরা পেয়েছি। ২৪ ঘণ্টা অপেক্ষা করুন আমরা আপনাকে পেমেন্ট করে দিব।");
                                    pDialog.setCancelable(false);
                                    pDialog.show();

                                    fourtyTakaBtn.setEnabled(false);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        } else {

                            showCustomDialog();
                        }

                    } else {

                        toastIconError("আপনার বিকাশ নাম্বারটি দিন!");
                        withdraw_phone_numberET.setError("এই ঘরটি আপনার বিকাশ নাম্বার দিয়ে পূরণ করুন");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        });


        eightyTakaBtn.setOnClickListener(view1 ->
        {


            eightyTakaBtn.setEnabled(false);

            firebaseFirestore.collection(Common.COLLECTION_NAME).document(Common.UID)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);

                    if (!withdraw_phone_numberET.getText().toString().isEmpty()) {

                        assert user != null;
                        if (user.getCoin() > 10000) {

                            WithdrawModel withdrawModel = new WithdrawModel();
                            withdrawModel.setUid(FirebaseAuth.getInstance().getUid());
                            withdrawModel.setUserName(user.getName());
                            withdrawModel.setUserPhoneNumber(withdraw_phone_numberET.getText().toString());
                            withdrawModel.setTotalBalance(user.getCoin() + "");
                            withdrawModel.setDemand("80 Taka");

                            firebaseFirestore.collection(Common.COLLECTION_WITHDRAW)
                                    .document(user.getName() + "    " + FirebaseAuth.getInstance().getUid())
                                    .set(withdrawModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    decrementUserCoins(10000);

                                    SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText("Transaction Pending!");
                                    pDialog.setContentText("আপনার পেমেন্ট রিকুয়েস্ট আমরা পেয়েছি। ২৪ ঘণ্টা অপেক্ষা করুন আমরা আপনাকে পেমেন্ট করে দিব।");
                                    pDialog.setCancelable(false);
                                    pDialog.show();

                                    eightyTakaBtn.setEnabled(true);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        } else {

                            showCustomDialog();
                        }

                    } else {
                        toastIconError("আপনার বিকাশ নাম্বারটি দিন!");
                        withdraw_phone_numberET.setError("এই ঘরটি আপনার বিকাশ নাম্বার দিয়ে পূরণ করুন");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        });


        oneHundredSixtyTakaBtn.setOnClickListener(view1 ->
        {


            oneHundredSixtyTakaBtn.setEnabled(false);
            firebaseFirestore.collection(Common.COLLECTION_NAME).document(Common.UID)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);

                    if (!withdraw_phone_numberET.getText().toString().isEmpty()) {

                        assert user != null;
                        if (user.getCoin() > 20000) {

                            WithdrawModel withdrawModel = new WithdrawModel();
                            withdrawModel.setUid(FirebaseAuth.getInstance().getUid());
                            withdrawModel.setUserName(user.getName());
                            withdrawModel.setUserPhoneNumber(withdraw_phone_numberET.getText().toString());
                            withdrawModel.setTotalBalance(user.getCoin() + "");
                            withdrawModel.setDemand("160 Taka");

                            firebaseFirestore.collection(Common.COLLECTION_WITHDRAW)
                                    .document(user.getName() + "    " + FirebaseAuth.getInstance().getUid())
                                    .set(withdrawModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    decrementUserCoins(20000);

                                    SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText("Transaction Pending!");
                                    pDialog.setContentText("আপনার পেমেন্ট রিকুয়েস্ট আমরা পেয়েছি। ২৪ ঘণ্টা অপেক্ষা করুন আমরা আপনাকে পেমেন্ট করে দিব।");
                                    pDialog.setCancelable(false);
                                    pDialog.show();

                                    oneHundredSixtyTakaBtn.setEnabled(true);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        } else {

                            showCustomDialog();
                        }

                    } else {
                        toastIconError("আপনার বিকাশ নাম্বারটি দিন!");
                        withdraw_phone_numberET.setError("এই ঘরটি আপনার বিকাশ নাম্বার দিয়ে পূরণ করুন");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        });


        threeHundredTwentyTakaBtn.setOnClickListener(view1 ->
        {

            threeHundredTwentyTakaBtn.setEnabled(false);


            firebaseFirestore.collection(Common.COLLECTION_NAME).document(Common.UID)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);

                    if (!withdraw_phone_numberET.getText().toString().isEmpty()) {

                        assert user != null;
                        if (user.getCoin() > 40000) {

                            WithdrawModel withdrawModel = new WithdrawModel();
                            withdrawModel.setUid(FirebaseAuth.getInstance().getUid());
                            withdrawModel.setUserName(user.getName());
                            withdrawModel.setUserPhoneNumber(withdraw_phone_numberET.getText().toString());
                            withdrawModel.setTotalBalance(user.getCoin() + "");
                            withdrawModel.setDemand("320 Taka");
                            withdrawModel.setPaymentStatus("");


                            firebaseFirestore.collection(Common.COLLECTION_WITHDRAW)
                                    .document(user.getName() + "    " + FirebaseAuth.getInstance().getUid())
                                    .set(withdrawModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    decrementUserCoins(40000);

                                    SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                    pDialog.setTitleText("Transaction Pending!");
                                    pDialog.setContentText("আপনার পেমেন্ট রিকুয়েস্ট আমরা পেয়েছি। ২৪ ঘণ্টা অপেক্ষা করুন আমরা আপনাকে পেমেন্ট করে দিব।");
                                    pDialog.setCancelable(false);
                                    pDialog.show();

                                    paymentStatus.setText(withdrawModel.getPaymentStatus());
                                    threeHundredTwentyTakaBtn.setEnabled(true);


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        } else {

                            showCustomDialog();
                        }

                    } else {
                        toastIconError("আপনার বিকাশ নাম্বারটি দিন!");
                        withdraw_phone_numberET.setError("এই ঘরটি আপনার বিকাশ নাম্বার দিয়ে পূরণ করুন");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });


        });

        return view;
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    private void getUserPoints() {

        firebaseFirestore.collection(Common.COLLECTION_NAME)
                .document(Common.UID).get()
                .addOnSuccessListener(documentSnapshot -> {

                    User user = documentSnapshot.toObject(User.class);
                    total_balance.setText(String.valueOf(user.getCoin()));


                }).addOnFailureListener(e -> {

        });


    }


    private void decrementUserCoins(int decrementNumber) {

        firebaseFirestore
                .collection(Common.COLLECTION_NAME)
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("coin", FieldValue.increment(-decrementNumber)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                // toastIconSuccess("10 Coins Added");

                Toast.makeText(activity, decrementNumber + " Coins Decremented", Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getUserPoints();

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

            dialog.dismiss();

        });
    }
}