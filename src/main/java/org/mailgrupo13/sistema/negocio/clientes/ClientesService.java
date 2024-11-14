package org.mailgrupo13.sistema.negocio.clientes;

import org.mailgrupo13.sistema.modelo.ClienteM;
import org.mailgrupo13.sistema.modelo.ConsultasMedicasM;
import org.mailgrupo13.sistema.modelo.NotaVentaM;
import org.mailgrupo13.sistema.modelo.UsuariosM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientesService {

    private ClienteM clienteM;
    private NotaVentaM notaVentaM;
    private ConsultasMedicasM consultasMedicasM;
    public  ClientesService(ClienteM clienteM) {
        this.clienteM = clienteM;
    }



    public  ClientesN leerCliente(int id) throws SQLException {
        this.clienteM=this.clienteM.leerCliente(id);
        ClientesN cliente=new ClientesN();
        cliente.setId(this.clienteM.getId());
        cliente.setNombre(this.clienteM.getNombre());
        cliente.setApellido(this.clienteM.getApellido());
        cliente.setTelefono(this.clienteM.getTelefono());
        cliente.setGenero(this.clienteM.getGenero());
        cliente.setFechaNacimiento(this.clienteM.getFechaNacimiento());
        cliente.setIdUsuario(this.clienteM.getIdUsuario());
        cliente.setCreadoEn(this.clienteM.getCreadoEn());
        cliente.setActualizadoEn(this.clienteM.getActualizadoEn());
        return cliente;
    }

    public String obtenerClientes() throws SQLException {
        return  mapear(clienteM.obtenerClientes());
    }


    // ClientesService.java
    public String agregarCliente(String nombre, String apellido, String telefono, String genero, String fechanaciemiento, String email, String password) throws SQLException {

        // Create user and get user ID
        UsuariosM usuariosMObj = new UsuariosM();
        usuariosMObj.setEmail(email);
        usuariosMObj.setPassword(password);
        usuariosMObj.setNombre(nombre);
        int userId = usuariosMObj.crearUsuarioYRetornarId();
        ClienteValidator.validarCampos(nombre, apellido, telefono, genero, fechanaciemiento, userId);

        // Create client with the obtained user ID
        ClienteM clienteMObj = cargar(0, nombre, apellido, telefono, genero, fechanaciemiento, userId);
        return clienteMObj.crearCliente();
    }

    public String actualizarCliente(int id, String nombre, String apellido,String telefono,String genero, String fechanaciemiento) throws SQLException {
            ClienteValidator.validarCampos(nombre, apellido, telefono, genero, fechanaciemiento,0);
            ClienteM clienteMObj=cargar(id,nombre,apellido,telefono,genero,fechanaciemiento,0);
            return clienteMObj.actualizarCliente();

    }

    // Método para eliminar un cliente por ID
    public String eliminarCliente(int id) throws SQLException {
        ClienteM cliente = clienteM.leerCliente(id);
        boolean clienteEliminado = clienteM.eliminarCliente(id);
        if (clienteEliminado) {
            UsuariosM usuariosM = new UsuariosM();
            usuariosM.eliminarUsuario(cliente.getIdUsuario());
        }
        return "cliente y usuario eliminado";
    }





    public String mapear(List<ClienteM> clientesM) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-10s %-15s %-10s %-15s %-10s %-30s %-30s%n";
        sb.append(String.format(format, "ID", "Nombre", "Apellido", "Teléfono", "Género", "Fecha Nac.", "ID Usuario", "Creado En", "Actualizado En"));
        sb.append("------------------------------------------------------------------------------------------------------------------------------------------------\r\n");
        for (ClienteM clienteM : clientesM) {
            sb.append(String.format(format,
                    clienteM.getId(),
                    clienteM.getNombre(),
                    clienteM.getApellido(),
                    clienteM.getTelefono(),
                    clienteM.getGenero(),
                    clienteM.getFechaNacimiento(),
                    clienteM.getIdUsuario(),
                    clienteM.getCreadoEn(),
                    clienteM.getActualizadoEn()));
        }
        return sb.toString();
    }


    public ClienteM cargar( int id,String nombre, String apellido,String telefono,String genero,String fechaNaciemiento,int idUsuario) throws SQLException {

        ClienteM clienteMObj = new ClienteM();
        clienteMObj.setId(id);
        clienteMObj.setNombre(nombre);
        clienteMObj.setApellido(apellido);
        clienteMObj.setTelefono(telefono);
        clienteMObj.setGenero(genero);
        clienteMObj.setFechaNacimiento(Date.valueOf(fechaNaciemiento));
        clienteMObj.setIdUsuario(idUsuario);
        clienteMObj.setCreadoEn(Timestamp.valueOf(LocalDateTime.now()));
        clienteMObj.setActualizadoEn(Timestamp.valueOf(LocalDateTime.now()));
        return clienteMObj;
    }



    public String obtenerInformacionCliente(int clienteId) throws SQLException {
        // Retrieve client information
        ClienteM cliente = clienteM.leerCliente(clienteId);
        String clienteNombre = cliente.getNombre() + " " + cliente.getApellido();
        notaVentaM=new NotaVentaM();
        consultasMedicasM=new ConsultasMedicasM();
        // Retrieve sales notes
        List<NotaVentaM> notasVenta = notaVentaM.buscarPorCliente(clienteId);
          System.out.println(notasVenta + "aver que pax");
        // Retrieve medical consultations
        List<ConsultasMedicasM> consultasMedicas = consultasMedicasM.leerConsultasPorCliente(clienteId);

        // Format the results
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(clienteNombre).append("\n\n");

        // Format sales notes
        sb.append("Notas de Venta:\n");
        String notaVentaFormat = "%-5s %-10s %-15s %-10s %-10s%n";
        sb.append(String.format(notaVentaFormat, "ID", "Fecha", "Monto Total", "Almacen ID", "User ID"));
        sb.append("------------------------------------------------------------\n");
        for (NotaVentaM notaVenta : notasVenta) {
            sb.append(String.format(notaVentaFormat,
                    notaVenta.getId(),
                    notaVenta.getFechaVenta(),
                    notaVenta.getTotalMonto(),
                    notaVenta.getWarehouseId(),
                    notaVenta.getUserId()));
        }

        // Format medical consultations
        sb.append("\nConsultas Médicas:\n");
        String consultaMedicaFormat = "%-5s %-10s %-15s %-15s %-10s %-10s%n";
        sb.append(String.format(consultaMedicaFormat, "ID", "Fecha", "Motivo", "Diagnóstico", "Tarifa", "User ID"));
        sb.append("--------------------------------------------------------------------------\n");
        for (ConsultasMedicasM consulta : consultasMedicas) {
            sb.append(String.format(consultaMedicaFormat,
                    consulta.getId(),
                    consulta.getFecha(),
                    consulta.getMotivo(),
                    consulta.getDiagnostico(),
                    consulta.getTarifaConsulta(),
                    consulta.getUserId()));
        }

        return sb.toString();
    }






}
