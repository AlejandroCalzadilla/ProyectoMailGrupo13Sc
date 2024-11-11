package org.mailgrupo13.sistema.negocio.almacenes;

import org.mailgrupo13.sistema.modelo.AlmacenesM;

import java.sql.SQLException;
import java.sql.Timestamp;


public class AlmacenesN {
    private int id;
    private String nombre;
    private String ubicacion;
    private String descripcion;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private AlmacenesM almacenesM;
    private  AlmacenesService almacenesService;
    public AlmacenesN() throws SQLException {
        almacenesM = new AlmacenesM();
        almacenesService = new AlmacenesService(almacenesM);

    }




   public AlmacenesN leerAlmacen(int id) throws SQLException {
        return almacenesService.leerAlmacen(id);
   }


    public String obtenerAlmacenes() throws SQLException {
        return almacenesService.obtenerAlmacenes();
    }

    public String agregarAlmacen(String nombre, String ubicacion, String descripcion) throws SQLException {
        return almacenesService.agregarAlmacen(nombre, ubicacion, descripcion);
    }

    public String actualizarAlmacen(int id, String nombre, String ubicacion, String descripcion) throws SQLException {
        return almacenesService.actualizarAlmacen(id, nombre, ubicacion, descripcion);
    }

    public String eliminarAlmacen(int id) throws SQLException {
        return almacenesService.eliminarAlmacen(id);
    }





    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Timestamp creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Timestamp getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(Timestamp actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    @Override
    public String toString() {
        return "Almacen: " + "id=" + id + ", nombre='" + nombre + '\'' + ", ubicacion='" + ubicacion + '\'' +
                ", descripcion='" + descripcion + '\'' + ", creadoEn=" + creadoEn +
                ", actualizadoEn=" + actualizadoEn ;
    }
}