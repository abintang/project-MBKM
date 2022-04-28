package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentLatestAdapter;
import com.project.inovationmobile.adapters.DividerDashboardAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ListInovasiActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContentLatestAdapter contentLatestAdapter;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_inovasi);

        items = new ArrayList<>();
        for (int i = 0; i < 100 ; i++) {
            items.add("Placeholder Title Text");
        }

        // Component list Inovator pada Dashboard melalui Recycleview
        recyclerView = findViewById(R.id.recycleViewListInovasi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentLatestAdapter = new ContentLatestAdapter(this,items);
        recyclerView.setAdapter(contentLatestAdapter);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_inovasi);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}