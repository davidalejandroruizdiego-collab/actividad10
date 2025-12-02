package com.diego.actividad10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.diego.actividad10.adapters.ThumbnailAdapter;
import com.diego.actividad10.models.Photo;
import java.util.List;

public class ThumbnailActivity extends AppCompatActivity
        implements ThumbnailAdapter.OnThumbnailClickListener,
        ThumbnailAdapter.OnSelectionChangeListener,
        ActionMode.Callback {
    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_SELECTED_POSITION = "extra_selected_position";

    private List<Photo> photos;
    private RecyclerView recyclerView;
    private ThumbnailAdapter adapter;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumbnail);

        photos = (List<Photo>) getIntent().getSerializableExtra(EXTRA_PHOTOS);
        if (photos == null || photos.isEmpty()) { finish(); return; }

        recyclerView = findViewById(R.id.recyclerViewThumbnails);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new ThumbnailAdapter(photos, this, this);
        recyclerView.setAdapter(adapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Galería - Miniaturas");
        }
    }

    @Override
    public void onThumbnailClick(int position) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SELECTED_POSITION, position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onSelectionChange(int count) {
        if (actionMode == null) {
            actionMode = startActionMode(this);
        }
        // Actualizar el título de la barra de acción
        if (actionMode != null) {
            actionMode.setTitle(count + " seleccionados");
        }
    }

    @Override
    public void onSelectionModeExit() {
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (adapter.getSelectedCount() > 0) {
            adapter.exitSelectionMode();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_selection_mode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        int selectedCount = adapter.getSelectedCount();
        List<Photo> selectedPhotos = adapter.getSelectedPhotos();

        if (item.getItemId() == R.id.action_share) {
            Toast.makeText(this, "Compartiendo " + selectedCount + " fotos.", Toast.LENGTH_SHORT).show();
            mode.finish();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            Toast.makeText(this, "Eliminando " + selectedCount + " fotos.", Toast.LENGTH_SHORT).show();
            mode.finish();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
        adapter.exitSelectionMode();
    }
}