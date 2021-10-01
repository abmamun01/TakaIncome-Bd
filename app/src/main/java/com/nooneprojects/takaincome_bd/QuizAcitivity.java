package com.nooneprojects.takaincome_bd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.nooneprojects.takaincome_bd.Model.Question;

import java.util.ArrayList;
import java.util.Random;

public class QuizAcitivity extends AppCompatActivity {


    ArrayList<Question> questions;
    int index = 0;
    Question question;
    CountDownTimer timer;
    TextView timerText, option_1_textview, option_2_textview, option_3_textview, option_4_textview, questionCounter_text, questionText;
    FirebaseFirestore database;
    int correctAnswers = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_acitivity);


        timerText = findViewById(R.id.timer);
        option_1_textview = findViewById(R.id.option_1);
        option_2_textview = findViewById(R.id.option_2);
        option_3_textview = findViewById(R.id.option_3);
        option_4_textview = findViewById(R.id.option_4);
        questionCounter_text = findViewById(R.id.questionCounter);
        questionText = findViewById(R.id.question);


        questions = new ArrayList<>();
        database = FirebaseFirestore.getInstance();


        Random random = new Random();
        final int rand = random.nextInt(12);

        database.collection(Common.COLLECTION_QUIZ)
                .document()
                .collection("questions")
                .whereGreaterThanOrEqualTo("index", rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() < 5) {
                    database.collection(Common.COLLECTION_QUIZ)
                            .document()
                            .collection("questions")
                            .whereLessThanOrEqualTo("index", rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
                            }
                            setNextQuestion();
                        }
                    });
                } else {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        Question question = snapshot.toObject(Question.class);
                        questions.add(question);
                    }
                    setNextQuestion();
                }
            }
        });


        resetTimer();
    }


    void resetTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerText.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    void showAnswer() {
        if (question.getAnswer().equals(option_1_textview.getText().toString()))
            option_1_textview.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (question.getAnswer().equals(option_2_textview.getText().toString()))
            option_2_textview.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (question.getAnswer().equals(option_3_textview.getText().toString()))
            option_3_textview.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if (question.getAnswer().equals(option_3_textview.getText().toString()))
            option_4_textview.setBackground(getResources().getDrawable(R.drawable.option_right));
    }

    void setNextQuestion() {
        if (timer != null)
            timer.cancel();

        timer.start();
        if (index < questions.size()) {
            questionCounter_text.setText(String.format("%d/%d", (index + 1), questions.size()));
            question = questions.get(index);
            questionText.setText(question.getQuestion());
            option_1_textview.setText(question.getOption1());
            option_2_textview.setText(question.getOption2());
            option_3_textview.setText(question.getOption3());
            option_4_textview.setText(question.getOption4());
        }
    }

    void checkAnswer(TextView textView) {
        String selectedAnswer = textView.getText().toString();
        if (selectedAnswer.equals(question.getAnswer())) {
            correctAnswers++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        } else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }

    void reset() {
        option_1_textview.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        option_2_textview.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        option_3_textview.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        option_4_textview.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.option_1:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:
                if (timer != null)
                    timer.cancel();
                TextView selected = (TextView) view;
                checkAnswer(selected);

                break;
            case R.id.nextBtn:
                reset();
                if (index <= questions.size()) {
                    index++;
                    setNextQuestion();
                } else {
                    Intent intent = new Intent(QuizAcitivity.this, ResultActivity.class);
                    intent.putExtra("correct", correctAnswers);
                    intent.putExtra("total", questions.size());
                    startActivity(intent);
                    //Toast.makeText(this, "Quiz Finished.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}