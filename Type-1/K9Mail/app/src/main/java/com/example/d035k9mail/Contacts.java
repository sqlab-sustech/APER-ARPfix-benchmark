package com.example.d035k9mail;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.core.content.ContextCompat;

public class Contacts {
    protected Context mContext;
    protected ContentResolver mContentResolver;
    protected static final String PROJECTION[] = {
            ContactsContract.CommonDataKinds.Email._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Email.CONTACT_ID
    };
    protected static final String SORT_ORDER =
            ContactsContract.CommonDataKinds.Email.TIMES_CONTACTED + " DESC, " +
                    ContactsContract.Contacts.DISPLAY_NAME + ", " +
                    ContactsContract.CommonDataKinds.Email._ID;

    protected Contacts(Context context) {
        mContext = context;
        mContentResolver = context.getContentResolver();
    }

    private boolean hasContactPermission() {
        return true;
        /*        return ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;*/
    }
    public boolean isInContacts(final String emailAddress) {
        boolean result = false;

        final Cursor c = getContactByAddress(emailAddress);

        if (c != null) {
            if (c.getCount() > 0) {
                result = true;
            }
            c.close();
        }

        return result;
    }
    private Cursor getContactByAddress(final String address) {
        final Uri uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, Uri.encode(address));

        if (hasContactPermission()) {
            return mContentResolver.query(
                    uri,
                    PROJECTION,
                    null,
                    null,
                    SORT_ORDER);
        } else {
            return null;
        }
    }

}
