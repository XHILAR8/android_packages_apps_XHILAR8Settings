
package com.xhilar8.settings;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

    int ACTION_NOTIFICATION_BG = 0;
    int ACTION_LOCKSCREEN_BG = 1;

    DrawerLayout drawer;
    ListView drawerList;

    String[] items = {
            "General", "Statusbar", "Navigation Bar",
            "Notification Shade", "Lockscreen", "Recents Screen", "Updates",
            "Help and Feedback", "Rebooter"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) this.findViewById(R.id.drawer_list);

        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new GeneralFragment()).commit();
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        this.getActionBar().setTitle(items[position]);
        drawer.closeDrawers();
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new GeneralFragment();
                break;
            case 1:
                fragment = new StatusbarFragment();
                break;
            case 2:
                fragment = new NavigationbarFragment();
                break;
            case 3:
                fragment = new NotificationShadeFragment();
                break;
            case 4:
                fragment = new LockscreenFragment();
                break;
            case 5:
                fragment = new RecentsScreenFragment();
                break;
            case 6:
                fragment = new UpdatesFragment();
                break;
            case 7:
                // perhaps there could be a central server using php which takes the device code and
                // redirects in the future
                // mean while we use an IF, for bacon and hammerhead, the only officially supported
                // devices
                String url = "xhilar8.hamzahrmalik.com";// directing them to rom homepage if their
                                                        // device isnt detected right
                if (Util.getDevice().equals("bacon"))
                    url = "http://forum.xda-developers.com/oneplus-one/orig-development/rom-xhilar8-blackout-onehandmode-t2961489";
                else if (Util.getDevice().equals("hammerhead"))
                    url = "http://forum.xda-developers.com/google-nexus-5/development/rom-xhilar8-blackout-onehandmode-t2979037";
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(browserIntent);
                return;
            case 8:
                fragment = new RebooterFragment();
                break;
        }
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment).commit();

    }

}
