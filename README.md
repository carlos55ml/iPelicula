# iPeliculas

Cartelera de cine para Android (Java) — Proyecto evaluable de **Programación Multimedia y Dispositivos Móviles** (2º DAM).

Autor: **Carlos Alberto Morán Lozano** · [carlosmoran.dev](https://carlosmoran.dev)

## Funcionalidades

| Pantalla | Qué hace |
|---|---|
| `MainActivity` | Cartelera en un `RecyclerView` con tarjetas (`MaterialCardView`): icono, título, género, `RatingBar` de solo lectura y sinopsis corta (3 líneas). Las tarjetas entran con una animación (deslizamiento desde abajo + aparición gradual) definida con `layoutAnimation`. |
| `DetailActivity` | Ficha completa: título, género, valoración, director, fecha de estreno, duración y sinopsis. Botón de retroceso en la toolbar. |
| `AboutActivity` | Información de la app y del desarrollador. Botón de retroceso en la toolbar. |
| Menú **Ir al cine** | Abre Google Maps con el cine más cercano a la ubicación del usuario. |
| Menú **Cómo llegar** | Abre la ruta en Google Maps desde la ubicación actual hasta el cine más cercano. |

## Estructura

```
dev.carlosmoran.ipeliculas
├── MainActivity            cartelera + menú + permisos de ubicación
├── adapter/MovieAdapter    adapter del RecyclerView (ViewHolder + listener de clic)
├── model/                  Movie, MovieGenre (enum con texto y color), Cinema
├── repository/             MovieRepository y CinemaRepository (Singleton, datos en memoria)
├── ui/                     BaseActivity (toolbar + edge-to-edge), DetailActivity, AboutActivity
└── util/                   LocationHelper (permisos y última ubicación), MapsLauncher (Intents a Maps)
```

- **Persistencia:** los datos están en memoria en repositorios Singleton (mismo patrón que LePokedex).
- **Permisos:** `ACCESS_FINE_LOCATION` y `ACCESS_COARSE_LOCATION`, pedidos en tiempo de ejecución con `ActivityResultContracts.RequestMultiplePermissions`. Si se deniegan, la app sigue funcionando: "Ir al cine" busca cines en Maps y "Cómo llegar" usa un cine por defecto.
- **Diseño responsivo:** `GridLayoutManager` con 1 columna en móvil vertical, 2 en horizontal o tablet y 3 en tablet horizontal (`res/values*/integers.xml`). El detalle tiene layout propio en horizontal (`layout-land`).
- **Tema:** oscuro con acentos dorados y rojos (`res/values/colors.xml`).

## Probar en el emulador

1. Ejecutar en un emulador con **Google Play** (trae Google Maps).
2. Para "Ir al cine" / "Cómo llegar": en *Extended controls → Location* fijar una ubicación (p. ej. Sevilla) y pulsar *Set location*. Abrir Maps una vez ayuda a que el sistema tenga una "última ubicación conocida".
3. Girar el emulador para comprobar el diseño en horizontal.

## Plan de trabajo (un commit por tarea)

| # | Tarea | Commit |
|---|---|---|
| T0 | Proyecto base de Android Studio y repositorio | `chore: initial Android Studio project` |
| T1 | Modelo `Movie`, `MovieGenre` y `MovieRepository` con datos | `feat(Model): ...` |
| T2 | Tema cinematográfico, toolbar común y `BaseActivity` | `feat(UI): added cinema dark theme...` |
| T3 | Cartelera: `RecyclerView` + `CardView` + `MovieAdapter` | `feat: added movie list...` |
| T4 | Animación de entrada del `RecyclerView` | `feat(Anim): ...` |
| T5 | `DetailActivity` con la ficha completa | `feat: added DetailActivity...` |
| T6 | Menú de la toolbar y `AboutActivity` | `feat: added toolbar menu and AboutActivity` |
| T7 | Ir al cine / Cómo llegar con Google Maps y permiso de ubicación | `feat(Maps): ...` |
| T8 | Diseño responsivo (columnas y layout horizontal) | `feat(UI): added responsive grid...` |
| T9 | Pruebas en emuladores (vertical/horizontal, móvil/tablet, permiso concedido/denegado), capturas y documentación | — |
