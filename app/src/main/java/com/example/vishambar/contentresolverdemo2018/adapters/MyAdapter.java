package com.example.vishambar.contentresolverdemo2018.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vishambar.contentresolverdemo2018.interfaces.OnClickInterface;
import com.example.vishambar.contentresolverdemo2018.models.MyModel;
import com.example.vishambar.contentresolverdemo2018.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<MyModel> list;
    private LayoutInflater layoutInflater;
    private OnClickInterface onClickInterface;

    public MyAdapter(Context context, OnClickInterface onClickInterface, List<MyModel> list) {
        this.list = (ArrayList<MyModel>) list;
        layoutInflater = LayoutInflater.from(context);
        this.onClickInterface = onClickInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(layoutInflater.inflate(R.layout.item_recycler_view, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.nameTv.setText(list.get(position).getName());
        myViewHolder.statusTv.setText(list.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv, statusTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.tv_name);
            statusTv = itemView.findViewById(R.id.tv_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickInterface.handleClick(getAdapterPosition());
                }
            });
        }
    }
}
