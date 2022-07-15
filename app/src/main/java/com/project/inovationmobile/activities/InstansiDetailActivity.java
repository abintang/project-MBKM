package com.project.inovationmobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.inovationmobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import www.sanju.motiontoast.MotionToast;
import www.sanju.motiontoast.MotionToastStyle;

public class InstansiDetailActivity extends AppCompatActivity {

    TextView namaInstansi, emailInstansi, alamatInstansi, desaInstansi;
    ShimmerFrameLayout shimmerFrameLayout;
    String url;
    CardView cardBio, cardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instansi_detail);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_detail_instansi);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        shimmerFrameLayout = findViewById(R.id.shimmer_inovator);

        int idInstansi = getIntent().getExtras().getInt("tempId");
        url = "https://api.koys.my.id/instansi/" + idInstansi;

        namaInstansi = findViewById(R.id.tv_nm_instansi_detail);
        cardBio = findViewById(R.id.cardBio);
        cardImage = findViewById(R.id.cardNamaImage);
        emailInstansi = findViewById(R.id.tv_em_instansi_detail);
        alamatInstansi = findViewById(R.id.tv_alamat_instansi_detail);
        desaInstansi = findViewById(R.id.tv_desa_instansi_detail);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            cardImage.setVisibility(View.VISIBLE);
                            cardBio.setVisibility(View.VISIBLE);
                            JSONObject object = jsonArray.getJSONObject(0);
                            namaInstansi.setText(object.getString("nama_instansi"));
                            if(object.getString("email_instansi").isEmpty()) {
                                emailInstansi.setText("-");
                            } else {
                                emailInstansi.setText(object.getString("email_instansi"));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MotionToast.Companion.createToast(
                                InstansiDetailActivity.this,
                                "Lost Connection, Please Refresh",
                                "Ada kesalahan dalam mengambil data dari server",
                                MotionToastStyle.ERROR,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(InstansiDetailActivity.this, R.font.montserrat_medium)
                        );
                        Log.i("TEST", "onErrorResponse: " + error.getMessage());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        requestQueue.add(stringRequest);
    }
}