package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codesgood.views.JustifiedTextView;
import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentInovasi_TerkaitAdapter;
import com.project.inovationmobile.adapters.ContentInovasi_in_InovatorAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class InovasiDetailActivity extends AppCompatActivity {
    ArrayList<String> items;

    // untuk kategori inovasi kemungkinan bakalan dibuat recyclerview
    ImageView fotoInovasiDetail, fotoInovatorDet;
    TextView kategoriInovasiDetail, namaInovasiDetail, alamatInovasiDetail, namaInovatorDet, kategoriInovatorDet;
    JustifiedTextView deskripsiInovasi, manfaatInovasi;

    Button detailInovator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inovasi_detail);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_detail_inovasi);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        items = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            items.add("Teh Pala (Limbah Kulit Pala)");
        }

        detailInovator = findViewById(R.id.button_detail_inovator);
        detailInovator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InovasiDetailActivity.this, InovatorDetailActivity.class);
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

        ContentInovasi_TerkaitAdapter adapter;

        // set up RecyclerView Inovasi Terkait
        RecyclerView recyclerView = findViewById(R.id.recycleViewInovasi_terkait);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new ContentInovasi_TerkaitAdapter(this,items);
        recyclerView.setAdapter(adapter);

    }
}