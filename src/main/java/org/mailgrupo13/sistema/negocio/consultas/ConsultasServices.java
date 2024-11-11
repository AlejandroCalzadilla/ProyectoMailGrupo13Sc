package org.mailgrupo13.sistema.negocio.consultas;

import org.mailgrupo13.sistema.modelo.ConsultasMedicasM;
import org.mailgrupo13.sistema.modelo.TratamientosM;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ConsultasServices {


    private ConsultasMedicasM consultasMedicasM;

    public ConsultasServices() throws SQLException {
        consultasMedicasM = new ConsultasMedicasM();
    }




    public String agregarConsultaMedicaConTratamientos(Date fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId, List<TratamientosM> tratamientos) throws SQLException {
        try {
            boolean success = consultasMedicasM.agregarConsultaConTratamientos(fecha, motivo, diagnostico, tarifaConsulta, petId, tratamientos);
            if (success) {
                return "Consulta médica y tratamientos agregados con éxito";
            } else {
                return "Error al agregar la consulta médica y tratamientos";
            }
        } catch (SQLException e) {
            return "Error al agregar la consulta médica y tratamientos: " + e.getMessage();
        }
    }





    public String actualizarConsultaMedicaConTratamientos(int consultaId, Date fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId, List<TratamientosM> tratamientos) throws SQLException {
        try {
            boolean success = consultasMedicasM.actualizarConsultaConTratamientos(consultaId, fecha, motivo, diagnostico, tarifaConsulta, petId, tratamientos);
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



}
