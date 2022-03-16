package com.example.blindaxe.Activity;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blindaxe.Questions;
import com.example.blindaxe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseReference;
    TextView question, remaining, timer;
    Button A, B, C;
    List<Questions> questionsList;
    int questOfNumber;
    CountDownTimer countDownTimer;
    FirebaseDatabase database;
    int score;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        type = getIntent().getStringExtra("type");
        question = findViewById(R.id.text_question);
        remaining = findViewById(R.id.remaining_questions);
        timer = findViewById(R.id.counter);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        getSupportActionBar().hide();


        A = findViewById(R.id.buttonA);
        B = findViewById(R.id.buttonB);
        C = findViewById(R.id.buttonC);

        A.setOnClickListener(this);
        B.setOnClickListener(this);
        C.setOnClickListener(this);
        database = FirebaseDatabase.getInstance();

        score = 0;
        getQuestionsList();

    }

    private void getQuestionsList() {
        questionsList = new ArrayList<>();
        databaseReference.child("Question").child(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String question = dataSnapshot.child("question").getValue().toString();
                    String a = dataSnapshot.child("A").getValue().toString();
                    String b = dataSnapshot.child("B").getValue().toString();
                    String c = dataSnapshot.child("C").getValue().toString();

                    long correctAnswer = (long) dataSnapshot.child("correctAnswer").getValue();
                    questionsList.add(new Questions(question, a, b, c, correctAnswer));
                }
                setQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void setQuestion() {
        System.out.println(question);
        timer.setText(String.valueOf(10));

        question.setText(questionsList.get(0).getQuestion());
        A.setText(questionsList.get(0).getA());
        B.setText(questionsList.get(0).getB());
        C.setText(questionsList.get(0).getC());

        remaining.setText(String.valueOf(1) + "/" + String.valueOf(questionsList.size()));
        startTimer();
        questOfNumber = 0;
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //  if (millisUntilFinished < 10000)
                timer.setText(String.valueOf(millisUntilFinished / 1000));
                System.out.println(String.valueOf(millisUntilFinished / 1000) + " " + questOfNumber);
            }

            @Override
            public void onFinish() {
                passQuestion();

            }
        };
        countDownTimer.start();
    }

    @Override
    public void onClick(View view) {
        int select = 0;
        switch (view.getId()) {
            case R.id.buttonA:
                select = 1;
                break;
            case R.id.buttonB:
                select = 2;
                break;
            case R.id.buttonC:
                select = 3;
                break;

            default:

        }

        control(select, view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void control(int select, View view) {
        if (select == questionsList.get(questOfNumber).getCorrectAnswers()) {
            //right answer

            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
            score++;
        } else {
            //wrong
            ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch ((int) questionsList.get(questOfNumber).getCorrectAnswers()) {
                case 1:
                    A.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                    break;
                case 2:
                    B.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                    break;
                case 3:
                    C.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                    break;

            }


        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                passQuestion();

            }
        }, 2000);

    }

    private void passQuestion() {
        if (questOfNumber < questionsList.size() - 1) {


            questOfNumber++;

            animasyon(question, 0, 0);
            animasyon(A, 0, 1);
            animasyon(B, 0, 2);
            animasyon(C, 0, 3);

            remaining.setText(String.valueOf(questOfNumber + 1) + "/" + String.valueOf(questionsList.size()));
            countDownTimer.cancel();
            timer.setText(String.valueOf(10));
            startTimer();


        } else {

            countDownTimer.cancel();
            Intent intent = new Intent(QuestionActivity.this, ScoreActivity.class);
            intent.putExtra("Score", score);


            startActivity(intent);
            QuestionActivity.this.finish();


        }
    }

    private void animasyon(View view, final int number, int anNumber) {

        view.animate().alpha(number).scaleX(number).scaleY(number).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {


                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (number == 0) {
                            switch (anNumber) {
                                case 0:
                                    question.setText(questionsList.get(questOfNumber).getQuestion());
                                    break;
                                case 1:
                                    A.setText(questionsList.get(questOfNumber).getA());
                                    break;
                                case 2:
                                    B.setText(questionsList.get(questOfNumber).getB());
                                    break;
                                case 3:
                                    C.setText(questionsList.get(questOfNumber).getC());
                                    break;
                            }

                            if (anNumber != 0)
                                ((Button) view).setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));// yarın bu düzeltilecek

                            animasyon(view, 1, anNumber);

                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }


}