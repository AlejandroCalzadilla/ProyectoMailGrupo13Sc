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
    private int userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }







    public ConsultasMedicasM leerConsulta(int id) throws SQLException {
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
                consulta.setUserId(rs.getInt("user_id"));
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



    public List<ConsultasMedicasM> leerConsultaPorMascota(int petId) throws SQLException {
        List<ConsultasMedicasM> consultas = new ArrayList<>();
        String sql = "SELECT * FROM medical_consultations WHERE pet_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, petId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ConsultasMedicasM consulta = new ConsultasMedicasM();
                consulta.setId(rs.getInt("id"));
                consulta.setFecha(rs.getDate("date"));
                consulta.setMotivo(rs.getString("reason"));
                consulta.setDiagnostico(rs.getString("diagnosis"));
                consulta.setTarifaConsulta(rs.getFloat("consultation_fee"));
                consulta.setPetId(rs.getInt("pet_id"));
                consulta.setUserId(rs.getInt("user_id"));
                consulta.setCreadoEn(rs.getTimestamp("created_at"));
                consulta.setActualizadoEn(rs.getTimestamp("updated_at"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer las consultas médicas: " + e.getMessage(), e);
        }
        return consultas;
    }





    public boolean agregarConsultaConTratamientos(Date fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId, int userId, List<TratamientosM> tratamientos) throws SQLException {
        try {
            conn.setAutoCommit(false);

            // Create consultation
            String consultaSql = "INSERT INTO medical_consultations (date, reason, diagnosis, consultation_fee, pet_id, user_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement consultaStmt = conn.prepareStatement(consultaSql, Statement.RETURN_GENERATED_KEYS)) {
                consultaStmt.setDate(1, fecha);
                consultaStmt.setString(2, motivo);
                consultaStmt.setString(3, diagnostico);
                consultaStmt.setFloat(4, tarifaConsulta);
                consultaStmt.setInt(5, petId);
                consultaStmt.setInt(6, userId);
                consultaStmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                consultaStmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                consultaStmt.executeUpdate();

                ResultSet generatedKeys = consultaStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int consultaId = generatedKeys.getInt(1);

                    // Create treatments
                    String tratamientoSql = "INSERT INTO treatments (medication, notes, consultation_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement tratamientoStmt = conn.prepareStatement(tratamientoSql)) {
                        for (TratamientosM tratamiento : tratamientos) {
                            tratamientoStmt.setString(1, tratamiento.getMedicamento());
                            tratamientoStmt.setString(2, tratamiento.getNotas());
                            tratamientoStmt.setInt(3, consultaId);
                            tratamientoStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                            tratamientoStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                            tratamientoStmt.executeUpdate();
                        }
                    }
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public boolean actualizarConsultaConTratamientos(int consultaId, Date fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId, int userId, List<TratamientosM> tratamientos) throws SQLException {
        try {
            conn.setAutoCommit(false);

            // Update consultation
            String consultaSql = "UPDATE medical_consultations SET date = ?, reason = ?, diagnosis = ?, consultation_fee = ?, pet_id = ?, user_id = ?, updated_at = ? WHERE id = ?";
            try (PreparedStatement consultaStmt = conn.prepareStatement(consultaSql)) {
                consultaStmt.setDate(1, fecha);
                consultaStmt.setString(2, motivo);
                consultaStmt.setString(3, diagnostico);
                consultaStmt.setFloat(4, tarifaConsulta);
                consultaStmt.setInt(5, petId);
                consultaStmt.setInt(6, userId);
                consultaStmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                consultaStmt.setInt(8, consultaId);
                consultaStmt.executeUpdate();
            }

            // Delete existing treatments
            String deleteTratamientosSql = "DELETE FROM treatments WHERE consultation_id = ?";
            try (PreparedStatement deleteTratamientosStmt = conn.prepareStatement(deleteTratamientosSql)) {
                deleteTratamientosStmt.setInt(1, consultaId);
                deleteTratamientosStmt.executeUpdate();
            }

            // Insert new treatments
            String tratamientoSql = "INSERT INTO treatments (medication, notes, consultation_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement tratamientoStmt = conn.prepareStatement(tratamientoSql)) {
                for (TratamientosM tratamiento : tratamientos) {
                    tratamientoStmt.setString(1, tratamiento.getMedicamento());
                    tratamientoStmt.setString(2, tratamiento.getNotas());
                    tratamientoStmt.setInt(3, consultaId);
                    tratamientoStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    tratamientoStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                    tratamientoStmt.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

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
                consulta.setUserId(rs.getInt("user_id"));
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

    public boolean eliminarConsultaConTratamientos(int consultaId) throws SQLException {
        try {
            conn.setAutoCommit(false);

            // Delete treatments associated with the consultation
            String deleteTratamientosSql = "DELETE FROM treatments WHERE consultation_id = ?";
            try (PreparedStatement deleteTratamientosStmt = conn.prepareStatement(deleteTratamientosSql)) {
                deleteTratamientosStmt.setInt(1, consultaId);
                deleteTratamientosStmt.executeUpdate();
            }

            // Delete the consultation
            String deleteConsultaSql = "DELETE FROM medical_consultations WHERE id = ?";
            try (PreparedStatement deleteConsultaStmt = conn.prepareStatement(deleteConsultaSql)) {
                deleteConsultaStmt.setInt(1, consultaId);
                deleteConsultaStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

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
                consulta.setUserId(rs.getInt("user_id"));
                consulta.setCreadoEn(rs.getTimestamp("created_at"));
                consulta.setActualizadoEn(rs.getTimestamp("updated_at"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultas;
    }

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


    public List<ConsultasMedicasM> leerConsultasPorCliente(int clienteId) throws SQLException {
        List<ConsultasMedicasM> consultas = new ArrayList<>();
        String sqlMascotas = "SELECT id FROM pets WHERE customer_id = ?";
        String sqlConsultas = "SELECT * FROM medical_consultations WHERE pet_id = ?";

        try (PreparedStatement stmtMascotas = conn.prepareStatement(sqlMascotas)) {
            stmtMascotas.setInt(1, clienteId);
            ResultSet rsMascotas = stmtMascotas.executeQuery();

            while (rsMascotas.next()) {
                int petId = rsMascotas.getInt("id");

                try (PreparedStatement stmtConsultas = conn.prepareStatement(sqlConsultas)) {
                    stmtConsultas.setInt(1, petId);
                    ResultSet rsConsultas = stmtConsultas.executeQuery();

                    while (rsConsultas.next()) {
                        ConsultasMedicasM consulta = new ConsultasMedicasM();
                        consulta.setId(rsConsultas.getInt("id"));
                        consulta.setFecha(rsConsultas.getDate("date"));
                        consulta.setMotivo(rsConsultas.getString("reason"));
                        consulta.setDiagnostico(rsConsultas.getString("diagnosis"));
                        consulta.setTarifaConsulta(rsConsultas.getFloat("consultation_fee"));
                        consulta.setPetId(rsConsultas.getInt("pet_id"));
                        consulta.setUserId(rsConsultas.getInt("user_id"));
                        consulta.setCreadoEn(rsConsultas.getTimestamp("created_at"));
                        consulta.setActualizadoEn(rsConsultas.getTimestamp("updated_at"));
                        consultas.add(consulta);
                    }
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer las consultas médicas por cliente: " + e.getMessage(), e);
        }
        return consultas;
    }














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