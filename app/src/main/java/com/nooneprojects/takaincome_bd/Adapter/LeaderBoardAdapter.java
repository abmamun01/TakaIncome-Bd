package com.nooneprojects.takaincome_bd.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nooneprojects.takaincome_bd.Model.User;
import com.nooneprojects.takaincome_bd.R;
import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.LeaderBoardHolder> {

   private Context context;
   private ArrayList<User> users;

    public LeaderBoardAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public LeaderBoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.leaderboard_layout,parent,false);
        return new LeaderBoardHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardHolder holder, int position) {

        User user=users.get(position);

        holder.name.setText(user.getName());
        holder.coins.setText(String.valueOf(user.getCoin()));
        holder.index.setText(String.format("#%d",position+1));


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderBoardHolder extends RecyclerView.ViewHolder {

        TextView index,name,coins;
        ImageView imageView7;
        public LeaderBoardHolder(@NonNull View itemView) {
            super(itemView);

            index=itemView.findViewById(R.id.index);
            name=itemView.findViewById(R.id.name);
            coins=itemView.findViewById(R.id.coins);
            imageView7=itemView.findViewById(R.id.imageView7);
        }
    }
}
