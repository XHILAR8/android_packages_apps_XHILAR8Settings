package com.xhilar8.settings;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	int ACTION_NOTIFICATION_BG = 0;
	int ACTION_LOCKSCREEN_BG = 1;

	DrawerLayout drawer;
	ListView drawerList;

	String[] items = { "General", "Statusbar", "Navigation Bar",
			"Notification Shade", "Lockscreen", "Recents Screen", "Updates",
			"Help and Feedback" };

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
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("http://forum.xda-developers.com/oneplus-one/orig-development/rom-xhilar8-blackout-onehandmode-t2961489"));
			startActivity(browserIntent);
			return;
		}
		if(fragment==null)
			return;
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

	}

}
