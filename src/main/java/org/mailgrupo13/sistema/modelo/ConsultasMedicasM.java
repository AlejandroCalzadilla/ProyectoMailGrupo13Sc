package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultasMedicasM {

    private int id;
    private Date fecha;
    private String motivo;
    private String diagnostico;
    private Float tarifaConsulta;
    private int petId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public ConsultasMedicasM() throws SQLException {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Float getTarifaConsulta() {
        return tarifaConsulta;
    }

    public void setTarifaConsulta(Float tarifaConsulta) {
        this.tarifaConsulta = tarifaConsulta;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
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

    // Crear una consulta médica
    public boolean crearConsultaMedica() {
        if (!existeMascota(petId)) {
            throw new IllegalArgumentException("No existe una mascota con el ID proporcionado: " + petId);
        }

        String sql = "INSERT INTO medical_consultations (date, reason, diagnosis, consultation_fee, pet_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fecha);
            stmt.setString(2, motivo);
            stmt.setString(3, diagnostico);
            stmt.setFloat(4, tarifaConsulta);
            stmt.setInt(5, petId);
            stmt.setTimestamp(6, creadoEn);
            stmt.setTimestamp(7, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al crear la consulta médica: " + e.getMessage(), e);
        }
    }

    // Leer una consulta médica por ID
    public ConsultasMedicasM leerConsultaMedica(int id) {
        String sql = "SELECT * FROM medical_consultations WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ConsultasMedicasM consulta = new ConsultasMedicasM();
                consulta.setId(rs.getInt("id"));
                consulta.setFecha(rs.getDate("date"));
                consulta.setMotivo(rs.getString("reason"));
                consulta.setDiagnostico(rs.getString("diagnosis"));
                consulta.setTarifaConsulta(rs.getFloat("consultation_fee"));
                consulta.setPetId(rs.getInt("pet_id"));
                consulta.setCreadoEn(rs.getTimestamp("created_at"));
                consulta.setActualizadoEn(rs.getTimestamp("updated_at"));
                return consulta;
            } else {
                throw new IllegalArgumentException("No existe una consulta médica con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer la consulta médica: " + e.getMessage(), e);
        }
    }

    // Actualizar una consulta médica
    public boolean actualizarConsultaMedica() {
        if (!existeConsultaMedica(id)) {
            throw new IllegalArgumentException("No existe una consulta médica con el ID proporcionado: " + id);
        }
        if (!existeMascota(petId)) {
            throw new IllegalArgumentException("No existe una mascota con el ID proporcionado: " + petId);
        }

        String sql = "UPDATE medical_consultations SET date = ?, reason = ?, diagnosis = ?, consultation_fee = ?, " +
                "pet_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fecha);
            stmt.setString(2, motivo);
            stmt.setString(3, diagnostico);
            stmt.setFloat(4, tarifaConsulta);
            stmt.setInt(5, petId);
            stmt.setTimestamp(6, actualizadoEn);
            stmt.setInt(7, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al actualizar la consulta médica: " + e.getMessage(), e);
        }
    }

    // Eliminar una consulta médica
    public boolean eliminarConsultaMedica(int id) {
        if (!existeConsultaMedica(id)) {
            throw new IllegalArgumentException("No existe una consulta médica con el ID proporcionado: " + id);
        }

        String sql = "DELETE FROM medical_consultations WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar la consulta médica: " + e.getMessage(), e);
        }
    }

    // Obtener todas las consultas médicas
    public List<ConsultasMedicasM> obtenerConsultasMedicas() {
        List<ConsultasMedicasM> consultas = new ArrayList<>();
        String sql = "SELECT * FROM medical_consultations";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ConsultasMedicasM consulta = new ConsultasMedicasM();
                consulta.setId(rs.getInt("id"));
                consulta.setFecha(rs.getDate("date"));
                consulta.setMotivo(rs.getString("reason"));
                consulta.setDiagnostico(rs.getString("diagnosis"));
                consulta.setTarifaConsulta(rs.getFloat("consultation_fee"));
                consulta.setPetId(rs.getInt("pet_id"));
                consulta.setCreadoEn(rs.getTimestamp("created_at"));
                consulta.setActualizadoEn(rs.getTimestamp("updated_at"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultas;
    }

    // Verificar si una consulta médica existe por ID
    private boolean existeConsultaMedica(int id) {
        String sql = "SELECT COUNT(*) FROM medical_consultations WHERE id = ?";
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
}