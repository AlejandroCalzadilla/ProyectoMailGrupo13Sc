package org.mailgrupo13.sistema.negocio.almacenes;

import org.mailgrupo13.sistema.modelo.MedicamentosM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class MedicamentosService {

    private final MedicamentosM medicamentosM;

    public MedicamentosService(MedicamentosM medicamentosM) {
        this.medicamentosM = medicamentosM;
    }



    public MedicamentosN leerMedicamento(int id) throws SQLException {
        MedicamentosM medicamentoMObj = medicamentosM.leerMedicamento(id);
        MedicamentosN medicamentoN = new MedicamentosN();
        medicamentoN.setId(medicamentoMObj.getId());
        medicamentoN.setNombre(medicamentoMObj.getNombre());
        medicamentoN.setDosis(medicamentoMObj.getDosis());
        medicamentoN.setFabricante(medicamentoMObj.getFabricante());
        medicamentoN.setFechaCaducidad(medicamentoMObj.getFechaCaducidad());
        medicamentoN.setSustanciaControlada(medicamentoMObj.isSustanciaControlada());
        medicamentoN.setCategoriaId(medicamentoMObj.getCategoriaId());
        medicamentoN.setCreadoEn(medicamentoMObj.getCreadoEn());
        medicamentoN.setActualizadoEn(medicamentoMObj.getActualizadoEn());
        return medicamentoN;
    }

    public String obtenerMedicamentos() throws SQLException {
        return mapear(medicamentosM.obtenerMedicamentos());
    }

    public String agregarMedicamento(String nombre, String dosis, String fabricante, Date fechaCaducidad, boolean sustanciaControlada, int categoriaId) throws SQLException {
        MedicamentoValidator.validarCampos(nombre, dosis, fabricante, (java.sql.Date) fechaCaducidad, sustanciaControlada, categoriaId);
        MedicamentosM medicamentoMObj = cargar(0, nombre, dosis, fabricante, fechaCaducidad, sustanciaControlada, categoriaId);
        return medicamentoMObj.crearMedicamento();
    }

    public String actualizarMedicamento(int id, String nombre, String dosis, String fabricante, Date fechaCaducidad, boolean sustanciaControlada, int categoriaId) throws SQLException {
        MedicamentoValidator.validarCampos(nombre, dosis, fabricante, (java.sql.Date) fechaCaducidad, sustanciaControlada, categoriaId);
        MedicamentosM medicamentoMObj = cargar(id, nombre, dosis, fabricante, fechaCaducidad, sustanciaControlada, categoriaId);
        return medicamentoMObj.actualizarMedicamento();
    }

    public String eliminarMedicamento(int id) throws SQLException {
        return medicamentosM.eliminarMedicamento(id);
    }

    private String mapear(List<MedicamentosM> medicamentosM) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-10s %-15s %-15s %-10s %-10s %-30s %-30s%n";
        sb.append(String.format(format, "ID", "Nombre", "Dosis", "Fabricante", "Fecha Caducidad", "Sustancia Controlada", "Categoria ID", "Creado En", "Actualizado En"));
        sb.append("------------------------------------------------------------------------------------------------------------------------------------------------\r\n");
        for (MedicamentosM medicamentoM : medicamentosM) {
            sb.append(String.format(format,
                    medicamentoM.getId(),
                    medicamentoM.getNombre(),
                    medicamentoM.getDosis(),
                    medicamentoM.getFabricante(),
                    medicamentoM.getFechaCaducidad(),
                    medicamentoM.isSustanciaControlada(),
                    medicamentoM.getCategoriaId(),
                    medicamentoM.getCreadoEn(),
                    medicamentoM.getActualizadoEn()));
        }
        return sb.toString();
    }

    private MedicamentosM cargar(int id, String nombre, String dosis, String fabricante, Date fechaCaducidad, boolean sustanciaControlada, int categoriaId) throws SQLException {
        MedicamentosM medicamentoMObj = new MedicamentosM();
        medicamentoMObj.setId(id);
        medicamentoMObj.setNombre(nombre);
        medicamentoMObj.setDosis(dosis);
        medicamentoMObj.setFabricante(fabricante);
        medicamentoMObj.setFechaCaducidad((java.sql.Date) fechaCaducidad);
        medicamentoMObj.setSustanciaControlada(sustanciaControlada);
        medicamentoMObj.setCategoriaId(categoriaId);
        medicamentoMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        medicamentoMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return medicamentoMObj;
    }
}