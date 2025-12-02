package com.diego.actividad10.adapters;

import android.graphics.Color; // Importación para cambiar el color del borde
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.diego.actividad10.R;
import com.google.android.material.card.MaterialCardView; // Importar la vista de tarjeta
import com.diego.actividad10.models.Photo;
import java.util.ArrayList;
import java.util.List;

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder> {

    private final List<Photo> photoList;
    private final OnThumbnailClickListener clickListener;
    private final OnSelectionChangeListener selectionListener;

    // ⬅️ NUEVO: Estado del adaptador
    private boolean isSelectionMode = false;
    public interface OnThumbnailClickListener {
        void onThumbnailClick(int position);
    }

    public interface OnSelectionChangeListener {
        void onSelectionChange(int count);
        void onSelectionModeExit();
    }

    public ThumbnailAdapter(List<Photo> photoList, OnThumbnailClickListener clickListener, OnSelectionChangeListener selectionListener) {
        this.photoList = photoList;
        this.clickListener = clickListener;
        this.selectionListener = selectionListener;
    }

    @NonNull
    @Override
    public ThumbnailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_thumbnail, parent, false);
        return new ThumbnailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThumbnailViewHolder holder, int position) {
        Photo photo = photoList.get(position);

        Glide.with(holder.imageView.getContext())
                .load(photo.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imageView);

        if (photo.isSelected()) {
            holder.cardView.setStrokeWidth(8);
            holder.cardView.setStrokeColor(Color.BLUE);
            holder.imageViewCheck.setVisibility(View.VISIBLE);
        } else {
            holder.cardView.setStrokeWidth(0);
            holder.imageViewCheck.setVisibility(View.GONE);
        }

        // ⬅️ NUEVO: Manejo de pulsaciones
        holder.itemView.setOnClickListener(v -> {
            if (isSelectionMode) {
                toggleSelection(position);
            } else {
                clickListener.onThumbnailClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (!isSelectionMode) {
                startSelectionMode(position);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
    private void startSelectionMode(int initialPosition) {
        isSelectionMode = true;
        toggleSelection(initialPosition);
    }

    private void toggleSelection(int position) {
        Photo photo = photoList.get(position);
        photo.setSelected(!photo.isSelected());
        notifyItemChanged(position);

        int count = getSelectedCount();
        if (count == 0) {
            exitSelectionMode();
            selectionListener.onSelectionChange(count);
        }
    }

    /**
     * Sale del modo de selección y resetea todos los estados.
     */
    public void exitSelectionMode() {
        if (isSelectionMode) {
            isSelectionMode = false;
            for (Photo photo : photoList) {
                photo.setSelected(false);
            }
            notifyDataSetChanged();
            selectionListener.onSelectionModeExit();
        }
    }

    public int getSelectedCount() {
        int count = 0;
        for (Photo photo : photoList) {
            if (photo.isSelected()) {
                count++;
            }
        }
        return count;
    }


    public List<Photo> getSelectedPhotos() {
        List<Photo> selected = new ArrayList<>();
        for (Photo photo : photoList) {
            if (photo.isSelected()) {
                selected.add(photo);
            }
        }
        return selected;
    }

    static class ThumbnailViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView imageView;
        ImageView imageViewCheck;

        ThumbnailViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewThumbnail);
            imageView = itemView.findViewById(R.id.imageViewThumbnail);
            imageViewCheck = itemView.findViewById(R.id.imageViewCheck);
        }
    }
}