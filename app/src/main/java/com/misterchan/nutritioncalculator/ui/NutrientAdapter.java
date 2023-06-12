package com.misterchan.nutritioncalculator.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.misterchan.nutritioncalculator.Lists;
import com.misterchan.nutritioncalculator.R;
import com.misterchan.nutritioncalculator.databinding.ItemNutrientBinding;

public class NutrientAdapter extends Adapter<String, ItemNutrientBinding> {

    public NutrientAdapter(Context context) {
        super(context, R.layout.item_nutrient, Lists.I.nutrientNames);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            ItemNutrientBinding binding = ItemNutrientBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            holder = new ViewHolder(binding);
            convertView = binding.getRoot();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String nutrientName = Lists.I.nutrientNames[position];
        holder.binding.tvNutrientLabel.setText(nutrientName);

        float nutrient = 0.0f;
        for (String food : Lists.I.checkedFoods.get(0L)) {
            nutrient += Lists.I.nutrientMap.get(food)[position];
        }
        holder.binding.tvNutrient.setText(String.valueOf(nutrient));
        holder.binding.tvSuggestion.setText(Lists.I.suggestionMap.get(Lists.I.selectedAgeGender)[position]);

        return convertView;
    }
}
