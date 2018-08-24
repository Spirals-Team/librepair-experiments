package convention.persistent;

public enum DuracionDeTema {
    CORTO("Corto", 30), LARGO("Largo", 120), MEDIO("Medio", 60);

    public static final String descripcion_FIELD = "descripcion";
    public static final String cantidadDeMinutos_FIELD = "cantidadDeMinutos";
    public static final String nombre_GETTER = "nombre";
    private String descripcion;
    private int cantidadDeMinutos;

    DuracionDeTema(String descripcion, int cantidadDeMinutos) {
        this.descripcion = descripcion;
        this.cantidadDeMinutos = cantidadDeMinutos;

    }

    public String getNombre(){
        return this.name();
    }

    public int getCantidadDeMinutos() {
        return cantidadDeMinutos;
    }

    public void setCantidadDeMinutos(int cantidadDeMinutos) {
        this.cantidadDeMinutos = cantidadDeMinutos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
