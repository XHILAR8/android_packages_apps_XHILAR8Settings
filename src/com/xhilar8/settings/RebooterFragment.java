
package com.xhilar8.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RebooterFragment extends Fragment {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b) {
        super.onCreateView(li, parent, b);
        View root = li.inflate(R.layout.fragment_rebooter, parent, false);
        root.findViewById(R.id.rebooter_reboot).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Util.reboot(getActivity());
                    }
                });
        root.findViewById(R.id.rebooter_restartSystemUI).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Util.restartSystemUI();
                    }
                });
        root.findViewById(R.id.rebooter_poweroff).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Util.powerOff(getActivity());
                    }
                });
        return root;
    }

}
