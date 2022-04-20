package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.project.inovationmobile.adapters.ContentLatestAdapter;
import com.project.inovationmobile.R;
import com.project.inovationmobile.fragments.AboutFragment;
import com.project.inovationmobile.fragments.DashboardFragment;
import com.project.inovationmobile.fragments.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*SliderView sliderView;
    int[] banners = {
            R.drawable.banner_1,
            R.drawable.banner_2
    };*/

    RecyclerView recyclerView;
    ContentLatestAdapter contentLatestAdapter;
    ArrayList<String> items;

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


      /*  sliderView = findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(banners);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();*/


//        items = new ArrayList<>();
//        for (int i = 0; i < 3 ; i++) {
//            items.add("Placeholder Title Text");
//        }
//
//
//        recyclerView = findViewById(R.id.recycleViewContentLatest);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        contentLatestAdapter = new ContentLatestAdapter(this,items);
//        recyclerView.setAdapter(contentLatestAdapter);
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