package com.example.xpensetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.xpensetracker.databinding.ActivityDashboardBinding;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    int sumExpense=0;
    int sumIncome=0;
    ArrayList<ExpenseModel> expenseModelArrayList;
    ExpenseAdaptor expenseAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        expenseModelArrayList=new ArrayList<>();


        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setHasFixedSize(true);


        Intent intent=new Intent(DashboardActivity.this,AddExpenseActivity.class);
        binding.addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("type","Income");
                startActivity(intent);

            }
        });
        binding.addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent.putExtra("type","Expense");
                startActivity(intent);

            }
        });

        loadData();
    }

    private void loadData() {
        firestore.collection("Expenses").document(firebaseAuth.getUid()).collection("Notes")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot ds:task.getResult()){
                            ExpenseModel ExpenseModel = new ExpenseModel(
                                    ds.getString("expenseId"),
                                    ds.getString("note"),
                                    ds.getString("amount"),
                                    ds.getString("type"),
                                    ds.getString("date"));

                            int amount = Integer.parseInt(ds.getString("amount"));
                            if(ds.getString("type").equals("Expense")){
                                sumExpense=sumExpense+amount;
                            }
                            else{
                                sumIncome=sumIncome+amount;
                            }
                            expenseModelArrayList.add(ExpenseModel);

                        }

                        expenseAdaptor = new ExpenseAdaptor(DashboardActivity.this,expenseModelArrayList);
                        binding.recycler.setAdapter(expenseAdaptor);
                        setUpGraph();
                    }
                });

    }

    private void setUpGraph() {
        List < PieEntry> pieEntryList = new ArrayList<>();
        List <Integer> colorsList = new ArrayList<>();
        if(sumIncome!=0){
            pieEntryList.add(new PieEntry(sumIncome,"Income"));
            colorsList.add(getResources().getColor(R.color.green_n1));
        }
        if(sumExpense!=0){
            pieEntryList.add(new PieEntry(sumExpense,"Expense"));
            colorsList.add(getResources().getColor(R.color.yellow_n1));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntryList, String.valueOf(sumIncome-sumExpense));
        pieDataSet.setColors(colorsList);
        PieData pieData = new PieData((pieDataSet));


        binding.pieChart.setData(pieData);
        binding.pieChart.invalidate();

    }


    protected void onStart(){
        super.onStart();
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please");
        progressDialog.setMessage("Wait");
        progressDialog.setCancelable(false);

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            progressDialog.show();
            FirebaseAuth.getInstance().signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(DashboardActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}

