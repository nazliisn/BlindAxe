package com.example.blindaxe.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blindaxe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class ScoreActivity extends AppCompatActivity {

    TextView yourScore, text;
    ImageView imageView;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Button backHome;
    int score;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        getSupportActionBar().hide();


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        score = getIntent().getIntExtra("Score", 0);
        yourScore = findViewById(R.id.yourScore);
        text = findViewById(R.id.textView16);
        backHome = findViewById(R.id.backHome);
        firebaseFirestore = FirebaseFirestore.getInstance();


        yourScore.setText(String.valueOf(score));


        databaseReference.child("Score").child(firebaseUser.getUid()).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // bu kısımda scorumuzu önceki kullanıcı verlermizle birleştiriyoruz.
                //eğer if koymamız lazım yoksa yeni kullanıcı geldğnde hata verir

                if (snapshot.exists()) {
                    score += Integer.parseInt(snapshot.getValue().toString());
                }
                snapshot.getRef().setValue((score));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
                ScoreActivity.this.finish();

            }
        });


    }
}

