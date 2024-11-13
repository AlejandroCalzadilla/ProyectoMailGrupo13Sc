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
        if (!existeMascota(petId)) {
            throw new IllegalArgumentException("No existe una mascota con el ID proporcionado: " + petId);
        }
        if (!existeVacuna(vaccinationId)) {
            throw new IllegalArgumentException("No existe una vacuna con el ID proporcionado: " + vaccinationId);
        }

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
            throw new IllegalArgumentException("Error al crear el historial de vacunación: " + e.getMessage(), e);
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
            } else {
                throw new IllegalArgumentException("No existe un historial de vacunación con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer el historial de vacunación: " + e.getMessage(), e);
        }
    }

    // Actualizar un historial de vacunación
    public boolean actualizarHistorialVacuna() {
        if (!existeHistorialVacuna(id)) {
            throw new IllegalArgumentException("No existe un historial de vacunación con el ID proporcionado: " + id);
        }
        if (!existeMascota(petId)) {
            throw new IllegalArgumentException("No existe una mascota con el ID proporcionado: " + petId);
        }
        if (!existeVacuna(vaccinationId)) {
            throw new IllegalArgumentException("No existe una vacuna con el ID proporcionado: " + vaccinationId);
        }

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
            throw new IllegalArgumentException("Error al actualizar el historial de vacunación: " + e.getMessage(), e);
        }
    }

    // Eliminar un historial de vacunación
    public boolean eliminarHistorialVacuna(int id) {
        if (!existeHistorialVacuna(id)) {
            throw new IllegalArgumentException("No existe un historial de vacunación con el ID proporcionado: " + id);
        }

        String sql = "DELETE FROM vaccinate_history WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar el historial de vacunación: " + e.getMessage(), e);
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

    // Verificar si un historial de vacunación existe por ID
    private boolean existeHistorialVacuna(int id) {
        String sql = "SELECT COUNT(*) FROM vaccinate_history WHERE id = ?";
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


    public List<HistorialVacunasM> obtenerHistorialesVacunasPorMascota(int petId) throws SQLException {
        List<HistorialVacunasM> historialVacunasMList = new ArrayList<>();
        String sql = "SELECT * FROM vaccinate_history WHERE pet_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, petId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HistorialVacunasM historialVacunasM = new HistorialVacunasM();
                historialVacunasM.setId(rs.getInt("id"));
                historialVacunasM.setPetId(rs.getInt("pet_id"));
                historialVacunasM.setVaccinationId(rs.getInt("vaccination_id"));
                historialVacunasM.setCreadoEn(rs.getTimestamp("created_at"));
                historialVacunasM.setActualizadoEn(rs.getTimestamp("updated_at"));
                historialVacunasM.setSiguienteFechaVencimiento(rs.getDate("next_due_date"));
                historialVacunasMList.add(historialVacunasM);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching vaccination history for pet ID: " + petId, e);
        }
        return historialVacunasMList;
    }




    // Verificar si una mascota existe por ID
    private boolean existeMascota(int petId) {
        String sql = "SELECT COUNT(*) FROM pets WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, petId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Verificar si una vacuna existe por ID
    private boolean existeVacuna(int vaccinationId) {
        String sql = "SELECT COUNT(*) FROM vaccinations WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vaccinationId);
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