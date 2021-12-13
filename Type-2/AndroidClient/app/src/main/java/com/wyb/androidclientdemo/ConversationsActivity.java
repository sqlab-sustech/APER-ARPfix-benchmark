package com.wyb.androidclientdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ConversationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        NumberValidation.start(this);
    }
}
