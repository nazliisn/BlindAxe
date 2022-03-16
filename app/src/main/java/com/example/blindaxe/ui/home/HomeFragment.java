package com.example.blindaxe.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.blindaxe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private TextView diamondText,heartTexT,moneyText;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//    final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        diamondText=view.findViewById(R.id.textView13);
        heartTexT=view.findViewById(R.id.heart2Text);
        moneyText=view.findViewById(R.id.coinText);
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,String> map=new HashMap<>();
                for (DataSnapshot users : snapshot.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getChildren()) {
                    System.out.println(users.getKey()+" "+users.getValue());
                    map.put(users.getKey(),  users.getValue().toString());
                }
                diamondText.setText(map.get("diamondCount"));
                heartTexT.setText(map.get("heartCount"));
                moneyText.setText(map.get("moneyCount"));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }
}