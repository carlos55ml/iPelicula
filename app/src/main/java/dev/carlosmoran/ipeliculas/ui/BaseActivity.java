package dev.carlosmoran.ipeliculas.ui;

import androidx.activity.EdgeToEdge;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;

import dev.carlosmoran.ipeliculas.R;

/**
 * Clase base de todas las pantallas (mismo enfoque que en MyRefugio).
 * Agrupa lo que se repetiría en cada Activity:
 * <ul>
 *   <li>Modo edge-to-edge y márgenes del sistema (barra de estado y navegación).</li>
 *   <li>Toolbar con título y, opcionalmente, botón de retroceso.</li>
 * </ul>
 *
 * @author Carlos Alberto Morán Lozano
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected MaterialToolbar toolbar;

    /**
     * Igual que {@link #setupScreen(int, String, boolean)} pero con el título como recurso.
     */
    protected void setupScreen(@LayoutRes int layout, @StringRes int title, boolean showBackButton) {
        setupScreen(layout, getString(title), showBackButton);
    }

    /**
     * Carga el layout y configura la toolbar y los márgenes del sistema.
     * Se llama en {@code onCreate} en lugar de {@code setContentView}.
     *
     * @param layout         layout de la pantalla (con una vista raíz de id {@code main}
     *                       y el include de {@code toolbar})
     * @param title          texto del título
     * @param showBackButton si se muestra la flecha de volver
     */
    protected void setupScreen(@LayoutRes int layout, String title, boolean showBackButton) {
        EdgeToEdge.enable(this);
        setContentView(layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
        }

        // Fondo oscuro: los iconos de la barra de estado tienen que ser claros
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
                .setAppearanceLightStatusBars(false);

        // La toolbar ocupa también la zona de la barra de estado;
        // el resto del contenido respeta los márgenes laterales e inferior del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, 0, bars.right, bars.bottom);
            toolbar.setPadding(toolbar.getPaddingLeft(), bars.top, toolbar.getPaddingRight(), 0);
            return insets;
        });
    }

    /**
     * La flecha de volver de la toolbar cierra la pantalla actual.
     *
     * @return siempre {@code true}
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
