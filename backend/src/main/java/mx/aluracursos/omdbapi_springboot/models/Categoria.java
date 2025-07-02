package mx.aluracursos.omdbapi_springboot.models;

public enum Categoria {
    ACCION("Action", "Accion"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comedia"),
    DRAMA("Drama", "Drama"),
    CRIMEN("Crime", "Crimen"),
    ANIMATION("Animation", "Animacion");

    private String categoriaOmdb;
    private String categoriaspanish;

    Categoria(String categoriaOmdb, String categoriaspanish) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaspanish = categoriaspanish;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    public static Categoria fromSpanish(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaspanish.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

}
