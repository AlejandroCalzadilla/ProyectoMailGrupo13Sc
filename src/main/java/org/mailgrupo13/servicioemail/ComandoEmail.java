package org.mailgrupo13.servicioemail;

import org.mailgrupo13.sistema.negocio.almacenes.AlmacenesN;
import org.mailgrupo13.sistema.negocio.almacenes.MedicamentosN;
import org.mailgrupo13.sistema.negocio.categorias.CategoriasN;
import org.mailgrupo13.sistema.negocio.clientes.ClientesN;
import org.mailgrupo13.sistema.negocio.consultas.ConsultasMedicasN;
import org.mailgrupo13.sistema.negocio.consultas.TratamientosN;
import org.mailgrupo13.sistema.negocio.mascotas.EspeciesN;
import org.mailgrupo13.sistema.negocio.mascotas.MascotasN;
import org.mailgrupo13.sistema.negocio.mascotas.RazasN;
import org.mailgrupo13.sistema.negocio.notacompra.NotaCompraN;
import org.mailgrupo13.sistema.negocio.notaventa.NotaVentaN;
import org.mailgrupo13.sistema.negocio.proveedores.ProveedoresN;
import org.mailgrupo13.sistema.negocio.usuarios.UsuariosN;
import org.mailgrupo13.sistema.negocio.vacunas.VacunasN;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
        try {
            switch (entidad) {
                case "CLIENTES" -> {
                    ClientesN clientesN = new ClientesN();
                    respuesta = clientesN.obtenerClientes();
                }
                case "MEDICAMENTOS" -> {
                    MedicamentosN medicamentosN = new MedicamentosN();
                    respuesta = medicamentosN.obtenerMedicamentos();
                }
                case "ESPECIES" -> {
                    EspeciesN especiesN = new EspeciesN();
                    respuesta = especiesN.obtenerEspecies();
                }
                case "MASCOTAS" -> {
                    MascotasN mascotasN = new MascotasN();
                    respuesta = mascotasN.obtenerMascotas();
                }
                case "RAZAS" -> {
                    RazasN razasN = new RazasN();
                    respuesta = razasN.obtenerRazas();
                }
                case "CATEGORIAS" -> {
                    CategoriasN categoriasN = new CategoriasN();
                    respuesta = categoriasN.obtenerCategorias();
                }
                case "ALMACENES" -> {
                    AlmacenesN almacenesN = new AlmacenesN();
                    respuesta = almacenesN.obtenerAlmacenes();
                }
                case "CONSULTAS" -> {
                    ConsultasMedicasN consultas = new ConsultasMedicasN();
                    //respuesta = consultas.obtenerConsultasMedicas();
                }
                case "PROVEEDORES" -> {
                    ProveedoresN proveedoresN = new ProveedoresN();
                    //respuesta = proveedoresN.obtenerProveedores();
                }
                case "USUARIOS" -> {
                    UsuariosN usuariosN = new UsuariosN();
                    respuesta = usuariosN.obtenerUsuarios();
                }
                case "VACUNAS" -> {
                    VacunasN vacunasN = new VacunasN();
                    //respuesta = vacunasN.obtenerVacunas();
                }
                case "NOTASVENTAS" -> {
                    NotaVentaN notaVentaN = new NotaVentaN();
                    //respuesta = notaVentaN.obtenerNotasVenta();
                }
                case "NOTASCOMPRAS" -> {
                    NotaCompraN notaCompraN = new NotaCompraN();
                    //respuesta = notaCompraN.obtenerNotasCompra();
                }
                default -> respuesta = "Entidad no encontrada";
            }
            return respuesta;
        } catch (Exception e) {
            return "Error al obtener listado de " + entidad + ": " + e.getMessage();
        }
    }

    private String ejecutarConsultaCrear(String entidad, String parametros) throws SQLException {
        String respuesta = "";
        try {
            String[] params = parametros.split(",");
            switch (entidad) {
                case "CLIENTES" -> {
                    ClientesN clientesN = new ClientesN();
                    respuesta = clientesN.agregarCliente(params[0], params[1], params[2], params[3], params[4], Integer.parseInt(params[5]));
                }
                case "MEDICAMENTOS" -> {
                    MedicamentosN medicamentosN = new MedicamentosN();
                    Date fechaCaducidad = new Date(new SimpleDateFormat("dd-MM-yyyy").parse(params[3]).getTime());
                    boolean esSustanciaControlada = Integer.parseInt(params[4]) != 0;
                    respuesta = medicamentosN.agregarMedicamento(params[0], params[1], params[2], fechaCaducidad, esSustanciaControlada, Integer.parseInt(params[5]));
                }
                case "ESPECIES" -> {
                    EspeciesN especiesN = new EspeciesN();
                    if (params.length == 1) {
                        respuesta = especiesN.agregarEspecie(params[0]);
                    } else {
                        respuesta = "Error en los parámetros";
                    }
                }
                default -> respuesta = "Entidad no encontrada";
            }
            return respuesta;
        } catch (Exception e) {
            return "Error al crear " + entidad + ": " + e.getMessage();
        }
    }

    private String ejecutarConsultaActualizar(String entidad, String parametros) {
        return "Actualización de " + entidad + " con parámetros: " + parametros;
    }

    private String ejecutarConsultaEliminar(String entidad, String id) {
        return "Eliminación de " + entidad + " con ID: " + id;
    }
}
