package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentInovasi_in_InovatorAdapter;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class InovatorDetailActivity extends AppCompatActivity {
    ArrayList<String> items;

    TextView namaInovatorDetail, emailInovatorDetail, ttlInovatorDetail, genderInovatorDetail, alamatInovatorDetail,
    kategoriInovatorDetail;
    CircleImageView fotoInovatorDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inovator_detail);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_detail_inovator);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

       items = new ArrayList<>();
        for (int i = 0; i < 5 ; i++) {
            items.add("Teh Pala (Limbah Kulit Pala)");
        }

        // variable yang udah di declare sesuai dengan id nya masing-masing
        namaInovatorDetail = findViewById(R.id.tv_nama_inovator_detail);
        emailInovatorDetail = findViewById(R.id.email_inovator_detail);
        ttlInovatorDetail = findViewById(R.id.tv_ttl_inovator_detail);
        genderInovatorDetail = findViewById(R.id.tv_gender_inovator_detail);
        alamatInovatorDetail = findViewById(R.id.tv_alamat_inovator_detail);
        kategoriInovatorDetail = findViewById(R.id.tv_category_inovator_detail);
        fotoInovatorDetail = findViewById(R.id.iv_foto_inovator_detail);

        ContentInovasi_in_InovatorAdapter adapter;

        // set up RecyclerView Inovasi yang dibuat oleh inovator yang bersangkutan
        RecyclerView recyclerView = findViewById(R.id.recycleViewInovasi_Inovator);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new ContentInovasi_in_InovatorAdapter(this,items);
        recyclerView.setAdapter(adapter);
    }
}