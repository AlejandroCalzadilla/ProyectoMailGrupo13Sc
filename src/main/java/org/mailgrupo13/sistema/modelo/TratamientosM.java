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


    public boolean agregarConsultaConTratamientos(Date fecha, String motivo, String diagnostico, Float tarifaConsulta, int petId, List<TratamientosM> tratamientos) throws SQLException {

        try {
            //conn = DriverManager.getConnection("jdbc:your_database_url");
            conn.setAutoCommit(false);

            // Create consultation
            String consultaSql = "INSERT INTO medical_consultations (date, reason, diagnosis, consultation_fee, pet_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement consultaStmt = conn.prepareStatement(consultaSql, Statement.RETURN_GENERATED_KEYS)) {
                consultaStmt.setDate(1, fecha);
                consultaStmt.setString(2, motivo);
                consultaStmt.setString(3, diagnostico);
                consultaStmt.setFloat(4, tarifaConsulta);
                consultaStmt.setInt(5, petId);
                consultaStmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                consultaStmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
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

























    // Crear un tratamiento
    public boolean crearTratamiento() {
        if (!existeConsulta(consultaId)) {
            throw new IllegalArgumentException("No existe una consulta con el ID proporcionado: " + consultaId);
        }

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
            throw new IllegalArgumentException("Error al crear el tratamiento: " + e.getMessage(), e);
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
            } else {
                throw new IllegalArgumentException("No existe un tratamiento con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer el tratamiento: " + e.getMessage(), e);
        }
    }

    /*
    public boolean actualizarTratamiento() {
        if (!existeTratamiento(id)) {
            throw new IllegalArgumentException("No existe un tratamiento con el ID proporcionado: " + id);
        }
        if (!existeConsulta(consultaId)) {
            throw new IllegalArgumentException("No existe una consulta con el ID proporcionado: " + consultaId);
        }

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
            throw new IllegalArgumentException("Error al actualizar el tratamiento: " + e.getMessage(), e);
        }
    }

     */

    // Eliminar un tratamiento
    /*
    public boolean eliminarTratamiento(int id) {
        if (!existeTratamiento(id)) {
            throw new IllegalArgumentException("No existe un tratamiento con el ID proporcionado: " + id);
        }

        String sql = "DELETE FROM treatments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar el tratamiento: " + e.getMessage(), e);
        }
    }*/

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

    // Verificar si un tratamiento existe por ID
    private boolean existeTratamiento(int id) {
        String sql = "SELECT COUNT(*) FROM treatments WHERE id = ?";
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

    // Verificar si una consulta existe por ID
    private boolean existeConsulta(int consultaId) {
        String sql = "SELECT COUNT(*) FROM medical_consultations WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consultaId);
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