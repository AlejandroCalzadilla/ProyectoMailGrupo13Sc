package org.mailgrupo13.sistema.negocio.mascotas;

import org.mailgrupo13.sistema.modelo.EspeciesM;

import java.sql.SQLException;
import java.sql.Timestamp;

public class EspeciesN {
    private int id;
    private String nombre;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    EspeciesM especieM;
    EspeciesService especiesService;

    @Override
    public String toString() {
        return "Especie: " + "id=" + id + ", nombre='" + nombre + '\'' ;
    }

    public EspeciesN( ) throws SQLException {
        especieM = new EspeciesM();
        especiesService=new EspeciesService(especieM);
    }


    public String agregarEspecie(String nombre) throws SQLException {
       return  especiesService.agregarEspecie(nombre);
    }


    public String actualizarEspecie(int id, String nombre) throws SQLException {
       return  especiesService.actualizarEspecie(id,nombre);
    }



    public String eliminarEspecie(int id) throws SQLException {
        return especiesService.eliminarEspecie(id);
    }

    public String obtenerEspecies() throws SQLException {
        return especiesService.obtenerEspecies();
    }

     public EspeciesN leerEspecie(int id) throws SQLException {
        return especiesService.leerEspecie(id);
     }




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
}
