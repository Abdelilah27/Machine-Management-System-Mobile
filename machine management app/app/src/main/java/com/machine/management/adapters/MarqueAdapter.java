package com.machine.management.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.machine.management.R;
import com.machine.management.models.Marque;

import java.util.ArrayList;
import java.util.List;

public class MarqueAdapter extends RecyclerView.Adapter<MarqueAdapter.MyViewHolder> {
    List<Marque> data;
    Context context;

    public MarqueAdapter(List<Marque> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.marque_item, parent, false);
        return new MyViewHolder(view);
    }


    public void filterList(ArrayList<Marque> filterContact) {
        data = filterContact;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(data.get(position).getId());
        holder.code.setText(data.get(position).getCode());
        holder.marque.setText(data.get(position).getLibelle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, code, marque;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = (TextView) itemView.findViewById(R.id.id_marque_fragment);
            this.code = (TextView) itemView.findViewById(R.id.code_marque_fragment);
            this.marque = (TextView) itemView.findViewById(R.id.marque_fragment);
        }
    }
}
