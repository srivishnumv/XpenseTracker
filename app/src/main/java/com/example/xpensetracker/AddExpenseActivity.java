package com.example.xpensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.xpensetracker.databinding.ActivityAddExpenseBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    ActivityAddExpenseBinding binding;
    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fstore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        binding.expenseRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="Expense";
                binding.expenseRadio.setChecked(true);
                binding.incomeRadio.setChecked(false);
            }
        });
        binding.incomeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="Income";
                binding.expenseRadio.setChecked(false);
                binding.incomeRadio.setChecked(true);
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount=binding.amount.getText().toString().trim();
                String note=binding.note.getText().toString().trim();
                String category = binding.category.getText().toString().trim();
                if(amount.length()<=0){
                    return;
                }

                if(type.length()<=0){
                    Toast.makeText(AddExpenseActivity.this,"Select Transaction type",Toast.LENGTH_SHORT).show();

                }
                String id=UUID.randomUUID().toString();
                Map<String,Object>  transaction = new HashMap<>();
                transaction.put("id",id);
                transaction.put("amount",amount);
                transaction.put("note",note);
                transaction.put("type",type);


                fstore.collection("Expenses").document(firebaseAuth.getUid()).collection("Notes")
                        .document(id)
                        .set(transaction)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddExpenseActivity.this,"Added Succesfully",Toast.LENGTH_SHORT).show();
                                binding.note.setText("");
                                binding.amount.setText("");
                                binding.category.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddExpenseActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}