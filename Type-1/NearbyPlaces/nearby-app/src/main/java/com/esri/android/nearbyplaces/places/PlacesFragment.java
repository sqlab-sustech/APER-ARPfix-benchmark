/* Copyright 2016 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For additional information, contact:
 * Environmental Systems Research Institute, Inc.
 * Attn: Contracts Dept
 * 380 New York Street
 * Redlands, California, USA 92373
 *
 * email: contracts@esri.com
 *
 */

package com.esri.android.nearbyplaces.places;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import com.esri.android.nearbyplaces.R;
public class PlacesFragment extends Fragment implements
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{



  private static final String TAG = PlacesFragment.class.getSimpleName();

  private GoogleApiClient mGoogleApiClient;

  private FragmentListener mCallback;

  private ProgressDialog mProgressDialog;

  public PlacesFragment(){

  }
  public static  PlacesFragment newInstance(){
    return new PlacesFragment();

  }
  @Override
  public final void onCreate(final Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    // retain this fragment
    setRetainInstance(true);

    mCallback.onCreationComplete();
  }

  @Nullable
  @Override
  public final View onCreateView(final LayoutInflater inflater, final ViewGroup container,
      final Bundle savedInstance){

    RecyclerView mPlacesView = (RecyclerView) inflater.inflate(
        R.layout.place_recycler_view, container, false);

    mPlacesView.setLayoutManager(new LinearLayoutManager(mPlacesView.getContext()));
    return mPlacesView;
  }

  @Override
  public void onAttach(final Context activity) {
    super.onAttach(activity);
    // This makes sure that the container activity has implemented
    // the callback interface. If not, it throws an exception
    try {
      mCallback = (FragmentListener) activity;
    } catch (final ClassCastException e) {
      throw new ClassCastException(activity.toString()
          + " must implement FragmentListener");
    }
  }







  @Override
  public final void onConnected(@Nullable final Bundle bundle) {
//    if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      LocationManager testManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
      testManager.requestLocationUpdates("", 1, 1, (LocationListener) this);

      final Task getLocationTask = LocationServices.getFusedLocationProviderClient(this.getContext()).getLastLocation();
//    }
  }

  @Override public void onConnectionSuspended(final int i) {
    Log.i(TAG, getString(R.string.location_connection_lost));
    mGoogleApiClient.connect();
  }
  @Override public final void onStart() {
    mGoogleApiClient.connect();
    super.onStart();
  }

  @Override  public final void onStop() {
    mGoogleApiClient.disconnect();
    super.onStop();
  }

  @Override public void onConnectionFailed(@NonNull final ConnectionResult connectionResult) {
    Toast.makeText(getContext(), getString(R.string.google_location_connection_problem),Toast.LENGTH_LONG).show();
    Log.e(TAG, getString(R.string.google_location_problem) + connectionResult.getErrorMessage());
  }



  /**
   * Signals to the activity that this fragment has
   * completed creation activities.
   */
  public interface FragmentListener{
    void onCreationComplete();
  }
}
