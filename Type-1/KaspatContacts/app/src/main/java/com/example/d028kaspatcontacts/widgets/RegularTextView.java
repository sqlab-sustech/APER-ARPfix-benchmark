package com.example.d028kaspatcontacts.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by Arshad on 03-06-2016.
 */
public class RegularTextView extends androidx.appcompat.widget.AppCompatTextView {
    /*
     * Caches typefaces based on their file path and name,
     * so that they don't have to be created every time when they are referenced.
     */
    private static Typeface mTypeface;

    public RegularTextView(final Context context) {
        this(context, null);
    }

    public RegularTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RegularTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        setTypeface(mTypeface);
    }
}
