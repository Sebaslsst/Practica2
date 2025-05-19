package practica.com.base.models;

public class Genero {
    private Integer id;
    private String nombre;
    private Integer id_cancion;

    public Integer getId_cancion() {
        return this.id_cancion;
    }

    public void setId_cancion(Integer id_cancion) {
        this.id_cancion = id_cancion;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
