package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientosM {

    private int id;
    private String medicamento;
    private String notas;
    private int consultaId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public TratamientosM() throws SQLException {
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

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getConsultaId() {
        return consultaId;
    }

    public void setConsultaId(int consultaId) {
        this.consultaId = consultaId;
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

    // Crear un tratamiento
    public boolean crearTratamiento() {
        String sql = "INSERT INTO treatments (medication, notes, consultation_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medicamento);
            stmt.setString(2, notas);
            stmt.setInt(3, consultaId);
            stmt.setTimestamp(4, creadoEn);
            stmt.setTimestamp(5, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer un tratamiento por ID
    public TratamientosM leerTratamiento(int id) {
        String sql = "SELECT * FROM treatments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                TratamientosM tratamiento = new TratamientosM();
                tratamiento.setId(rs.getInt("id"));
                tratamiento.setMedicamento(rs.getString("medication"));
                tratamiento.setNotas(rs.getString("notes"));
                tratamiento.setConsultaId(rs.getInt("consultation_id"));
                tratamiento.setCreadoEn(rs.getTimestamp("created_at"));
                tratamiento.setActualizadoEn(rs.getTimestamp("updated_at"));
                return tratamiento;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el tratamiento
    }

    // Actualizar un tratamiento
    public boolean actualizarTratamiento() {
        String sql = "UPDATE treatments SET medication = ?, notes = ?, consultation_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medicamento);
            stmt.setString(2, notas);
            stmt.setInt(3, consultaId);
            stmt.setTimestamp(4, actualizadoEn);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un tratamiento
    public boolean eliminarTratamiento(int id) {
        String sql = "DELETE FROM treatments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los tratamientos
    public List<TratamientosM> obtenerTratamientos() {
        List<TratamientosM> tratamientos = new ArrayList<>();
        String sql = "SELECT * FROM treatments";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TratamientosM tratamiento = new TratamientosM();
                tratamiento.setId(rs.getInt("id"));
                tratamiento.setMedicamento(rs.getString("medication"));
                tratamiento.setNotas(rs.getString("notes"));
                tratamiento.setConsultaId(rs.getInt("consultation_id"));
                tratamiento.setCreadoEn(rs.getTimestamp("created_at"));
                tratamiento.setActualizadoEn(rs.getTimestamp("updated_at"));
                tratamientos.add(tratamiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tratamientos;
    }
}

