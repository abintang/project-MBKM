package com.project.inovationmobile.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.project.inovationmobile.R;
import com.project.inovationmobile.activities.SortListCategory_InovasiActivity;
import com.project.inovationmobile.activities.SortListCategory_InovatorActivity;
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
    public void onBindViewHolder(@NonNull ContentCategoryInovatorAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.idCategoryInovator.setText(Integer.toString(data.get(position).getIdCategoryInovator()));
        holder.namaCategoryInovator.setText(data.get(position).getNamaCategoryInovator());

        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SortListCategory_InovatorActivity.class);
                intent.putExtra("tempCategoryId", data.get(position).getIdCategoryInovator());
                intent.putExtra("tempCategoryName", data.get(position).getNamaCategoryInovator());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idCategoryInovator, namaCategoryInovator;
        MaterialCardView cardLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardLayout = itemView.findViewById(R.id.cardKategori);
            idCategoryInovator = itemView.findViewById(R.id.tv_id_category_inovasi);
            namaCategoryInovator = itemView.findViewById(R.id.tv_list_category_inovasi);
        }
    }
}
