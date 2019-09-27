package com.orange.flickrtask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavHost;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavHostFragment controller;
    private BottomNavigationView bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom = findViewById(R.id.bottomTab);
        controller = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.host_fragment);
        NavigationUI.setupWithNavController(bottom,controller.getNavController());
    }
}
