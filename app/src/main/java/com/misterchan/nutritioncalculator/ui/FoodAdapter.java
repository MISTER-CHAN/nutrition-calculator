package com.misterchan.nutritioncalculator.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.misterchan.nutritioncalculator.Lists;
import com.misterchan.nutritioncalculator.R;
import com.misterchan.nutritioncalculator.databinding.ItemFoodBinding;

public class FoodAdapter extends Adapter<String, ItemFoodBinding> {
    interface OnListItemCheckedChangedListener {
        void onCheckedChanged(String food, boolean isChecked);
    }

    private OnListItemCheckedChangedListener onListItemCheckedChangedListener;

    public FoodAdapter(Context context, String[] data, OnListItemCheckedChangedListener l) {
        super(context, R.layout.item_food, data);
        onListItemCheckedChangedListener = l;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = parent.getContext();
        ViewHolder holder;
        if (convertView == null) {
            ItemFoodBinding binding = ItemFoodBinding.inflate(LayoutInflater.from(context), parent, false);
            holder = new ViewHolder(binding);
            convertView = binding.getRoot();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String food = getItem(position);
        holder.binding.tv.setText(food);
        holder.binding.cb.setOnCheckedChangeListener(null);
        holder.binding.cb.setChecked(Lists.I.checkedFoods.contains(food));
        holder.binding.cb.setOnCheckedChangeListener((buttonView, isChecked) ->
                onListItemCheckedChangedListener.onCheckedChanged(food, isChecked));
        return convertView;
    }
}
