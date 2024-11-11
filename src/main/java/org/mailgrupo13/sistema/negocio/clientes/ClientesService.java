package org.mailgrupo13.sistema.negocio.clientes;

import org.mailgrupo13.sistema.modelo.ClienteM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientesService {

    private ClienteM clienteM;

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


    public String agregarCliente(String nombre, String apellido,String telefono,String genero, String fechanaciemiento,int idUsuario) throws SQLException {
             ClienteValidator.validarCampos(nombre, apellido, telefono, genero, fechanaciemiento, idUsuario);
            ClienteM clienteMObj = cargar(0, nombre, apellido, telefono, genero, fechanaciemiento, idUsuario);
            return  clienteMObj.crearCliente();

    }

    public boolean actualizarCliente(int id, String nombre, String apellido,String telefono,String genero, String fechanaciemiento,int idUsuario) throws SQLException {
            ClienteValidator.validarCampos(nombre, apellido, telefono, genero, fechanaciemiento, idUsuario);

            ClienteM clienteMObj=cargar(id,nombre,apellido,telefono,genero,fechanaciemiento,idUsuario);
            return clienteMObj.actualizarCliente();

    }

    // Método para eliminar un cliente por ID
    public boolean eliminarCliente(int id) throws SQLException {
        return clienteM.eliminarCliente(id);
    }


    public String mapear(List<ClienteM> clientesM) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-10s %-10s %-15s %-10s %-15s %-10s %-30s %-30s%n";
        sb.append(String.format(format, "ID", "Nombre", "Apellido", "Teléfono", "Género", "Fecha Nac.", "ID Usuario", "Creado En", "Actualizado En"));
        sb.append("------------------------------------------------------------------------------------------------------------------------------------------------\n");
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








}
