package dev.carlosmoran.ipeliculas;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import dev.carlosmoran.ipeliculas.adapter.MovieAdapter;
import dev.carlosmoran.ipeliculas.model.Cinema;
import dev.carlosmoran.ipeliculas.repository.CinemaRepository;
import dev.carlosmoran.ipeliculas.repository.MovieRepository;
import dev.carlosmoran.ipeliculas.ui.AboutActivity;
import dev.carlosmoran.ipeliculas.ui.BaseActivity;
import dev.carlosmoran.ipeliculas.ui.DetailActivity;
import dev.carlosmoran.ipeliculas.util.LocationHelper;
import dev.carlosmoran.ipeliculas.util.MapsLauncher;

/**
 * Pantalla principal: cartelera con las películas en exhibición.
 * <p>
 * Desde el menú de la toolbar se accede a "Ir al cine", "Cómo llegar" y "Acerca de".
 * Las dos primeras necesitan la ubicación del usuario para elegir el cine más cercano,
 * por eso se pide el permiso de ubicación en tiempo de ejecución.
 *
 * @author Carlos Alberto Morán Lozano
 */
public class MainActivity extends BaseActivity {

    /** Acción de mapas que estaba esperando a que el usuario respondiera al permiso. */
    private enum MapsAction { GO_TO_CINEMA, DIRECTIONS }

    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;

    private final MovieRepository movieRepository = MovieRepository.getInstance();
    private final CinemaRepository cinemaRepository = CinemaRepository.getInstance();

    private MapsAction pendingMapsAction;

    // Petición de permisos de ubicación. Se registra al crear la Activity (obligatorio)
    // y al recibir la respuesta se ejecuta la acción que el usuario había pulsado.
    private final ActivityResultLauncher<String[]> locationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                    this::onLocationPermissionResult);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pantalla principal: sin flecha de volver
        setupScreen(R.layout.activity_main, R.string.title_main, false);

        rvMovies = findViewById(R.id.rvMovies);
        setupMovieList();
    }

    /**
     * Configura el RecyclerView con su LayoutManager y el adapter de películas.
     */
    private void setupMovieList() {
        // Al pulsar una película se abre su detalle pasando el id por el Intent
        movieAdapter = new MovieAdapter(movieRepository.getAll(), movie -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.getId());
            startActivity(intent);
        });
        // Diseño responsivo: el número de columnas depende del tamaño y la orientación
        // de la pantalla (res/values*/integers.xml). En móvil vertical es 1, como una lista.
        int columns = getResources().getInteger(R.integer.movie_grid_columns);
        rvMovies.setLayoutManager(new GridLayoutManager(this, columns));
        rvMovies.setAdapter(movieAdapter);
        // La animación de entrada está definida en el XML (android:layoutAnimation);
        // aquí se lanza explícitamente para que se vea al cargar los elementos
        rvMovies.scheduleLayoutAnimation();
    }

    // ---------------- Menú de la toolbar ----------------

    /**
     * Infla el menú de la toolbar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Gestiona las opciones del menú de la toolbar.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (itemId == R.id.action_go_to_cinema) {
            runWithLocationPermission(MapsAction.GO_TO_CINEMA);
            return true;
        } else if (itemId == R.id.action_directions) {
            runWithLocationPermission(MapsAction.DIRECTIONS);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ---------------- Mapas y permisos ----------------

    /**
     * Ejecuta la acción si ya hay permiso de ubicación; si no, lo pide y la deja pendiente.
     *
     * @param action acción a ejecutar
     */
    private void runWithLocationPermission(MapsAction action) {
        if (LocationHelper.hasLocationPermission(this)) {
            runMapsAction(action);
        } else {
            pendingMapsAction = action;
            locationPermissionLauncher.launch(LocationHelper.LOCATION_PERMISSIONS);
        }
    }

    /**
     * Respuesta del usuario al diálogo de permisos. Si lo deniega, la acción se ejecuta
     * igualmente pero sin ubicación (Maps busca los cines o calcula la ruta por su cuenta).
     *
     * @param results permiso → concedido o no
     */
    private void onLocationPermissionResult(Map<String, Boolean> results) {
        if (!results.containsValue(true)) {
            Toast.makeText(this, R.string.msg_location_denied, Toast.LENGTH_LONG).show();
        }
        if (pendingMapsAction != null) {
            runMapsAction(pendingMapsAction);
            pendingMapsAction = null;
        }
    }

    /**
     * Busca el cine más cercano a la ubicación del usuario y abre Google Maps.
     *
     * @param action "Ir al cine" o "Cómo llegar"
     */
    private void runMapsAction(MapsAction action) {
        Location userLocation = LocationHelper.getLastKnownLocation(this);

        if (action == MapsAction.GO_TO_CINEMA) {
            if (userLocation != null) {
                Cinema nearest = cinemaRepository.findNearest(userLocation);
                showNearestCinemaToast(nearest);
                MapsLauncher.showCinema(this, nearest);
            } else {
                // Sin ubicación: Maps muestra los cines cercanos a donde esté el usuario
                MapsLauncher.searchNearbyCinemas(this);
            }
        } else {
            Cinema destination;
            if (userLocation != null) {
                destination = cinemaRepository.findNearest(userLocation);
                showNearestCinemaToast(destination);
            } else {
                // Sin ubicación no se puede saber cuál es el más cercano: se usa el cine por defecto
                // y Maps calcula la ruta desde su propia ubicación actual
                destination = cinemaRepository.getDefault();
                Toast.makeText(this, R.string.msg_location_unknown, Toast.LENGTH_LONG).show();
            }
            MapsLauncher.showDirections(this, userLocation, destination);
        }
    }

    private void showNearestCinemaToast(Cinema cinema) {
        Toast.makeText(this, getString(R.string.msg_nearest_cinema, cinema.getName()),
                Toast.LENGTH_SHORT).show();
    }
}
