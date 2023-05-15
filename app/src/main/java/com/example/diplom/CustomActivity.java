package com.example.diplom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.diplom.databinding.ActivityCustomBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CustomActivity extends AppCompatActivity {

    ActivityCustomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new NewsFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item ->
        {
            switch (item.getItemId()) {
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;

                case R.id.news:
                    replaceFragment(new NewsFragment());
                    break;

                case R.id.records:
                    replaceFragment(new RecordsFragment());
                    break;

                case R.id.about:
                    replaceFragment(new AboutFragment());
                    break;
            }
            return true;
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.black01, null)));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}