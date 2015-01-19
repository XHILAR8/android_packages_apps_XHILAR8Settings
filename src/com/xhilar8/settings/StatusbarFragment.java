
package com.xhilar8.settings;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class StatusbarFragment extends Fragment {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
    }

    CheckBox allowSBColour;
    EditText SBSize;

    SharedPreferences pref;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b) {
        super.onCreateView(li, parent, b);

        pref = this.getActivity().getSharedPreferences("pref", Context.MODE_WORLD_READABLE);

        View root = li.inflate(R.layout.fragment_statusbar, parent, false);

        allowSBColour = (CheckBox) root.findViewById(R.id.allow_sb_colour);
        SBSize = (EditText) root.findViewById(R.id.sb_size);

        allowSBColour.setChecked(pref.getBoolean("allowColour", true));
        // SBSize.setText(Integer.toString(pref.getInt("sb_size", 25)));
        SBSize.setVisibility(View.GONE);

        root.findViewById(R.id.statusbar_save).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // int size = Integer.parseInt(SBSize.getText().toString());
                boolean allowColour = allowSBColour.isChecked();
                Editor editor = pref.edit();
                // editor.putInt("sb_size", size);
                editor.putBoolean("allowStatusbarColour", allowColour);
                editor.apply();
                Toast.makeText(StatusbarFragment.this.getActivity(), "Settings Saved",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

}
