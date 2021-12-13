package com.wyb.androidclientdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NumberValidation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_validation);
    }
    public static void start(Context context) {
        Intent i = new Intent(context, NumberValidation.class);
        context.startActivity(i);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        detectMyNumber();
    }

    void detectMyNumber() {
        RegistrationService.getMyNumber(this);
    }
}
