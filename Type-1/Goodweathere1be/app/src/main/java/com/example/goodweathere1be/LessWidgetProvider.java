package com.example.goodweathere1be;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class LessWidgetProvider extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, LocationUpdateService.class));
        super.onReceive(context, intent);
    }
}
