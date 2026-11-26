package dev.carlosmoran.ipeliculas.ui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import dev.carlosmoran.ipeliculas.R;
import dev.carlosmoran.ipeliculas.model.Movie;
import dev.carlosmoran.ipeliculas.repository.MovieRepository;

/**
 * Detalle de una película: título, género, valoración, director, fecha de estreno,
 * duración y sinopsis completa. Recibe el id de la película por el Intent.
 *
 * @author Carlos Alberto Morán Lozano
 */
public class DetailActivity extends BaseActivity {

    /** Clave del extra con el id de la película a mostrar. */
    public static final String EXTRA_MOVIE_ID = "movieId";

    private ImageView ivIcon;
    private TextView tvTitle;
    private TextView tvGenre;
    private RatingBar rbRating;
    private TextView tvRating;
    private TextView tvDirector;
    private TextView tvReleaseDate;
    private TextView tvDuration;
    private TextView tvSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Con flecha de volver en la toolbar
        setupScreen(R.layout.activity_detail, R.string.title_detail, true);

        bindViews();

        int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);
        Movie movie = MovieRepository.getInstance().getById(movieId);
        if (movie == null) {
            // No debería pasar, pero si llega un id incorrecto se vuelve a la cartelera
            Toast.makeText(this, R.string.msg_movie_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        showMovie(movie);
    }

    // Asignación de las vistas del layout
    private void bindViews() {
        ivIcon = findViewById(R.id.ivDetailIcon);
        tvTitle = findViewById(R.id.tvDetailTitle);
        tvGenre = findViewById(R.id.tvDetailGenre);
        rbRating = findViewById(R.id.rbDetailRating);
        tvRating = findViewById(R.id.tvDetailRating);
        tvDirector = findViewById(R.id.tvDetailDirector);
        tvReleaseDate = findViewById(R.id.tvDetailReleaseDate);
        tvDuration = findViewById(R.id.tvDetailDuration);
        tvSynopsis = findViewById(R.id.tvDetailSynopsis);
    }

    /**
     * Rellena la pantalla con los datos de la película.
     *
     * @param movie película a mostrar
     */
    private void showMovie(Movie movie) {
        int genreColor = ContextCompat.getColor(this, movie.getGenre().getColorRes());

        // El título de la toolbar pasa a ser el de la película
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(movie.getTitle());

        tvTitle.setText(movie.getTitle());
        tvGenre.setText(movie.getGenre().getLabelRes());
        tvGenre.setBackgroundTintList(ColorStateList.valueOf(genreColor));
        ImageViewCompat.setImageTintList(ivIcon, ColorStateList.valueOf(genreColor));

        rbRating.setRating(movie.getRating());
        tvRating.setText(getString(R.string.detail_rating_value, movie.getRating()));

        tvDirector.setText(movie.getDirector());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvDuration.setText(getString(R.string.detail_duration_value, movie.getDurationMinutes()));
        tvSynopsis.setText(movie.getSynopsis());
    }
}
