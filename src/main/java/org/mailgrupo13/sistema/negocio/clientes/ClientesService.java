package org.mailgrupo13.sistema.negocio.clientes;

import org.mailgrupo13.sistema.modelo.ClienteM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class ClientesService {

    private ClienteM clienteM;

    public  ClientesService(ClienteM clienteM) {
        this.clienteM = clienteM;
    }


    public List<ClientesN> obtenerClientes() throws SQLException {
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


    public List<ClientesN> mapear(List<ClienteM> clientesM) throws SQLException {
        List<ClientesN> clientesN = new ArrayList<>();

        for (ClienteM clienteM : clientesM) {
            ClientesN clienteN = new ClientesN();
            clienteN.setId(clienteM.getId());
            clienteN.setNombre(clienteM.getNombre());
            clienteN.setApellido(clienteM.getApellido());
            clienteN.setTelefono(clienteM.getTelefono());
            clienteN.setGenero(clienteM.getGenero());
            clienteN.setFechaNacimiento(clienteM.getFechaNacimiento());
            clienteN.setIdUsuario(clienteM.getIdUsuario());
            clienteN.setCreadoEn(clienteM.getCreadoEn());
            clienteN.setActualizadoEn(clienteM.getActualizadoEn());
            clientesN.add(clienteN);
        }

        return clientesN;
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



    // Método para validar la fecha en formato "yyyy-MM-dd"




}
