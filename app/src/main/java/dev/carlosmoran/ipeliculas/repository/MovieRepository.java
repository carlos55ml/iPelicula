package dev.carlosmoran.ipeliculas.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.carlosmoran.ipeliculas.model.Movie;
import dev.carlosmoran.ipeliculas.model.MovieGenre;

/**
 * Almacén en memoria con las películas de la cartelera.
 * <p>
 * Es un Singleton para que todas las Activities compartan los mismos datos
 * (la MainActivity pinta la lista y la DetailActivity busca la película por id).
 *
 * @author Carlos Alberto Morán Lozano
 */
public class MovieRepository {
    private static MovieRepository instance;
    private final List<Movie> movies = new ArrayList<>();

    private MovieRepository() {
        loadSampleMovies();
    }

    /**
     * @return la única instancia del repositorio
     */
    public static MovieRepository getInstance() {
        if (instance == null) instance = new MovieRepository();
        return instance;
    }

    // Datos de ejemplo de la cartelera
    private void loadSampleMovies() {
        movies.add(new Movie("Dune: Parte Dos", MovieGenre.SCI_FI, 4.5f,
                "Paul Atreides se une a Chani y a los Fremen mientras busca venganza contra los "
                        + "conspiradores que destruyeron a su familia. Enfrentado a elegir entre el amor "
                        + "de su vida y el destino del universo, intenta evitar un futuro terrible que "
                        + "solo él puede prever.",
                "Denis Villeneuve", "01/03/2024", 166));
        movies.add(new Movie("Oppenheimer", MovieGenre.DRAMA, 4.5f,
                "La historia del físico J. Robert Oppenheimer y su papel al frente del Proyecto "
                        + "Manhattan, desde los años de investigación hasta la primera prueba nuclear "
                        + "y las consecuencias personales y políticas que vinieron después.",
                "Christopher Nolan", "21/07/2023", 180));
        movies.add(new Movie("La sociedad de la nieve", MovieGenre.DRAMA, 4.5f,
                "En 1972, un avión con un equipo de rugby uruguayo se estrella en el corazón de los "
                        + "Andes. Los supervivientes tendrán que resistir el frío extremo, el hambre y "
                        + "las avalanchas en uno de los lugares más inhóspitos del planeta.",
                "J. A. Bayona", "15/12/2023", 144));
        movies.add(new Movie("Gladiator II", MovieGenre.ACTION, 3.5f,
                "Años después de la muerte de Máximo, Lucio es capturado y obligado a luchar como "
                        + "gladiador en el Coliseo. Con la rabia en el corazón y el futuro del imperio en "
                        + "juego, deberá mirar a su pasado para devolver la gloria a Roma.",
                "Ridley Scott", "15/11/2024", 148));
        movies.add(new Movie("Del revés 2", MovieGenre.ANIMATION, 4.0f,
                "Riley acaba de entrar en la adolescencia y el cuartel general de su mente se "
                        + "prepara para una reforma inesperada: llegan nuevas emociones, con Ansiedad a "
                        + "la cabeza, dispuestas a quedarse con el mando.",
                "Kelsey Mann", "19/06/2024", 96));
        movies.add(new Movie("Robot Dreams", MovieGenre.ANIMATION, 4.0f,
                "En el Nueva York de los años 80, un perro solitario construye un robot para que le "
                        + "haga compañía. Su amistad crece durante un verano inolvidable, hasta que un "
                        + "día en la playa todo cambia.",
                "Pablo Berger", "06/12/2023", 102));
        movies.add(new Movie("Alien: Romulus", MovieGenre.HORROR, 3.5f,
                "Un grupo de jóvenes colonos explora una estación espacial abandonada en busca de "
                        + "una vida mejor. Lo que encuentran allí dentro es la forma de vida más "
                        + "aterradora del universo.",
                "Fede Álvarez", "16/08/2024", 119));
        movies.add(new Movie("Wicked", MovieGenre.FANTASY, 4.0f,
                "Antes de la llegada de Dorothy, en la tierra de Oz, la incomprendida Elphaba y la "
                        + "popular Glinda se conocen en la universidad de Shiz. Una amistad improbable "
                        + "que acabará marcando el destino de ambas.",
                "Jon M. Chu", "22/11/2024", 160));
        movies.add(new Movie("El 47", MovieGenre.DRAMA, 4.0f,
                "Manolo Vital, conductor de autobuses, secuestra el autobús de la línea 47 para "
                        + "demostrar que el barrio de Torre Baró, en Barcelona, merece el mismo servicio "
                        + "que el resto de la ciudad.",
                "Marcel Barrena", "06/09/2024", 110));
        movies.add(new Movie("Barbie", MovieGenre.COMEDY, 3.5f,
                "Barbie vive feliz en Barbieland hasta que empieza a tener dudas existenciales. "
                        + "Junto a Ken viajará al mundo real, donde descubrirá que las cosas no son tan "
                        + "perfectas como creía.",
                "Greta Gerwig", "21/07/2023", 114));
        movies.add(new Movie("Top Gun: Maverick", MovieGenre.ACTION, 4.5f,
                "Tras más de treinta años de servicio, Pete \"Maverick\" Mitchell vuelve a la escuela "
                        + "Top Gun para entrenar a un grupo de jóvenes pilotos en una misión casi "
                        + "imposible, entre ellos el hijo de su antiguo compañero Goose.",
                "Joseph Kosinski", "27/05/2022", 131));
        movies.add(new Movie("Interstellar", MovieGenre.SCI_FI, 5.0f,
                "Con la Tierra al borde del colapso, un grupo de astronautas atraviesa un agujero de "
                        + "gusano en busca de un nuevo hogar para la humanidad, sabiendo que el tiempo "
                        + "corre de forma distinta para los que se quedan.",
                "Christopher Nolan", "07/11/2014", 169));
    }

    /**
     * @return todas las películas (lista de solo lectura)
     */
    public List<Movie> getAll() {
        return Collections.unmodifiableList(movies);
    }

    /**
     * @param id id de la película
     * @return la película con ese id, o {@code null} si no existe
     */
    public Movie getById(int id) {
        for (Movie movie : movies) {
            if (movie.getId() == id) return movie;
        }
        return null;
    }
}
