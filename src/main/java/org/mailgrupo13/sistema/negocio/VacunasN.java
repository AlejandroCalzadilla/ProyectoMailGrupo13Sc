package org.mailgrupo13.sistema.negocio;

import org.mailgrupo13.sistema.modelo.VacunasM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class VacunasN {
    private int id;
    private String vacuna;
    private int duracionDias;
    private String notas;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private VacunasM vacunasM;

    public VacunasN() throws SQLException {
        vacunasM = new VacunasM();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
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
    public List<VacunasN> obtenerVacunas() throws SQLException {
        return mapear(vacunasM.obtenerVacunas());
    }

    public String agregarVacuna(String vacuna, int duracionDias, String notas) throws SQLException {
        try {
            validarCampos(vacuna, duracionDias, notas);
            VacunasM vacunasMObj = cargar(0, vacuna, duracionDias, notas);
            vacunasMObj.crearVacuna();
            return "Vacuna agregada con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar la vacuna: " + e.getMessage();
        }
    }

    public boolean actualizarVacuna(int id, String vacuna, int duracionDias, String notas) throws SQLException {
        try {
            validarCampos(vacuna, duracionDias, notas);
            VacunasM vacunasMObj = cargar(id, vacuna, duracionDias, notas);
            return vacunasMObj.actualizarVacuna();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarVacuna(int id) throws SQLException {
        return vacunasM.eliminarVacuna(id);
    }

    private List<VacunasN> mapear(List<VacunasM> vacunasMList) throws SQLException {
        List<VacunasN> vacunasNList = new ArrayList<>();
        for (VacunasM vacunasM : vacunasMList) {
            VacunasN vacunasN = new VacunasN();
            vacunasN.setId(vacunasM.getId());
            vacunasN.setVacuna(vacunasM.getVacuna());
            vacunasN.setDuracionDias(vacunasM.getDuracionDias());
            vacunasN.setNotas(vacunasM.getNotas());
            vacunasN.setCreadoEn(vacunasM.getCreadoEn());
            vacunasN.setActualizadoEn(vacunasM.getActualizadoEn());
            vacunasNList.add(vacunasN);
        }
        return vacunasNList;
    }

    private VacunasM cargar(int id, String vacuna, int duracionDias, String notas) throws SQLException {
        VacunasM vacunasMObj = new VacunasM();
        vacunasMObj.setId(id);
        vacunasMObj.setVacuna(vacuna);
        vacunasMObj.setDuracionDias(duracionDias);
        vacunasMObj.setNotas(notas);
        vacunasMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        vacunasMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return vacunasMObj;
    }

    private void validarCampos(String vacuna, int duracionDias, String notas) {
        if (vacuna == null || vacuna.isEmpty()) {
            throw new IllegalArgumentException("La vacuna no puede estar vacía");
        }
        if (duracionDias <= 0) {
            throw new IllegalArgumentException("La duración en días debe ser mayor que 0");
        }
        if (notas == null || notas.isEmpty()) {
            throw new IllegalArgumentException("Las notas no pueden estar vacías");
        }
    }
}