package org.mailgrupo13.sistema.negocio.mascotas;

import org.mailgrupo13.sistema.modelo.MascotasM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MascotasN {
    private int id;
    private String nombre;
    private Float peso;
    private String color;
    private java.sql.Date fechaNacimiento;
    private String urlFoto;
    private int idCliente;
    private int idRaza;
    private MascotasM mascotasM;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
   private  MascotasService mascotasService;

    public MascotasN() throws SQLException {
        mascotasM = new MascotasM();
       mascotasService= new MascotasService(mascotasM);
    }




     public String leerMascota(int id) throws SQLException {
       return mascotasService.leerMascota(id);
     }


    public String obtenerMascotas() throws SQLException {
        return mascotasService.obtenerMascotas();
    }

    public String agregarMascota(String nombre, Float peso, String color, String fechaNacimiento, String urlFoto, int idCliente, int idRaza) throws SQLException {
        return mascotasService.agregarMascota(nombre,peso,color,fechaNacimiento,urlFoto,idCliente,idRaza);
    }

    public String actualizarMascota(int id, String nombre, Float peso, String color, String fechaNacimiento, String urlFoto, int idCliente, int idRaza) throws SQLException {
       return  mascotasService.actualizarMascota(id,nombre,peso,color,fechaNacimiento,urlFoto,idCliente,idRaza);
    }

    public String eliminarMascota(int id) throws SQLException {
        return mascotasService.eliminarMascota(id);
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

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public java.sql.Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(java.sql.Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(int idRaza) {
        this.idRaza = idRaza;
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
        return "-----Mascota = " +'\r'+'\n'+
                " id=" + id +
                " nombre='" + nombre + '\r'+'\n' +
                " peso=" + peso + '\r'+'\n' +
                " color='" + color + '\r'+'\n' +
                " fechaNacimiento=" + fechaNacimiento +'\r'+'\n'+
                " urlFoto='" + urlFoto + '\r'+'\n' +
                " idCliente=" + idCliente +
                ", idRaza=" + idRaza
                ;
    }
}