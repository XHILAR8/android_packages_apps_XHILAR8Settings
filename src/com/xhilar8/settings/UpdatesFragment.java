package com.xhilar8.settings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UpdatesFragment extends Fragment {

	TextView currentVersion, latestVersion;
	public static final String installedVersion = "05-12-2014";
	String latest = "";

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
	}

	@Override
	public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b) {
		super.onCreateView(li, parent, b);
		View root = li.inflate(R.layout.fragment_updates, parent, false);
		root.findViewById(R.id.updates_download).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent browserIntent = new Intent(Intent.ACTION_VIEW,
								Uri.parse("http://xhilar8.hamzahrmalik.com/"));
						startActivity(browserIntent);
					}
				});
		currentVersion = (TextView) root.findViewById(R.id.updates_current);
		currentVersion.setText("Your version: " + installedVersion);
		latestVersion = (TextView) root.findViewById(R.id.updates_latest);
		new getLatest().execute();

		return root;
	}

	private class getLatest extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				URL oracle = new URL(
						"http://xhilar8.hamzahrmalik.com/version.txt");
				BufferedReader in = new BufferedReader(new InputStreamReader(
						oracle.openStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null)
					latest += inputLine;
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			UpdatesFragment.this.getActivity().runOnUiThread(new Runnable() {

				@Override
				public void run() {
					latestVersion
							.setText("Latest available version: " + latest);
					if (!latest.equals(installedVersion))
						currentVersion.setTextColor(Color.RED);
					else
						currentVersion.setTextColor(Color.GREEN);
				}

			});

			return latest;
		}

	}

}
