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
import com.project.inovationmobile.adapters.ContentInovatorAdapter;
import com.project.inovationmobile.models.ContentLatestModel;
import com.project.inovationmobile.models.ListInovatorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class SearchInovatorActivity extends AppCompatActivity {

    AppCompatEditText searchFieldInovator;
    View backgroundResultInovator;
    ImageView iconResultInovator;
    TextView inputSearchResultInovator, emptyText, textResultEmpty;;
    RecyclerView recyclerView;
    ContentInovatorAdapter contentInovatorAdapter;
    ArrayList<ListInovatorModel> items;
    String url;
    ShimmerFrameLayout shimmerFrameLayout;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search_inovator);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_search_inovator);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        searchFieldInovator = mToolbar.findViewById(R.id.searchinovator_src_text);
        backgroundResultInovator = findViewById(R.id.bg_result_search_inovator);
        iconResultInovator = findViewById(R.id.icon_result_inovator);
        inputSearchResultInovator = findViewById(R.id.text_result_search_inovator);
        recyclerView = findViewById(R.id.recycleViewSearchInovator);
        shimmerFrameLayout = findViewById(R.id.shimmer_search_inovator_container);
        emptyText = findViewById(R.id.text_empty_search);
        textResultEmpty = findViewById(R.id.textView);
        lottieAnimationView = findViewById(R.id.temp_bg_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contentInovatorAdapter);

        searchFieldInovator.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                recyclerView.setVisibility(View.INVISIBLE);
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String textPencarianInovator = searchFieldInovator.getText().toString();
                    if(!textPencarianInovator.isEmpty()) {
                        inputSearchResultInovator.setText("Hasil Pencarian: "+textPencarianInovator);
                        backgroundResultInovator.setVisibility(View.VISIBLE);
                        iconResultInovator.setVisibility(View.VISIBLE);
                        emptyText.setVisibility(View.GONE);
                        textResultEmpty.setVisibility(View.GONE);
                        lottieAnimationView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.INVISIBLE);
                        shimmerFrameLayout.setVisibility(View.VISIBLE);
                        url = "https://api.koys.my.id/inovator/search/" + textPencarianInovator;
                        getData();
                    } else {
                        inputSearchResultInovator.setText("");
                        backgroundResultInovator.setVisibility(View.INVISIBLE);
                        iconResultInovator.setVisibility(View.INVISIBLE);
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
                                    ListInovatorModel listInovatorModel = new ListInovatorModel();
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    listInovatorModel.setId_inovator(object.getInt("id_inovator"));
                                    listInovatorModel.setNama_inovator(object.getString("nama_inovator"));
                                    listInovatorModel.setFotoInovator(object.getString("foto_inovator"));
                                    listInovatorModel.setAlamat_inovator(object.getString("alamat"));
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