package com.nooneprojects.takaincome_bd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {

    int POINTS = 10;
    TextView scoreText, earnedCoinsText;
    Button restartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);
        scoreText = findViewById(R.id.score);


        earnedCoinsText = findViewById(R.id.earnedCoins);
        int correctAnswers = getIntent().getIntExtra("correct", 0);
        int totalQuestions = getIntent().getIntExtra("total", 0);


        Log.d("COCOOOC", "onCreate: " + correctAnswers + "   " + totalQuestions);
        long points = correctAnswers * POINTS;

        scoreText.setText(String.format("%d/%d", correctAnswers, totalQuestions));
        earnedCoinsText.setText(String.valueOf(points));

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        database.collection(Common.COLLECTION_NAME)
                .document(FirebaseAuth.getInstance().getUid())
                .update("coin", FieldValue.increment(points));

       findViewById(R.id.restartBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finishAffinity();
            }
        });


    }
}