package com.example.d022omim;

import android.location.Location;

import androidx.annotation.NonNull;

public interface LocationFixChecker {
    boolean isLocationBetterThanLast(@NonNull Location newLocation);
    boolean isAccuracySatisfied(@NonNull Location location);
}
