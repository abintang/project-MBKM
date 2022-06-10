package com.project.inovationmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.inovationmobile.R;
import com.project.inovationmobile.activities.InovasiDetailActivity;
import com.project.inovationmobile.models.ContentLatestModel;

import java.util.ArrayList;
import java.util.List;

public class ContentLatestAdapter extends RecyclerView.Adapter<ContentLatestAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private final ArrayList<ContentLatestModel> data;

    /* adapter ini adalah adapter recyclerview yang ada di page dashboard (Terbaru - Max 5 data)
    , bisa diubah/diganti sesuai dengan adapter yang di mau. lalu untuk id masing-masing
    isi/content nya itu ada dibawah (ViewHolder) */

    public ContentLatestAdapter(Context context, ArrayList<ContentLatestModel> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.content_inovasi_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        // bind the textview with data received
        viewHolder.kategoriInovasi.setText(data.get(i).getKategoriInovasi());
        viewHolder.namaInovasi.setText(data.get(i).getNama_inovasi());
        viewHolder.namaInovator.setText(data.get(i).getNama_inovator());

        // similarly you can set new image for each card and descriptions

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InovasiDetailActivity.class);
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView namaInovasi, namaInovator, kategoriInovasi;
        ImageView fotoInovasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // variable yang udah di declare sesuai dengan id nya masing-masing
            int idInovasi;
            namaInovasi = itemView.findViewById(R.id.tv_nama_inovasi);
            namaInovator = itemView.findViewById(R.id.tv_nama_inovator);
            fotoInovasi = itemView.findViewById(R.id.iv_foto_inovasi);
            kategoriInovasi = itemView.findViewById(R.id.tv_category_inovasi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}