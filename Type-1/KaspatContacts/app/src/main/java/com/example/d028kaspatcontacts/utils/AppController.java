package com.example.d028kaspatcontacts.utils;

import android.app.Application;


import com.example.d028kaspatcontacts.model.Contacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/1/18.
 */

public class AppController extends Application {

    private static AppController instance = null;
    List<Contacts> contacts = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized AppController getInstance() {
        return instance;
    }

    public List<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contacts> newContacts) {
        contacts = newContacts;
    }
}
