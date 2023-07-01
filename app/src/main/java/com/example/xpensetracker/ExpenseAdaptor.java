package com.example.xpensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdaptor extends RecyclerView.Adapter<ExpenseAdaptor.MyViewHolder>{
    Context context;
    ArrayList<ExpenseModel> expenseModelArrayList;

    public ExpenseAdaptor(Context context,ArrayList<ExpenseModel> expenseModelArrayList){
        this.context=context;
        this.expenseModelArrayList=expenseModelArrayList;
    }
    public void add (ExpenseModel expenseModel){
        expenseModelArrayList.add(expenseModel);
        notifyDataSetChanged();
    }
    public void clear(){
        expenseModelArrayList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.one_recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ExpenseModel model = expenseModelArrayList.get(position);
        holder.amount.setText(model.getAmount());
        holder.date.setText(model.getDate());
        holder.note.setText(model.getNote());

    }

    @Override
    public int getItemCount() {
        return expenseModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView note,amount,date;
        View priority;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note=itemView.findViewById(R.id.note);
            amount=itemView.findViewById(R.id.amount_one);
            date=itemView.findViewById(R.id.date_one);
            priority=itemView.findViewById(R.id.priority);
        }
    }
}
