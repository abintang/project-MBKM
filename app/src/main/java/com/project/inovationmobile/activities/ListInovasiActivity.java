package com.project.inovationmobile.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentInovasiAdapter;
import com.project.inovationmobile.adapters.ContentLatestAdapter;
import com.project.inovationmobile.fragments.KategoriInovasiFragment;
import com.project.inovationmobile.fragments.RegisterFragment;
import com.project.inovationmobile.models.ContentLatestModel;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import me.ibrahimsn.lib.CirclesLoadingView;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class ListInovasiActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContentInovasiAdapter contentInovasiAdapter;
    ArrayList<ContentLatestModel> items;
    String url = "https://api.koys.my.id/inovasi";
    ExtendedFloatingActionButton searchButton;
    ShimmerFrameLayout shimmerFrameLayout;
    ProgressBar loadingPage;
    NestedScrollView nestedScrollView;
    int page = 1;
    int limit;
    private Parcelable parcelable;
    PowerSpinnerView powerSpinnerView;
    CardView buttonPrev, buttonNext;
    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_inovasi);
        Button bottomSheetKategoriButton = findViewById(R.id.buttonsheet_categoryinovasi);

        shimmerFrameLayout = findViewById(R.id.shimmer_inovasi_container);
        // set up RecyclerView List Inovasi
        recyclerView = findViewById(R.id.recycleViewListInovasi);

        /*progressBar = findViewById(R.id.progress_inovasi);*/
        nestedScrollView = findViewById(R.id.scrollView2);
        buttonNext = findViewById(R.id.buttonNextPage);
        buttonPrev = findViewById(R.id.buttonPrevPage);
        view = findViewById(R.id.padBottom);

        powerSpinnerView = findViewById(R.id.spinner_year);
        ArrayList<String> years = new ArrayList<String>();
        int thisYears = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2016; i <= thisYears ; i++) {
            years.add(Integer.toString(i));
        }

        powerSpinnerView.setItems(years);

        powerSpinnerView.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener<String>() {
            @Override
            public void onItemSelected(int i, @Nullable String s, int i1, String t1) {
                Intent intent = new Intent(getApplicationContext(), SortYear_InovasiActivity.class);
                intent.putExtra("tempYear", powerSpinnerView.getText());
                startActivity(intent);
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_inovasi);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchButton = findViewById(R.id.search_button_inovasi);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListInovasiActivity.this, SearchInovasiActivity.class);
                overridePendingTransition(0,0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(ListInovasiActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int max = response.getInt("total_data");
                    limit = (max + 20 - 1) / 20;
                    getData(page, limit);
                    buttonNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            page++;
                            recyclerView.setVisibility(View.GONE);
                            buttonNext.setVisibility(View.GONE);
                            buttonPrev.setVisibility(View.GONE);
                            shimmerFrameLayout.setVisibility(View.VISIBLE);
                            shimmerFrameLayout.startShimmer();
                            getData(page, limit);
                            nestedScrollView.scrollTo(0, 0);
                        }
                    });

                    buttonPrev.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            page--;
                            recyclerView.setVisibility(View.INVISIBLE);
                            shimmerFrameLayout.setVisibility(View.VISIBLE);
                            shimmerFrameLayout.startShimmer();
                            buttonNext.setVisibility(View.GONE);
                            buttonPrev.setVisibility(View.GONE);
                            getData(page, limit);
                            nestedScrollView.scrollTo(0, 0);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListInovasiActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(jsonObjectRequest);


        bottomSheetKategoriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(view);
            }
        });

    }

    public void showBottomSheet(View view) {
        KategoriInovasiFragment addPhotoBottomDialogFragment =
                KategoriInovasiFragment.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                KategoriInovasiFragment.TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    private void getData(int page, int limit){

        String url2 = "https://api.koys.my.id/inovasi/paginate?page=" + page;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            recyclerView.setVisibility(View.VISIBLE);
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            buttonNext.setVisibility(View.VISIBLE);
                            buttonPrev.setVisibility(View.VISIBLE);
                            if (page == 1 ) {
                                buttonNext.setVisibility(View.VISIBLE);
                                buttonPrev.setVisibility(View.GONE);
                            } else {
                                buttonPrev.setVisibility(View.VISIBLE);
                            }

                            if (page == limit) {
                                buttonNext.setVisibility(View.INVISIBLE);
                                buttonPrev.setVisibility(View.VISIBLE);
                            }

                            recyclerView.setVisibility(View.VISIBLE);
                            view.setVisibility(View.VISIBLE);

                            for (int i = 0; i < jsonArray.length() ; i++) {
                                ContentLatestModel contentLatestModel = new ContentLatestModel();
                                JSONObject object = jsonArray.getJSONObject(i);

                                contentLatestModel.setId_inovasi(object.getInt("id_inovasi"));
                                contentLatestModel.setNama_inovasi(object.getString("nama_inovasi"));
                                contentLatestModel.setUrlGambar(object.getString("foto_inovasi1"));

                                JSONObject object1 = object.getJSONObject("inovator");
                                contentLatestModel.setNama_inovator(object1.getString("nama_inovator"));

                                JSONObject object2 = object.getJSONObject("bidang");
                                contentLatestModel.setKategoriInovasi(object2.getString("nama_bidang_inovasi"));
                                /*contentLatestModel.setNama_inovator(object.getString("nama_kelompok"));*/

                                items.add(contentLatestModel);
                                contentInovasiAdapter = new ContentInovasiAdapter(getApplicationContext(), items);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ListInovasiActivity.this));
                                recyclerView.setAdapter(contentInovasiAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i("TEST", "onErrorResponse: " + error.getMessage());
                    }
                });


        requestQueue.add(stringRequest);

    }

}