package com.project.inovationmobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.project.inovationmobile.R;

import java.util.Objects;

public class PetaActivity extends AppCompatActivity {

    WebView peta;
    private GeolocationPermissions.Callback mCallback;
    private String mOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peta);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_peta);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        peta = findViewById(R.id.webPeta);
        peta.getSettings().setJavaScriptEnabled(true);
        peta.getSettings().setGeolocationEnabled(true);

        peta.setWebViewClient(new WebViewClient());
        peta.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                if (hasPermission()) {
                    callback.invoke(origin,true,true);
                }
            }
        });

        peta.loadUrl("https://tim2.koys.my.id/petakhusus");

    }

    private boolean hasPermission(){
        return ContextCompat.checkSelfPermission(PetaActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
}