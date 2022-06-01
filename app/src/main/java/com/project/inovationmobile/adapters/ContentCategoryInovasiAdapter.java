package com.project.inovationmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.inovationmobile.R;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.idCategoryInovasi.setText(Integer.toString(data.get(position).getIdCategoryInovasi()));
        holder.namaCategoryInovasi.setText(data.get(position).getNamaCategoryInovasi());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idCategoryInovasi, namaCategoryInovasi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idCategoryInovasi = itemView.findViewById(R.id.tv_id_category_inovasi);
            namaCategoryInovasi = itemView.findViewById(R.id.tv_list_category_inovasi);
        }
    }
}
