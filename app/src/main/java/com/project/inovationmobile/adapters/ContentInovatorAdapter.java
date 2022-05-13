package com.project.inovationmobile.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.inovationmobile.R;
import com.project.inovationmobile.activities.InovasiDetailActivity;
import com.project.inovationmobile.activities.InovatorDetailActivity;

import java.util.List;

public class ContentInovatorAdapter extends RecyclerView.Adapter<ContentInovatorAdapter.ViewHolder> {

    private final LayoutInflater layoutInflater;
    private final List<String> data;

    public ContentInovatorAdapter(Context context, List<String> data) {
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
        String namaInovator = data.get(i);
        viewHolder.namaInovator.setText(namaInovator);
        // similarly you can set new image for each card and descriptions

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), InovatorDetailActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView namaInovator;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaInovator = itemView.findViewById(R.id.nama_inovator);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

}

