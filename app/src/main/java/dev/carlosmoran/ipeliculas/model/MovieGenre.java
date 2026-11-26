package dev.carlosmoran.ipeliculas.model;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;

import dev.carlosmoran.ipeliculas.R;

/**
 * Géneros de las películas de la cartelera.
 * <p>
 * Cada género guarda el texto que se muestra (recurso de strings, para poder traducirlo)
 * y un color de acento que se usa en la etiqueta y el icono de la tarjeta.
 *
 * @author Carlos Alberto Morán Lozano
 */
public enum MovieGenre {
    ACTION(R.string.genre_action, R.color.genre_action),
    SCI_FI(R.string.genre_sci_fi, R.color.genre_sci_fi),
    DRAMA(R.string.genre_drama, R.color.genre_drama),
    COMEDY(R.string.genre_comedy, R.color.genre_comedy),
    ANIMATION(R.string.genre_animation, R.color.genre_animation),
    HORROR(R.string.genre_horror, R.color.genre_horror),
    FANTASY(R.string.genre_fantasy, R.color.genre_fantasy);

    @StringRes
    private final int labelRes;
    @ColorRes
    private final int colorRes;

    MovieGenre(@StringRes int labelRes, @ColorRes int colorRes) {
        this.labelRes = labelRes;
        this.colorRes = colorRes;
    }

    /**
     * @return recurso de texto con el nombre del género
     */
    @StringRes
    public int getLabelRes() {
        return labelRes;
    }

    /**
     * @return recurso de color asociado al género
     */
    @ColorRes
    public int getColorRes() {
        return colorRes;
    }
}
