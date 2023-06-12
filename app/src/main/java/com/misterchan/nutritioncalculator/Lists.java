package com.misterchan.nutritioncalculator;

import android.content.res.AssetManager;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Lists {
    public static final Lists I = new Lists(); // Instance

    public final List<List<String>> foods = new ArrayList<>();
    public final List<String> categoryNames = new ArrayList<>();
    public final List<String> ageGenderNames = new ArrayList<>();
    public final Map<String, float[]> nutrientMap = new HashMap<>();
    public final Map<String, String[]> suggestionMap = new HashMap<>();
    public String selectedAgeGender;
    public String[] nutrientNames;

    public final Map<Long, Set<String>> checkedFoods = new HashMap<>() {
        {
            put(0L, new HashSet<>());
        }
    };

    public void populate(AssetManager assets) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("nutrients.txt")))) {
            String line = br.readLine();
            {
                String[] arr = line.split("\\t");
                nutrientNames = Arrays.copyOfRange(arr, 1, arr.length);
            }
            boolean readSuggestion = true;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split("\\t");
                if (arr.length == 1) {
                    categoryNames.add(line);
                    foods.add(new ArrayList<>());
                    readSuggestion = false;
                } else if (arr.length > 1) {
                    if (readSuggestion) {
                        ageGenderNames.add(arr[0]);
                        suggestionMap.put(arr[0], Arrays.copyOfRange(arr, 1, arr.length));
                        if (ageGenderNames.size() == 1) {
                            selectedAgeGender = arr[0];
                        }
                    } else {
                        foods.get(foods.size() - 1).add(arr[0]);
                        float[] nutrients = new float[arr.length - 1];
                        for (int i = 0; i < arr.length - 1; ++i) {
                            nutrients[i] = Float.parseFloat(arr[i + 1]);
                        }
                        nutrientMap.put(arr[0], nutrients);
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
