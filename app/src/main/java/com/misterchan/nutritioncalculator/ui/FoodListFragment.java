package com.misterchan.nutritioncalculator.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.misterchan.nutritioncalculator.Lists;
import com.misterchan.nutritioncalculator.MainActivity;
import com.misterchan.nutritioncalculator.databinding.FragmentFoodBinding;

public class FoodListFragment extends ListFragment {
    private FragmentFoodBinding binding;

    private final FoodAdapter.OnListItemCheckedChangedListener onListItemCheckedChangedListener = (food, isChecked) -> {
        if (isChecked) {
            Lists.I.checkedFoods.get(0L).add(food);
        } else {
            Lists.I.checkedFoods.get(0L).remove(food);
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFoodBinding.inflate(inflater, container, false);
        String[] data = getArguments().getStringArray("data");
        Context context = inflater.getContext();
        setListAdapter(new FoodAdapter(context, data, onListItemCheckedChangedListener));
        ((MainActivity) context).miTips.setVisible(false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}