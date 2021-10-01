package com.nooneprojects.takaincome_bd;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nooneprojects.takaincome_bd.Model.User;

public class Common {
    public static String COLLECTION_NAME = "Users";
    public static String COLLECTION_WITHDRAW = "Withdraw";
    public static String COLLECTION_QUIZ = "QUIZ";
    public static String UID = FirebaseAuth.getInstance().getUid();
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    ;


    public static void getUserCoin() {


    }


}
