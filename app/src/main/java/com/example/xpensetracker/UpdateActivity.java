package com.example.xpensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.xpensetracker.databinding.ActivityUpdateBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateActivity extends AppCompatActivity {
    ActivityUpdateBinding binding;

    String newType;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        String  id=getIntent().getStringExtra("id");
        String  amount=getIntent().getStringExtra("amount");
        String  note=getIntent().getStringExtra("note");
        String  type=getIntent().getStringExtra("type");

        binding.amount.setText(amount);
        binding.note.setText(note);
        switch (type){
            case "Income":
                newType="Income";
                binding.incomeRadio.setChecked(true);
                break;
            case "Expense":
                newType="Expense";
                binding.expenseRadio.setChecked(true);
                break;
        }
        binding.incomeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newType="Income";
                binding.incomeRadio.setChecked(true);
                binding.expenseRadio.setChecked(false);
            }
        });
        binding.expenseRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newType="Expense";
                binding.incomeRadio.setChecked(false);
                binding.expenseRadio.setChecked(true);
            }
        });
        binding.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  amount=binding.amount.getText().toString();
                String  note=binding.note.getText().toString();

            firebaseFirestore.collection("Expenses").document(firebaseAuth.getUid())
                    .collection("Notes").document(id)
                    .update("amount",amount,"note",note,"type",type)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            onBackPressed();
                            Toast.makeText(UpdateActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });
        binding.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("Expenses").document(firebaseAuth.getUid())
                        .collection("Notes")
                        .document(id).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                onBackPressed();
                                Toast.makeText(UpdateActivity.this,"Deleted",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
