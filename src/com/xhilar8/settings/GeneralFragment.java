
package com.xhilar8.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GeneralFragment extends Fragment {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b) {
        super.onCreateView(li, parent, b);
        return li.inflate(R.layout.fragment_general, parent, false);
    }

}
