package com.example.xpensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.xpensetracker.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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