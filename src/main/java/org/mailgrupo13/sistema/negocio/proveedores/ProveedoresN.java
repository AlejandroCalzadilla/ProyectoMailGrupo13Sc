package org.mailgrupo13.sistema.negocio.proveedores;

import org.mailgrupo13.sistema.modelo.ProveedoresM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProveedoresN {
    private int id;
    private String nombre;
    private String pais;
    private String telefono;
    private String email;
    private String direccion;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private ProveedoresM proveedoresM;
    private  ProveedoresService proveedoresService;



    public ProveedoresN() throws SQLException {
        proveedoresM = new ProveedoresM();
        proveedoresService= new ProveedoresService(proveedoresM);
    }


    public ProveedoresN leerProveedor(int id ) throws SQLException {

        return  proveedoresService.leerProveedor(id);
    }


    public String obtenerProveedores() throws SQLException {
        return proveedoresService.obtenerProveedores();
    }

    public String agregarProveedor(String nombre, String pais, String telefono, String email, String direccion) throws SQLException {
       return proveedoresService.agregarProveedor(nombre, pais, telefono, email, direccion);
    }

    public String actualizarProveedor(int id, String nombre, String pais, String telefono, String email, String direccion) throws SQLException {
       return proveedoresService.actualizarProveedor(id, nombre, pais, telefono, email, direccion);
    }

    public String eliminarProveedor(int id) throws SQLException {
        return proveedoresM.eliminarProveedor(id);
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
        return "ProveedoresN{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}