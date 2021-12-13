package com.lucasg234.protesttracker.mainactivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.lucasg234.protesttracker.permissions.PermissionsHandler;
import com.lucasg234.protesttracker.util.LocationUtils;

import java.util.Date;
import java.util.List;

/**
 * Fragment containing a RecyclerView using FeedAdapter
 * Queries for and displays post from Parse
 */
public class FeedFragment extends Fragment {

    private static final String TAG = "FeedFragment";

    private MainActivity mParent;
    private FeedAdapter mAdapter;
    private int mFeetFilter;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of FeedFragment
     */
    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mParent = (MainActivity) getActivity();



        // Ask for location permissions before loading posts into the feed
        // If they are not given, posts will load without relative positions
        if (!PermissionsHandler.checkLocationPermission(mParent)) {
            PermissionsHandler.requestLocationPermission(this);
        }

                queryClearPosts();

    }


    // Removes all posts within the FeedAdapter and replaces them with the result of a new query
    private void queryClearPosts() {
LocationUtils.getCurrentLocation(mParent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // If permission was just granted to allow location services, then notify the adapter
        // This will rebind the currently viewed posts with the location information
        if (requestCode == PermissionsHandler.REQUEST_CODE_LOCATION_PERMISSIONS && permissions.length >= 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            mAdapter.notifyDataSetChanged();
        }
    }

}