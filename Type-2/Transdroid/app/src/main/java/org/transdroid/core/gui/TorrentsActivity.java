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
package org.transdroid.core.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.transdroid.R;
import org.transdroid.core.service.*;
/**
 * Main activity that holds the fragment that shows the torrents list, presents a way to filter the list (via an action bar spinner or list side list)
 * and potentially shows a torrent details fragment too, if there is room. Task execution such as loading of and adding torrents is performs in this
 * activity, using background methods. Finally, the activity offers navigation elements such as access to settings and showing connection issues.
 * @author Eric Kok
 */
//@EActivity(R.layout.activity_torrents)
public class TorrentsActivity extends AppCompatActivity {

//	@Bean
	protected ConnectivityHelper connectivityHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

//	@AfterViews
	protected void init() {


			connectivityHelper.getConnectedNetworkName();

	}


	@Override
	protected void onResume() {
		super.onResume();
		connectivityHelper.getConnectedNetworkName();
	}

}
