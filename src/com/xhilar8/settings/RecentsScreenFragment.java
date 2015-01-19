
package com.xhilar8.settings;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class RecentsScreenFragment extends Fragment {

    SharedPreferences pref;
    Context c;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        pref = this.getActivity().getSharedPreferences("pref",
                Context.MODE_WORLD_READABLE);
        c = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b) {
        super.onCreateView(li, parent, b);
        View root = li.inflate(R.layout.fragment_recents_screen, parent, false);
        root.findViewById(R.id.recents_clear_all_pos).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        chooseClearAllPos();
                    }
                });
        root.findViewById(R.id.recents_background).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if (i.resolveActivity(getActivity().getPackageManager()) != null)
                            startActivityForResult(i, 1);
                    }
                });
        Spinner searchOptions = (Spinner) root
                .findViewById(R.id.recents_search_options);
        searchOptions.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                Editor editor = pref.edit();
                editor.putInt("recents_search_options", position);
                editor.apply();
                Toast.makeText(c, "Please reboot to apply changes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getActivity();
        if (requestCode == 1 && resultCode == Activity.RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {
                MediaColumns.DATA
            };

            Cursor cursor = getActivity().getContentResolver().query(
                    selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap b = BitmapFactory.decodeFile(picturePath);
            Util.save(b, new File("/sdcard/XHILAR8/recents_bg.png"));
        }
    }

    public void chooseClearAllPos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this.getActivity());
        builder.setTitle("Clear all button");
        String[] options = {
                "Top right", "Top Left", "Bottom Right",
                "Bottom Left"
        };
        builder.setAdapter(new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, options),
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        Editor editor = pref.edit();
                        editor.putInt("clear_all_pos", pos);
                        editor.apply();
                        Toast.makeText(c,
                                "Settings saved. Restarting SystemUI...",
                                Toast.LENGTH_SHORT).show();
                        Util.restartSystemUI();
                    }

                });
        builder.show();
    }

}
