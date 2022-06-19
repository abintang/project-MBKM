package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentInovasiAdapter;
import com.project.inovationmobile.adapters.ContentInovatorAdapter;
import com.project.inovationmobile.fragments.KategoriInovasiFragment;
import com.project.inovationmobile.fragments.KategoriInovatorFragment;
import com.project.inovationmobile.models.ContentLatestModel;
import com.project.inovationmobile.models.ListInovatorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class ListInovatorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContentInovatorAdapter contentInovatorAdapter;
    ArrayList<ListInovatorModel> items;
    String url = "https://api.koys.my.id/inovator";
    ExtendedFloatingActionButton searchButton;
    ShimmerFrameLayout shimmerFrameLayout;
    Button bottomSheetKategoriButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_inovator);

        shimmerFrameLayout = findViewById(R.id.shimmer_inovator_container);

        // set up RecyclerView List Inovator
        recyclerView = findViewById(R.id.recycleViewListInovator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contentInovatorAdapter);

        getData();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_inovator);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchButton = findViewById(R.id.search_button_inovator);
        ExtendedFloatingActionButton searchButton = findViewById(R.id.search_button_inovator);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListInovatorActivity.this, SearchInovatorActivity.class);
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
                Intent intent = new Intent(ListInovatorActivity.this, SearchInovatorActivity.class);
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
                            for (int i = 0; i < jsonArray.length() ; i++) {
                                ListInovatorModel listInovatorModel = new ListInovatorModel();
                                JSONObject object = jsonArray.getJSONObject(i);

                                listInovatorModel.setId_inovator(object.getInt("id_inovator"));
                                listInovatorModel.setNama_inovator(object.getString("nama_inovator"));
                                listInovatorModel.setAlamat_inovator(object.getString("alamat"));
                                if (object.isNull("kategori_inovator")) {
                                    listInovatorModel.setKategoriInovator("Tidak Berkategori");
                                } else {
                                    JSONObject object1 = object.getJSONObject("kategori_inovator");
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