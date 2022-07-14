package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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
import com.google.android.material.card.MaterialCardView;
import com.project.inovationmobile.R;
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
    ImageView fotoInovasiDetail, fotoInovatorDet, fotoAnggota1, fotoAnggota2, fotoAnggota3, fotoAnggota4;
    TextView kategoriInovasiDetail, namaInovasiDetail, alamatInovasiDetail, namaInovatorDet, kategoriInovatorDet, instansiDet, tahunDibuat, titleAnggota;
    TextView anggota1, anggota2, anggota3, anggota4, kategoriInovasiDetail2, kategoriInovasiDetail3;

    JustifiedTextView deskripsiInovasi, manfaatInovasi;

    Button detailInovator, detailInstansi;
    String url, urlTerkait, urlAnggota1, urlAnggota2, urlAnggota3, urlAnggota4;
    int idInovator, idInovasi;
    ShimmerFrameLayout shimmerFrame;
    OptRoundCardView content;
    RecyclerView recyclerView;
    CardView cardInovasi;
    MaterialCardView cardKategori, cardAnggota, cardAnggota2, cardAnggota3, cardAnggota4, kategori2, kategori3;
    int idBidang, idBidang2, idBidang3;

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
        tahunDibuat = findViewById(R.id.tv_tahun_inovasi_detail);
        cardInovasi = findViewById(R.id.cardInovasi);
        cardKategori = findViewById(R.id.kategoriInovatorDet);

        titleAnggota = findViewById(R.id.title_anggota);
        anggota1 = findViewById(R.id.nama_anggota_inovasi);
        anggota2 = findViewById(R.id.nama_anggota_inovasi_2);
        anggota3 = findViewById(R.id.nama_anggota_inovasi3);
        anggota4 = findViewById(R.id.nama_anggota_inovasi4);

        cardAnggota = findViewById(R.id.cardAnggota);
        cardAnggota2 = findViewById(R.id.cardAnggota2);
        cardAnggota3 = findViewById(R.id.cardAnggota3);
        cardAnggota4 = findViewById(R.id.cardAnggota4);

        kategori2 = findViewById(R.id.kategoriInovatorDet2);
        kategori3 = findViewById(R.id.kategoriInovatorDet3);
        kategoriInovasiDetail2 = findViewById(R.id.tv_category_inovasi_detail_2);
        kategoriInovasiDetail3 = findViewById(R.id.tv_category_inovasi_detail3);

        fotoAnggota1 = findViewById(R.id.iv_foto_kelompok_det);
        fotoAnggota2 = findViewById(R.id.iv_foto_kelompok_det2);
        fotoAnggota3 = findViewById(R.id.iv_foto_kelompok_de3);
        fotoAnggota4 = findViewById(R.id.iv_foto_kelompok_det4);

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
                            cardInovasi.setVisibility(View.VISIBLE);
                            JSONObject object = jsonArray.getJSONObject(0);

                                if(!object.isNull("nama_anggota1") && object.isNull("nama_anggota2")
                                        && object.isNull("nama_anggota3") && object.isNull("nama_anggota4")) {
                                    titleAnggota.setVisibility(View.VISIBLE);
                                    cardAnggota.setVisibility(View.VISIBLE);
                                    if (!object.isNull("foto_anggota1")) {
                                        urlAnggota1 = "http://tim1.koys.my.id/assets/images/upload/foto_anggota_inovator/" + object.getString("foto_anggota1");
                                        Picasso.get().load(urlAnggota1).into(fotoAnggota1);
                                    }

                                    anggota1.setText(object.getString("nama_anggota1"));

                                } else if (!object.isNull("nama_anggota1") && !object.isNull("nama_anggota2")
                                        && object.isNull("nama_anggota3") && object.isNull("nama_anggota4")) {
                                    titleAnggota.setVisibility(View.VISIBLE);
                                    cardAnggota.setVisibility(View.VISIBLE);
                                    cardAnggota2.setVisibility(View.VISIBLE);
                                    if (!object.isNull("foto_anggota1")) {
                                        urlAnggota1 = "http://tim1.koys.my.id/assets/images/upload/foto_anggota_inovator/" + object.getString("foto_anggota1");
                                        Picasso.get().load(urlAnggota1).into(fotoAnggota1);
                                        urlAnggota2 = "http://tim1.koys.my.id/assets/images/upload/foto_anggota_inovator/" + object.getString("foto_anggota2");
                                        Picasso.get().load(urlAnggota2).into(fotoAnggota2);
                                    }
                                    anggota1.setText(object.getString("nama_anggota1"));
                                    anggota2.setText(object.getString("nama_anggota2"));
                                } else if (!object.isNull("nama_anggota1") && !object.isNull("nama_anggota2")
                                        && !object.isNull("nama_anggota3") && object.isNull("nama_anggota4")) {
                                    titleAnggota.setVisibility(View.VISIBLE);
                                    cardAnggota.setVisibility(View.VISIBLE);
                                    cardAnggota2.setVisibility(View.VISIBLE);
                                    cardAnggota3.setVisibility(View.VISIBLE);
                                    if (!object.isNull("foto_anggota1")) {
                                        urlAnggota1 = "http://tim1.koys.my.id/assets/images/upload/foto_anggota_inovator/" + object.getString("foto_anggota1");
                                        Picasso.get().load(urlAnggota1).into(fotoAnggota1);
                                        urlAnggota2 = "http://tim1.koys.my.id/assets/images/upload/foto_anggota_inovator/" + object.getString("foto_anggota2");
                                        Picasso.get().load(urlAnggota2).into(fotoAnggota2);
                                        urlAnggota3 = "http://tim1.koys.my.id/assets/images/upload/foto_anggota_inovator/" + object.getString("foto_anggota3");
                                        Picasso.get().load(urlAnggota3).into(fotoAnggota3);
                                    }
                                    anggota1.setText(object.getString("nama_anggota1"));
                                    anggota2.setText(object.getString("nama_anggota2"));
                                    anggota3.setText(object.getString("nama_anggota3"));
                                } else if (!object.isNull("nama_anggota1") && !object.isNull("nama_anggota2")
                                        && !object.isNull("nama_anggota3") && !object.isNull("nama_anggota4")) {
                                    titleAnggota.setVisibility(View.VISIBLE);
                                    cardAnggota.setVisibility(View.VISIBLE);
                                    cardAnggota2.setVisibility(View.VISIBLE);
                                    cardAnggota3.setVisibility(View.VISIBLE);
                                    cardAnggota4.setVisibility(View.VISIBLE);
                                    if (!object.isNull("foto_anggota1")) {
                                        urlAnggota1 = "http://tim1.koys.my.id/assets/images/upload/foto_anggota_inovator/" + object.getString("foto_anggota1");
                                        Picasso.get().load(urlAnggota1).into(fotoAnggota1);
                                        urlAnggota2 = "http://tim1.koys.my.id/assets/images/upload/foto_anggota_inovator/" + object.getString("foto_anggota2");
                                        Picasso.get().load(urlAnggota2).into(fotoAnggota2);
                                        urlAnggota3 = "http://tim1.koys.my.id/assets/images/upload/foto_anggota_inovator/" + object.getString("foto_anggota3");
                                        Picasso.get().load(urlAnggota3).into(fotoAnggota3);
                                        urlAnggota4 = "http://tim1.koys.my.id/assets/images/upload/foto_anggota_inovator/" + object.getString("foto_anggota4");
                                        Picasso.get().load(urlAnggota4).into(fotoAnggota4);
                                    }
                                    anggota1.setText(object.getString("nama_anggota1"));
                                    anggota2.setText(object.getString("nama_anggota2"));
                                    anggota3.setText(object.getString("nama_anggota3"));
                                    anggota4.setText(object.getString("nama_anggota4"));
                                } else {
                                    titleAnggota.setVisibility(View.GONE);
                                    cardAnggota.setVisibility(View.GONE);
                                    cardAnggota2.setVisibility(View.GONE);
                                    cardAnggota3.setVisibility(View.GONE);
                                    cardAnggota4.setVisibility(View.GONE);
                                }

                                namaInovasiDetail.setText(object.getString("nama_inovasi"));
                                deskripsiInovasi.setText(object.getString("deskripsi"));
                                manfaatInovasi.setText(object.getString("manfaat_inovasi"));
                                tahunDibuat.setText("Tahun dibuat:   " + object.getString("tahun_pembuatan_inovasi"));
                                idBidang = object.getInt("id_bidang_inovasi");
                                String urlImage = "https://tim1.koys.my.id/assets/images/upload/foto_inovasi/" +
                                        object.getString("foto_inovasi1");
                                Picasso.get().load(urlImage).into(fotoInovasiDetail);

                                JSONObject object1 = object.getJSONObject("bidang");
                                kategoriInovasiDetail.setText(object1.getString("nama_bidang_inovasi"));
                                urlTerkait = "https://api.koys.my.id/inovasi/terkait/" + object.getString("id_bidang_inovasi");
                                getData();

                                if (!object.isNull("id_bidang_inovasi_2")) {
                                    JSONObject objectBidang2 = object.getJSONObject("bidang2");
                                    kategori2.setVisibility(View.VISIBLE);
                                    idBidang2 = objectBidang2.getInt("id_bidang_inovasi");
                                    kategoriInovasiDetail2.setText(objectBidang2.getString("nama_bidang_inovasi"));
                                }

                                if (!object.isNull("id_bidang_inovasi_3")) {
                                JSONObject objectBidang3 = object.getJSONObject("bidang3");
                                kategori3.setVisibility(View.VISIBLE);
                                idBidang3 = objectBidang3.getInt("id_bidang_inovasi");
                                kategoriInovasiDetail3.setText(objectBidang3.getString("nama_bidang_inovasi"));
                                }

                                JSONObject object2 = object.getJSONObject("inovator");
                                namaInovatorDet.setText(object2.getString("nama_inovator"));
                                idInovator = object2.getInt("id_inovator");
                            String urlInovatorId =  "https://api.koys.my.id/inovator/" + idInovator;

                            StringRequest stringRequest = new StringRequest(Request.Method.GET, urlInovatorId,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                                JSONObject object = jsonArray.getJSONObject(0);
                                                JSONObject object1 = object.getJSONObject("kategori");
                                                kategoriInovatorDet.setText(object1.getString("nama_kategori_inovator"));
                                                if(object.isNull("instansi")){
                                                    detailInstansi.setVisibility(View.INVISIBLE);
                                                    instansiDet.setText("Tidak memiliki Instansi");
                                                } else {
                                                    detailInstansi.setVisibility(View.VISIBLE);
                                                    JSONObject object2 = object.getJSONObject("instansi");
                                                    instansiDet.setText(object2.getString("nama_instansi"));
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("TEST", "onErrorResponse: " + error.getMessage());
                                        }
                                    });
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(stringRequest);

                                alamatInovasiDetail.setText(object2.getString("alamat"));
                                String urlInovator;
                                if (!object2.isNull("foto_inovator")) {
                                    urlInovator = "https://tim1.koys.my.id/assets/images/upload/foto_inovator/" +
                                            object2.getString("foto_inovator");
                                } else {
                                    urlInovator = "https://tim1.koys.my.id/assets/images/upload/foto_inovator/default.png";
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

        cardKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InovasiDetailActivity.this, SortListCategory_InovasiActivity.class);
                intent.putExtra("tempCategoryId", idBidang);
                intent.putExtra("tempCategoryName", kategoriInovasiDetail.getText());
                startActivity(intent);
            }
        });

        if(kategori2.getVisibility() == View.VISIBLE) {
            kategori2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(InovasiDetailActivity.this, SortListCategory_InovasiActivity.class);
                    intent.putExtra("tempCategoryId", idBidang2);
                    intent.putExtra("tempCategoryName", kategoriInovasiDetail.getText());
                    startActivity(intent);
                }
            });
        }

        if(kategori3.getVisibility() == View.VISIBLE) {
            kategori3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(InovasiDetailActivity.this, SortListCategory_InovasiActivity.class);
                    intent.putExtra("tempCategoryId", idBidang3);
                    intent.putExtra("tempCategoryName", kategoriInovasiDetail.getText());
                    startActivity(intent);
                }
            });
        }

        fotoInovasiDetail.setClickable(true);
        fotoInovasiDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InovasiDetailActivity.this, ImageViewerActivity.class);
                intent.putExtra("tempId", idInovasi);
                startActivity(intent);
            }
        });

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
        shimmerFrame.startShimmer();
    }

    @Override
    protected void onPause() {
        shimmerFrame.stopShimmer();
        super.onPause();
    }

}