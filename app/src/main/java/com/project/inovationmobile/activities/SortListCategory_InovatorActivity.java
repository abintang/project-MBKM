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
import com.project.inovationmobile.adapters.ContentInovatorAdapter;
import com.project.inovationmobile.fragments.KategoriInovatorFragment;
import com.project.inovationmobile.models.ListInovatorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class SortListCategory_InovatorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ContentInovatorAdapter contentInovatorAdapter;
    ArrayList<ListInovatorModel> items;
    String url;
    ExtendedFloatingActionButton searchButton;
    ShimmerFrameLayout shimmerFrameLayout;
    Button bottomSheetKategoriButton;
    TextView textCategorySort;
    LottieAnimationView lottieAnimationView;
    TextView invalidText, invalidTextDesc;
    CardView buttonPrev, buttonNext;
    View view;
    NestedScrollView nestedScrollView;
    int page = 1;
    int limit;
    int idCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_inovator);

        shimmerFrameLayout = findViewById(R.id.shimmer_inovator_container);

        textCategorySort = findViewById(R.id.text_category_inovator);
        lottieAnimationView = findViewById(R.id.temp_bg_invalid);
        invalidText = findViewById(R.id.invalid_text);
        invalidTextDesc = findViewById(R.id.textView);

        nestedScrollView = findViewById(R.id.scrollView2);
        buttonNext = findViewById(R.id.buttonNextPage);
        buttonPrev = findViewById(R.id.buttonPrevPage);
        view = findViewById(R.id.padBottom);

        // set up RecyclerView List Inovator
        recyclerView = findViewById(R.id.recycleViewListInovator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        idCategory = getIntent().getExtras().getInt("tempCategoryId");
        Log.d("Check id - > ", "ID: " + idCategory);
        String categoryName = getIntent().getExtras().getString("tempCategoryName");
        textCategorySort.setText(categoryName);

        url = "https://api.koys.my.id/inovator/kategori/" + idCategory;

        RequestQueue queue = Volley.newRequestQueue(SortListCategory_InovatorActivity.this);
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
                            buttonNext.setVisibility(View.GONE);
                            buttonPrev.setVisibility(View.GONE);
                            shimmerFrameLayout.setVisibility(View.VISIBLE);
                            shimmerFrameLayout.startShimmer();
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
                Toast.makeText(SortListCategory_InovatorActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(jsonObjectRequest);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_inovator);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchButton = findViewById(R.id.search_button_inovator);
        ExtendedFloatingActionButton searchButton = findViewById(R.id.search_button_inovator);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SortListCategory_InovatorActivity.this, SearchInovatorActivity.class);
                overridePendingTransition(0, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        bottomSheetKategoriButton = findViewById(R.id.buttonsheet_categoryinovator);
        bottomSheetKategoriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(view);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SortListCategory_InovatorActivity.this, SearchInovatorActivity.class);
                overridePendingTransition(0, 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

    }


    public void showBottomSheet(View view) {
        KategoriInovatorFragment addPhotoBottomDialogFragment =
                KategoriInovatorFragment.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                KategoriInovatorFragment.TAG);
    }

    private void getData(int page, int limit){
        String url2 = "https://api.koys.my.id/inovator/kategori/"+ idCategory +"/paginate?page=" + page;
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
                            if (page == 1  && limit != 1) {
                                buttonNext.setVisibility(View.VISIBLE);
                                buttonPrev.setVisibility(View.GONE);
                            } else if (page == limit && limit != 1) {
                                buttonNext.setVisibility(View.INVISIBLE);
                                buttonPrev.setVisibility(View.VISIBLE);
                            } else if (limit == 1) {
                                buttonNext.setVisibility(View.GONE);
                                buttonPrev.setVisibility(View.GONE);
                            }

                            recyclerView.setVisibility(View.VISIBLE);
                            view.setVisibility(View.VISIBLE);

                            for (int i = 0; i < jsonArray.length() ; i++) {
                                ListInovatorModel listInovatorModel = new ListInovatorModel();
                                JSONObject object = jsonArray.getJSONObject(i);

                                listInovatorModel.setId_inovator(object.getInt("id_inovator"));
                                listInovatorModel.setNama_inovator(object.getString("nama_inovator"));
                                listInovatorModel.setAlamat_inovator(object.getString("alamat"));
                                listInovatorModel.setFotoInovator(object.getString("foto_inovator"));
                                if (object.isNull("id_kategori_inovator")) {
                                    listInovatorModel.setKategoriInovator("Tidak Berkategori");
                                } else {
                                    JSONObject object1 = object.getJSONObject("kategori");
                                    listInovatorModel.setKategoriInovator(object1.getString("nama_kategori_inovator"));
                                }

                                items.add(listInovatorModel);
                            }
                            contentInovatorAdapter = new ContentInovatorAdapter(getApplicationContext(), items);
                            recyclerView.setAdapter(contentInovatorAdapter);
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

}