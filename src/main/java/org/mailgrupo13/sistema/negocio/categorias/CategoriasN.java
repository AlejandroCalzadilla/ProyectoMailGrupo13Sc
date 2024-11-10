package org.mailgrupo13.sistema.negocio.categorias;

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
    private CategoriaService   categoriasService;
    public CategoriasN() throws SQLException {
        categoriasM = new CategoriasM();
        categoriasService = new CategoriaService(categoriasM);

    }



    public String obtenerCategorias() throws SQLException {
        return categoriasService.obtenerCategorias();
    }

    public String agregarCategoria(String nombre) throws SQLException {
          return categoriasService.agregarCategoria(nombre);
    }

    public String actualizarCategoria(int id, String nombre) throws SQLException {
        return categoriasService.actualizarCategoria(id, nombre);

    }

    public String eliminarCategoria(int id) throws SQLException {
        return categoriasService.eliminarCategoria(id);
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


}