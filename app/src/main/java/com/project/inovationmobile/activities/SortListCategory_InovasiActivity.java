package com.project.inovationmobile.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
import com.project.inovationmobile.fragments.KategoriInovasiFragment;
import com.project.inovationmobile.models.ContentLatestModel;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class SortListCategory_InovasiActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ContentInovasiAdapter contentInovasiAdapter;
    ArrayList<ContentLatestModel> items;
    String url;
    ExtendedFloatingActionButton searchButton;
    ShimmerFrameLayout shimmerFrameLayout;
    TextView textCategorySort;
    NestedScrollView nestedScrollView;
    CardView buttonPrev, buttonNext;
    View view;
    PowerSpinnerView powerSpinnerView;
    int page = 1;
    int limit;
    LottieAnimationView lottieAnimationView;
    TextView invalidText, invalidTextDesc;
    int idCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_inovasi);
        Button bottomSheetKategoriButton = findViewById(R.id.buttonsheet_categoryinovasi);

        textCategorySort = findViewById(R.id.text_category_inovasi);
        idCategory = getIntent().getExtras().getInt("tempCategoryId");
        Log.d("Check id - > ", "ID: " + idCategory);
        String categoryName = getIntent().getExtras().getString("tempCategoryName");
        textCategorySort.setText(categoryName);
        lottieAnimationView = findViewById(R.id.temp_bg_invalid);
        invalidText = findViewById(R.id.invalid_text);
        invalidTextDesc = findViewById(R.id.textView);

        url = "https://api.koys.my.id/inovasi/kategori/" + idCategory;

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
                Intent intent = new Intent(getApplicationContext(), SortYearCategory_InovasiActivity.class);
                intent.putExtra("tempYear", powerSpinnerView.getText());
                intent.putExtra("tempCategoryId", idCategory);
                intent.putExtra("tempCategoryName", categoryName);
                startActivity(intent);
            }
        });


        shimmerFrameLayout = findViewById(R.id.shimmer_inovasi_container);

        // set up RecyclerView List Inovasi
        recyclerView = findViewById(R.id.recycleViewListInovasi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nestedScrollView = findViewById(R.id.scrollView2);

        RequestQueue queue = Volley.newRequestQueue(SortListCategory_InovasiActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (!response.has("data")){
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        invalidText.setVisibility(View.VISIBLE);
                        invalidTextDesc.setText("Data dengan kategori " + textCategorySort.getText() + " tidak dapat ditemukan");
                        invalidTextDesc.setVisibility(View.VISIBLE);
                    }
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
                Toast.makeText(SortListCategory_InovasiActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(jsonObjectRequest);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_inovasi);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchButton = findViewById(R.id.search_button_inovasi);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SortListCategory_InovasiActivity.this, SearchInovasiActivity.class);
                overridePendingTransition(0,0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

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
        String url2 = "https://api.koys.my.id/inovasi/kategori/"+ idCategory +"/paginate?page=" + page;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
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
                            }
                            contentInovasiAdapter = new ContentInovasiAdapter(getApplicationContext(), items);
                            recyclerView.setAdapter(contentInovasiAdapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);
    }

}