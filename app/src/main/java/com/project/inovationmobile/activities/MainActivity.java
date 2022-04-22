package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.project.inovationmobile.R;
import com.project.inovationmobile.fragments.AboutFragment;
import com.project.inovationmobile.fragments.DashboardFragment;
import com.project.inovationmobile.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        chipNavigationBar.setItemSelected(R.id.dashboard,
                true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainDisplay,
                        new DashboardFragment()).commit();
        bottomMenu();

    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        Fragment fragment = null;
                        switch (i){
                            case R.id.dashboard:
                                fragment = new DashboardFragment();
                                break;
                            case R.id.search:
                                fragment = new SearchFragment();
                                break;
                            case R.id.about:
                                fragment = new AboutFragment();
                                break;
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.mainDisplay,
                                        fragment).commit();
                    }
                });
    }
}