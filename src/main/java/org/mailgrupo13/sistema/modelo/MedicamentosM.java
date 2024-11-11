package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentosM {

    private int id;
    private String nombre;
    private String dosis;
    private String fabricante;
    private Date fechaCaducidad;
    private boolean sustanciaControlada;
    private int categoriaId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public MedicamentosM() throws SQLException {
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

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public boolean isSustanciaControlada() {
        return sustanciaControlada;
    }

    public void setSustanciaControlada(boolean sustanciaControlada) {
        this.sustanciaControlada = sustanciaControlada;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
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

    // Crear un medicamento
    public String crearMedicamento() {
        String sql = "INSERT INTO medicaments (name, dosage, manufacturer, expiration_date, controlled_substance, category_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, dosis);
            stmt.setString(3, fabricante);
            stmt.setDate(4, fechaCaducidad);
            stmt.setBoolean(5, sustanciaControlada);
            stmt.setInt(6, categoriaId);
            stmt.setTimestamp(7, creadoEn);
            stmt.setTimestamp(8, actualizadoEn);
            stmt.executeUpdate();
            return "Medicamento creado";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al crear el medicamento";
        }
    }

    // Leer un medicamento por ID
    public MedicamentosM leerMedicamento(int id) {
        String sql = "SELECT * FROM medicaments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MedicamentosM medicamento = new MedicamentosM();
                medicamento.setId(rs.getInt("id"));
                medicamento.setNombre(rs.getString("name"));
                medicamento.setDosis(rs.getString("dosage"));
                medicamento.setFabricante(rs.getString("manufacturer"));
                medicamento.setFechaCaducidad(rs.getDate("expiration_date"));
                medicamento.setSustanciaControlada(rs.getBoolean("controlled_substance"));
                medicamento.setCategoriaId(rs.getInt("category_id"));
                medicamento.setCreadoEn(rs.getTimestamp("created_at"));
                medicamento.setActualizadoEn(rs.getTimestamp("updated_at"));
                return medicamento;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Actualizar un medicamento
    public String actualizarMedicamento() {
        String sql = "UPDATE medicaments SET name = ?, dosage = ?, manufacturer = ?, expiration_date = ?, " +
                "controlled_substance = ?, category_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, dosis);
            stmt.setString(3, fabricante);
            stmt.setDate(4, fechaCaducidad);
            stmt.setBoolean(5, sustanciaControlada);
            stmt.setInt(6, categoriaId);
            stmt.setTimestamp(7, actualizadoEn);
            stmt.setInt(8, id);
            stmt.executeUpdate();
            return "Medicamento actualizado";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar el medicamento";
        }
    }

    // Eliminar un medicamento
    public String eliminarMedicamento(int id) {
        String sql = "DELETE FROM medicaments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return "Medicamento eliminado";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar el medicamento";
        }
    }

    // Obtener todos los medicamentos
    public List<MedicamentosM> obtenerMedicamentos() {
        List<MedicamentosM> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicaments";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MedicamentosM medicamento = new MedicamentosM();
                medicamento.setId(rs.getInt("id"));
                medicamento.setNombre(rs.getString("name"));
                medicamento.setDosis(rs.getString("dosage"));
                medicamento.setFabricante(rs.getString("manufacturer"));
                medicamento.setFechaCaducidad(rs.getDate("expiration_date"));
                medicamento.setSustanciaControlada(rs.getBoolean("controlled_substance"));
                medicamento.setCategoriaId(rs.getInt("category_id"));
                medicamento.setCreadoEn(rs.getTimestamp("created_at"));
                medicamento.setActualizadoEn(rs.getTimestamp("updated_at"));
                medicamentos.add(medicamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicamentos;
    }
}