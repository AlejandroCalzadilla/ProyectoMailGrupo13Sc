package org.mailgrupo13.sistema.negocio.vacunas;

import org.mailgrupo13.sistema.modelo.HistorialVacunasM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HistorialVacunasN {
    private int id;
    private int petId;
    private int vaccinationId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private Date siguienteFechaVencimiento;
    private HistorialVacunasM historialVacunasM;

    public HistorialVacunasN() throws SQLException {
        historialVacunasM = new HistorialVacunasM();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(int vaccinationId) {
        this.vaccinationId = vaccinationId;
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

    public Date getSiguienteFechaVencimiento() {
        return siguienteFechaVencimiento;
    }

    public void setSiguienteFechaVencimiento(Date siguienteFechaVencimiento) {
        this.siguienteFechaVencimiento = siguienteFechaVencimiento;
    }

    // CRUD Methods
    public String obtenerHistorialesVacunas() throws SQLException {
        return mapear(historialVacunasM.obtenerHistorialesVacunas());
    }

    public String agregarHistorialVacuna(int petId, int vaccinationId, Date siguienteFechaVencimiento) throws SQLException {
        try {
            validarCampos(petId, vaccinationId, siguienteFechaVencimiento);
            HistorialVacunasM historialVacunasMObj = cargar(0, petId, vaccinationId, siguienteFechaVencimiento);
            historialVacunasMObj.crearHistorialVacuna();
            return "Historial de vacuna agregado con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar el historial de vacuna: " + e.getMessage();
        }
    }

    public boolean actualizarHistorialVacuna(int id, int petId, int vaccinationId, Date siguienteFechaVencimiento) throws SQLException {
        try {
            validarCampos(petId, vaccinationId, siguienteFechaVencimiento);
            HistorialVacunasM historialVacunasMObj = cargar(id, petId, vaccinationId, siguienteFechaVencimiento);
            return historialVacunasMObj.actualizarHistorialVacuna();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarHistorialVacuna(int id) throws SQLException {
        return historialVacunasM.eliminarHistorialVacuna(id);
    }

    private String mapear(List<HistorialVacunasM> historialVacunasMList) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-15s %-30s %-30s %-30s%n";
        sb.append(String.format(format, "ID", "Pet ID", "Vaccination ID", "Creado En", "Actualizado En", "Siguiente Fecha Vencimiento"));
        sb.append("------------------------------------------------------------------------------------------------------------\r\n");
        for (HistorialVacunasM historialVacunasM : historialVacunasMList) {
            sb.append(String.format(format,
                    historialVacunasM.getId(),
                    historialVacunasM.getPetId(),
                    historialVacunasM.getVaccinationId(),
                    historialVacunasM.getCreadoEn(),
                    historialVacunasM.getActualizadoEn(),
                    historialVacunasM.getSiguienteFechaVencimiento()));
        }
        return sb.toString();
    }


    public String buscarPorMascotaId(int id) throws SQLException {
        return  mapear(historialVacunasM.obtenerHistorialesVacunasPorMascota(id));
    }



    private HistorialVacunasM cargar(int id, int petId, int vaccinationId, Date siguienteFechaVencimiento) throws SQLException {
        HistorialVacunasM historialVacunasMObj = new HistorialVacunasM();
        historialVacunasMObj.setId(id);
        historialVacunasMObj.setPetId(petId);
        historialVacunasMObj.setVaccinationId(vaccinationId);
        historialVacunasMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        historialVacunasMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        historialVacunasMObj.setSiguienteFechaVencimiento(siguienteFechaVencimiento);
        return historialVacunasMObj;
    }

    private void validarCampos(int petId, int vaccinationId, Date siguienteFechaVencimiento) {
        if (petId <= 0) {
            throw new IllegalArgumentException("El ID de la mascota debe ser mayor que 0");
        }
        if (vaccinationId <= 0) {
            throw new IllegalArgumentException("El ID de la vacuna debe ser mayor que 0");
        }
        if (siguienteFechaVencimiento == null) {
            throw new IllegalArgumentException("La siguiente fecha de vencimiento no puede estar vacía");
        }
    }


}