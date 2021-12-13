package com.example.androidcommons3ec9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fmanager = getSupportFragmentManager();
        ftransaction = fmanager.beginTransaction();

        ContributionsListFragment contributionsList = new ContributionsListFragment();

        ftransaction.replace(1, contributionsList);
        ftransaction.commit();
        //        contributionsList = (ContributionsListFragment)getSupportFragmentManager().findFragmentById(1);

    }
}