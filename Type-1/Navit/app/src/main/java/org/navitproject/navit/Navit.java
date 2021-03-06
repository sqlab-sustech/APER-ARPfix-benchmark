/**
 * Navit, a modular navigation system.
 * Copyright (C) 2005-2008 Navit Team
 * <p>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 */

package org.navitproject.navit;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

public class Navit extends Activity {


    public static final int NavitDownloaderSelectMap_id = 967;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        runOptionsItem(item.getItemId());
        return true;
    }

    public void runOptionsItem(int id) {
        // Handle item selection
        switch (id) {
            case 3:
                // map download menu
                Intent map_download_list_activity = new Intent(this, NavitDownloadSelectMapActivity.class);
                startActivityForResult(map_download_list_activity, Navit.NavitDownloaderSelectMap_id);
                break;

            case 99:
                // exit
                this.onStop();
                break;
        }
    }


//    public void exit() {
//        NavitVehicle.removeListener();
//    }
}
