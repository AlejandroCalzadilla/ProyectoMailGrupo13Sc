package org.mailgrupo13.sistema.negocio;

import org.mailgrupo13.sistema.modelo.CategoriasM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CategoriasN {
    private int id;
    private String nombre;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private CategoriasM categoriasM;

    public CategoriasN() throws SQLException {
        categoriasM = new CategoriasM();
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
    public List<CategoriasN> obtenerCategorias() throws SQLException {
        return mapear(categoriasM.obtenerCategorias());
    }

    public String agregarCategoria(String nombre) throws SQLException {
        try {
            validarCampos(nombre);
            CategoriasM categoriasMObj = cargar(0, nombre);
            categoriasMObj.crearCategoria();
            return "Categoría agregada con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar la categoría: " + e.getMessage();
        }
    }

    public boolean actualizarCategoria(int id, String nombre) throws SQLException {
        try {
            validarCampos(nombre);
            CategoriasM categoriasMObj = cargar(id, nombre);
            return categoriasMObj.actualizarCategoria();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarCategoria(int id) throws SQLException {
        return categoriasM.eliminarCategoria(id);
    }

    private List<CategoriasN> mapear(List<CategoriasM> categoriasMList) throws SQLException {
        List<CategoriasN> categoriasNList = new ArrayList<>();
        for (CategoriasM categoriasM : categoriasMList) {
            CategoriasN categoriasN = new CategoriasN();
            categoriasN.setId(categoriasM.getId());
            categoriasN.setNombre(categoriasM.getNombre());
            categoriasN.setCreadoEn(categoriasM.getCreadoEn());
            categoriasN.setActualizadoEn(categoriasM.getActualizadoEn());
            categoriasNList.add(categoriasN);
        }
        return categoriasNList;
    }

    private CategoriasM cargar(int id, String nombre) throws SQLException {
        CategoriasM categoriasMObj = new CategoriasM();
        categoriasMObj.setId(id);
        categoriasMObj.setNombre(nombre);
        categoriasMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        categoriasMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return categoriasMObj;
    }

    private void validarCampos(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nombre.length() < 3) {
            throw new IllegalArgumentException("El nombre es muy corto");
        }
    }
}