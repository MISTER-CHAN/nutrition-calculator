package com.misterchan.nutritioncalculator;

import android.content.res.AssetManager;

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

    public final Map<String, float[]> nutrientMap = new HashMap<>();
    public final Set<String> checkedFoods = new HashSet<>();
    public final List<List<String>> foods = new ArrayList<>();
    public final List<String> categoryNames = new ArrayList<>();
    public String[] nutrientNames;

    public void populate(AssetManager assets) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(assets.open("nutrients.txt")))) {
            String line = br.readLine();
            {
                String[] arr = line.split("\\t");
                nutrientNames = Arrays.copyOfRange(arr, 1, arr.length);
            }
            while ((line = br.readLine()) != null) {
                String[] arr = line.split("\\t");
                if (arr.length == 1) {
                    categoryNames.add(line);
                    foods.add(new ArrayList<>());
                } else if (arr.length > 1) {
                    foods.get(foods.size() - 1).add(arr[0]);
                    float[] nutrients = new float[arr.length - 1];
                    for (int i = 0; i < arr.length - 1; ++i) {
                        nutrients[i] = Float.parseFloat(arr[i + 1]);
                    }
                    nutrientMap.put(arr[0], nutrients);
                }
            }
        } catch (IOException e) {
        }
    }
}
