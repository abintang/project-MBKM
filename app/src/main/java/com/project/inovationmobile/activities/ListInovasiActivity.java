package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentInovasiAdapter;
import com.project.inovationmobile.adapters.ContentLatestAdapter;
import com.project.inovationmobile.fragments.KategoriInovasiFragment;
import com.project.inovationmobile.fragments.RegisterFragment;
import com.project.inovationmobile.models.ContentLatestModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ListInovasiActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContentInovasiAdapter contentInovasiAdapter;
    ArrayList<ContentLatestModel> items;
    String url = "https://api.koys.my.id/inovasi";
    ExtendedFloatingActionButton searchButton;
    ShimmerFrameLayout shimmerFrameLayout;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_inovasi);
        Button bottomSheetKategoriButton = findViewById(R.id.buttonsheet_categoryinovasi);

        shimmerFrameLayout = findViewById(R.id.shimmer_inovasi_container);

        // set up RecyclerView List Inovasi
        recyclerView = findViewById(R.id.recycleViewListInovasi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contentInovasiAdapter);
        getData();

        /*progressBar = findViewById(R.id.progress_inovasi);*/
        nestedScrollView = findViewById(R.id.scrollView2);

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

    private void getData(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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

                            for (int i = 0; i < jsonArray.length() ; i++) {
                                ContentLatestModel contentLatestModel = new ContentLatestModel();
                                JSONObject object = jsonArray.getJSONObject(i);

                                contentLatestModel.setId_inovasi(object.getInt("id_inovasi"));
                                contentLatestModel.setNama_inovasi(object.getString("nama_inovasi"));
                                contentLatestModel.setUrlGambar(object.getString("foto_inovasi"));

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


        requestQueue.add(stringRequest);
    }

}