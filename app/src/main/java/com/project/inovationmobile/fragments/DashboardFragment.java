package com.project.inovationmobile.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.inovationmobile.activities.ListInovasiActivity;
import com.project.inovationmobile.activities.ListInovatorActivity;
import com.project.inovationmobile.activities.PetaActivity;
import com.project.inovationmobile.adapters.ContentLatestAdapter;
import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.SliderAdapter;
import com.project.inovationmobile.models.ContentLatestModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    SliderView sliderView;
    ArrayList<ContentLatestModel> items;
    String url = "https://run.mocky.io/v3/5fa76df8-45a1-4969-99c5-1dfdf4623617";
    int[] banners = {
            R.drawable.banner_1,
            R.drawable.banner_2,
            R.drawable.banner_3,
            R.drawable.banner_4,
            R.drawable.banner_5,
            R.drawable.banner_6
    };

    RecyclerView recyclerView;
    ContentLatestAdapter contentLatestAdapter;

    ShimmerFrameLayout shimmerFrameLayout;


    TextView greetText;

    FloatingActionButton btnInovasi,btnInovator,btnPeta;

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


        // Component list Inovator pada Dashboard melalui Recycleview
        recyclerView = rootView.findViewById(R.id.recycleViewContentLatest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       /* contentLatestAdapter = new ContentLatestAdapter(getActivity(),items);*/

        shimmerFrameLayout = rootView.findViewById(R.id.shimmer_dashboard_container);


        getData();


        // Intent button pada Dashboard
        btnInovator = rootView.findViewById(R.id.button_inovator);
        btnInovasi = rootView.findViewById(R.id.button_inovasi);
        btnPeta = rootView.findViewById(R.id.button_peta);


        btnInovator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListInovatorActivity.class));
            }
        });

        btnInovasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListInovasiActivity.class));
            }
        });

        btnPeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PetaActivity.class));
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    // Function yang digunakan untuk menentukan greeting pada Dashboard
    private void greetingDashboard(){
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

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                ContentLatestModel contentLatestModel = new ContentLatestModel();
                                JSONObject object = jsonArray.getJSONObject(i);

                                contentLatestModel.setId_inovasi(object.getInt("id_inovasi"));
                                contentLatestModel.setNama_inovasi(object.getString("nama_inovasi"));
                                contentLatestModel.setNama_inovator(object.getString("nama_kelompok"));

                                items.add(contentLatestModel);
                            }
                            contentLatestAdapter = new ContentLatestAdapter(getActivity(), items);
                            recyclerView.setAdapter(contentLatestAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }
}
