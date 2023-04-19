package com.example.diplom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;

import com.example.diplom.databinding.ActivityCustomBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CustomActivity extends AppCompatActivity {

    ActivityCustomBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);
        FloatingActionButton fab = findViewById(R.id.fabtest);
        fab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black01, null)));
    }
}