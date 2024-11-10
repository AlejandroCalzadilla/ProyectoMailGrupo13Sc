package org.mailgrupo13.sistema.negocio.mascotas;

import org.mailgrupo13.sistema.modelo.EspeciesM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class EspeciesService {


    private final EspeciesM especieM;

    public EspeciesService(EspeciesM especieM ) throws SQLException {
        this.especieM =  especieM;
    }



    public String obtenerEspecies() throws SQLException {
        List<EspeciesM> especiesMList = especieM.obtenerEspecies();
        return mapear(especiesMList);
    }


    public String agregarEspecie(String nombre) throws SQLException {
            validarCampos(nombre);
            especieM.setNombre(nombre);
            especieM.setCreadoEn(Timestamp.valueOf(LocalDateTime.now()));
            especieM.setActualizadoEn(Timestamp.valueOf(LocalDateTime.now()));
            return especieM.crearEspecie() ;

    }

    public String actualizarEspecie(int id, String nombre) throws SQLException {
            validarCampos(nombre);
            EspeciesM clienteMObj = cargar(id, nombre);
            return clienteMObj.actualizarEspecie();


    }


    public String eliminarEspecie(int id) throws SQLException {
        return especieM.eliminarEspecie(id);
    }


     public EspeciesN  leerEspecie(int id) throws SQLException {
         EspeciesM especieMObj = especieM.leerEspecie(id);
         EspeciesN especieN = new EspeciesN();
         especieN.setId(especieMObj.getId());
         especieN.setNombre(especieMObj.getNombre());
         especieN.setCreadoEn(especieMObj.getCreadoEn());
         especieN.setActualizadoEn(especieMObj.getActualizadoEn());
         return especieN;
     }




    public String mapear(List<EspeciesM> especiesM) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-30s %-30s%n";
        sb.append(String.format(format, "ID", "Nombre", "Creado En", "Actualizado En"));
        sb.append("---------------------------------------------------------------------------\n");
        for (EspeciesM especieM : especiesM) {
            sb.append(String.format(format,
                    especieM.getId(),
                    especieM.getNombre(),
                    especieM.getCreadoEn(),
                    especieM.getActualizadoEn()));
        }
        return sb.toString();
    }




    public EspeciesM cargar(int id, String nombre) throws SQLException {

        EspeciesM especieMObj = new EspeciesM();
        especieMObj.setId(id);
        especieMObj.setNombre(nombre);
        especieMObj.setCreadoEn(Timestamp.valueOf(LocalDateTime.now()));
        especieMObj.setActualizadoEn(Timestamp.valueOf(LocalDateTime.now()));
        return especieMObj;
    }





    private void validarCampos(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

        if (nombre.length() > 255) {
            throw new IllegalArgumentException("el nombre puede exceder los 255 caracteres");
        }

        if (!nombre.matches("[\\p{L}0-9 ]+")) {
            throw new IllegalArgumentException("El nombre solo debe contener letras, números y espacios");
        }

    }

}
