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

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.transdroid.R;
import org.transdroid.core.service.ConnectivityHelper;
/**
 * An activity that holds a single torrents details fragment. It is used on devices (i.e. phones) where there is no room to show details in the {@link
 * TorrentsActivity} directly. Task execution, such as loading of more details and updating file priorities, is performed in this activity via
 * background methods.
 * @author Eric Kok
 */
//@EActivity(R.layout.activity_details)
//@OptionsMenu(R.menu.activity_details)
public class DetailsActivity extends AppCompatActivity {

//	@Bean
	protected ConnectivityHelper connectivityHelper;


//	@AfterViews
	protected void init() {

		connectivityHelper.getConnectedNetworkName();


	}


}
