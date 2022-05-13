package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.inovationmobile.R;

import java.util.Objects;

public class SearchInovasiActivity extends AppCompatActivity {

    AppCompatEditText searchFieldInovasi;
    View backgroundResult;
    ImageView iconResult;
    TextView inputSearchResult;

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

        searchFieldInovasi.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String textPencarianInovasi = searchFieldInovasi.getText().toString();
                    if(!textPencarianInovasi.isEmpty()) {
                        inputSearchResult.setText("Hasil Pencarian: "+textPencarianInovasi);
                        backgroundResult.setVisibility(View.VISIBLE);
                        iconResult.setVisibility(View.VISIBLE);
                    } else {
                        inputSearchResult.setText("");
                        backgroundResult.setVisibility(View.INVISIBLE);
                        iconResult.setVisibility(View.INVISIBLE);
                    }
                }
                return false;
            }
        });
    }
}