package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuariosM {

    private int id;
    private String email;
    private String password;
    private String nombre;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private Conexion conexion;
    private Connection conn;

    // Constructor
    public UsuariosM() throws SQLException {
        conexion = Conexion.getInstancia();
        conn = conexion.getConnection();
    }

    // Getters y Setters
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

    // Métodos CRUD

    // Crear un usuario
    public String crearUsuario() {
        String sql = "INSERT INTO users (email, password, name) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, nombre);

            stmt.executeUpdate();
            return "Usuario creado con éxito";
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new IllegalArgumentException("El email ya está registrado: " + email);
            } else {
                throw new IllegalArgumentException("Error al crear el usuario: " + e.getMessage(), e);
            }
        }
    }

    // Leer un usuario por ID
    public UsuariosM leerUsuario(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UsuariosM usuario = new UsuariosM();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword("********"); // Placeholder value
                usuario.setNombre(rs.getString("name"));

                return usuario;
            } else {
                throw new IllegalArgumentException("No existe un usuario con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer el usuario: " + e.getMessage(), e);
        }
    }

    // Actualizar un usuario
    public String actualizarUsuario() {
        if (!existeUsuario(id)) {
            throw new IllegalArgumentException("No existe un usuario con el ID proporcionado: " + id);
        }

        String sql = "UPDATE users SET email = ?, password = ?, name = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, nombre);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            return "Usuario actualizado con éxito";
        } catch (SQLException e) {
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                throw new IllegalArgumentException("El email ya está registrado: " + email);
            } else {
                throw new IllegalArgumentException("Error al actualizar el usuario: " + e.getMessage(), e);
            }
        }
    }

    // Eliminar un usuario
    public String eliminarUsuario(int id) {
        if (!existeUsuario(id)) {
            throw new IllegalArgumentException("No existe un usuario con el ID proporcionado: " + id);
        }
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return "Usuario eliminado con éxito";
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar el usuario: " + e.getMessage(), e);
        }
    }

    // Obtener todos los usuarios
    public List<UsuariosM> obtenerUsuarios() {
        List<UsuariosM> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UsuariosM usuario = new UsuariosM();
                usuario.setId(rs.getInt("id"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword("********"); // Placeholder value
                usuario.setNombre(rs.getString("name"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Verificar si un usuario existe por ID
    private boolean existeUsuario(int id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}