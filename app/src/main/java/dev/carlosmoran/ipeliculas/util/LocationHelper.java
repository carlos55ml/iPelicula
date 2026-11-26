package dev.carlosmoran.ipeliculas.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Utilidades para la ubicación del usuario.
 * <p>
 * Se usa el {@link LocationManager} del sistema (sin Google Play Services) y la
 * última ubicación conocida, que es inmediata y suficiente para elegir un cine.
 *
 * @author Carlos Alberto Morán Lozano
 */
public final class LocationHelper {

    /** Permisos que se piden en tiempo de ejecución. */
    public static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private LocationHelper() {
        // Clase de utilidades: no se instancia
    }

    /**
     * @param context contexto
     * @return {@code true} si el usuario ha concedido la ubicación precisa o la aproximada
     */
    public static boolean hasLocationPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Devuelve la ubicación conocida más reciente entre todos los proveedores
     * (GPS, red y pasivo).
     *
     * @param context contexto
     * @return la ubicación, o {@code null} si no hay permiso o el dispositivo aún no tiene ninguna
     */
    @Nullable
    @SuppressLint("MissingPermission") // el permiso se comprueba al principio del método
    public static Location getLastKnownLocation(Context context) {
        if (!hasLocationPermission(context)) return null;

        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) return null;

        Location best = null;
        for (String provider : locationManager.getProviders(true)) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null && (best == null || location.getTime() > best.getTime())) {
                best = location;
            }
        }
        return best;
    }
}
