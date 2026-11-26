package dev.carlosmoran.ipeliculas.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Locale;

import dev.carlosmoran.ipeliculas.R;
import dev.carlosmoran.ipeliculas.model.Cinema;

/**
 * Abre Google Maps mediante Intents implícitos (ACTION_VIEW).
 * <p>
 * Se intenta abrir directamente la app de Google Maps y, si no está instalada,
 * cualquier otra app que entienda la URI (navegador u otra app de mapas).
 *
 * @author Carlos Alberto Morán Lozano
 */
public final class MapsLauncher {

    private static final String GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps";

    private MapsLauncher() {
        // Clase de utilidades: no se instancia
    }

    /**
     * "Ir al cine": muestra el cine en el mapa.
     * URI {@code geo:lat,lng?q=nombre, dirección}
     *
     * @param context contexto
     * @param cinema  cine a mostrar
     */
    public static void showCinema(Context context, Cinema cinema) {
        Uri uri = Uri.parse(String.format(Locale.US, "geo:%f,%f?q=%s",
                cinema.getLatitude(), cinema.getLongitude(), Uri.encode(cinema.getMapsQuery())));
        open(context, uri);
    }

    /**
     * "Ir al cine" sin ubicación conocida: Maps busca cines alrededor del usuario.
     * URI {@code geo:0,0?q=cines}
     *
     * @param context contexto
     */
    public static void searchNearbyCinemas(Context context) {
        open(context, Uri.parse("geo:0,0?q=" + Uri.encode("cines")));
    }

    /**
     * "Cómo llegar": abre la ruta desde la ubicación actual hasta el cine.
     * Usa la URL universal de Google Maps:
     * {@code https://www.google.com/maps/dir/?api=1&origin=...&destination=...}
     *
     * @param context contexto
     * @param origin  ubicación del usuario; si es {@code null} Maps usa su propia ubicación actual
     * @param cinema  cine de destino
     */
    public static void showDirections(Context context, @Nullable Location origin, Cinema cinema) {
        Uri.Builder builder = Uri.parse("https://www.google.com/maps/dir/").buildUpon()
                .appendQueryParameter("api", "1")
                .appendQueryParameter("destination", cinema.getMapsQuery())
                .appendQueryParameter("travelmode", "driving");
        if (origin != null) {
            builder.appendQueryParameter("origin", String.format(Locale.US, "%f,%f",
                    origin.getLatitude(), origin.getLongitude()));
        }
        open(context, builder.build());
    }

    // Lanza el Intent: primero con Google Maps y, si no está, con cualquier app compatible
    private static void open(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(GOOGLE_MAPS_PACKAGE);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent.setPackage(null);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(context, R.string.msg_no_maps_app, Toast.LENGTH_LONG).show();
            }
        }
    }
}
