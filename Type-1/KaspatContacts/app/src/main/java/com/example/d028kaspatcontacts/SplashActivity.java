package com.example.d028kaspatcontacts;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.d028kaspatcontacts.model.Contacts;
import com.example.d028kaspatcontacts.utils.AppController;
import com.example.d028kaspatcontacts.widgets.RegularTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private RegularTextView tvInfo;


    List<Contacts> contacts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check permissions here
        tvInfo = (RegularTextView) findViewById(R.id.accelerate);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Permission hasn't been granted, ask the user
            final Activity activity = this;
            tvInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.READ_CONTACTS}, 233);
                }
            });
        }
        else {
        standardLoad();
        }
    }

    private void standardLoad() {
        new LoadContacts().execute("");
    }


    private class LoadContacts extends AsyncTask<String, Void, List<Contacts>> {

        @Override
        protected List<Contacts> doInBackground(String... params) {
            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            Cursor pCur = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{"1"}, null);

            Cursor ce = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{"1"}, null);

            cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{"1"}, null);

            return contacts;
        }

        @Override
        protected void onPostExecute(List<Contacts> contacts) {
            Collections.sort(contacts);
            AppController.getInstance().setContacts(contacts);
//            mp.stop();
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        @Override
        protected void onPreExecute() {
//            mp = MediaPlayer.create(SplashActivity.this, R.raw.power2);
//            mp.setLooping(true);
//            mp.start();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
