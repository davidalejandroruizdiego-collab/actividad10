package com.diego.actividad10.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.diego.actividad10.R;
import com.diego.actividad10.models.Photo;
import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoPagerAdapter extends RecyclerView.Adapter<PhotoPagerAdapter.PhotoViewHolder> {

    private final List<Photo> photoList;

    public PhotoPagerAdapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_card, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photoList.get(position);

        holder.textViewTitle.setText(photo.getTitle());
        holder.textViewDescription.setText(photo.getDescription());
        holder.textViewDate.setText("📅 Fecha: " + photo.getDate());

        Glide.with(holder.imageViewPhoto.getContext())
                .load(photo.getImageUrl()) // Carga desde la URL/ruta
                .placeholder(R.drawable.ic_launcher_background) // Imagen de carga
                .into(holder.imageViewPhoto);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPhoto;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewDate;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}