package com.machine.management.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.machine.management.R;
import com.machine.management.models.Machine;

import java.util.ArrayList;
import java.util.List;

public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.MyViewHolder> {
    List<Machine> data;
    Context context;

    public MachineAdapter(List<Machine> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public MachineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.machine_item, parent, false);
        return new MachineAdapter.MyViewHolder(view);
    }


    public void filterList(ArrayList<Machine> filterContact) {
        data = filterContact;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MachineAdapter.MyViewHolder holder, int position) {
        holder.id.setText(data.get(position).getId());
        holder.ref.setText(data.get(position).getReference());
        holder.dateAchat.setText(data.get(position).getDate());
        holder.prix.setText(data.get(position).getPrix());
        holder.marque.setText(data.get(position).getMarque().getLibelle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, ref, dateAchat, prix, marque;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = (TextView) itemView.findViewById(R.id.id_machine_fragment);
            this.ref = (TextView) itemView.findViewById(R.id.ref_machine_fragment);
            this.dateAchat = (TextView) itemView.findViewById(R.id.date_achat_machine_fragment);
            this.prix = (TextView) itemView.findViewById(R.id.prix_machine_fragment);
            this.marque = (TextView) itemView.findViewById(R.id.marque_machine_fragment);

        }
    }
}
