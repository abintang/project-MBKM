package com.project.inovationmobile.adapters;

import android.view.LayoutInflater;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.inovationmobile.R;
import com.project.inovationmobile.activities.InovasiDetailActivity;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContentInovasi_in_InovatorAdapter extends RecyclerView.Adapter<ContentInovasi_in_InovatorAdapter.ViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<String> data;

    public ContentInovasi_in_InovatorAdapter(Context context, List<String> data){
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
    public void onBindViewHolder(@NonNull ContentInovasi_in_InovatorAdapter.ViewHolder holder, int position) {
        // bind the textview with data received
        String titleInovasi = data.get(position);
        holder.title_inovasi.setText(titleInovasi);
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
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title_inovasi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_inovasi = itemView.findViewById(R.id.title_inovasi_inov);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}