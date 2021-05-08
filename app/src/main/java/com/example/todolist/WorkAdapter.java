package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder>
{
    ArrayList<Work> list;
    ItemClicked activity;
    public WorkAdapter(Context context,ArrayList<Work> WorkList)
    {
        list = WorkList;
        activity = (ItemClicked)context;
    }
    public interface ItemClicked
    {
        void onDeleteClick(int index);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate,tvTime,tvWork;
        ImageView btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvTime = itemView.findViewById(R.id.tvTime);
                tvWork = itemView.findViewById(R.id.tvWork);
                btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    activity.onDeleteClick(position);
                }
            });
        }
    }
    @NonNull
    @Override
    public WorkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkAdapter.ViewHolder holder, int position) {
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvTime.setText(list.get(position).getTime());
        holder.tvWork.setText(list.get(position).getWork());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
