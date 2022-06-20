package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentInovasiAdapter;
import com.project.inovationmobile.models.ContentLatestModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class SearchInovasiActivity extends AppCompatActivity {

    AppCompatEditText searchFieldInovasi;
    View backgroundResult;
    ImageView iconResult;
    TextView inputSearchResult, emptyText, textResultEmpty;
    String url;
    ArrayList<ContentLatestModel> items;
    RecyclerView recyclerView;
    ContentInovasiAdapter contentInovasiAdapter;
    ShimmerFrameLayout shimmerFrameLayout;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_inovasi);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_search_inovasi);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        searchFieldInovasi = mToolbar.findViewById(R.id.searchinovasi_src_text);
        backgroundResult = findViewById(R.id.bg_result_search);
        iconResult = findViewById(R.id.icon_result);
        inputSearchResult = findViewById(R.id.text_result_search);
        recyclerView = findViewById(R.id.recycleViewSearchInovasi);
        shimmerFrameLayout = findViewById(R.id.shimmer_search_inovasi_container);
        emptyText = findViewById(R.id.text_empty_search);
        textResultEmpty = findViewById(R.id.textView);
        lottieAnimationView = findViewById(R.id.temp_bg_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contentInovasiAdapter);


        searchFieldInovasi.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String textPencarianInovasi = searchFieldInovasi.getText().toString();
                    recyclerView.setVisibility(View.INVISIBLE);
                    if(!textPencarianInovasi.isEmpty()) {
                        inputSearchResult.setText("Hasil Pencarian: "+textPencarianInovasi);
                        backgroundResult.setVisibility(View.VISIBLE);
                        iconResult.setVisibility(View.VISIBLE);
                        emptyText.setVisibility(View.GONE);
                        textResultEmpty.setVisibility(View.GONE);
                        lottieAnimationView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        shimmerFrameLayout.setVisibility(View.VISIBLE);
                        url = "https://api.koys.my.id/inovasi/search/" + textPencarianInovasi;

                        Log.e("FIELD", "onEditorAction: " + url);
                        getData();



                    } else {
                        inputSearchResult.setText("");
                        recyclerView.setVisibility(View.INVISIBLE);
                        backgroundResult.setVisibility(View.INVISIBLE);
                        iconResult.setVisibility(View.INVISIBLE);
                        lottieAnimationView.setProgress(0);
                        lottieAnimationView.playAnimation();
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        textResultEmpty.setVisibility(View.VISIBLE);
                        emptyText.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            items = new ArrayList<>();
                            if (!jsonObject.has("data")){
                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.INVISIBLE);
                                lottieAnimationView.setProgress(0);
                                lottieAnimationView.playAnimation();
                                lottieAnimationView.setVisibility(View.VISIBLE);
                                emptyText.setText("Hasil Pencarian Kosong");
                                textResultEmpty.setText("Whoops... keyword yang kamu cari tidak dapat ditemukan");
                                textResultEmpty.setVisibility(View.VISIBLE);
                                emptyText.setVisibility(View.VISIBLE);

                            } else {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);
    }

}