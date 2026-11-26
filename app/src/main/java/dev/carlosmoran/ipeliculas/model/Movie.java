package dev.carlosmoran.ipeliculas.model;

/**
 * Película de la cartelera.
 * <p>
 * El id se asigna automáticamente con un contador estático, igual que en LePokedex,
 * y es lo que se pasa por el Intent para abrir el detalle.
 *
 * @author Carlos Alberto Morán Lozano
 */
public class Movie {
    private static int nextId = 0;

    private final int id;
    private final String title;
    private final MovieGenre genre;
    private final float rating;          // valoración de 0 a 5 estrellas
    private final String synopsis;       // sinopsis completa (en la lista se recorta a 3 líneas)
    private final String director;
    private final String releaseDate;    // dd/MM/yyyy, se guarda como texto porque solo se muestra
    private final int durationMinutes;

    public Movie(String title, MovieGenre genre, float rating, String synopsis,
                 String director, String releaseDate, int durationMinutes) {
        this.id = nextId++;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.synopsis = synopsis;
        this.director = director;
        this.releaseDate = releaseDate;
        this.durationMinutes = durationMinutes;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public float getRating() {
        return rating;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getDirector() {
        return director;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}
