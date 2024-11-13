package org.mailgrupo13.sistema.negocio.vacunas;

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



    // CRUD Methods
    public String obtenerVacunas() throws SQLException {
        return mapear(vacunasM.obtenerVacunas());
    }

    public VacunasN leerVacuna(int id) throws SQLException {
        VacunasM vacunasMObj = vacunasM.leerVacuna(id);
        VacunasN vacunasNObj = new VacunasN();
        vacunasNObj.setId(vacunasMObj.getId());
        vacunasNObj.setVacuna(vacunasMObj.getVacuna());
        vacunasNObj.setDuracionDias(vacunasMObj.getDuracionDias());
        vacunasNObj.setNotas(vacunasMObj.getNotas());
        vacunasNObj.setCreadoEn(vacunasMObj.getCreadoEn());
        vacunasNObj.setActualizadoEn(vacunasMObj.getActualizadoEn());
        return vacunasNObj;


    }


    public String agregarVacuna(String vacuna, int duracionDias, String notas) throws SQLException {
            validarCampos(vacuna, duracionDias, notas);
            VacunasM vacunasMObj = cargar(0, vacuna, duracionDias, notas);
         return vacunasMObj.crearVacuna();

    }

    public String actualizarVacuna(int id, String vacuna, int duracionDias, String notas) throws SQLException {
            validarCampos(vacuna, duracionDias, notas);
            VacunasM vacunasMObj = cargar(id, vacuna, duracionDias, notas);
            return vacunasMObj.actualizarVacuna();

    }

    public boolean eliminarVacuna(int id) throws SQLException {
        return vacunasM.eliminarVacuna(id);
    }

    private String mapear(List<VacunasM> vacunasMList) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-20s %-15s %-30s %-30s %-30s%n";
        sb.append(String.format(format, "ID", "Vacuna", "Duración (días)", "Notas", "Creado En", "Actualizado En"));
        sb.append("------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (VacunasM vacunasM : vacunasMList) {
            sb.append(String.format(format,
                    vacunasM.getId(),
                    vacunasM.getVacuna(),
                    vacunasM.getDuracionDias(),
                    vacunasM.getNotas(),
                    vacunasM.getCreadoEn(),
                    vacunasM.getActualizadoEn()));
        }
        return sb.toString();
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


    @Override
    public String toString() {
        return "VacunasN{" +
                "id=" + id +
                ", vacuna='" + vacuna + '\'' +
                ", duracionDias=" + duracionDias +
                ", notas='" + notas + '\'' +
                ", creadoEn=" + creadoEn +
                ", actualizadoEn=" + actualizadoEn +
                '}';
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
}