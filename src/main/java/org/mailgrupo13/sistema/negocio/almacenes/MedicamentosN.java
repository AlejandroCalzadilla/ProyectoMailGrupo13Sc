package org.mailgrupo13.sistema.negocio.almacenes;

import org.mailgrupo13.sistema.modelo.MedicamentosM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class MedicamentosN {

    private int id;
    private String nombre;
    private String dosis;
    private String fabricante;
    private Date fechaCaducidad;
    private boolean sustanciaControlada;
    private int categoriaId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private MedicamentosM medicamentosM;
    private MedicamentosService medicamentosService;

    public MedicamentosN() throws SQLException {
        medicamentosM = new MedicamentosM();
        medicamentosService = new MedicamentosService(medicamentosM);
    }


    public MedicamentosN leerMedicamento(int id) throws SQLException {
        return medicamentosService.leerMedicamento(id);
    }

    public String obtenerMedicamentos() throws SQLException {
        return medicamentosService.obtenerMedicamentos();
    }

    public String agregarMedicamento(String nombre, String dosis, String fabricante, Date fechaCaducidad, boolean sustanciaControlada, int categoriaId) throws SQLException {
        return medicamentosService.agregarMedicamento(nombre, dosis, fabricante, fechaCaducidad, sustanciaControlada, categoriaId);
    }

    public String actualizarMedicamento(int id, String nombre, String dosis, String fabricante, Date fechaCaducidad, boolean sustanciaControlada, int categoriaId) throws SQLException {
        return medicamentosService.actualizarMedicamento(id, nombre, dosis, fabricante, fechaCaducidad, sustanciaControlada, categoriaId);
    }

    public String eliminarMedicamento(int id) throws SQLException {
        return medicamentosService.eliminarMedicamento(id);
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

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public boolean isSustanciaControlada() {
        return sustanciaControlada;
    }

    public void setSustanciaControlada(boolean sustanciaControlada) {
        this.sustanciaControlada = sustanciaControlada;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
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
        return "MedicamentosN{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dosis='" + dosis + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", fechaCaducidad=" + fechaCaducidad +
                ", sustanciaControlada=" + sustanciaControlada +
                ", categoriaId=" + categoriaId +
                '}';
    }
}