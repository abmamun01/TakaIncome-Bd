package com.nooneprojects.takaincome_bd.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.nooneprojects.takaincome_bd.MainActivity;
import com.nooneprojects.takaincome_bd.R;


public class SignIn_Fragments extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private RelativeLayout signInBtn;


    EditText emailET, passwordET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in__fragments, container, false);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        emailET = view.findViewById(R.id.emailID);
        passwordET = view.findViewById(R.id.passwordID);
        signInBtn = view.findViewById(R.id.sign_btn_id);


        emailET.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        passwordET.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();


        signInBtn.setOnClickListener(view1 ->
        {

            signInBtn.setEnabled(false);
            firebaseAuth.signInWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Toast.makeText(getContext(), "Logged in!", Toast.LENGTH_SHORT).show();

                            goToHome();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        });


        return view;
    }

    private void goToHome() {
        startActivity(new Intent(getContext(), MainActivity.class));
    }
}