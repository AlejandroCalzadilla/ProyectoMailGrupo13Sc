package org.mailgrupo13.sistema.negocio.usuarios;


import org.mailgrupo13.sistema.modelo.UsuariosM;

import java.sql.SQLException;
import java.sql.Timestamp;


public class UsuariosN {
    private int id;
    private String email;
    private String password;
    private String nombre;
    private UsuariosM usuariosM;


    private UsuariosService usuariosService;
    public UsuariosN() throws SQLException {
        usuariosM = new UsuariosM();
        usuariosService = new UsuariosService(usuariosM);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    // CRUD Methods



    public UsuariosN leerUsuario(int id) throws SQLException {
        return usuariosService.leerUsuario(id);
    }

    public String obtenerUsuarios() throws SQLException {
      return usuariosService.obtenerUsuarios();
    }

    public String agregarUsuario(String email, String password, String nombre) throws SQLException {
        return  usuariosService.agregarUsuario(email, password, nombre);
    }

    public String actualizarUsuario(int id, String email, String password, String nombre) throws SQLException {
        return  usuariosService.actualizarUsuario(id, email, password, nombre);
    }

    public String eliminarUsuario(int id) throws SQLException {
      return usuariosService.eliminarUsuario(id);
    }


    @Override
    public String toString() {
        return "UsuariosN{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}