package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.project.inovationmobile.R;
import com.project.inovationmobile.adapters.ContentInovatorAdapter;
import com.project.inovationmobile.fragments.KategoriInovasiFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ListInovatorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ContentInovatorAdapter contentInovatorAdapter;
    ArrayList<String> items;
    Button bottomSheetKategoriButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_inovator);

        items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add("Wirda Ningsih");
        }

        // set up RecyclerView List Inovator
        recyclerView = findViewById(R.id.recycleViewListInovator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentInovatorAdapter = new ContentInovatorAdapter(this, items);
        recyclerView.setAdapter(contentInovatorAdapter);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_inovator);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
    }

    public void showBottomSheet(View view) {
        KategoriInovasiFragment addPhotoBottomDialogFragment =
                KategoriInovasiFragment.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                KategoriInovasiFragment.TAG);
    }
}