
package com.xhilar8.settings;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class NavigationbarFragment extends Fragment {

    SharedPreferences pref;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
    }

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b) {
        super.onCreateView(li, parent, b);
        pref = this.getActivity().getSharedPreferences("pref",
                Context.MODE_WORLD_READABLE);

        View root = li.inflate(R.layout.fragment_navigationbar, parent, false);
        Spinner buttonColour = (Spinner) root
                .findViewById(R.id.navbar_background_colour);
        buttonColour.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                    int position, long id) {
                Editor editor = pref.edit();
                editor.putInt("navbar_background_colour",
                        Util.getSpinnerColourCode(position));
                editor.apply();
                Toast.makeText(NavigationbarFragment.this.getActivity(),
                        "Restart SystemUI for changes to take effect",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        return root;
    }

}
