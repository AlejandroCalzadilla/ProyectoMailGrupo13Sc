package org.mailgrupo13.sistema.modelo;
import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioM {

    private int id;
    private int stock;
    private float precio;
    private int medicamentoId;
    private int bodegaId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public InventarioM() throws SQLException {
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(int medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public int getBodegaId() {
        return bodegaId;
    }

    public void setBodegaId(int bodegaId) {
        this.bodegaId = bodegaId;
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

    // Crear un inventario
    public boolean crearInventario() {
        String sql = "INSERT INTO inventory (stock, price, medicament_id, warehouse_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stock);
            stmt.setFloat(2, precio);
            stmt.setInt(3, medicamentoId);
            stmt.setInt(4, bodegaId);
            stmt.setTimestamp(5, creadoEn);
            stmt.setTimestamp(6, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer un inventario por ID (Devuelve un objeto InventarioM completo)
    public InventarioM leerInventario(int id) {
        String sql = "SELECT * FROM inventory WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                InventarioM inventario = new InventarioM();
                inventario.setId(rs.getInt("id"));
                inventario.setStock(rs.getInt("stock"));
                inventario.setPrecio(rs.getFloat("price"));
                inventario.setMedicamentoId(rs.getInt("medicament_id"));
                inventario.setBodegaId(rs.getInt("warehouse_id"));
                inventario.setCreadoEn(rs.getTimestamp("created_at"));
                inventario.setActualizadoEn(rs.getTimestamp("updated_at"));
                return inventario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el inventario
    }

    // Actualizar un inventario
    public boolean actualizarInventario() {
        String sql = "UPDATE inventory SET stock = ?, price = ?, medicament_id = ?, warehouse_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stock);
            stmt.setFloat(2, precio);
            stmt.setInt(3, medicamentoId);
            stmt.setInt(4, bodegaId);
            stmt.setTimestamp(5, actualizadoEn);
            stmt.setInt(6, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un inventario
    public boolean eliminarInventario(int id) {
        String sql = "DELETE FROM inventory WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los inventarios
    public List<InventarioM> obtenerInventarios() {
        List<InventarioM> inventarios = new ArrayList<>();
        String sql = "SELECT * FROM inventory";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                InventarioM inventario = new InventarioM();
                inventario.setId(rs.getInt("id"));
                inventario.setStock(rs.getInt("stock"));
                inventario.setPrecio(rs.getFloat("price"));
                inventario.setMedicamentoId(rs.getInt("medicament_id"));
                inventario.setBodegaId(rs.getInt("warehouse_id"));
                inventario.setCreadoEn(rs.getTimestamp("created_at"));
                inventario.setActualizadoEn(rs.getTimestamp("updated_at"));
                inventarios.add(inventario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventarios;
    }
}

