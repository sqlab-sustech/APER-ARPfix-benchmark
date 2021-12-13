package com.lucasg234.protesttracker.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.lucasg234.protesttracker.permissions.PermissionsHandler;
import com.lucasg234.protesttracker.util.LocationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView Adapter used by the FeedFragment
 * Holds all posts queried from Parse
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private static final String TAG = "FeedAdapter";


    public FeedAdapter(MainActivity parentActivity, RecyclerView recyclerView) {
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(null);
        return new FeedViewHolder(null);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class FeedViewHolder extends RecyclerView.ViewHolder {

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind() {
            if (PermissionsHandler.checkLocationPermission(null)) {
                String relativeLocation = LocationUtils.toRelativeLocation(null);

            } else {
//                mBinding.postLocation.setText(null);
            }

        }

    }


}

