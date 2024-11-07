package org.mailgrupo13.sistema.negocio;

import org.mailgrupo13.sistema.modelo.AlmacenesM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AlmacenesN {
    private int id;
    private String nombre;
    private String ubicacion;
    private String descripcion;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private AlmacenesM almacenesM;

    public AlmacenesN() throws SQLException {
        almacenesM = new AlmacenesM();
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

    // CRUD Methods
    public List<AlmacenesN> obtenerAlmacenes() throws SQLException {
        return mapear(almacenesM.obtenerAlmacenes());
    }

    public String agregarAlmacen(String nombre, String ubicacion, String descripcion) throws SQLException {
        try {
            validarCampos(nombre, ubicacion, descripcion);
            AlmacenesM almacenesMObj = cargar(0, nombre, ubicacion, descripcion);
            almacenesMObj.crearAlmacen();
            return "Almacén agregado con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar el almacén: " + e.getMessage();
        }
    }

    public boolean actualizarAlmacen(int id, String nombre, String ubicacion, String descripcion) throws SQLException {
        try {
            validarCampos(nombre, ubicacion, descripcion);
            AlmacenesM almacenesMObj = cargar(id, nombre, ubicacion, descripcion);
            return almacenesMObj.actualizarAlmacen();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarAlmacen(int id) throws SQLException {
        return almacenesM.eliminarAlmacen(id);
    }

    private List<AlmacenesN> mapear(List<AlmacenesM> almacenesMList) throws SQLException {
        List<AlmacenesN> almacenesNList = new ArrayList<>();
        for (AlmacenesM almacenesM : almacenesMList) {
            AlmacenesN almacenesN = new AlmacenesN();
            almacenesN.setId(almacenesM.getId());
            almacenesN.setNombre(almacenesM.getNombre());
            almacenesN.setUbicacion(almacenesM.getUbicacion());
            almacenesN.setDescripcion(almacenesM.getDescripcion());
            almacenesN.setCreadoEn(almacenesM.getCreadoEn());
            almacenesN.setActualizadoEn(almacenesM.getActualizadoEn());
            almacenesNList.add(almacenesN);
        }
        return almacenesNList;
    }

    private AlmacenesM cargar(int id, String nombre, String ubicacion, String descripcion) throws SQLException {
        AlmacenesM almacenesMObj = new AlmacenesM();
        almacenesMObj.setId(id);
        almacenesMObj.setNombre(nombre);
        almacenesMObj.setUbicacion(ubicacion);
        almacenesMObj.setDescripcion(descripcion);
        almacenesMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        almacenesMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return almacenesMObj;
    }

    private void validarCampos(String nombre, String ubicacion, String descripcion) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (ubicacion == null || ubicacion.isEmpty()) {
            throw new IllegalArgumentException("La ubicación no puede estar vacía");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (nombre.length() < 3) {
            throw new IllegalArgumentException("El nombre es muy corto");
        }
        if (ubicacion.length() < 3) {
            throw new IllegalArgumentException("La ubicación es muy corta");
        }
    }
}