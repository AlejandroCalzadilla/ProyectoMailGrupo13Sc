package org.mailgrupo13.servicioemail;

import org.mailgrupo13.sistema.negocio.almacenes.AlmacenesN;
import org.mailgrupo13.sistema.negocio.almacenes.MedicamentosN;
import org.mailgrupo13.sistema.negocio.categorias.CategoriasN;
import org.mailgrupo13.sistema.negocio.clientes.ClientesN;
import org.mailgrupo13.sistema.negocio.consultas.ConsultasMedicasN;
import org.mailgrupo13.sistema.negocio.consultas.TratamientosN;
import org.mailgrupo13.sistema.negocio.estadistica.EstadisticaN;
import org.mailgrupo13.sistema.negocio.mascotas.EspeciesN;
import org.mailgrupo13.sistema.negocio.mascotas.MascotasN;
import org.mailgrupo13.sistema.negocio.mascotas.RazasN;
import org.mailgrupo13.sistema.negocio.notacompra.DetalleNotaCompraN;
import org.mailgrupo13.sistema.negocio.notacompra.InventarioN;
import org.mailgrupo13.sistema.negocio.notacompra.NotaCompraN;
import org.mailgrupo13.sistema.negocio.notaventa.DetalleNotaVentaN;
import org.mailgrupo13.sistema.negocio.notaventa.NotaVentaN;
import org.mailgrupo13.sistema.negocio.proveedores.ProveedoresN;
import org.mailgrupo13.sistema.negocio.usuarios.UsuariosN;
import org.mailgrupo13.sistema.negocio.vacunas.VacunasN;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComandoEmail {
    public String evaluarYEjecutar(String subject) throws SQLException {
        if (Objects.equals(subject, "HELP")) {
            return obtenerComandosDisponibles();
        }
        String respuestaConsulta;

        // Definir patrones para cada operación CRUD
        Pattern listarPatron = Pattern.compile("^LISTAR(\\w+)\\[\\*\\]$"); // Ej: LISTARCLIENTES[*]
        Pattern crearPatron = Pattern.compile("^CREATE(\\w+)\\[(.+)]$"); // Ej: CREATECLIENTES[nombre, apellido, otros]
        Pattern actualizarPatron = Pattern.compile("^UPDATE(\\w+)\\[(.+)]$"); // Ej: UPDATECLIENTES[param1, param2]
        Pattern eliminarPatron = Pattern.compile("^DELETE(\\w+)\\[(.+)]$"); // Ej: DELETECLIENTES[id]
        Pattern getPatron = Pattern.compile("^GET(\\w+)\\[(\\d+)]$"); // Ej: GETMEDICAMENTOS[2]

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
        } else if ((matcher = getPatron.matcher(subject)).matches()) {
            String entidad = matcher.group(1);
            String id = matcher.group(2);
            respuestaConsulta = ejecutarConsultaGet(entidad, id);
        } else {
            respuestaConsulta = "Comando no reconocido.";
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
                    respuesta = consultas.obtenerConsultasMedicas();
                }
                case "PROVEEDORES" -> {
                    ProveedoresN proveedoresN = new ProveedoresN();
                    respuesta = proveedoresN.obtenerProveedores();
                }
                case "USUARIOS" -> {
                    UsuariosN usuariosN = new UsuariosN();
                    respuesta = usuariosN.obtenerUsuarios();
                }
                case "VACUNAS" -> {
                    VacunasN vacunasN = new VacunasN();
                    respuesta = vacunasN.obtenerVacunas();
                }
                case "NOTASVENTAS" -> {
                    NotaVentaN notaVentaN = new NotaVentaN();
                    respuesta = notaVentaN.obtenerNotaVentas();
                }
                case "NOTASCOMPRAS" -> {
                    NotaCompraN notaCompraN = new NotaCompraN();
                    respuesta = notaCompraN.obtenerNotaCompras();
                }
                case "INVENTARIOS" -> {
                    InventarioN inventarioN = new InventarioN();
                    respuesta = inventarioN.obtenerInventarios();
                }
                case "COMPRASPORMES" -> {
                    EstadisticaN estadisticaN = new EstadisticaN();
                    respuesta = estadisticaN.obtenerComprasPorMes();
                }
                case "COMPRASPORPROVEEDOR" -> {
                    EstadisticaN estadisticaN = new EstadisticaN();
                    respuesta = estadisticaN.obtenerComprasPorProveedor();
                }
                case "VENTASPORCLIENTE" -> {
                    EstadisticaN estadisticaN = new EstadisticaN();
                    respuesta = estadisticaN.obtenerVentasPorCliente();
                }
                case "VENTASPORMES" -> {
                    EstadisticaN estadisticaN = new EstadisticaN();
                    respuesta = estadisticaN.obtenerVentasPorMes();
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
            for (int i = 0; i < params.length; i++) {
                params[i] = params[i].trim();
            }
            switch (entidad) {
                case "CLIENTES" -> {
                    if (params.length != 7) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    ClientesN clientesN = new ClientesN();
                    respuesta = clientesN.agregarCliente(
                            params[0], params[1], params[2], params[3], params[4], params[5], params[6]
                    );
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
                case "MASCOTAS" -> {
                    if (params.length != 7) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    MascotasN mascotasN = new MascotasN();
                    respuesta = mascotasN.agregarMascota(
                            params[0], Float.parseFloat(params[1]), params[2], params[3], params[4], Integer.parseInt(params[5]), Integer.parseInt(params[6])
                    );
                }
                case "RAZAS" -> {
                    if (params.length != 2) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    RazasN razasN = new RazasN();
                    respuesta = razasN.agregarRaza(
                            params[0], Integer.parseInt(params[1])
                    );
                }
                case "CATEGORIAS" -> {
                    if (params.length != 1) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    CategoriasN categoriasN = new CategoriasN();
                    respuesta = categoriasN.agregarCategoria(params[0]);
                }
                case "ALMACENES" -> {
                    if (params.length != 3) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    AlmacenesN almacenesN = new AlmacenesN();
                    respuesta = almacenesN.agregarAlmacen(params[0], params[1], params[2]);
                }
                case "CONSULTAS" -> {
                    if (params.length < 8 && (params.length - 6) % 2 != 0) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    ConsultasMedicasN consultas = new ConsultasMedicasN();
                    List<TratamientosN> tratamientos = new ArrayList<>();
                    for (int i = 6; i < params.length; i += 2) {
                        tratamientos.add(new TratamientosN(params[i], params[i + 1]));
                    }
                    respuesta = consultas.agregarConsultaMedica(
                            params[0], params[1], params[2], Float.parseFloat(params[3]), Integer.parseInt(params[4]),
                            Integer.parseInt(params[5]), tratamientos
                    );
                }
                case "PROVEEDORES" -> {
                    if (params.length != 5) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    ProveedoresN proveedoresN = new ProveedoresN();
                    respuesta = proveedoresN.agregarProveedor(
                            params[0], params[1], params[2], params[3], params[4]
                    );
                }
                case "USUARIOS" -> {
                    if (params.length != 3) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    UsuariosN usuariosN = new UsuariosN();
                    respuesta = usuariosN.agregarUsuario(
                            params[0], params[1], params[2]
                    );
                }
                case "VACUNAS" -> {
                    if (params.length != 3) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    VacunasN vacunasN = new VacunasN();
                    respuesta = vacunasN.agregarVacuna(
                            params[0], Integer.parseInt(params[1]), params[2]
                    );
                }
                case "NOTASVENTAS" -> {
                    if (params.length < 8 && (params.length - 4) % 4 != 0) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    NotaVentaN notaVentaN = new NotaVentaN();
                    List<DetalleNotaVentaN> detalles = new ArrayList<>();
                    for (int i = 4; i < params.length; i += 4) {
                        detalles.add(new DetalleNotaVentaN(
                                Integer.parseInt(params[i]), Float.parseFloat(params[i + 1]),
                                Float.parseFloat(params[i + 2]), Integer.parseInt(params[i + 3])
                        ));
                    }
                    respuesta = notaVentaN.crearNotaVenta(
                            params[0], Integer.parseInt(params[1]), Integer.parseInt(params[2]),
                            Integer.parseInt(params[3]), detalles
                    );
                }
                case "NOTASCOMPRAS" -> {
                    if (params.length < 8 && (params.length - 4) % 5 != 0) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    List<DetalleNotaCompraN> detalles = new ArrayList<>();
                    for (int i = 4; i < params.length; i += 5) {
                        detalles.add(new DetalleNotaCompraN(
                                Integer.parseInt(params[i]), Float.parseFloat(params[i + 1]),
                                Float.parseFloat(params[i + 2]), Float.parseFloat(params[i + 3]),
                                Integer.parseInt(params[i + 4])
                        ));
                    }
                    NotaCompraN notaCompraN = new NotaCompraN();
                    respuesta = notaCompraN.crearNotaCompra(
                            params[0], Integer.parseInt(params[1]), Integer.parseInt(params[2]),
                            Integer.parseInt(params[3]), detalles
                    );
                }
                default -> respuesta = "Entidad no encontrada";
            }
            return respuesta;
        } catch (Exception e) {
            return "Error al crear " + entidad + ": " + e.getMessage();
        }
    }

    private String ejecutarConsultaActualizar(String entidad, String parametros) {
        String respuesta = "";
        try {
            String[] params = parametros.split(",");
            for (int i = 0; i < params.length; i++) {
                params[i] = params[i].trim();
            }
            switch (entidad) {
                case "CLIENTES" -> {
                    if (params.length != 6) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    ClientesN clientesN = new ClientesN();
                    respuesta = clientesN.actualizarCliente(
                            Integer.parseInt(params[0]), params[1], params[2], params[3], params[4], params[5]
                    );
                }
                case "MEDICAMENTOS" -> {
                    if (params.length != 7) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    MedicamentosN medicamentosN = new MedicamentosN();
                    Date fechaCaducidad = new Date(new SimpleDateFormat("dd-MM-yyyy").parse(params[4]).getTime());
                    boolean esSustanciaControlada = Integer.parseInt(params[5]) != 0;
                    respuesta = medicamentosN.actualizarMedicamento(
                            Integer.parseInt(params[0]), params[1], params[2], params[3], fechaCaducidad, esSustanciaControlada, Integer.parseInt(params[6])
                    );
                }
                case "ESPECIES" -> {
                    if (params.length != 2) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    EspeciesN especiesN = new EspeciesN();
                    respuesta = especiesN.actualizarEspecie(Integer.parseInt(params[0]), params[1]);
                }
                case "MASCOTAS" -> {
                    if (params.length != 8) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    MascotasN mascotasN = new MascotasN();
                    respuesta = mascotasN.actualizarMascota(
                            Integer.parseInt(params[0]), params[1], Float.parseFloat(params[2]), params[3], params[4], params[5], Integer.parseInt(params[6]), Integer.parseInt(params[7])
                    );
                }
                case "RAZAS" -> {
                    if (params.length != 3) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    RazasN razasN = new RazasN();
                    boolean actualizado = razasN.actualizarRaza(
                            Integer.parseInt(params[0]), params[1], Integer.parseInt(params[2])
                    );
                    respuesta = actualizado ? "Raza actualizada" : "Error al actualizar raza";
                }
                case "CATEGORIAS" -> {
                    if (params.length != 2) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    CategoriasN categoriasN = new CategoriasN();
                    respuesta = categoriasN.actualizarCategoria(Integer.parseInt(params[0]), params[1]);
                }
                case "ALMACENES" -> {
                    if (params.length != 4) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    AlmacenesN almacenesN = new AlmacenesN();
                    respuesta = almacenesN.actualizarAlmacen(
                            Integer.parseInt(params[0]), params[1], params[2], params[3]
                    );
                }
                case "CONSULTAS" -> {
                    if (params.length < 9 || (params.length - 7) % 2 != 0) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    ConsultasMedicasN consultas = new ConsultasMedicasN();
                    List<TratamientosN> tratamientos = new ArrayList<>();
                    for (int i = 7; i < params.length; i += 2) {
                        tratamientos.add(new TratamientosN(params[i], params[i + 1]));
                    }
                    respuesta = consultas.actualizarConsultaMedica(
                            Integer.parseInt(params[0]), params[1], params[2], params[3], Float.parseFloat(params[4]),
                            Integer.parseInt(params[5]), Integer.parseInt(params[6]), tratamientos
                    );
                    ;
                }
                case "PROVEEDORES" -> {
                    if (params.length != 6) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    ProveedoresN proveedoresN = new ProveedoresN();
                    respuesta = proveedoresN.actualizarProveedor(
                            Integer.parseInt(params[0]), params[1], params[2], params[3], params[4], params[5]
                    );
                }
                case "USUARIOS" -> {
                    if (params.length != 4) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    UsuariosN usuariosN = new UsuariosN();
                    respuesta = usuariosN.actualizarUsuario(
                            Integer.parseInt(params[0]), params[1], params[2], params[3]
                    );
                }
                case "VACUNAS" -> {
                    if (params.length != 4) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    VacunasN vacunasN = new VacunasN();
                    respuesta = vacunasN.actualizarVacuna(
                            Integer.parseInt(params[0]), params[1], Integer.parseInt(params[2]), params[3]
                    );
                }
                case "NOTASVENTAS" -> {
                    if (params.length < 9 && (params.length - 5) % 4 != 0) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    NotaVentaN notaVentaN = new NotaVentaN();
                    List<DetalleNotaVentaN> detalles = new ArrayList<>();
                    for (int i = 5; i < params.length; i += 4) {
                        detalles.add(new DetalleNotaVentaN(
                                Integer.parseInt(params[i]), Float.parseFloat(params[i + 1]),
                                Float.parseFloat(params[i + 2]), Integer.parseInt(params[i + 3])
                        ));
                    }
                    respuesta = notaVentaN.actualizarNotaVenta(
                            Integer.parseInt(params[0]), params[1], Integer.parseInt(params[2]), Integer.parseInt(params[3]),
                            Integer.parseInt(params[4]), detalles
                    );
                }
                case "NOTASCOMPRAS" -> {
                    if (params.length < 9 && (params.length - 5) % 5 != 0) {
                        throw new IllegalArgumentException("Número de parámetros incorrecto");
                    }
                    List<DetalleNotaCompraN> detalles = new ArrayList<>();
                    for (int i = 5; i < params.length; i += 5) {
                        detalles.add(new DetalleNotaCompraN(
                                Integer.parseInt(params[i]), Float.parseFloat(params[i + 1]),
                                Float.parseFloat(params[i + 2]), Float.parseFloat(params[i + 3]),
                                Integer.parseInt(params[i + 4])
                        ));
                    }
                    NotaCompraN notaCompraN = new NotaCompraN();
                    respuesta = notaCompraN.actualizarNotaCompra(
                            Integer.parseInt(params[0]), params[1], Integer.parseInt(params[2]), Integer.parseInt(params[3]),
                            Integer.parseInt(params[4]), detalles
                    );
                }
                default -> respuesta = "Entidad no encontrada";
            }
            return respuesta;
        } catch (Exception e) {
            return "Error al actualizar " + entidad + ": " + e.getMessage();
        }
    }

    private String ejecutarConsultaEliminar(String entidad, String id) {
        int entityId = Integer.parseInt(id);
        if (entityId <= 0) {
            return "ID inválido";
        }
        try {
            String respuesta = "";
            switch (entidad) {
                case "CLIENTES" -> {
                    ClientesN clientesN = new ClientesN();
                    respuesta = clientesN.eliminarCliente(entityId) ? "Cliente eliminado" : "Error al eliminar cliente";
                }
                case "MEDICAMENTOS" -> {
                    MedicamentosN medicamentosN = new MedicamentosN();
                    respuesta = medicamentosN.eliminarMedicamento(entityId);
                }
                case "ESPECIES" -> {
                    EspeciesN especiesN = new EspeciesN();
                    respuesta = especiesN.eliminarEspecie(entityId);
                }
                case "MASCOTAS" -> {
                    MascotasN mascotasN = new MascotasN();
                    respuesta = mascotasN.eliminarMascota(entityId);
                }
                case "RAZAS" -> {
                    RazasN razasN = new RazasN();
                    respuesta = razasN.eliminarRaza(entityId) ? "Raza eliminada" : "Error al eliminar raza";
                }
                case "CATEGORIAS" -> {
                    CategoriasN categoriasN = new CategoriasN();
                    respuesta = categoriasN.eliminarCategoria(entityId);
                }
                case "ALMACENES" -> {
                    AlmacenesN almacenesN = new AlmacenesN();
                    respuesta = almacenesN.eliminarAlmacen(entityId);
                }
                case "CONSULTAS" -> {
                    ConsultasMedicasN consultas = new ConsultasMedicasN();
                    respuesta = consultas.eliminarConsultaMedica(entityId) ? "Consulta eliminada" : "Error al eliminar consulta";
                }
                case "PROVEEDORES" -> {
                    ProveedoresN proveedoresN = new ProveedoresN();
                    respuesta = proveedoresN.eliminarProveedor(entityId);
                }
                case "USUARIOS" -> {
                    UsuariosN usuariosN = new UsuariosN();
                    respuesta = usuariosN.eliminarUsuario(entityId);
                }
                case "VACUNAS" -> {
                    VacunasN vacunasN = new VacunasN();
                    respuesta = vacunasN.eliminarVacuna(entityId) ? "Vacuna eliminada" : "Error al eliminar vacuna";
                }
                case "NOTASVENTAS" -> {
                    NotaVentaN notaVentaN = new NotaVentaN();
                    respuesta = notaVentaN.eliminarNotaVenta(entityId);
                }
                case "NOTASCOMPRAS" -> {
                    NotaCompraN notaCompraN = new NotaCompraN();
                    respuesta = notaCompraN.eliminarNotaCompra(entityId);
                }
                default -> respuesta = "Entidad no encontrada";
            }
            return respuesta;
        } catch (Exception e) {
            return "Error al eliminar " + entidad + ": " + e.getMessage();
        }
    }

    private String ejecutarConsultaGet(String entidad, String stringId) {
        int id = Integer.parseInt(stringId);
        if (id <= 0) {
            return "ID inválido";
        }
        try {
            String respuesta = "";
            switch (entidad) {
                case "CLIENTES" -> {
                    ClientesN clientesN = new ClientesN();
                    respuesta = clientesN.leerCliente(id).toString();
                }
                case "MEDICAMENTOS" -> {
                    MedicamentosN medicamentosN = new MedicamentosN();
                    respuesta = medicamentosN.leerMedicamento(id).toString();
                }
                case "ESPECIES" -> {
                    EspeciesN especiesN = new EspeciesN();
                    respuesta = especiesN.leerEspecie(id).toString();
                }
                case "MASCOTAS" -> {
                    MascotasN mascotasN = new MascotasN();
                    respuesta = mascotasN.leerMascota(id);
                }
                case "RAZAS" -> {
                    RazasN razasN = new RazasN();
                    respuesta = razasN.leerRaza(id).toString();
                }
                case "CATEGORIAS" -> {
                    CategoriasN categoriasN = new CategoriasN();
                    respuesta = categoriasN.leerCategoria(id).toString();
                }
                case "ALMACENES" -> {
                    AlmacenesN almacenesN = new AlmacenesN();
                    respuesta = almacenesN.leerAlmacen(id).toString();
                }
                case "CONSULTAS" -> {
                    ConsultasMedicasN consultas = new ConsultasMedicasN();
                    respuesta = consultas.leerConsulta(id);
                }
                case "PROVEEDORES" -> {
                    ProveedoresN proveedoresN = new ProveedoresN();
                    respuesta = proveedoresN.leerProveedor(id).toString();
                }
                case "USUARIOS" -> {
                    UsuariosN usuariosN = new UsuariosN();
                    respuesta = usuariosN.leerUsuario(id).toString();
                }
                case "VACUNAS" -> {
                    VacunasN vacunasN = new VacunasN();
                    respuesta = vacunasN.leerVacuna(id).toString();
                }
                case "NOTASVENTAS" -> {
                    NotaVentaN notaVentaN = new NotaVentaN();
                    respuesta = notaVentaN.leerNotaVenta(id).toString();
                }
                case "NOTASCOMPRAS" -> {
                    NotaCompraN notaCompraN = new NotaCompraN();
                    respuesta = notaCompraN.leerNotaCompra(id);
                }
                default -> respuesta = "Entidad no encontrada";
            }
            return respuesta;
        } catch (Exception e) {
            return "Error al obtener " + entidad + ": " + e.getMessage();
        }
    }

    private String obtenerComandosDisponibles() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comandos generales disponibles:\r\n")
                .append("- LISTAR<entidad>[*]: Listar todos los registros de una entidad\r\n")
                .append("- CREATE<entidad>[param1, param2, ...]: Crear un nuevo registro en una entidad\r\n")
                .append("- UPDATE<entidad>[param1, param2, ...]: Actualizar un registro en una entidad\r\n")
                .append("- DELETE<entidad>[id]: Eliminar un registro en una entidad\r\n")
                .append("- GET<entidad>[id]: Obtener un registro por ID en una entidad\r\n");
        sb.append("Entidades disponibles:\r\n");
        sb.append("CLIENTES, MEDICAMENTOS, ESPECIES, MASCOTAS, RAZAS, CATEGORIAS, ALMACENES, CONSULTAS, PROVEEDORES, " +
                "USUARIOS, VACUNAS, NOTASVENTAS, NOTASCOMPRAS, INVENTARIOS, COMPRASPORMES, COMPRASPORPROVEEDOR," +
                "VENTASPORCLIENTE, VENTASPORMES\r\n");
        sb.append("Comandos específicos disponibles:\r\n");
        sb.append("CREAR CLIENTE:\r\n");
        sb.append("Parámetros: nombre, apellido, numero, fecha de nacimiento, correo y contraseña\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATECLIENTES[Gonzalo, Quispe Huanca, 12345678, masculino, 2000-11-01, ruddygonzqh@gmail.com, password]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR MASCOTA:\r\n");
        sb.append("Parámetros: nombre, peso, color, fecha nac, url_foto, id_cliente, id_raza\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATEMASCOTAS[Pacho 3, 10.5, Blanco, 2028-10-10, https://google.com, 2, 6]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR MEDICAMENTOS:\r\n");
        sb.append("Parámetros: nombre, cant_dosis, fabriacante, fecha caducidad, sustancia controlado (bool 0 o 1), id_categoria\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATEMEDICAMENTOS[Amoxicilina 500mg, 500mg, Labs ATI, 01-12-2028, 0, 1]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR ESPECIES:\r\n");
        sb.append("Parámetros: nombre de la especie\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATEESPECIES[Perro]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR RAZA:\r\n");
        sb.append("Parámetros: nombre de la raza, id_especie\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATERAZAS[Raza 6, 15]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR CATEGORÍA:\r\n");
        sb.append("Parámetros: nombre de la categoría\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATECATEGORIAS[Categoria Y]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR ALMACÉN:\r\n");
        sb.append("Parámetros: nombre del almacén, dirección, descipción adicional del almacén\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATEALMACENES[Almacen Y, Los pozos casco viejo, Descripcion del almacen]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR CONSULTA:\r\n");
        sb.append("Parámetros: fecha de consulta, motivo, diagnóstico, tarifa, id_mascota, id_cliente, detalles del tratamiento (receta y nota)\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATECONSULTAS[2024-10-10, vomitos de la mascota, la mascota está deshidratada, 10.5, 1, 2, Recetas de medicamentos, Nota adicional, Recetas de tratamientos 2, Nota adicional 2]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR PROVEEDOR:\r\n");
        sb.append("Parámetros: nombre proveedor, país, celular, correo, dirección\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATEPROVEEDORES[Proveedor Y, Bolivia, 12345678, prov02@gmail.com, Virgen de lujan]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR USUARIO:\r\n");
        sb.append("Parámetros: correo, contraseña, nombre de usuario\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATEUSUARIOS[admin2@gmail.com, password, usuario admininistrador]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR VACUNA:\r\n");
        sb.append("Parámetros: nombre vacuna, duración en días, descripcion\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATEVACUNAS[vacuna Y, 10, Vacuna controlada para la rabia]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR NOTA DE VENTA:\r\n");
        sb.append("Parámetros: fecha, id_almacen, id_usuario (que registra la venta), id_cliente, cantidad_detalle_1,\r\n" +
                "precio_venta_detalle_1, subtotal_detalle_1, id_medicamento_detalle1,..., cantidad_detalle_n, precio_venta_detalle_n, subtotal_detalle_n, id_medicamento_detalle_n\r\n");
        sb.append("los detalles las notas son: cantida, precio de venta, subtotal, id_medicamento\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATENOTASVENTAS[2024-11-13, 2, 5, 2, 2, 200, 400, 2, 2, 220, 440, 3]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("CREAR NOTA DE COMPRA:\r\n");
        sb.append("Parámetros: fecha, id_almacen, id_usuario (que registra la venta), id_cliente, cantidad_detalle_1,\r\n" +
                "precio_compra_detalle_1, porcent_ganancia_detalle_1, id_medicamento_detalle1,..., cantidad_detalle_n, precio_venta_detalle_n, porcent_ganancia_detalle_n, id_medicamento_detalle_n\r\n");
        sb.append("los detalles las notas son: cantida, precio de venta, subtotal, id_medicamento\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("CREATENOTASCOMPRAS[2024-11-13, 2, 3, 2, 2, 200, 50, 400,2]\r\n");
        sb.append("=====================================================================================================\r\n");
        sb.append("ACTUALIZAR CLIENTE:\r\n");
        sb.append("Parámetros: id, nombre, apellido, numero, fecha de nacimiento, correo y contraseña\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATECLIENTES[1, Gonzalo, Quispe Huanca, 12345678, masculino, 2000-11-01, ruddygonzqh@gmail.com, password]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR MASCOTA:\r\n");
        sb.append("Parámetros: id, nombre, peso, color, fecha nac, url_foto, id_cliente, id_raza\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATEMASCOTAS[2, Pacho 3, 10.5, Blanco, 2028-10-10, https://google.com, 2, 6]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR MEDICAMENTOS:\r\n");
        sb.append("Parámetros: id, nombre, cant_dosis, fabriacante, fecha caducidad, sustancia controlado (bool 0 o 1), id_categoria\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATEMEDICAMENTOS[3, Amoxicilina 500mg, 500mg, Labs ATI, 01-12-2028, 0, 1]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR ESPECIES:\r\n");
        sb.append("Parámetros: id, nombre de la especie\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATEESPECIES[4, Perro]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR RAZA:\r\n");
        sb.append("Parámetros: id, nombre de la raza, id_especie\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATERAZAS[5, Raza 6, 15]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR CATEGORÍA:\r\n");
        sb.append("Parámetros: id, nombre de la categoría\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATECATEGORIAS[6, Categoria Y]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR ALMACÉN:\r\n");
        sb.append("Parámetros: id, nombre del almacén, dirección, descipción adicional del almacén\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATEALMACENES[7, Almacen Y, Los pozos casco viejo, Descripcion del almacen]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR CONSULTA:\r\n");
        sb.append("Parámetros: id, fecha de consulta, motivo, diagnóstico, tarifa, id_mascota, id_cliente, detalles del tratamiento (receta y nota)\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATECONSULTAS[8, 2024-10-10, vomitos de la mascota, la mascota está deshidratada, 10.5, 1, 2, Recetas de medicamentos, Nota adicional, Recetas de tratamientos 2, Nota adicional 2]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR PROVEEDOR:\r\n");
        sb.append("Parámetros: id, nombre proveedor, país, celular, correo, dirección\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATEPROVEEDORES[9, Proveedor Y, Bolivia, 12345678, prov02@gmail.com, Virgen de lujan]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR USUARIO:\r\n");
        sb.append("Parámetros: id, correo, contraseña, nombre de usuario\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATEUSUARIOS[10, admin2@gmail.com, password, usuario admininistrador]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR VACUNA:\r\n");
        sb.append("Parámetros: id, nombre vacuna, duración en días, descripcion\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATEVACUNAS[11, vacuna Y, 10, Vacuna controlada para la rabia]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR NOTA DE VENTA:\r\n");
        sb.append("Parámetros: id, fecha, id_almacen, id_usuario (que registra la venta), id_cliente, cantidad_detalle_1,\r\n" +
                "precio_venta_detalle_1, subtotal_detalle_1, id_medicamento_detalle1,..., cantidad_detalle_n, precio_venta_detalle_n, subtotal_detalle_n, id_medicamento_detalle_n\r\n");
        sb.append("los detalles las notas son: cantida, precio de venta, subtotal, id_medicamento\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATENOTASVENTAS[2, 2024-11-13, 2, 5, 2, 2, 200, 400, 2, 2, 220, 440, 3]\r\n");
        sb.append("-----------------------------------------------------------------------------------------------------\r\n");
        sb.append("ACTUALIZAR NOTA DE COMPRA:\r\n");
        sb.append("Parámetros: id, fecha, id_almacen, id_usuario (que registra la venta), id_cliente, cantidad_detalle_1,\r\n" +
                "precio_venta_detalle_1, subtotal_detalle_1, id_medicamento_detalle1,..., cantidad_detalle_n, precio_venta_detalle_n, subtotal_detalle_n, id_medicamento_detalle_n\r\n");
        sb.append("los detalles las notas son: cantida, precio de venta, subtotal, id_medicamento\r\n");
        sb.append("Ejemplo:\r\n");
        sb.append("UPDATENOTASCOMPRAS[4, 2024-11-13, 2, 3, 2, 2, 200, 50, 400,2]\r\n");
        sb.append("=====================================================================================================\r\n");
        return sb.toString();
    }
}
