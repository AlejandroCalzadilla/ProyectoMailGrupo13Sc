package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaCompraM {

    private int id;
    private Date fechaCompra;
    private float montoTotal;
    private int proveedorId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public NotaCompraM() throws SQLException {
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

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
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

    // Crear una nota de compra
    public boolean crearNotaCompra() {
        if (!existeProveedor(proveedorId)) {
            throw new IllegalArgumentException("No existe un proveedor con el ID proporcionado: " + proveedorId);
        }

        String sql = "INSERT INTO purchase_note (purchase_date, total_amount, supplier_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fechaCompra);
            stmt.setFloat(2, montoTotal);
            stmt.setInt(3, proveedorId);
            stmt.setTimestamp(4, creadoEn);
            stmt.setTimestamp(5, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al crear la nota de compra: " + e.getMessage(), e);
        }
    }

    // Leer una nota de compra por ID
    public NotaCompraM leerNotaCompra(int id) {
        String sql = "SELECT * FROM purchase_note WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                NotaCompraM notaCompra = new NotaCompraM();
                notaCompra.setId(rs.getInt("id"));
                notaCompra.setFechaCompra(rs.getDate("purchase_date"));
                notaCompra.setMontoTotal(rs.getFloat("total_amount"));
                notaCompra.setProveedorId(rs.getInt("supplier_id"));
                notaCompra.setCreadoEn(rs.getTimestamp("created_at"));
                notaCompra.setActualizadoEn(rs.getTimestamp("updated_at"));
                return notaCompra;
            } else {
                throw new IllegalArgumentException("No existe una nota de compra con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer la nota de compra: " + e.getMessage(), e);
        }
    }

    // Actualizar una nota de compra
    public boolean actualizarNotaCompra() {
        if (!existeNotaCompra(id)) {
            throw new IllegalArgumentException("No existe una nota de compra con el ID proporcionado: " + id);
        }
        if (!existeProveedor(proveedorId)) {
            throw new IllegalArgumentException("No existe un proveedor con el ID proporcionado: " + proveedorId);
        }

        String sql = "UPDATE purchase_note SET purchase_date = ?, total_amount = ?, supplier_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fechaCompra);
            stmt.setFloat(2, montoTotal);
            stmt.setInt(3, proveedorId);
            stmt.setTimestamp(4, actualizadoEn);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al actualizar la nota de compra: " + e.getMessage(), e);
        }
    }

    // Eliminar una nota de compra
    public boolean eliminarNotaCompra(int id) {
        if (!existeNotaCompra(id)) {
            throw new IllegalArgumentException("No existe una nota de compra con el ID proporcionado: " + id);
        }

        String sql = "DELETE FROM purchase_note WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar la nota de compra: " + e.getMessage(), e);
        }
    }

    // Obtener todas las notas de compra
    public List<NotaCompraM> obtenerNotasCompra() {
        List<NotaCompraM> notasCompra = new ArrayList<>();
        String sql = "SELECT * FROM purchase_note";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NotaCompraM notaCompra = new NotaCompraM();
                notaCompra.setId(rs.getInt("id"));
                notaCompra.setFechaCompra(rs.getDate("purchase_date"));
                notaCompra.setMontoTotal(rs.getFloat("total_amount"));
                notaCompra.setProveedorId(rs.getInt("supplier_id"));
                notaCompra.setCreadoEn(rs.getTimestamp("created_at"));
                notaCompra.setActualizadoEn(rs.getTimestamp("updated_at"));
                notasCompra.add(notaCompra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notasCompra;
    }

    // Verificar si una nota de compra existe por ID
    private boolean existeNotaCompra(int id) {
        String sql = "SELECT COUNT(*) FROM purchase_note WHERE id = ?";
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

    // Verificar si un proveedor existe por ID
    private boolean existeProveedor(int proveedorId) {
        String sql = "SELECT COUNT(*) FROM suppliers WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, proveedorId);
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