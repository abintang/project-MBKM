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

import java.util.List;

public class ContentInovasi_TerkaitAdapter extends RecyclerView.Adapter<ContentInovasi_TerkaitAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<String> data;


    /* adapter ini adalah adapter recyclerview yang ada di page detail inovasi (isinya list
    inovasi terkait dengan detail inovasi yang bersangkutan). adapter ini bisa diubah/diganti sesuai
    dengan adapter yang di mau. lalu untuk id masing-masing isi/content nya itu ada fun ViewHolder */

    public ContentInovasi_TerkaitAdapter(Context context, List<String> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ContentInovasi_TerkaitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.content_inovasi_inside_inovator_layout,parent,false);
        return new ContentInovasi_TerkaitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentInovasi_TerkaitAdapter.ViewHolder holder, int position) {
        // bind the textview with data received
        String titleInovasi = data.get(position);
        holder.namaInovasi.setText(titleInovasi);
        // similarly you can set new image for each card and descriptions

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InovasiDetailActivity.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namaInovasi, kategoriInovasi;
        ImageView fotoInovasi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // variable yang udah di declare sesuai dengan id nya masing-masing
            namaInovasi = itemView.findViewById(R.id.tv_nama_inovasi_inov);
            kategoriInovasi = itemView.findViewById(R.id.tv_category_inovasi_inov);
            fotoInovasi = itemView.findViewById(R.id.iv_foto_inovasi_inov);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

}
