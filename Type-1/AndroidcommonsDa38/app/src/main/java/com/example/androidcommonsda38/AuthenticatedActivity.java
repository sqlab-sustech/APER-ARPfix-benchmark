package com.example.androidcommonsda38;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

public abstract class AuthenticatedActivity extends AppCompatActivity {

    String accountType;

    private String authCookie;

    public AuthenticatedActivity() {
        this.accountType = null;
    }

    protected void requestAuthToken() {
        AccountManager accountManager = AccountManager.get(this);
        /*if (ActivityCompat.checkSelfPermission(AuthenticatedActivity.this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.GET_ACCOUNTS},0);
        }*/
        Account[] allAccounts = accountManager.getAccountsByType(accountType);
        /*AddAccountTask addAccountTask = new AddAccountTask(accountManager);
        addAccountTask.execute();*/
    }

/*    private class AddAccountTask extends AsyncTask<Void, String, String> {
        private AccountManager accountManager;

        public AddAccountTask(AccountManager accountManager) {
            this.accountManager = accountManager;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Account curAccount = getCurrentAccount();
        }

        @Nullable
        private Account getCurrentAccount() {
            if (ActivityCompat.checkSelfPermission(AuthenticatedActivity.this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Account[] allAccounts = accountManager.getAccountsByType(accountType);
            if (allAccounts == null) {
                return null;
            }
            return allAccounts[0];
        }
    }*/
}
