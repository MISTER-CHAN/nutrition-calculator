package com.misterchan.nutritioncalculator;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavActionBuilder;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavType;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.misterchan.nutritioncalculator.databinding.ActivityMainBinding;
import com.misterchan.nutritioncalculator.ui.FoodListFragment;

import kotlin.jvm.JvmClassMappingKt;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private void loadNavigation(NavController navController) {
        FragmentNavigator fragmentNavigator = navController.getNavigatorProvider().getNavigator(FragmentNavigator.class);
        for (int i = 0; i < Lists.I.categoryNames.size(); ++i) {
            int itemId = 1000020 + i;
            String name = Lists.I.categoryNames.get(i);
            binding.navView.getMenu().add(R.id.group, itemId, Menu.NONE, name);
            FragmentNavigator.Destination dest =
                    new FragmentNavigatorDestinationBuilder(fragmentNavigator, name + "ListFragment", JvmClassMappingKt.getKotlinClass(FoodListFragment.class))
                            .build();
            dest.setId(itemId);
            dest.setLabel(name);
            dest.addArgument("data", new NavArgument.Builder().setDefaultValue(Lists.I.foods.get(i).toArray(new String[0])).build());
            navController.getGraph().addDestination(dest);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Lists.I.populate(getAssets());

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        loadNavigation(navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}