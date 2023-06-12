package com.misterchan.nutritioncalculator.ui;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class Adapter<T, B> extends ArrayAdapter<T> {
    protected final class ViewHolder {
        public final B binding;

        public ViewHolder(B binding) {
            this.binding = binding;
        }
    }

    public Adapter(@NonNull Context context, int resource, @NonNull T[] objects) {
        super(context, resource, objects);
    }
}
