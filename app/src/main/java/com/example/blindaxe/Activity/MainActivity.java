package com.example.blindaxe.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.blindaxe.LeaderBoardActivity;
import com.example.blindaxe.R;
import com.example.blindaxe.ScoreData;
import com.example.blindaxe.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Bitmap selectedImage;
    ImageView profilePhoto;
    Uri imageData;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    TextView textView13;
    FirebaseUser currentUser;
    HomeFragment homeFragment;
    DrawerLayout drawer;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);

        textView13 = findViewById(R.id.textView13);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Score");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Friends");


        homeFragment = new HomeFragment();


        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home,
                R.id.nav_leaderboard, R.id.nav_inventory)
                .setDrawerLayout(drawer)
                .build();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);


        updateNavHeader();


    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_home);

        } else if (id == R.id.nav_exit) {
            logout();
        } else if (id == R.id.nav_inventory) {

            startActivity(new Intent(getApplicationContext(), InventoryActivity.class));

        } else if (id == R.id.nav_leaderboard) {
            startActivity(new Intent(getApplicationContext(), LeaderBoardActivity.class));

            //Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.leaderBoard);
        }
        return true;
    }


    public void questionClick1(View view) {

        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("type", "ScienceQuestion");
        startActivity(intent);

    }

    public void questionClick2(View view) {

        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("type", "HistoryQuestion");

        startActivity(intent);

    }

    public void questionClick3(View view) {

        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("type", "GeneralKnowledgeQuestion");
        startActivity(intent);

    }

    public void questionClick4(View view) {

        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("type", "SportQuestion");
        startActivity(intent);

    }

    public void questionClick5(View view) {

        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("type", "TVShowQuestion");
        startActivity(intent);

    }

    public void questionClick6(View view) {

        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("type", "ComplexQuestion");
        startActivity(intent);

    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        TextView header_email = (TextView) headerView.findViewById(R.id.header_email);

        header_email.setText(currentUser.getEmail());
        ImageView imageView = headerView.findViewById(R.id.profilePhoto);


    }


    public void selectImage(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery, 2);
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        profilePhoto = headerView.findViewById(R.id.profilePhoto);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            imageData = data.getData();

            try {

                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    profilePhoto.setImageBitmap(selectedImage);
                    upload(headerView);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                    profilePhoto.setImageBitmap(selectedImage);
                    upload(headerView);


                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    public void upload(View view) {

        if (imageData != null) {


            //universal unique id
            UUID uuid = UUID.randomUUID();
            final String imageName = "images/" + uuid + ".jpg";

            storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Download URL

                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String downloadUrl = uri.toString();

                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String userEmail = firebaseUser.getEmail();


                            HashMap<String, Object> postData = new HashMap<>();
                            postData.put("useremail", userEmail);
                            postData.put("downloadurl", downloadUrl);
                            postData.put("userId", mAuth.getCurrentUser().getUid());
                            postData.put("date", FieldValue.serverTimestamp());
                            ScoreData scoreData = new ScoreData();


                            scoreData.setImage(downloadUrl);


                            databaseReference.child(firebaseUser.getUid()).child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    snapshot.getRef().setValue((downloadUrl));
                                    scoreData.setImage(downloadUrl);
                                    // friendData.setDownloadUrl(downloadUrl);

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            databaseReference1.child(firebaseUser.getUid()).child("image").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    snapshot.getRef().setValue((downloadUrl));


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                            DocumentReference documentReference = firebaseFirestore.collection("Score").
                                    document();

                            Map<String, Object> map = new HashMap<>();
                            map.put("downloadurl", downloadUrl);
                            documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    System.out.println(" ");
                                }
                            });


                            firebaseFirestore.collection("Profile").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    //             Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                                    //           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    //         startActivity(intent);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }


    }


}
