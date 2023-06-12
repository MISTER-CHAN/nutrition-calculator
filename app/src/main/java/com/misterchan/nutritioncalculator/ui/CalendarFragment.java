package com.misterchan.nutritioncalculator.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.misterchan.nutritioncalculator.Lists;
import com.misterchan.nutritioncalculator.databinding.FragmentCalendarBinding;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR), m = calendar.get(Calendar.MONTH), d = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.set(y, m, d);
        binding.cv.setDate(calendar.getTimeInMillis());
        binding.cv.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar c = Calendar.getInstance();
            c.clear();
            c.set(year, month, dayOfMonth);
            view.setDate(c.getTimeInMillis());
        });
        binding.bLoad.setOnClickListener(v -> {
            Lists.I.checkedFoods.put(0L, new HashSet<>(Lists.I.checkedFoods.getOrDefault(binding.cv.getDate(), new HashSet<>())));
            getActivity().onBackPressed();
        });
        binding.bSave.setOnClickListener(v -> {
            Lists.I.checkedFoods.put(binding.cv.getDate(), new HashSet<>(Lists.I.checkedFoods.get(0L)));
            getActivity().onBackPressed();
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
