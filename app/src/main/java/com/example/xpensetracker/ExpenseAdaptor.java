package com.example.xpensetracker;
import android.content.Context;
import android.content.Intent;
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
        String priority= model.getType();
        if(priority.equals("Expense")){
            holder.priority.setBackgroundResource(R.drawable.yellow_shape);
        }
        else{
            holder.priority.setBackgroundResource(R.drawable.green_shape);
        }
        holder.amount.setText(model.getAmount());
        holder.date.setText(model.getDate());
        holder.note.setText(model.getNote());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,UpdateActivity.class);
                intent.putExtra("id",expenseModelArrayList.get(position).getExpenseId());
                intent.putExtra("amount",expenseModelArrayList.get(position).getAmount());
                intent.putExtra("note",expenseModelArrayList.get(position).getNote());
                intent.putExtra("type",expenseModelArrayList.get(position).getType());
                context.startActivity(intent);
            }
        });

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
