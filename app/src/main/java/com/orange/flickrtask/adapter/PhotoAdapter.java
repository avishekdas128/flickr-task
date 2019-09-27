package com.orange.flickrtask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orange.flickrtask.R;
import com.orange.flickrtask.model.Photo;


import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private Context context;
    private ArrayList<Photo> photoArrayList;

    public PhotoAdapter(Context context, ArrayList<Photo> photoArrayList) {
        this.context = context;
        this.photoArrayList = photoArrayList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_cards,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.imageTitle.setText(photoArrayList.get(position).getTitle());
        String imagePath = "https://farm" + photoArrayList.get(position).getFarm() + ".staticflickr.com/" + photoArrayList.get(position).getServer() + "/" + photoArrayList.get(position).getId() + "_" + photoArrayList.get(position).getSecret() + ".jpg";
        Glide.with(context)
                .load(imagePath)
                .placeholder(R.drawable.loading)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photoArrayList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{

        private TextView imageTitle;
        private ImageView imageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTitle = itemView.findViewById(R.id.tvTitle);
            imageView = itemView.findViewById(R.id.ivMovie);
        }
    }
}
