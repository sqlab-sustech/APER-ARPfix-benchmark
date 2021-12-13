package com.example.d022omim;

import androidx.annotation.NonNull;

abstract  class BaseLocationProvider {
    @NonNull
    private final LocationFixChecker mLocationFixChecker;
    private boolean mActive;
    @NonNull
    LocationFixChecker getLocationFixChecker()
    {
        return mLocationFixChecker;
    }

    BaseLocationProvider(@NonNull LocationFixChecker locationFixChecker)
    {
        mLocationFixChecker = locationFixChecker;
    }

    protected abstract void start();
    protected abstract void stop();

    /**
     * Indicates whether this provider is providing location updates or not
     * @return true - if locations are actively coming from this provider, false - otherwise
     */
    public final boolean isActive()
    {
        return mActive;
    }

    final void setActive(boolean active)
    {
        mActive = active;
    }
}
