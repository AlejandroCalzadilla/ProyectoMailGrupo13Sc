package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacunasM {

    private int id;
    private String vacuna;
    private int duracionDias;
    private String notas;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public VacunasM() throws SQLException {
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

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
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

    // Crear una vacuna
    public boolean crearVacuna() {
        String sql = "INSERT INTO vaccinations (vaccine, duration_days, notes, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vacuna);
            stmt.setInt(2, duracionDias);
            stmt.setString(3, notas);
            stmt.setTimestamp(4, creadoEn);
            stmt.setTimestamp(5, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer una vacuna por ID
    public VacunasM leerVacuna(int id) {
        String sql = "SELECT * FROM vaccinations WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                VacunasM vacuna = new VacunasM();
                vacuna.setId(rs.getInt("id"));
                vacuna.setVacuna(rs.getString("vaccine"));
                vacuna.setDuracionDias(rs.getInt("duration_days"));
                vacuna.setNotas(rs.getString("notes"));
                vacuna.setCreadoEn(rs.getTimestamp("created_at"));
                vacuna.setActualizadoEn(rs.getTimestamp("updated_at"));
                return vacuna;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra la vacuna
    }

    // Actualizar una vacuna
    public boolean actualizarVacuna() {
        String sql = "UPDATE vaccinations SET vaccine = ?, duration_days = ?, notes = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vacuna);
            stmt.setInt(2, duracionDias);
            stmt.setString(3, notas);
            stmt.setTimestamp(4, actualizadoEn);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar una vacuna
    public boolean eliminarVacuna(int id) {
        String sql = "DELETE FROM vaccinations WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todas las vacunas
    public List<VacunasM> obtenerVacunas() {
        List<VacunasM> vacunas = new ArrayList<>();
        String sql = "SELECT * FROM vaccinations";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                VacunasM vacuna = new VacunasM();
                vacuna.setId(rs.getInt("id"));
                vacuna.setVacuna(rs.getString("vaccine"));
                vacuna.setDuracionDias(rs.getInt("duration_days"));
                vacuna.setNotas(rs.getString("notes"));
                vacuna.setCreadoEn(rs.getTimestamp("created_at"));
                vacuna.setActualizadoEn(rs.getTimestamp("updated_at"));
                vacunas.add(vacuna);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vacunas;
    }
}
