package com.example.xpensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.xpensetracker.databinding.ActivityUpdateBinding;

public class UpdateActivity extends AppCompatActivity {
    ActivityUpdateBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
