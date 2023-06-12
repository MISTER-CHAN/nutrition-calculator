package com.misterchan.nutritioncalculator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.misterchan.nutritioncalculator.databinding.ActivityMainBinding;
import com.misterchan.nutritioncalculator.ui.FoodListFragment;

import java.util.List;
import java.util.Objects;

import kotlin.jvm.JvmClassMappingKt;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;

    private void loadNavigation(NavController navController) {
        FragmentNavigator fragmentNavigator = navController.getNavigatorProvider().getNavigator(FragmentNavigator.class);
        Menu navMenu = binding.navView.getMenu();
        for (int i = 0; i < Lists.I.categoryNames.size(); ++i) {
            int itemId = 1000100 + i;
            String name = Lists.I.categoryNames.get(i);
            navMenu.add(R.id.group_nav, itemId, Menu.NONE, name);
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
        ;

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
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        loadNavigation(navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Menu ageGenderMenu = menu.findItem(R.id.action_age_gender).getSubMenu();
        List<Fragment> fragments = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main).getChildFragmentManager().getFragments();
        for (String name : Lists.I.ageGenderNames) {
            MenuItem menuItem = ageGenderMenu.add(R.id.group_age_gender, Menu.NONE, Menu.NONE, name);
            menuItem.setCheckable(true);
            menuItem.setOnMenuItemClickListener(item -> {
                Lists.I.selectedAgeGender = name;
                if (navController.getCurrentDestination().getId() == R.id.nav_home) {
                    ((BaseAdapter) ((ListFragment) fragments.get(0)).getListAdapter()).notifyDataSetChanged();
                }
                for (int i = 0; i < Lists.I.ageGenderNames.size(); ++i) {
                    ageGenderMenu.getItem(i).setChecked(false);
                }
                item.setChecked(true);
                return true;
            });
            if (Objects.equals(name, Lists.I.selectedAgeGender)) {
                menuItem.setChecked(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_pick_date) {
            if (navController.getCurrentDestination().getId() != R.id.nav_calendar) {
                navController.navigate(R.id.nav_calendar);
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}