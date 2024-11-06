package org.mailgrupo13.sistema.negocio;

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

public class ClientesN {

    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String genero;
    private Date fechaNacimiento;
    private int idUsuario;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    ClienteM clienteM;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Timestamp getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Timestamp creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Timestamp getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(Timestamp actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    ClienteM cliente;

    public ClientesN() throws SQLException {
      cliente = new ClienteM();
    }




    public List<ClientesN> obtenerClientes() throws SQLException {
        return  mapear(clienteM.obtenerClientes());
    }


    public String agregarCliente(String nombre, String apellido,String telefono,String genero, String fechanaciemiento,int idUsuario) throws SQLException {
        try {
            validarCampos(nombre, apellido, telefono, genero, fechanaciemiento, idUsuario);
            ClienteM clienteMObj = cargar(0, nombre, apellido, telefono, genero, fechanaciemiento, idUsuario);
            clienteMObj.crearCliente();
            return "Cliente agregado con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar el cliente "+ e;
        }
    }

    public boolean actualizarCliente(int id, String nombre, String apellido,String telefono,String genero, String fechanaciemiento,int idUsuario) throws SQLException {
       try{
           validarCampos(nombre, apellido, telefono, genero, fechanaciemiento, idUsuario);
           ClienteM clienteMObj=cargar(id,nombre,apellido,telefono,genero,fechanaciemiento,idUsuario);
           return clienteMObj.actualizarCliente();
       }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
       }
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
    public static boolean esFechaValida(String fechaStr) {
        try {
            // Intentamos parsear la fecha con el formato esperado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(fechaStr, formatter);
            return true;  // Si no lanza excepción, la fecha es válida
        } catch (DateTimeParseException e) {
            return false;  // Si lanza excepción, el formato no es válido
        }
    }

    private void validarCampos(String nombre, String apellido, String telefono, String genero, String fechanaciemiento, int idUsuario) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede estar vacío");
        }
        if (genero == null || genero.isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío");
        }
        if (!ClientesN.esFechaValida(fechanaciemiento)) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida");
        }
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("El ID de usuario debe ser mayor que 0");
        }
    }
}
