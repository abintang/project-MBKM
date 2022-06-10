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
import com.project.inovationmobile.activities.InovatorDetailActivity;
import com.project.inovationmobile.models.ContentLatestModel;
import com.project.inovationmobile.models.ListInovatorModel;

import java.util.ArrayList;
import java.util.List;

public class ContentInovatorAdapter extends RecyclerView.Adapter<ContentInovatorAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final ArrayList<ListInovatorModel> data;

    /* adapter ini adalah adapter recyclerview yang ada di page list inovator
    , bisa diubah/diganti sesuai dengan adapter yang di mau. lalu untuk id
    masing-masing isi/content nya itu ada dibawah (ViewHolder) */

    public ContentInovatorAdapter(Context context, ArrayList<ListInovatorModel> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ContentInovatorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.content_inovator_layout,viewGroup,false);
        return new ContentInovatorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentInovatorAdapter.ViewHolder viewHolder, int i) {
        // bind the textview with data received

        viewHolder.kategoriInovator.setText(Integer.toString(data.get(i).getId_inovator()));
        viewHolder.namaInovator.setText(data.get(i).getNama_inovator());
        viewHolder.alamatInovator.setText(data.get(i).getAlamat_inovator());
        viewHolder.fotoInovator.setImageResource(R.drawable.dump_image_inovator);

        // similarly you can set new image for each card and descriptions

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InovatorDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView namaInovator, kategoriInovator, alamatInovator;
        ImageView fotoInovator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // variable yang udah di declare sesuai dengan id nya masing-masing
            namaInovator = itemView.findViewById(R.id.tv_nama_inovator);
            kategoriInovator = itemView.findViewById(R.id.tv_category_inovator);
            alamatInovator = itemView.findViewById(R.id.tv_alamat_inovator);
            fotoInovator = itemView.findViewById(R.id.iv_foto_inovator);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

}

