package com.nextcloud.android.sso;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.nextcloud.android.sso.model.SingleSignOnAccount;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccountImporter accountImporter = new AccountImporter();
        accountImporter.accountsToImportAvailable(this); //sync
        accountImporter.onActivityResult(0, 0, new Intent(), this, new AccountImporter.IAccountAccessGranted() {
            @Override
            public void accountAccessGranted(SingleSignOnAccount singleSignOnAccount) {

            }
        });
    }
}