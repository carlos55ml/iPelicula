package dev.carlosmoran.ipeliculas;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.carlosmoran.ipeliculas.adapter.MovieAdapter;
import dev.carlosmoran.ipeliculas.repository.MovieRepository;
import dev.carlosmoran.ipeliculas.ui.BaseActivity;

/**
 * Pantalla principal: cartelera con las películas en exhibición.
 *
 * @author Carlos Alberto Morán Lozano
 */
public class MainActivity extends BaseActivity {

    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;

    private final MovieRepository movieRepository = MovieRepository.getInstance();

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
        movieAdapter = new MovieAdapter(movieRepository.getAll(), movie -> {
            // La navegación al detalle se añade con la DetailActivity
        });
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(movieAdapter);
        // La animación de entrada está definida en el XML (android:layoutAnimation);
        // aquí se lanza explícitamente para que se vea al cargar los elementos
        rvMovies.scheduleLayoutAnimation();
    }
}
