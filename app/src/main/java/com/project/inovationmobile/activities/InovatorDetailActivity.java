package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentInovasiAdapter;
import com.project.inovationmobile.adapters.ContentInovasi_in_InovatorAdapter;
import com.project.inovationmobile.models.ContentLatestModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class InovatorDetailActivity extends AppCompatActivity {
    ArrayList<ContentLatestModel> items;

    TextView namaInovatorDetail, emailInovatorDetail, ttlInovatorDetail, genderInovatorDetail, alamatInovatorDetail,
    kategoriInovatorDetail, instansiInovatorDetail;
    CircleImageView fotoInovatorDetail;
    ContentInovasi_in_InovatorAdapter contentInovasi_in_inovatorAdapter;
    String url;
    ShimmerFrameLayout shimmerFrameLayout;
    CardView cardBio, cardImage, cardCategory, cardInovasi;
    String urlInovator, urlInovasiInovator;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inovator_detail);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_detail_inovator);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        shimmerFrameLayout = findViewById(R.id.shimmer_inovator);

        int idInovator = getIntent().getExtras().getInt("tempInovatorId");
        url = "https://api.koys.my.id/inovator/" + idInovator;


        // variable yang udah di declare sesuai dengan id nya masing-masing
        namaInovatorDetail = findViewById(R.id.tv_nama_inovator_detail);
        emailInovatorDetail = findViewById(R.id.email_inovator_detail);
        ttlInovatorDetail = findViewById(R.id.tv_ttl_inovator_detail);
        genderInovatorDetail = findViewById(R.id.tv_gender_inovator_detail);
        alamatInovatorDetail = findViewById(R.id.tv_alamat_inovator_detail);
        kategoriInovatorDetail = findViewById(R.id.tv_category_inovator_detail);
        fotoInovatorDetail = findViewById(R.id.iv_foto_inovator_detail);
        cardBio = findViewById(R.id.cardBio);
        cardImage = findViewById(R.id.cardNamaImage);
        cardCategory = findViewById(R.id.cardCategory);
        cardInovasi = findViewById(R.id.cardInovasi_in_Inovator);
        instansiInovatorDetail = findViewById(R.id.tv_instansi_inovator_detail);


        // set up RecyclerView Inovasi yang dibuat oleh inovator yang bersangkutan
        recyclerView = findViewById(R.id.recycleViewInovasi_Inovator);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                cardImage.setVisibility(View.VISIBLE);
                                cardBio.setVisibility(View.VISIBLE);
                                cardCategory.setVisibility(View.VISIBLE);
                                JSONObject object = jsonArray.getJSONObject(0);

                                namaInovatorDetail.setText(object.getString("nama_inovator"));

                                urlInovasiInovator = "https://api.koys.my.id/inovasiInovator/" + idInovator;
                                cardInovasi.setVisibility(View.VISIBLE);
                                getData();

                                if(!object.isNull("email")) {
                                    emailInovatorDetail.setText(object.getString("email"));
                                } else {
                                    emailInovatorDetail.setText("Tidak Mempunyai Email");
                                }

                                if(object.getString("jenis_kelamin").equalsIgnoreCase("L")) {
                                    genderInovatorDetail.setText("Laki-Laki");
                                } else if (object.getString("jenis_kelamin").equalsIgnoreCase("P")){
                                    genderInovatorDetail.setText("Perempuan");
                                } else if(object.isNull("jenis_kelamin")) {
                                    genderInovatorDetail.setText("-");
                                }

                                if(!object.isNull("tgl_lahir")) {
                                    ttlInovatorDetail.setText(object.getString("tgl_lahir"));
                                } else {
                                    ttlInovatorDetail.setText("-");
                                }

                                if (!object.isNull("id_instansi")) {
                                    JSONObject object2 = object.getJSONObject("instansi");
                                    instansiInovatorDetail.setText(object2.getString("nama_instansi"));
                                } else {
                                    instansiInovatorDetail.setText("Tidak memilki instansi");
                                }

                                alamatInovatorDetail.setText(object.getString("alamat"));

                                JSONObject object1 = object.getJSONObject("kategori");
                                kategoriInovatorDetail.setText(object1.getString("nama_kategori_inovator"));

                                if(!object.isNull("foto_inovator")) {
                                    urlInovator = "https://tim1.koys.my.id/assets/images/upload/foto_inovator/" +
                                            object.getString("foto_inovator");
                                } else {
                                    urlInovator = "https://tim1.koys.my.id/assets/images/upload/foto_inovator/default.png";
                                }
                                Picasso.get().load(urlInovator).into(fotoInovatorDetail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MotionToast.Companion.createToast(
                                InovatorDetailActivity.this,
                                "Lost Connection, Please Refresh",
                                "Ada kesalahan dalam mengambil data dari server",
                                MotionToastStyle.ERROR,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(InovatorDetailActivity.this,R.font.montserrat_medium)
                        );
                        Log.i("TEST", "onErrorResponse: " + error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);


    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlInovasiInovator,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length() ; i++) {
                                ContentLatestModel contentLatestModel = new ContentLatestModel();
                                JSONObject object = jsonArray.getJSONObject(i);

                                contentLatestModel.setId_inovasi(object.getInt("id_inovasi"));
                                contentLatestModel.setNama_inovasi(object.getString("nama_inovasi"));
                                contentLatestModel.setUrlGambar(object.getString("foto_inovasi1"));

                                JSONObject object1 = object.getJSONObject("bidang");
                                contentLatestModel.setKategoriInovasi(object1.getString("nama_bidang_inovasi"));

                                items.add(contentLatestModel);
                            }
                            contentInovasi_in_inovatorAdapter = new ContentInovasi_in_InovatorAdapter(getApplicationContext(), items);
                            recyclerView.setAdapter(contentInovasi_in_inovatorAdapter);
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