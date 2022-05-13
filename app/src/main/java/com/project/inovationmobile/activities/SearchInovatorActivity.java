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

public class SearchInovatorActivity extends AppCompatActivity {

    AppCompatEditText searchFieldInovator;
    View backgroundResultInovator;
    ImageView iconResultInovator;
    TextView inputSearchResultInovator;

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

        searchFieldInovator.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String textPencarianInovator = searchFieldInovator.getText().toString();
                    if(!textPencarianInovator.isEmpty()) {
                        inputSearchResultInovator.setText("Hasil Pencarian: "+textPencarianInovator);
                        backgroundResultInovator.setVisibility(View.VISIBLE);
                        iconResultInovator.setVisibility(View.VISIBLE);
                    } else {
                        inputSearchResultInovator.setText("");
                        backgroundResultInovator.setVisibility(View.INVISIBLE);
                        iconResultInovator.setVisibility(View.INVISIBLE);
                    }
                }
                return false;
            }
        });
    }
}