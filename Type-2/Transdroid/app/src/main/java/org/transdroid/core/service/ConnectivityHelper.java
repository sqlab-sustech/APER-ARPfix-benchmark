/* 
 * Copyright 2010-2018 Eric Kok et al.
 * 
 * Transdroid is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Transdroid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Transdroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.transdroid.core.service;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.EBean.Scope;
import org.transdroid.R;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.v4.content.ContextCompat;

@EBean(scope = Scope.Singleton)
public class ConnectivityHelper {

	@SystemService
	protected ConnectivityManager connectivityManager;
	@SystemService
	protected WifiManager wifiManager;


	public String getConnectedNetworkName() {
//		if(!hasNetworkNamePermission((Context)null)){
////			requestedPermissionWasGranted(3,)
//		}
		if (wifiManager.getConnectionInfo() != null && wifiManager.getConnectionInfo().getSSID() != null) {
			return wifiManager.getConnectionInfo().getSSID().replace("\"", "");
		}
		return null;
	}
	public boolean hasNetworkNamePermission(final Context activityContext) {
		return ContextCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_FINE_LOCATION) ==
				PackageManager.PERMISSION_GRANTED;
	}

	public boolean requestedPermissionWasGranted(int requestCode, String[] permissions, int[] grantResults) {
		return (requestCode == 3
				&& permissions != null
				&& grantResults != null
				&& permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED);
	}

}
