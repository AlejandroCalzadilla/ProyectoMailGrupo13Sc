package org.mailgrupo13.sistema.negocio.consultas;

import org.mailgrupo13.sistema.modelo.ConsultasMedicasM;
import org.mailgrupo13.sistema.modelo.TratamientosM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ConsultasServices {


    private ConsultasMedicasM consultasMedicasM;

    public ConsultasServices() throws SQLException {
        consultasMedicasM = new ConsultasMedicasM();
    }




    public String obtenerConsultasMedicas() throws SQLException {
        return mapearConsultas(consultasMedicasM.obtenerConsultasMedicas());
    }

    public String agregarConsultaMedicaConTratamientos(String fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId,int user_id,List<TratamientosN> tratamientos) throws SQLException {
        try {
            // Convert List<TratamientosN> to List<TratamientosM>
            List<TratamientosM> tratamientosMList = new ArrayList<>();
            for (TratamientosN tratamientoN : tratamientos) {
                validarCamposTratamientos(tratamientoN.getMedicamento(), tratamientoN.getNotas(), tratamientoN.getConsultaId());
                tratamientosMList.add(mapearTratamiento(tratamientoN));
            }

            validarCampos(Date.valueOf(fecha), motivo, diagnostico, tarifaConsulta, petId);

            boolean success = consultasMedicasM.agregarConsultaConTratamientos(Date.valueOf(fecha), motivo, diagnostico, tarifaConsulta, petId,user_id, tratamientosMList);
            if (success) {
                return "Consulta médica y tratamientos agregados con éxito";
            } else {
                return "Error al agregar la consulta médica y tratamientos";
            }
        } catch (SQLException e) {
            return "Error al agregar la consulta médica y tratamientos: " + e.getMessage();
        }
    }








    public String actualizarConsultaMedicaConTratamientos(int consultaId, String fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId,int user_id ,List<TratamientosN> tratamientos) throws SQLException {
        try {
            List<TratamientosM> tratamientosMList = new ArrayList<>();
            for (TratamientosN tratamientoN : tratamientos) {
                tratamientosMList.add(mapearTratamiento(tratamientoN));
            }

            boolean success = consultasMedicasM.actualizarConsultaConTratamientos(consultaId, Date.valueOf(fecha), motivo, diagnostico, tarifaConsulta, petId,user_id,tratamientosMList );
            if (success) {
                return "Consulta médica y tratamientos actualizados con éxito";
            } else {
                return "Error al actualizar la consulta médica y tratamientos";
            }
        } catch (SQLException e) {
            return "Error al actualizar la consulta médica y tratamientos: " + e.getMessage();
        }
    }


    public String eliminarConsultaMedicaConTratamientos(int consultaId) throws SQLException {
        try {
            boolean success = consultasMedicasM.eliminarConsultaConTratamientos(consultaId);
            if (success) {
                return "Consulta médica y tratamientos eliminados con éxito";
            } else {
                return "Error al eliminar la consulta médica y tratamientos";
            }
        } catch (SQLException e) {
            return "Error al eliminar la consulta médica y tratamientos: " + e.getMessage();
        }
    }




    public String leerConsulta(int id) throws SQLException {
        ConsultasMedicasM consulta = consultasMedicasM.leerConsulta(id);
        List<TratamientosM> tratamientos = new TratamientosM().buscarPorConsultaId(id);

        StringBuilder sb = new StringBuilder();
        String consultaFormat = "%-5s %-10s %-20s %-20s %-15s %-10s %-10s %-30s %-30s%n";
        sb.append("Consulta:\n");
        sb.append(String.format(consultaFormat, "ID", "Fecha", "Motivo", "Diagnóstico", "Tarifa", "Pet ID", "User ID", "Creado En", "Actualizado En"));
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        sb.append(String.format(consultaFormat,
                consulta.getId(),
                consulta.getFecha(),
                consulta.getMotivo(),
                consulta.getDiagnostico(),
                consulta.getTarifaConsulta(),
                consulta.getPetId(),
                consulta.getUserId(),
                consulta.getCreadoEn(),
                consulta.getActualizadoEn()));

        String tratamientoFormat = "%-5s %-20s %-20s %-30s %-30s%n";
        sb.append("\nTratamientos:\n");
        sb.append(String.format(tratamientoFormat, "ID", "Medicamento", "Notas", "Creado En", "Actualizado En"));
        sb.append("------------------------------------------------------------------------------------------------------------------------\n");
        for (TratamientosM tratamiento : tratamientos) {
            sb.append(String.format(tratamientoFormat,
                    tratamiento.getId(),
                    tratamiento.getMedicamento(),
                    tratamiento.getNotas(),
                    tratamiento.getCreadoEn(),
                    tratamiento.getActualizadoEn()));
        }

        return sb.toString();
    }



    public String leerConsultaPorMascota(int petId) throws SQLException {
        List<ConsultasMedicasM> consultas = consultasMedicasM.leerConsultaPorMascota(petId);
        StringBuilder sb = new StringBuilder();
        String consultaFormat = "%-5s %-10s %-20s %-20s %-15s %-10s %-10s %-30s %-30s%n";
        sb.append("Consultas:\n");
        sb.append(String.format(consultaFormat, "ID", "Fecha", "Motivo", "Diagnóstico", "Tarifa", "Pet ID", "User ID", "Creado En", "Actualizado En"));
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (ConsultasMedicasM consulta : consultas) {
            sb.append(String.format(consultaFormat,
                    consulta.getId(),
                    consulta.getFecha(),
                    consulta.getMotivo(),
                    consulta.getDiagnostico(),
                    consulta.getTarifaConsulta(),
                    consulta.getPetId(),
                    consulta.getUserId(),
                    consulta.getCreadoEn(),
                    consulta.getActualizadoEn()));

            List<TratamientosM> tratamientos = new TratamientosM().buscarPorConsultaId(consulta.getId());
            String tratamientoFormat = "%-5s %-20s %-20s %-30s %-30s%n";
            sb.append("\nTratamientos:\n");
            sb.append(String.format(tratamientoFormat, "ID", "Medicamento", "Notas", "Creado En", "Actualizado En"));
            sb.append("------------------------------------------------------------------------------------------------------------------------\n");
            for (TratamientosM tratamiento : tratamientos) {
                sb.append(String.format(tratamientoFormat,
                        tratamiento.getId(),
                        tratamiento.getMedicamento(),
                        tratamiento.getNotas(),
                        tratamiento.getCreadoEn(),
                        tratamiento.getActualizadoEn()));
            }
            sb.append("\n");
        }
        return sb.toString();
    }




    private TratamientosM mapearTratamiento(TratamientosN tratamientoN) throws SQLException {
        TratamientosM tratamientoM = new TratamientosM();
        tratamientoM.setId(0);
        tratamientoM.setMedicamento(tratamientoN.getMedicamento());
        tratamientoM.setNotas(tratamientoN.getNotas());
        tratamientoM.setConsultaId(0);
        tratamientoM.setCreadoEn(tratamientoN.getCreadoEn());
        tratamientoM.setActualizadoEn(tratamientoN.getActualizadoEn());
        return tratamientoM;
    }


    private ConsultasMedicasM cargar(int id, java.sql.Date fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId,int user_id) throws SQLException {
        ConsultasMedicasM consultasMedicasMObj = new ConsultasMedicasM();
        consultasMedicasMObj.setId(id);
        consultasMedicasMObj.setFecha(fecha);
        consultasMedicasMObj.setMotivo(motivo);
        consultasMedicasMObj.setDiagnostico(diagnostico);
        consultasMedicasMObj.setTarifaConsulta(tarifaConsulta);
        consultasMedicasMObj.setPetId(petId);
        consultasMedicasMObj.setUserId(user_id);
        consultasMedicasMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        consultasMedicasMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return consultasMedicasMObj;
    }







    public String mapearConsultas(List<ConsultasMedicasM> consultasMedicasMList) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-20s %-20s %-15s %-10s %-10s  %-30s %-30s%n";

        sb.append(String.format(format, "ID", "Fecha", "Motivo", "Diagnóstico", "Tarifa", "Pet ID", "User ID", "Creado En", "Actualizado En"));
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\r\n");
        for (ConsultasMedicasM consulta : consultasMedicasMList) {
            sb.append(String.format(format,
                    consulta.getId(),
                    consulta.getFecha(),
                    consulta.getMotivo(),
                    consulta.getDiagnostico(),
                    consulta.getTarifaConsulta(),
                    consulta.getPetId(),
                    consulta.getUserId(),
                    consulta.getCreadoEn(),
                    consulta.getActualizadoEn()));
        }
        return sb.toString();
    }










    private void validarCamposTratamientos(String medicamento, String notas, int consultaId) {
        if (medicamento == null || medicamento.isEmpty()) {
            throw new IllegalArgumentException("El medicamento no puede estar vacío");
        }
        if (notas == null || notas.isEmpty()) {
            throw new IllegalArgumentException("Las notas no pueden estar vacías");
        }

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
