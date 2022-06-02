package com.project.inovationmobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.inovationmobile.R;
import com.project.inovationmobile.models.ListCategoryInovatorModel;

import java.util.ArrayList;

public class ContentCategoryInovatorAdapter extends RecyclerView.Adapter<ContentCategoryInovatorAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private final ArrayList<ListCategoryInovatorModel> data;

    public ContentCategoryInovatorAdapter(Context context, ArrayList<ListCategoryInovatorModel> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ContentCategoryInovatorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.content_category_inovasi_layout,parent,false);
        return new ContentCategoryInovatorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentCategoryInovatorAdapter.ViewHolder holder, int position) {
        holder.idCategoryInovator.setText(Integer.toString(data.get(position).getIdCategoryInovator()));
        holder.namaCategoryInovator.setText(data.get(position).getNamaCategoryInovator());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idCategoryInovator, namaCategoryInovator;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idCategoryInovator = itemView.findViewById(R.id.tv_id_category_inovasi);
            namaCategoryInovator = itemView.findViewById(R.id.tv_list_category_inovasi);
        }
    }
}
