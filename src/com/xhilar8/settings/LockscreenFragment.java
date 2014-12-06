package com.xhilar8.settings;

import java.io.File;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LockscreenFragment extends Fragment {

	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
	}

	@Override
	public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b) {
		super.onCreateView(li, parent, b);
		View root = li.inflate(R.layout.fragment_lockscreen, parent, false);
		root.findViewById(R.id.lockscreen_background).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						background();
					}
				});
		return root;
	}

	public void background() {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		if (i.resolveActivity(getActivity().getPackageManager()) != null)
			startActivityForResult(i, 1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		getActivity();
		if (requestCode == 1 && resultCode == Activity.RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaColumns.DATA };

			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			Bitmap b = BitmapFactory.decodeFile(picturePath);
			Util.save(b, new File("/sdcard/XHILAR8/lockscreen_bg.png"));
		}
	}

}
