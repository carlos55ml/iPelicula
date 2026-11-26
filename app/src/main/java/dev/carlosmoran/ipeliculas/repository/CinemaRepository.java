package dev.carlosmoran.ipeliculas.repository;

import android.location.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.carlosmoran.ipeliculas.model.Cinema;

/**
 * Cines de Sevilla y alrededores entre los que se busca el más cercano al usuario.
 * Singleton, como {@link MovieRepository}.
 *
 * @author Carlos Alberto Morán Lozano
 */
public class CinemaRepository {
    private static CinemaRepository instance;
    private final List<Cinema> cinemas = new ArrayList<>();

    private CinemaRepository() {
        // Coordenadas aproximadas: bastan para comparar distancias
        cinemas.add(new Cinema("mk2 Nervión Plaza",
                "Calle Luis de Morales s/n, 41005 Sevilla", 37.3832, -5.9738));
        cinemas.add(new Cinema("Los Arcos Multicines",
                "C.C. Los Arcos, Av. de Andalucía s/n, 41006 Sevilla", 37.3897, -5.9548));
        cinemas.add(new Cinema("Odeon Multicines Plaza de Armas",
                "Plaza de la Legión 8, 41001 Sevilla", 37.3920, -6.0015));
        cinemas.add(new Cinema("Yelmo Cines Premium Lagoh",
                "C.C. Lagoh, Palmas Altas, 41014 Sevilla", 37.3405, -5.9879));
        cinemas.add(new Cinema("Cines Axion Sevilla",
                "C.C. Aleste Plaza, Calle Periodista José Antonio Garmendia 3, 41020 Sevilla",
                37.4040, -5.9300));
        cinemas.add(new Cinema("Metromar Cinemas 12",
                "Av. de los Descubrimientos s/n, 41927 Mairena del Aljarafe", 37.3445, -6.0598));
        cinemas.add(new Cinema("mk2 Alcores",
                "C.C. Los Alcores, Ctra. Sevilla-Málaga km 8, 41500 Alcalá de Guadaíra",
                37.3495, -5.8495));
    }

    /**
     * @return la única instancia del repositorio
     */
    public static CinemaRepository getInstance() {
        if (instance == null) instance = new CinemaRepository();
        return instance;
    }

    /**
     * @return todos los cines (lista de solo lectura)
     */
    public List<Cinema> getAll() {
        return Collections.unmodifiableList(cinemas);
    }

    /**
     * @return cine que se usa cuando no se conoce la ubicación del usuario
     */
    public Cinema getDefault() {
        return cinemas.get(0);
    }

    /**
     * Recorre los cines y se queda con el de menor distancia a la ubicación dada.
     *
     * @param location ubicación del usuario
     * @return el cine más cercano
     */
    public Cinema findNearest(Location location) {
        Cinema nearest = cinemas.get(0);
        float minDistance = nearest.distanceTo(location);
        for (Cinema cinema : cinemas) {
            float distance = cinema.distanceTo(location);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = cinema;
            }
        }
        return nearest;
    }
}
