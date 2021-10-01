package com.nooneprojects.takaincome_bd.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.nooneprojects.takaincome_bd.Common;
import com.nooneprojects.takaincome_bd.MainActivity;
import com.nooneprojects.takaincome_bd.Model.User;
import com.nooneprojects.takaincome_bd.R;


public class SignUp_Fragment extends Fragment {


    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    EditText emailET, passwordET, phoneET, nameET;

    private RelativeLayout signUpBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        signUpBtn = view.findViewById(R.id.signup_btn_id);
        emailET = view.findViewById(R.id.emailID);
        passwordET = view.findViewById(R.id.passwordID);
        phoneET = view.findViewById(R.id.phoneNumberID);
        nameET = view.findViewById(R.id.nameID);


        emailET.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        passwordET.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        phoneET.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        nameET.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();


        signUpBtn.setOnClickListener(view1 ->
        {
            firebaseAuth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                    .addOnCompleteListener(task -> {

                        int coin = 0;
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        User user = new User(nameET.getText().toString(), emailET.getText().toString(), passwordET.getText().toString(), phoneET.getText().toString(), coin);


                        firebaseFirestore.collection(Common.COLLECTION_NAME).document(uid).set(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d("USERS", "User Created ON Firebase: ");

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("USERS", "User Failed To Created ON Firebase: " + e.getMessage());

                            }
                        });


                        Toast.makeText(getContext(), "SuccessFully Created!", Toast.LENGTH_SHORT).show();
                        goToHome();

                    }).addOnFailureListener(e -> {

                Toast.makeText(getContext(), "Failed To Created!" + e.getMessage(), Toast.LENGTH_SHORT).show();

            });

        });


        return view;
    }


    private void goToHome() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }
}