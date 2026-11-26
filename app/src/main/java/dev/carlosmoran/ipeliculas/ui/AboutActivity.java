package dev.carlosmoran.ipeliculas.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import dev.carlosmoran.ipeliculas.R;

/**
 * Pantalla "Acerca de": nombre y versión de la app e información del desarrollador.
 *
 * @author Carlos Alberto Morán Lozano
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Con flecha de volver en la toolbar
        setupScreen(R.layout.activity_about, R.string.title_about, true);

        String version = getAppVersion();
        TextView tvVersion = findViewById(R.id.tvAboutVersion);
        TextView tvDescription = findViewById(R.id.tvAboutDescription);
        tvVersion.setText(getString(R.string.about_version, version));
        tvDescription.setText(getString(R.string.about_description, version));
    }

    /**
     * Lee la versión de la app (versionName de build.gradle) para no escribirla a mano.
     *
     * @return el nombre de versión, o "1.0" si no se puede leer
     */
    private String getAppVersion() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0";
        }
    }
}
