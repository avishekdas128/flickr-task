package com.orange.flickrtask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orange.flickrtask.R;
import com.orange.flickrtask.model.Photo;
import com.orange.flickrtask.utils.NetworkState;


import java.util.ArrayList;

public class PhotoAdapter extends PagedListAdapter<Photo,RecyclerView.ViewHolder> {

    private Context context;
    private NetworkState networkState;

    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    public PhotoAdapter(Context context) {
        super(Photo.CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_PROGRESS) {
            View view = layoutInflater.inflate(R.layout.item_progress,parent,false);
            NetworkStateItemViewHolder viewHolder = new NetworkStateItemViewHolder(view);
            return viewHolder;
        } else {
            View view = layoutInflater.inflate(R.layout.image_cards,parent,false);
            PhotoViewHolder viewHolder = new PhotoViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PhotoViewHolder) {
            ((PhotoViewHolder)holder).bindTo(getItem(position));
        } else {
            ((NetworkStateItemViewHolder) holder).bindView(networkState);
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{

        private TextView imageTitle;
        private ImageView imageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageTitle = itemView.findViewById(R.id.tvTitle);
            imageView = itemView.findViewById(R.id.ivMovie);
        }

        public void bindTo(Photo photo) {
            imageTitle.setText(photo.getTitle());
            String imagePath = "https://farm" + photo.getFarm() + ".staticflickr.com/" + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() + ".jpg";
            Glide.with(context)
                    .load(imagePath)
                    .placeholder(R.drawable.loading)
                    .into(imageView);
        }
    }

    public class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        public TextView errorMsg;
        public ProgressBar progressBar;

        public NetworkStateItemViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
            errorMsg = itemView.findViewById(R.id.error_msg);
        }

        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkState.Status.FAILED) {
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText(networkState.getMsg());
            } else {
                errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
