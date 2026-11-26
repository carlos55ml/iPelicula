package dev.carlosmoran.ipeliculas.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.carlosmoran.ipeliculas.R;
import dev.carlosmoran.ipeliculas.model.Movie;

/**
 * Adapter del RecyclerView de la cartelera. Pinta cada película con el layout
 * {@code item_movie} (tarjeta con icono, título, género, estrellas y sinopsis corta).
 * <p>
 * La pulsación se delega en un {@link OnMovieClickListener}: el adapter no sabe
 * a qué pantalla se navega, eso lo decide la Activity.
 *
 * @author Carlos Alberto Morán Lozano
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    /**
     * Lo implementa la Activity para saber qué película se ha pulsado.
     */
    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    private final List<Movie> movies;
    private final OnMovieClickListener listener;

    /**
     * @param movies   películas a mostrar
     * @param listener acción al pulsar una tarjeta
     */
    public MovieAdapter(List<Movie> movies, OnMovieClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    /**
     * Infla una tarjeta nueva. El RecyclerView solo crea las que caben en pantalla
     * y las va reutilizando al hacer scroll.
     */
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    /**
     * Rellena una tarjeta (posiblemente reutilizada) con la película de esa posición.
     */
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        int genreColor = ContextCompat.getColor(holder.itemView.getContext(),
                movie.getGenre().getColorRes());

        holder.tvTitle.setText(movie.getTitle());
        holder.tvGenre.setText(movie.getGenre().getLabelRes());
        holder.rbRating.setRating(movie.getRating());
        holder.tvSynopsis.setText(movie.getSynopsis());

        // El color del género se aplica a la etiqueta y al icono
        holder.tvGenre.setBackgroundTintList(ColorStateList.valueOf(genreColor));
        ImageViewCompat.setImageTintList(holder.ivIcon, ColorStateList.valueOf(genreColor));

        holder.itemView.setOnClickListener(v -> listener.onMovieClick(movie));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /**
     * Guarda las referencias a las vistas de una tarjeta para no repetir
     * {@code findViewById} cada vez que se reutiliza.
     */
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivIcon;
        final TextView tvTitle;
        final TextView tvGenre;
        final RatingBar rbRating;
        final TextView tvSynopsis;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivMovieIcon);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvGenre = itemView.findViewById(R.id.tvMovieGenre);
            rbRating = itemView.findViewById(R.id.rbMovieRating);
            tvSynopsis = itemView.findViewById(R.id.tvMovieSynopsis);
        }
    }
}
