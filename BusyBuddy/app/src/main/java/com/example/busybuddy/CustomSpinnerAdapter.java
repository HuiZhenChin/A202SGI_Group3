package com.example.busybuddy;

import android.content.Context;
import android.widget.ArrayAdapter;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    public CustomSpinnerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public boolean isEnabled(int position) {
        // Disable the first item from Spinner (position 0) which acts as a hint
        return position != 0;
    }
}

