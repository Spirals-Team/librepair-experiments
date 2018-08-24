package convention.persistent;

import javax.persistence.*;

/**
 * Created by sandro on 03/07/17.
 */
@MappedSuperclass
public abstract class Tema extends PersistableSupport {

    public static final String autor_FIELD = "autor";
    public static final String ultimoModificador_FIELD = "ultimoModificador";
    public static final String titulo_FIELD = "titulo";
    public static final String descripcion_FIELD = "descripcion";
    public static final String duracion_FIELD = "duracion";
    @ManyToOne
    private Usuario autor;
    @ManyToOne
    private Usuario ultimoModificador;
    @Column(length = 1024)
    private String titulo;
    @Lob
    private String descripcion;
    @Enumerated(EnumType.STRING)
    private DuracionDeTema duracion;

    public DuracionDeTema getDuracion(){
        return duracion;
    }
    public void setDuracion(DuracionDeTema duracion) {
        this.duracion=duracion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUltimoModificador() {
        return ultimoModificador;
    }

    public void setUltimoModificador(Usuario ultimoModificador) {
        this.ultimoModificador = ultimoModificador;
    }
}
