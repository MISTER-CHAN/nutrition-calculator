package com.misterchan.nutritioncalculator.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import com.misterchan.nutritioncalculator.Lists;
import com.misterchan.nutritioncalculator.MainActivity;
import com.misterchan.nutritioncalculator.databinding.FragmentHomeBinding;

public class HomeFragment extends ListFragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        Context context = inflater.getContext();
        setListAdapter(new NutrientAdapter(context));
        MenuItem miTips = ((MainActivity) context).miTips;
        if (miTips != null) {
            miTips.setVisible(true);
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}