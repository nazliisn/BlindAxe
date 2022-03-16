package com.example.blindaxe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ScoreRecyclerViewAdapter extends RecyclerView.Adapter<ScoreRecyclerViewAdapter.ScoreRViewAdapter> {

    Context context;
    int i = 1;
    private List<ScoreData> scoreDataList;

    public ScoreRecyclerViewAdapter(List<ScoreData> scoreDataList, Context context) {
        this.scoreDataList = scoreDataList;
        this.context = context;
    }
    @NonNull
    @Override
    //layout ile recyleView burda bağlanıypr
    public ScoreRViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new ScoreRViewAdapter(view);
    }

    //tanımladığım görünümleri burda bağladım, hangi pozisyonda ne gösterilsin vs burda hallettim
    @Override
    public void onBindViewHolder(@NonNull ScoreRViewAdapter holder, int position) {
        ScoreData currents = scoreDataList.get(position);
        holder.username.setText(currents.getName());
        holder.scoreResult.setText(String.valueOf(currents.getScore()));
        holder.sorting.setText(String.valueOf(i));
        Glide.with(context).load(currents.getImage()).into(holder.userImage);
        i++;

    }

    //Kaç tane row oluşturulacağını  get itemda söylüyoruz
    @Override
    public int getItemCount() {
        return scoreDataList.size();
    }

    //görünümlerimizi tanımladım
    public class ScoreRViewAdapter extends RecyclerView.ViewHolder {
        TextView username;
        TextView scoreResult;
        TextView sorting;
        ImageView userImage;
        public ScoreRViewAdapter(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.leaderboard_username);
            scoreResult = itemView.findViewById(R.id.leaderboard_scoreResult);
            sorting = itemView.findViewById(R.id.sorting);
            userImage = itemView.findViewById(R.id.user_image);


        }
    }
}
