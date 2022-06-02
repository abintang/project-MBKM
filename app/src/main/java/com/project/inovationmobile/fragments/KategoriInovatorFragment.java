package com.project.inovationmobile.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.project.inovationmobile.R;
import com.project.inovationmobile.activities.ListInovatorActivity;
import com.project.inovationmobile.adapters.ContentCategoryInovasiAdapter;
import com.project.inovationmobile.adapters.ContentCategoryInovatorAdapter;
import com.project.inovationmobile.models.ListCategoryInovasiModel;
import com.project.inovationmobile.models.ListCategoryInovatorModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class KategoriInovatorFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    public static final String TAG = "ActionBottomDialog";

    ArrayList<ListCategoryInovatorModel> items;
    String url = "https://run.mocky.io/v3/e605dfc3-1729-4f0b-899c-545236917b58";
    ContentCategoryInovatorAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public void onClick(View view) {

    }

    public static KategoriInovatorFragment newInstance() {
        return new KategoriInovatorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_kategori_inovasi, container, false);
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
        MaterialCardView semuaKategoriInovator = view.findViewById(R.id.card_semuaKategori);
        semuaKategoriInovator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListInovatorActivity.class));
            }
        });
        getDataCategoryInovator();
    }

    private void getDataCategoryInovator(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    items = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ListCategoryInovatorModel listCategoryInovatorModel = new ListCategoryInovatorModel();
                        JSONObject object = jsonArray.getJSONObject(i);

                        listCategoryInovatorModel.setIdCategoryInovator(object.getInt("id_kategori_inovator"));
                        listCategoryInovatorModel.setNamaCategoryInovator(object.getString("nama_kategori_inovator"));

                        items.add(listCategoryInovatorModel);
                    }
                    adapter = new ContentCategoryInovatorAdapter(getActivity(), items);
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