package com.example.blindaxe;

import android.os.Bundle;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ScrollView scrollView;

    FirebaseStorage firebaseStorage;
    List<ScoreData> scoreDataList;
    ScoreRecyclerViewAdapter scoreRecyclerViewAdapter;
    DatabaseReference databaseReference;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        firebaseFirestore = FirebaseFirestore.getInstance();
        scoreDataList = new ArrayList<>();
        getSupportActionBar().hide();
        recyclerView = findViewById(R.id.scoreRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Score");

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);


        databaseReference.orderByChild("score").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //veriyi çekiyoruz
                    ScoreData data = dataSnapshot.getValue(ScoreData.class);
                    scoreDataList.add(data);

                }
                scoreRecyclerViewAdapter = new ScoreRecyclerViewAdapter(scoreDataList, LeaderBoardActivity.this);
                recyclerView.setAdapter(scoreRecyclerViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
