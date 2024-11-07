package org.mailgrupo13.sistema.negocio;


import org.mailgrupo13.sistema.modelo.UsuariosM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UsuariosN {
    private int id;
    private String email;
    private String password;
    private String nombre;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private UsuariosM usuariosM;

    public UsuariosN() throws SQLException {
        usuariosM = new UsuariosM();
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

    // CRUD Methods
    public List<UsuariosN> obtenerUsuarios() throws SQLException {
        return mapear(usuariosM.obtenerUsuarios());
    }

    public String agregarUsuario(String email, String password, String nombre) throws SQLException {
        try {
            validarCampos(email, password, nombre);
            UsuariosM usuariosMObj = cargar(0, email, password, nombre);
            usuariosMObj.crearUsuario();
            return "Usuario agregado con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar el usuario: " + e.getMessage();
        }
    }

    public boolean actualizarUsuario(int id, String email, String password, String nombre) throws SQLException {
        try {
            validarCampos(email, password, nombre);
            UsuariosM usuariosMObj = cargar(id, email, password, nombre);
            return usuariosMObj.actualizarUsuario();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarUsuario(int id) throws SQLException {
        return usuariosM.eliminarUsuario(id);
    }

    private List<UsuariosN> mapear(List<UsuariosM> usuariosMList) throws SQLException {
        List<UsuariosN> usuariosNList = new ArrayList<>();
        for (UsuariosM usuariosM : usuariosMList) {
            UsuariosN usuariosN = new UsuariosN();
            usuariosN.setId(usuariosM.getId());
            usuariosN.setEmail(usuariosM.getEmail());
            usuariosN.setPassword(usuariosM.getPassword());
            usuariosN.setNombre(usuariosM.getNombre());
            usuariosN.setCreadoEn(usuariosM.getCreadoEn());
            usuariosN.setActualizadoEn(usuariosM.getActualizadoEn());
            usuariosNList.add(usuariosN);
        }
        return usuariosNList;
    }

    private UsuariosM cargar(int id, String email, String password, String nombre) throws SQLException {
        UsuariosM usuariosMObj = new UsuariosM();
        usuariosMObj.setId(id);
        usuariosMObj.setEmail(email);
        usuariosMObj.setPassword(password);
        usuariosMObj.setNombre(nombre);
        usuariosMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        usuariosMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return usuariosMObj;
    }

    private void validarCampos(String email, String password, String nombre) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (email.length() < 5) {
            throw new IllegalArgumentException("El email es muy corto");
        }
        if (password.length() < 5) {
            throw new IllegalArgumentException("La contraseña es muy corta");
        }
        if (nombre.length() < 3) {
            throw new IllegalArgumentException("El nombre es muy corto");
        }
    }
}