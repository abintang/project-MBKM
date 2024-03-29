package com.project.inovationmobile.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.inovationmobile.R;
import com.project.inovationmobile.activities.InovasiDetailActivity;
import com.project.inovationmobile.models.ContentLatestModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContentInovasi_in_InovatorAdapter extends RecyclerView.Adapter<ContentInovasi_in_InovatorAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final ArrayList<ContentLatestModel> data;

    /* adapter ini adalah adapter recyclerview yang ada di page detail inovator (isinya list
    inovasi yang dibuat oleh inovator bersangkutan). adapter ini bisa diubah/diganti sesuai
    dengan adapter yang di mau. lalu untuk id masing-masing isi/content nya itu ada fun ViewHolder */

    public ContentInovasi_in_InovatorAdapter(Context context, ArrayList<ContentLatestModel> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ContentInovasi_in_InovatorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.content_inovasi_inside_inovator_layout,parent,false);
        return new ContentInovasi_in_InovatorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentInovasi_in_InovatorAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // bind the textview with data received
        holder.namaInovasi.setText(data.get(position).getNama_inovasi());
        holder.kategoriInovasi.setText(data.get(position).getKategoriInovasi());

        String urlImage = "https://tim1.koys.my.id/assets/images/upload/foto_inovasi/" + data.get(position).getUrlGambar();
        // similarly you can set new image for each card and descriptions
        Picasso.get().load(urlImage).into(holder.fotoInovasi);
        // similarly you can set new image for each card and descriptions

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InovasiDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tempInovasiId", data.get(position).getId_inovasi());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
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
