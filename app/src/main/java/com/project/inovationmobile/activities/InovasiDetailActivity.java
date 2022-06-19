package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codesgood.views.JustifiedTextView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.captain_miao.optroundcardview.OptRoundCardView;
import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentInovasi_TerkaitAdapter;
import com.project.inovationmobile.adapters.ContentInovasi_in_InovatorAdapter;
import com.project.inovationmobile.models.ContentLatestModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class InovasiDetailActivity extends AppCompatActivity {
    ArrayList<ContentLatestModel> items;

    // untuk kategori inovasi kemungkinan bakalan dibuat recyclerview
    ImageView fotoInovasiDetail, fotoInovatorDet;
    TextView kategoriInovasiDetail, namaInovasiDetail, alamatInovasiDetail, namaInovatorDet, kategoriInovatorDet, instansiDet;
    JustifiedTextView deskripsiInovasi, manfaatInovasi;

    Button detailInovator, detailInstansi;
    String url, urlTerkait;
    int idInovator, idInovasi;
    ShimmerFrameLayout shimmerFrame;
    OptRoundCardView content;
    RecyclerView recyclerView;

    ContentInovasi_in_InovatorAdapter contentInovasi_in_inovatorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inovasi_detail);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_detail_inovasi);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        shimmerFrame = findViewById(R.id.shimmer_detail);

        idInovasi = getIntent().getExtras().getInt("tempInovasiId");
        Log.d("Check id - > ", "ID: " + idInovasi);

        url = "https://api.koys.my.id/inovasi/" + idInovasi;

        detailInovator = findViewById(R.id.button_detail_inovator);

        detailInstansi = findViewById(R.id.button_detail_instansi);
        detailInstansi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InovasiDetailActivity.this, InstansiDetailActivity.class);
                startActivity(intent);
            }
        });

        // variable yang udah di declare sesuai dengan id nya masing-masing
        fotoInovasiDetail = findViewById(R.id.iv_foto_inovasi_detail);
        fotoInovatorDet = findViewById(R.id.iv_foto_inovator_det);
        kategoriInovasiDetail = findViewById(R.id.tv_category_inovasi_detail);
        namaInovasiDetail = findViewById(R.id.tv_nama_inovasi_detail);
        alamatInovasiDetail = findViewById(R.id.tv_alamat_inovasi_detail);
        namaInovatorDet = findViewById(R.id.tv_nama_inovator_det);
        kategoriInovatorDet = findViewById(R.id.tv_category_inovator_det);
        deskripsiInovasi = findViewById(R.id.tv_deskripsi_inovasi_detail);
        manfaatInovasi = findViewById(R.id.deskripsi_manfaat_detail);
        content = findViewById(R.id.contentDetail);
        instansiDet = findViewById(R.id.tv_nama_instansi_det);

        recyclerView = findViewById(R.id.recycleViewInovasi_terkait);
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
                            shimmerFrame.stopShimmer();
                            shimmerFrame.setVisibility(View.GONE);
                            content.setVisibility(View.VISIBLE);
                            JSONObject object = jsonArray.getJSONObject(0);

                                namaInovasiDetail.setText(object.getString("nama_inovasi"));
                                deskripsiInovasi.setText(object.getString("deskripsi"));
                                manfaatInovasi.setText(object.getString("manfaat_inovasi"));
                                String urlImage = "https://tim1.koys.my.id/assets/upload/foto_inovasi/" +
                                        object.getString("foto_inovasi");
                                Picasso.get().load(urlImage).into(fotoInovasiDetail);

                                if(object.isNull("instansi")){
                                    detailInstansi.setVisibility(View.INVISIBLE);
                                    instansiDet.setText("Tidak memiliki Instansi");
                                }

                                JSONObject object1 = object.getJSONObject("bidang");
                                kategoriInovasiDetail.setText(object1.getString("nama_bidang_inovasi"));
                                urlTerkait = "https://api.koys.my.id/inovasi/terkait/" + object.getString("id_bidang_inovasi");
                                getData();

                                JSONObject object2 = object.getJSONObject("inovator");
                                namaInovatorDet.setText(object2.getString("nama_inovator"));
                                idInovator = object2.getInt("id_inovator");
                                alamatInovasiDetail.setText(object2.getString("alamat"));
                                String urlInovator;
                                if (!object2.isNull("foto_inovator")) {
                                    urlInovator = "https://tim1.koys.my.id/assets/upload/foto_inovator/" +
                                            object2.getString("foto_inovator");
                                } else {
                                    urlInovator = "https://tim1.koys.my.id/assets/upload/foto_inovator/default.png";
                                }

                                int idKategoriInovator = object2.getInt("id_kategori_inovator");

                                switch (idKategoriInovator) {
                                    case 1:
                                        kategoriInovatorDet.setText("Mahasiswa");
                                        break;
                                    case 2:
                                        kategoriInovatorDet.setText("SMP");
                                        break;
                                    case 3:
                                        kategoriInovatorDet.setText("SMA/SMK");
                                        break;
                                    case 4:
                                        kategoriInovatorDet.setText("Dosen Peneliti");
                                        break;
                                    case 5:
                                        kategoriInovatorDet.setText("Desa/Kelurahan");
                                        break;
                                    case 6:
                                        kategoriInovatorDet.setText("Kecamatan");
                                        break;
                                    case 7:
                                        kategoriInovatorDet.setText("Perangkat Desa");
                                        break;
                                    case 8:
                                        kategoriInovatorDet.setText("Masyarakat Umum");
                                        break;
                                    case 9:
                                        kategoriInovatorDet.setText("Puskesmas");
                                        break;
                                    default:
                                        kategoriInovatorDet.setText("Invalid");
                                        break;

                                }
                                Picasso.get().load(urlInovator).into(fotoInovatorDet);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MotionToast.Companion.createToast(
                                InovasiDetailActivity.this,
                                "Lost Connection, Please Refresh",
                                "Ada kesalahan dalam mengambil data dari server",
                                MotionToastStyle.ERROR,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(InovasiDetailActivity.this,R.font.montserrat_medium)
                        );
                        Log.i("TEST", "onErrorResponse: " + error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);

        detailInovator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InovasiDetailActivity.this, InovatorDetailActivity.class);
                intent.putExtra("tempInovatorId", idInovator);
                startActivity(intent);
            }
        });


        // set up RecyclerView Inovasi Terkait

      /*  adapter = new ContentInovasi_in_InovatorAdapter(this,items);
        recyclerView.setAdapter(adapter);*/

    }

    private void getData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlTerkait,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            items = new ArrayList<>();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < 5 ; i++) {
                                ContentLatestModel contentLatestModel = new ContentLatestModel();
                                Random random = new Random();
                                int pointer = random.nextInt(jsonArray.length());
                                JSONObject object = jsonArray.getJSONObject(pointer);

                                contentLatestModel.setId_inovasi(object.getInt("id_inovasi"));
                                contentLatestModel.setNama_inovasi(object.getString("nama_inovasi"));
                                contentLatestModel.setUrlGambar(object.getString("foto_inovasi"));

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
        shimmerFrame.startShimmer();
    }

    @Override
    protected void onPause() {
        shimmerFrame.stopShimmer();
        super.onPause();
    }

}