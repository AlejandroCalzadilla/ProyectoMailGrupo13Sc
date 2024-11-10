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
   ClientesService clientesService;

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

    public ClientesN() throws SQLException {
      clienteM = new ClienteM();
       clientesService = new ClientesService(clienteM);
    }

    public List<ClientesN> obtenerClientes() throws SQLException {
       return clientesService.obtenerClientes();
    }


    public String agregarCliente(String nombre, String apellido,String telefono,String genero, String fechanaciemiento,int idUsuario) throws SQLException {
       return clientesService.agregarCliente(nombre, apellido, telefono, genero, fechanaciemiento, idUsuario);
    }

    public boolean actualizarCliente(int id, String nombre, String apellido,String telefono,String genero, String fechanaciemiento,int idUsuario) throws SQLException {
      return  clientesService.actualizarCliente(id, nombre, apellido, telefono, genero, fechanaciemiento, idUsuario);
    }

    // Método para eliminar un cliente por ID
    public boolean eliminarCliente(int id) throws SQLException {
        return clienteM.eliminarCliente(id);
    }



}
