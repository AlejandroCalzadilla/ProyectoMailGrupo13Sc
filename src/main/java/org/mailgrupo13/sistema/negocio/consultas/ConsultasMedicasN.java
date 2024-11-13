package org.mailgrupo13.sistema.negocio.consultas;

import org.mailgrupo13.sistema.modelo.ConsultasMedicasM;

import java.sql.Date;
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
    private  int user_id;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private ConsultasMedicasM consultasMedicasM;
    private  ConsultasServices consultasServices;
    public ConsultasMedicasN() throws SQLException {
        consultasMedicasM = new ConsultasMedicasM();
        consultasServices = new ConsultasServices();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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







    public String leerConsulta(int id ) throws SQLException {
        return  consultasServices.leerConsulta(id);
    }
    // CRUD Methods
    public String obtenerConsultasMedicas() throws SQLException {
        return consultasServices.obtenerConsultasMedicas();
    }


    public String agregarConsultaMedica(String fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId,int user_id,List<TratamientosN> tratamientosNS) throws SQLException {
        return  consultasServices.agregarConsultaMedicaConTratamientos(fecha, motivo, diagnostico, tarifaConsulta, petId,user_id ,tratamientosNS);
    }


    public String actualizarConsultaMedica(int id, String fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId,int user_id, List<TratamientosN> tratamientosNS) throws SQLException {
       return consultasServices.actualizarConsultaMedicaConTratamientos(id, fecha, motivo, diagnostico, tarifaConsulta, petId,user_id ,tratamientosNS);
    }


    public boolean eliminarConsultaMedica(int id) throws SQLException {
        return consultasMedicasM.eliminarConsultaConTratamientos(id);
    }




}