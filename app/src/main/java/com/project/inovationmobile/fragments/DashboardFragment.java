package com.project.inovationmobile.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.inovationmobile.adapters.ContentLatestAdapter;
import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.DividerDashboardAdapter;
import com.project.inovationmobile.adapters.SliderAdapter;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    SliderView sliderView;
    int[] banners = {
            R.drawable.bappeda,
            R.drawable.banner_2
    };

    RecyclerView recyclerView;
    ContentLatestAdapter contentLatestAdapter;
    ArrayList<String> items;

    TextView greetText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        greetText = rootView.findViewById(R.id.greeting);
        sliderView = rootView.findViewById(R.id.imageSlider);
        SliderAdapter sliderAdapter = new SliderAdapter(banners);

        greetingDashboard();

        // Component Image Slider pada Dashboard
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        items = new ArrayList<>();
        for (int i = 0; i < 3 ; i++) {
            items.add("Placeholder Title Text");
        }

        // Component list Inovator pada Dashboard melalui Recycleview
        recyclerView = rootView.findViewById(R.id.recycleViewContentLatest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contentLatestAdapter = new ContentLatestAdapter(getActivity(),items);
        recyclerView.setAdapter(contentLatestAdapter);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerDashboardAdapter(ContextCompat.getDrawable(requireContext(), R.drawable.divider_dashboard));
        recyclerView.addItemDecoration(dividerItemDecoration);

        // Inflate the layout for this fragment
        return rootView;
    }

    // Function yang digunakan untuk menentukan greeting pada Dashboard
    public void greetingDashboard(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 4 && hour < 11) {
            greetText.setText(R.string.greet_pagi);
        } else if(hour >= 11 && hour < 15) {
            greetText.setText(R.string.greet_siang);
        } else if(hour >= 15 && hour < 18) {
            greetText.setText(R.string.greet_sore);
        } else {
            greetText.setText(R.string.greet_malam);
        }
    }
}