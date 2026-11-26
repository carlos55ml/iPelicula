package dev.carlosmoran.ipeliculas.model;

import android.location.Location;

/**
 * Cine con su dirección y coordenadas.
 * <p>
 * Las coordenadas solo se usan para calcular cuál es el más cercano al usuario;
 * para abrir Google Maps se usa el nombre y la dirección, así Maps encuentra
 * el sitio exacto aunque las coordenadas sean aproximadas.
 *
 * @author Carlos Alberto Morán Lozano
 */
public class Cinema {
    private final String name;
    private final String address;
    private final double latitude;
    private final double longitude;

    public Cinema(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /**
     * @return texto de búsqueda para Google Maps ("nombre, dirección")
     */
    public String getMapsQuery() {
        return name + ", " + address;
    }

    /**
     * @param location ubicación de referencia
     * @return distancia en metros desde esa ubicación hasta el cine
     */
    public float distanceTo(Location location) {
        float[] result = new float[1];
        Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                latitude, longitude, result);
        return result[0];
    }
}
