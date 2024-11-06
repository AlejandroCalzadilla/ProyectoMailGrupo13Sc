package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecieM {

    private int id;
    private String nombre;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private Conexion conexion;
    private Connection conn;

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


    public EspecieM() throws SQLException {

        conexion= conexion.getInstancia();
        conn=conexion.getConnection();
    }

    public boolean crearEspecie() {
        String sql = "INSERT INTO species (name, created_at, updated_at) VALUES (?,  ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setTimestamp(2, creadoEn);
            stmt.setTimestamp(3, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<EspecieM> obtenerEspecies() {
        List<EspecieM> especies = new ArrayList<>();
        String sql = "SELECT * FROM species";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                EspecieM especie = new EspecieM();
                especie.setId(rs.getInt("id"));
                especie.setNombre(rs.getString("name"));
                especie.setCreadoEn(rs.getTimestamp("created_at"));
                especie.setActualizadoEn(rs.getTimestamp("updated_at"));
                especies.add(especie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return especies;
    }


    public EspecieM leerEspecie(int id) {
        String sql = "SELECT * FROM species WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Creamos un nuevo objeto EspecieM
                EspecieM especie = new EspecieM();
                especie.setId(rs.getInt("id"));
                especie.setNombre(rs.getString("name"));
                especie.setCreadoEn(rs.getTimestamp("created_at"));
                especie.setActualizadoEn(rs.getTimestamp("updated_at"));
                return especie;  // Devolvemos el objeto EspecieM
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Si no se encuentra, devolvemos null
    }

    // Actualizar un cliente
    public boolean actualizarEspecie() {
        String sql = "UPDATE species SET name = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setTimestamp(2, actualizadoEn);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un cliente
    public boolean eliminarEspecie(int id) {
        String sql = "DELETE FROM species WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }






}
