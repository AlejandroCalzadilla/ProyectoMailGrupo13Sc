package org.mailgrupo13.sistema.negocio;

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

    public ProveedoresN() throws SQLException {
        proveedoresM = new ProveedoresM();
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

    // CRUD Methods
    public List<ProveedoresN> obtenerProveedores() throws SQLException {
        return mapear(proveedoresM.obtenerProveedores());
    }

    public String agregarProveedor(String nombre, String pais, String telefono, String email, String direccion) throws SQLException {
        try {
            validarCampos(nombre, pais, telefono, email, direccion);
            ProveedoresM proveedoresMObj = cargar(0, nombre, pais, telefono, email, direccion);
            proveedoresMObj.crearProveedor();
            return "Proveedor agregado con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar el proveedor: " + e.getMessage();
        }
    }

    public boolean actualizarProveedor(int id, String nombre, String pais, String telefono, String email, String direccion) throws SQLException {
        try {
            validarCampos(nombre, pais, telefono, email, direccion);
            ProveedoresM proveedoresMObj = cargar(id, nombre, pais, telefono, email, direccion);
            return proveedoresMObj.actualizarProveedor();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarProveedor(int id) throws SQLException {
        return proveedoresM.eliminarProveedor(id);
    }

    private List<ProveedoresN> mapear(List<ProveedoresM> proveedoresMList) throws SQLException {
        List<ProveedoresN> proveedoresNList = new ArrayList<>();
        for (ProveedoresM proveedoresM : proveedoresMList) {
            ProveedoresN proveedoresN = new ProveedoresN();
            proveedoresN.setId(proveedoresM.getId());
            proveedoresN.setNombre(proveedoresM.getNombre());
            proveedoresN.setPais(proveedoresM.getPais());
            proveedoresN.setTelefono(proveedoresM.getTelefono());
            proveedoresN.setEmail(proveedoresM.getEmail());
            proveedoresN.setDireccion(proveedoresM.getDireccion());
            proveedoresN.setCreadoEn(proveedoresM.getCreadoEn());
            proveedoresN.setActualizadoEn(proveedoresM.getActualizadoEn());
            proveedoresNList.add(proveedoresN);
        }
        return proveedoresNList;
    }

    private ProveedoresM cargar(int id, String nombre, String pais, String telefono, String email, String direccion) throws SQLException {
        ProveedoresM proveedoresMObj = new ProveedoresM();
        proveedoresMObj.setId(id);
        proveedoresMObj.setNombre(nombre);
        proveedoresMObj.setPais(pais);
        proveedoresMObj.setTelefono(telefono);
        proveedoresMObj.setEmail(email);
        proveedoresMObj.setDireccion(direccion);
        proveedoresMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        proveedoresMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return proveedoresMObj;
    }

    private void validarCampos(String nombre, String pais, String telefono, String email, String direccion) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (pais == null || pais.isEmpty()) {
            throw new IllegalArgumentException("El país no puede estar vacío");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede estar vacía");
        }
        if (nombre.length() < 3) {
            throw new IllegalArgumentException("El nombre es muy corto");
        }
        if (pais.length() < 3) {
            throw new IllegalArgumentException("El país es muy corto");
        }
    }
}