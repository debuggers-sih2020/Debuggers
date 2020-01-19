package com.example.voiceprescription;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    Context mCtx;
    ArrayList<DatabasePojo> pal;

    public HistoryAdapter(){}

    public HistoryAdapter(Context mCtx, ArrayList<DatabasePojo> pal) {
        this.mCtx = mCtx;
        this.pal=pal;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf=LayoutInflater.from(mCtx);
        View v=inf.inflate(R.layout.cardview,parent,false);
        return new HistoryAdapter.MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final HistoryAdapter.MyViewHolder holder, final int position) {
        DatabasePojo databasePojo=pal.get(position);
        holder.tv_date.setText(databasePojo.getDate());
    }

    @Override
    public int getItemCount() {
        return pal.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_date=(TextView)itemView.findViewById(R.id.date_cv);
        }
    }
}
