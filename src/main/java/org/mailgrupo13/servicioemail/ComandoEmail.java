package org.mailgrupo13.servicioemail;

import org.mailgrupo13.sistema.negocio.clientes.ClientesN;
import org.mailgrupo13.sistema.negocio.mascotas.EspeciesN;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComandoEmail {
    public String evaluarYEjecutar(String subject) throws SQLException {
        String respuestaConsulta;

        // Definir patrones para cada operación CRUD
        Pattern listarPatron = Pattern.compile("^LISTAR(\\w+)\\[\\*\\]$"); // Ej: LISTARCLIENTES[*]
        Pattern crearPatron = Pattern.compile("^CREATE(\\w+)\\[(.+)]$"); // Ej: CREATECLIENTES[nombre, apellido, otros]
        Pattern actualizarPatron = Pattern.compile("^UPDATE(\\w+)\\[(.+)]$"); // Ej: UPDATECLIENTES[param1, param2]
        Pattern eliminarPatron = Pattern.compile("^DELETE(\\w+)\\[(.+)]$"); // Ej: DELETECLIENTES[id]

        Matcher matcher;

        // Evaluar cada patrón
        if ((matcher = listarPatron.matcher(subject)).matches()) {
            String entidad = matcher.group(1);
            respuestaConsulta = ejecutarConsultaListar(entidad);
        } else if ((matcher = crearPatron.matcher(subject)).matches()) {
            String entidad = matcher.group(1);
            String parametros = matcher.group(2);
            respuestaConsulta = ejecutarConsultaCrear(entidad, parametros);
        } else if ((matcher = actualizarPatron.matcher(subject)).matches()) {
            String entidad = matcher.group(1);
            String parametros = matcher.group(2);
            respuestaConsulta = ejecutarConsultaActualizar(entidad, parametros);
        } else if ((matcher = eliminarPatron.matcher(subject)).matches()) {
            String entidad = matcher.group(1);
            String id = matcher.group(2);
            respuestaConsulta = ejecutarConsultaEliminar(entidad, id);
        } else {
            respuestaConsulta = "";
        }

        return respuestaConsulta;
    }

    // Métodos para ejecutar consultas CRUD simuladas
    private String ejecutarConsultaListar(String entidad) throws SQLException {
        String respuesta = "";
        switch (entidad) {
            case "CLIENTES" -> {
                ClientesN clientesN = new ClientesN();
                respuesta = clientesN.obtenerClientes().toString();
            }
            case "MEDICAMENTOS" -> respuesta = "Listado de productos";
            case "ESPECIES" -> {
                EspeciesN especiesN = new EspeciesN();
                respuesta = especiesN.obtenerEspecies().toString();
            }
            default -> respuesta = "Entidad no encontrada";
        }
        return respuesta;
    }

    private String ejecutarConsultaCrear(String entidad, String parametros) throws SQLException {
        String respuesta = "";
        if (entidad.equals("CLIENTES")) {
            ClientesN clientesN = new ClientesN();
            respuesta = clientesN.agregarCliente("nombre", "apellido", "telefono", "genero", "fechanaciemiento", "email", "password");
        } else if (entidad.equals("MEDICAMENTOS")) {
            respuesta = "Listado de medicamentos";
        } else if (entidad.equals("ESPECIES")) {
            EspeciesN especiesN = new EspeciesN();
            //contar parametros
            String[] params = parametros.split(",");
            if (params.length == 1) {
                respuesta = especiesN.agregarEspecie(params[0]);
            } else {
                respuesta = "Error en los parámetros";
            }
        } else {
            respuesta = "Entidad no encontrada";
        }
        return respuesta;
    }

    private String ejecutarConsultaActualizar(String entidad, String parametros) {
        return "Actualización de " + entidad + " con parámetros: " + parametros;
    }

    private String ejecutarConsultaEliminar(String entidad, String id) {
        return "Eliminación de " + entidad + " con ID: " + id;
    }
}
