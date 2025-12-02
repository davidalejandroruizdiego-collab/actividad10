package com.diego.actividad10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.diego.actividad10.adapters.PhotoPagerAdapter;
import com.diego.actividad10.models.Photo;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPagerGallery;
    private PhotoPagerAdapter adapter;
    private TextView textViewPositionIndicator;
    private Button buttonPrevious;
    private Button buttonNext;
    private Button buttonGrid;
    private List<Photo> photos;

    private static final int REQUEST_CODE_THUMBNAIL = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeData();
        initializeUI();
        setupViewPager();
        setupListeners();
        updateIndicator(0);
    }

    private void initializeData() {
        photos = new ArrayList<>();
        photos.add(new Photo(1, "Atardecer en la Playa", "Colores cálidos sobre el mar", "15/11/2024", "https://picsum.photos/400/600?random=1"));
        photos.add(new Photo(2, "Montañas Nevadas", "Picos altos y cielo despejado", "01/02/2025", "https://picsum.photos/400/600?random=2"));
        photos.add(new Photo(3, "Retrato Urbano", "Luz de neón en la ciudad", "10/03/2025", "https://picsum.photos/400/600?random=3"));
        photos.add(new Photo(4, "Flores Silvestres", "Primer plano de un campo", "22/05/2025", "https://picsum.photos/400/600?random=4"));
        photos.add(new Photo(5, "Río Tranquilo", "Vista panorámica del valle", "05/08/2025", "https://picsum.photos/400/600?random=5"));
    }

    private void initializeUI() {
        viewPagerGallery = findViewById(R.id.viewPagerGallery);
        textViewPositionIndicator = findViewById(R.id.textViewPositionIndicator);
        buttonPrevious = findViewById(R.id.buttonPrevious);
        buttonNext = findViewById(R.id.buttonNext);
        buttonGrid = findViewById(R.id.buttonGrid); // ⬅️ Nuevo: Inicialización del botón Grid
    }

    private void setupViewPager() {
        adapter = new PhotoPagerAdapter(photos);
        viewPagerGallery.setAdapter(adapter);

        viewPagerGallery.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateIndicator(position);
            }
        });
    }

    private void setupListeners() {
        // Botón Anterior
        buttonPrevious.setOnClickListener(v -> {
            int currentItem = viewPagerGallery.getCurrentItem();
            if (currentItem > 0) {
                viewPagerGallery.setCurrentItem(currentItem - 1, true); // Desplazamiento suave (true)
            }
        });

        buttonNext.setOnClickListener(v -> {
            int currentItem = viewPagerGallery.getCurrentItem();
            if (currentItem < photos.size() - 1) {
                viewPagerGallery.setCurrentItem(currentItem + 1, true); // Desplazamiento suave (true)
            }
        });

        buttonGrid.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ThumbnailActivity.class);
            intent.putExtra(ThumbnailActivity.EXTRA_PHOTOS, (java.io.Serializable) photos);

            startActivityForResult(intent, REQUEST_CODE_THUMBNAIL);
        });
    }

    // ⬅️ Nuevo: Método para recibir el resultado de ThumbnailActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THUMBNAIL && resultCode == RESULT_OK && data != null) {

            int selectedPosition = data.getIntExtra(ThumbnailActivity.EXTRA_SELECTED_POSITION, 0);

            viewPagerGallery.setCurrentItem(selectedPosition, true);
        }
    }

    private void updateIndicator(int position) {
        String indicatorText = (position + 1) + " de " + photos.size();
        textViewPositionIndicator.setText(indicatorText);

        buttonPrevious.setEnabled(position > 0);
        buttonNext.setEnabled(position < photos.size() - 1);
    }
}