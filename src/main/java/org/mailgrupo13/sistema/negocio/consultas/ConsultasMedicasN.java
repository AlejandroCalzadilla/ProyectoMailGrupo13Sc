package org.mailgrupo13.sistema.negocio.consultas;

import org.mailgrupo13.sistema.modelo.ConsultasMedicasM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ConsultasMedicasN {
    private int id;
    private java.sql.Date fecha;
    private String motivo;
    private String diagnostico;
    private Float tarifaConsulta;
    private int petId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private ConsultasMedicasM consultasMedicasM;

    public ConsultasMedicasN() throws SQLException {
        consultasMedicasM = new ConsultasMedicasM();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.sql.Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Float getTarifaConsulta() {
        return tarifaConsulta;
    }

    public void setTarifaConsulta(Float tarifaConsulta) {
        this.tarifaConsulta = tarifaConsulta;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
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
    public List<ConsultasMedicasN> obtenerConsultasMedicas() throws SQLException {
        return mapear(consultasMedicasM.obtenerConsultasMedicas());
    }










   /*
    public String agregarConsultaMedica(java.sql.Date fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId) throws SQLException {
        try {
            validarCampos(fecha, motivo, diagnostico, tarifaConsulta, petId);
            ConsultasMedicasM consultasMedicasMObj = cargar(0, fecha, motivo, diagnostico, tarifaConsulta, petId);
            consultasMedicasMObj.crearConsultaMedica();
            return "Consulta médica agregada con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar la consulta médica: " + e.getMessage();
        }
    }

    */


    /*
    public boolean actualizarConsultaMedica(int id, java.sql.Date fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId) throws SQLException {
        try {
            validarCampos(fecha, motivo, diagnostico, tarifaConsulta, petId);
            ConsultasMedicasM consultasMedicasMObj = cargar(id, fecha, motivo, diagnostico, tarifaConsulta, petId);
            return consultasMedicasMObj.actualizarConsultaMedica();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


     */



    public boolean eliminarConsultaMedica(int id) throws SQLException {
        return consultasMedicasM.eliminarConsultaConTratamientos(id);
    }

    private List<ConsultasMedicasN> mapear(List<ConsultasMedicasM> consultasMedicasMList) throws SQLException {
        List<ConsultasMedicasN> consultasMedicasNList = new ArrayList<>();
        for (ConsultasMedicasM consultasMedicasM : consultasMedicasMList) {
            ConsultasMedicasN consultasMedicasN = new ConsultasMedicasN();
            consultasMedicasN.setId(consultasMedicasM.getId());
            consultasMedicasN.setFecha(consultasMedicasM.getFecha());
            consultasMedicasN.setMotivo(consultasMedicasM.getMotivo());
            consultasMedicasN.setDiagnostico(consultasMedicasM.getDiagnostico());
            consultasMedicasN.setTarifaConsulta(consultasMedicasM.getTarifaConsulta());
            consultasMedicasN.setPetId(consultasMedicasM.getPetId());
            consultasMedicasN.setCreadoEn(consultasMedicasM.getCreadoEn());
            consultasMedicasN.setActualizadoEn(consultasMedicasM.getActualizadoEn());
            consultasMedicasNList.add(consultasMedicasN);
        }
        return consultasMedicasNList;
    }

    private ConsultasMedicasM cargar(int id, java.sql.Date fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId) throws SQLException {
        ConsultasMedicasM consultasMedicasMObj = new ConsultasMedicasM();
        consultasMedicasMObj.setId(id);
        consultasMedicasMObj.setFecha(fecha);
        consultasMedicasMObj.setMotivo(motivo);
        consultasMedicasMObj.setDiagnostico(diagnostico);
        consultasMedicasMObj.setTarifaConsulta(tarifaConsulta);
        consultasMedicasMObj.setPetId(petId);
        consultasMedicasMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        consultasMedicasMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return consultasMedicasMObj;
    }

    private void validarCampos(java.sql.Date fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId) {
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha no puede estar vacía");
        }
        if (motivo == null || motivo.isEmpty()) {
            throw new IllegalArgumentException("El motivo no puede estar vacío");
        }
        if (diagnostico == null || diagnostico.isEmpty()) {
            throw new IllegalArgumentException("El diagnóstico no puede estar vacío");
        }
        if (tarifaConsulta == null || tarifaConsulta <= 0) {
            throw new IllegalArgumentException("La tarifa de consulta debe ser mayor que 0");
        }
        if (petId <= 0) {
            throw new IllegalArgumentException("El ID de la mascota debe ser mayor que 0");
        }
    }
}