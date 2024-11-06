package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialVacunasM {

    private int id;
    private int petId;
    private int vaccinationId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private Date siguienteFechaVencimiento;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public HistorialVacunasM() throws SQLException {
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

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getVaccinationId() {
        return vaccinationId;
    }

    public void setVaccinationId(int vaccinationId) {
        this.vaccinationId = vaccinationId;
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

    public Date getSiguienteFechaVencimiento() {
        return siguienteFechaVencimiento;
    }

    public void setSiguienteFechaVencimiento(Date siguienteFechaVencimiento) {
        this.siguienteFechaVencimiento = siguienteFechaVencimiento;
    }

    // Métodos CRUD

    // Crear un historial de vacunación
    public boolean crearHistorialVacuna() {
        String sql = "INSERT INTO vaccinate_history (pet_id, vaccination_id, created_at, updated_at, next_due_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, petId);
            stmt.setInt(2, vaccinationId);
            stmt.setTimestamp(3, creadoEn);
            stmt.setTimestamp(4, actualizadoEn);
            stmt.setDate(5, siguienteFechaVencimiento);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer un historial de vacunación por ID
    public HistorialVacunasM leerHistorialVacuna(int id) {
        String sql = "SELECT * FROM vaccinate_history WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                HistorialVacunasM historial = new HistorialVacunasM();
                historial.setId(rs.getInt("id"));
                historial.setPetId(rs.getInt("pet_id"));
                historial.setVaccinationId(rs.getInt("vaccination_id"));
                historial.setCreadoEn(rs.getTimestamp("created_at"));
                historial.setActualizadoEn(rs.getTimestamp("updated_at"));
                historial.setSiguienteFechaVencimiento(rs.getDate("next_due_date"));
                return historial;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el historial
    }

    // Actualizar un historial de vacunación
    public boolean actualizarHistorialVacuna() {
        String sql = "UPDATE vaccinate_history SET pet_id = ?, vaccination_id = ?, updated_at = ?, next_due_date = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, petId);
            stmt.setInt(2, vaccinationId);
            stmt.setTimestamp(3, actualizadoEn);
            stmt.setDate(4, siguienteFechaVencimiento);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un historial de vacunación
    public boolean eliminarHistorialVacuna(int id) {
        String sql = "DELETE FROM vaccinate_history WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los historiales de vacunación
    public List<HistorialVacunasM> obtenerHistorialesVacunas() {
        List<HistorialVacunasM> historiales = new ArrayList<>();
        String sql = "SELECT * FROM vaccinate_history";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                HistorialVacunasM historial = new HistorialVacunasM();
                historial.setId(rs.getInt("id"));
                historial.setPetId(rs.getInt("pet_id"));
                historial.setVaccinationId(rs.getInt("vaccination_id"));
                historial.setCreadoEn(rs.getTimestamp("created_at"));
                historial.setActualizadoEn(rs.getTimestamp("updated_at"));
                historial.setSiguienteFechaVencimiento(rs.getDate("next_due_date"));
                historiales.add(historial);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historiales;
    }
}
