package com.example.blindaxe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blindaxe.R;
import com.example.blindaxe.ScoreData;
import com.example.blindaxe.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextEmail, editTextUsername;
    FirebaseDatabase rootN;
    DatabaseReference reference;
    DatabaseReference reference1;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<String> usernameList;
    private ArrayList<Integer> score;

    public ArrayList<String> getUsernameList() {
        return usernameList;
    }

    public void setUsernameList(ArrayList<String> usernameList) {
        this.usernameList = usernameList;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    public void setScore(ArrayList<Integer> score) {
        this.score = score;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.register_email);
        editTextUsername = findViewById(R.id.register_username);

        usernameList = new ArrayList<>();
        score = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();


    }

    public void createUser(View view) {
        String registerEmail = editTextEmail.getText().toString();
        String registerUsername = editTextUsername.getText().toString();


        HashMap<String, Object> postData = new HashMap<>();
        postData.put("username", registerUsername);
        postData.put("downloadurl", "downloadUrl");
        postData.put("score", 0);

        firebaseFirestore.collection("Score").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                //             Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                //           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //         startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


        mAuth.createUserWithEmailAndPassword(registerEmail, registerUsername)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Registration Successful !", Toast.LENGTH_SHORT).show();
                            rootN = FirebaseDatabase.getInstance();
                            reference = rootN.getReference().child("users");
                            reference1 = rootN.getReference().child("Score");


                            UserHelperClass helperClass = new UserHelperClass(registerEmail, registerUsername, "1", "3", "1000");
                            reference.child(user.getUid()).setValue(helperClass);


                            ScoreData scoreData = new ScoreData();
                            scoreData.setName(registerUsername);
                            reference1.child(user.getUid()).setValue(scoreData);


                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }

    public void returnLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}