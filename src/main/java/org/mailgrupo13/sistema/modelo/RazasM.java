package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RazasM {

    private int id;
    private String nombre;
    private int idEspecie;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public RazasM() throws SQLException {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(int idEspecie) {
        this.idEspecie = idEspecie;
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

    // Crear una raza
    public boolean crearRaza() {
        if (!existeEspecie(idEspecie)) {
            throw new IllegalArgumentException("No existe una especie con el ID proporcionado: " + idEspecie);
        }

        String sql = "INSERT INTO breeds (name, species_id, created_at, updated_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setInt(2, idEspecie);
            stmt.setTimestamp(3, creadoEn);
            stmt.setTimestamp(4, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al crear la raza: " + e.getMessage(), e);
        }
    }

    // Leer una raza por ID
    public RazasM leerRaza(int id) {
        String sql = "SELECT * FROM breeds WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                RazasM raza = new RazasM();
                raza.setId(rs.getInt("id"));
                raza.setNombre(rs.getString("name"));
                raza.setIdEspecie(rs.getInt("species_id"));
                raza.setCreadoEn(rs.getTimestamp("created_at"));
                raza.setActualizadoEn(rs.getTimestamp("updated_at"));
                return raza;
            } else {
                throw new IllegalArgumentException("No existe una raza con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer la raza: " + e.getMessage(), e);
        }
    }

    // Actualizar una raza
    public boolean actualizarRaza() {
        if (!existeRaza(id)) {
            throw new IllegalArgumentException("No existe una raza con el ID proporcionado: " + id);
        }
        if (!existeEspecie(idEspecie)) {
            throw new IllegalArgumentException("No existe una especie con el ID proporcionado: " + idEspecie);
        }

        String sql = "UPDATE breeds SET name = ?, species_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setInt(2, idEspecie);
            stmt.setTimestamp(3, actualizadoEn);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al actualizar la raza: " + e.getMessage(), e);
        }
    }

    // Eliminar una raza
    public boolean eliminarRaza(int id) {
        if (!existeRaza(id)) {
            throw new IllegalArgumentException("No existe una raza con el ID proporcionado: " + id);
        }

        String sql = "DELETE FROM breeds WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar la raza: " + e.getMessage(), e);
        }
    }

    // Obtener todas las razas
    public List<RazasM> obtenerRazas() {
        List<RazasM> razas = new ArrayList<>();
        String sql = "SELECT * FROM breeds";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                RazasM raza = new RazasM();
                raza.setId(rs.getInt("id"));
                raza.setNombre(rs.getString("name"));
                raza.setIdEspecie(rs.getInt("species_id"));
                raza.setCreadoEn(rs.getTimestamp("created_at"));
                raza.setActualizadoEn(rs.getTimestamp("updated_at"));
                razas.add(raza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return razas;
    }

    // Verificar si una raza existe por ID
    private boolean existeRaza(int id) {
        String sql = "SELECT COUNT(*) FROM breeds WHERE id = ?";
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

    // Verificar si una especie existe por ID
    private boolean existeEspecie(int idEspecie) {
        String sql = "SELECT COUNT(*) FROM species WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEspecie);
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