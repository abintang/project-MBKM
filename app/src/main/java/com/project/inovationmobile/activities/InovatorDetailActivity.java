package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentInovasi_in_InovatorAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class InovatorDetailActivity extends AppCompatActivity {
    ArrayList<String> items;

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

        ContentInovasi_in_InovatorAdapter adapter;

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycleViewInovasi_Inovator);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new ContentInovasi_in_InovatorAdapter(this,items);
        recyclerView.setAdapter(adapter);
    }
}