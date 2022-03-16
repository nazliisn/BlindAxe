package com.example.blindaxe.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blindaxe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class InventoryActivity extends AppCompatActivity {
    private int diamondCount, coinCount, lifeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, String> map = new HashMap<>();
                for (DataSnapshot users : snapshot.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getChildren()) {

                    map.put(users.getKey(), users.getValue().toString());
                }
                diamondCount = Integer.parseInt(map.get("diamondCount"));
                lifeCount = Integer.parseInt(map.get("heartCount"));
                coinCount = Integer.parseInt(map.get("moneyCount"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void addOneDiamond(View view) {
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("diamondCount")
                .setValue(diamondCount + 1);
    }

    public void addThreeDiamond(View view) {
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("diamondCount")
                .setValue(diamondCount + 3);
    }

    public void addOneLife(View view) {
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("heartCount")
                .setValue(lifeCount + 1);
    }

    public void addThreeLife(View view) {
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("heartCount")
                .setValue(lifeCount + 3);
    }

    public void addThCoin(View view) {
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("moneyCount")
                .setValue(coinCount + 1000);
    }

    public void addThThCoin(View view) {
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("moneyCount")
                .setValue(coinCount + 3000);
    }


}
