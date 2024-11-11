package org.mailgrupo13.sistema.negocio.consultas;

import org.mailgrupo13.sistema.modelo.TratamientosM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TratamientosN {
    private int id;
    private String medicamento;
    private String notas;
    private int consultaId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private TratamientosM tratamientosM;

    public TratamientosN() throws SQLException {
        tratamientosM = new TratamientosM();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(int consultaId) {
        this.consultaId = consultaId;
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
    public List<TratamientosN> obtenerTratamientos() throws SQLException {
        return mapear(tratamientosM.obtenerTratamientos());
    }

    public String agregarTratamiento(String medicamento, String notas, int consultaId) throws SQLException {
        try {
            validarCampos(medicamento, notas, consultaId);
            TratamientosM tratamientosMObj = cargar(0, medicamento, notas, consultaId);
            tratamientosMObj.crearTratamiento();
            return "Tratamiento agregado con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar el tratamiento: " + e.getMessage();
        }
    }

    /*
    public boolean actualizarTratamiento(int id, String medicamento, String notas, int consultaId) throws SQLException {
        try {
            validarCampos(medicamento, notas, consultaId);
            TratamientosM tratamientosMObj = cargar(id, medicamento, notas, consultaId);
            return tratamientosMObj.actualizarTratamiento();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


     */
    /*
    public boolean eliminarTratamiento(int id) throws SQLException {
        return tratamientosM.eliminarTratamiento(id);
    }

     */

    private List<TratamientosN> mapear(List<TratamientosM> tratamientosMList) throws SQLException {
        List<TratamientosN> tratamientosNList = new ArrayList<>();
        for (TratamientosM tratamientosM : tratamientosMList) {
            TratamientosN tratamientosN = new TratamientosN();
            tratamientosN.setId(tratamientosM.getId());
            tratamientosN.setMedicamento(tratamientosM.getMedicamento());
            tratamientosN.setNotas(tratamientosM.getNotas());
            tratamientosN.setConsultaId(tratamientosM.getConsultaId());
            tratamientosN.setCreadoEn(tratamientosM.getCreadoEn());
            tratamientosN.setActualizadoEn(tratamientosM.getActualizadoEn());
            tratamientosNList.add(tratamientosN);
        }
        return tratamientosNList;
    }

    private TratamientosM cargar(int id, String medicamento, String notas, int consultaId) throws SQLException {
        TratamientosM tratamientosMObj = new TratamientosM();
        tratamientosMObj.setId(id);
        tratamientosMObj.setMedicamento(medicamento);
        tratamientosMObj.setNotas(notas);
        tratamientosMObj.setConsultaId(consultaId);
        tratamientosMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        tratamientosMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return tratamientosMObj;
    }

    private void validarCampos(String medicamento, String notas, int consultaId) {
        if (medicamento == null || medicamento.isEmpty()) {
            throw new IllegalArgumentException("El medicamento no puede estar vacío");
        }
        if (notas == null || notas.isEmpty()) {
            throw new IllegalArgumentException("Las notas no pueden estar vacías");
        }
        if (consultaId <= 0) {
            throw new IllegalArgumentException("El ID de la consulta debe ser mayor que 0");
        }
    }
}