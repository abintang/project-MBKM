package com.project.inovationmobile.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.project.inovationmobile.R;
import com.project.inovationmobile.activities.InovasiDetailActivity;
import com.project.inovationmobile.activities.SortListCategory_InovasiActivity;
import com.project.inovationmobile.models.ListCategoryInovasiModel;

import java.util.ArrayList;

public class ContentCategoryInovasiAdapter extends RecyclerView.Adapter<ContentCategoryInovasiAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private final ArrayList<ListCategoryInovasiModel> data;

    public ContentCategoryInovasiAdapter(Context context, ArrayList<ListCategoryInovasiModel> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ContentCategoryInovasiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.content_category_inovasi_layout,parent,false);
        return new ContentCategoryInovasiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.idCategoryInovasi.setText(Integer.toString(data.get(position).getIdCategoryInovasi()));
        holder.namaCategoryInovasi.setText(data.get(position).getNamaCategoryInovasi());

        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SortListCategory_InovasiActivity.class);
                intent.putExtra("tempCategoryId", data.get(position).getIdCategoryInovasi());
                intent.putExtra("tempCategoryName", data.get(position).getNamaCategoryInovasi());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idCategoryInovasi, namaCategoryInovasi;
        MaterialCardView cardLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardLayout = itemView.findViewById(R.id.cardKategori);
            idCategoryInovasi = itemView.findViewById(R.id.tv_id_category_inovasi);
            namaCategoryInovasi = itemView.findViewById(R.id.tv_list_category_inovasi);

        }
    }
}
