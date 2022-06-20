package com.project.inovationmobile.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.project.inovationmobile.R;
import com.project.inovationmobile.activities.ListInovasiActivity;
import com.project.inovationmobile.activities.ListInovatorActivity;
import com.project.inovationmobile.adapters.ContentCategoryInovasiAdapter;
import com.project.inovationmobile.models.ContentLatestModel;
import com.project.inovationmobile.models.ListCategoryInovasiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KategoriInovasiFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    public static final String TAG = "ActionBottomDialog";

    ArrayList<ListCategoryInovasiModel> items;
    String url = "https://api.koys.my.id/bidangInovasi";
    ContentCategoryInovasiAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public void onClick(View view) {

    }

    public static KategoriInovasiFragment newInstance() {
        return new KategoriInovasiFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_bidang_inovasi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.rv_list_kategori_inovasi);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ImageView closeButton = view.findViewById(R.id.closeIv);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        MaterialCardView semuaKategori = view.findViewById(R.id.card_semuaKategori);
        semuaKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListInovasiActivity.class));
            }
        });
        getDataListCategoryInovasi();

    }

    private void getDataListCategoryInovasi(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    items = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ListCategoryInovasiModel listCategoryInovasiModel = new ListCategoryInovasiModel();
                        JSONObject object = jsonArray.getJSONObject(i);

                        listCategoryInovasiModel.setIdCategoryInovasi(object.getInt("id_bidang_inovasi"));
                        listCategoryInovasiModel.setNamaCategoryInovasi(object.getString("nama_bidang_inovasi"));

                        items.add(listCategoryInovasiModel);
                    }
                    adapter = new ContentCategoryInovasiAdapter(getActivity(), items);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        requestQueue.add(stringRequest);
    }
}
