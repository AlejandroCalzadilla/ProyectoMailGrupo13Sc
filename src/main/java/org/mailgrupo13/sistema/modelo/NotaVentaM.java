package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaVentaM {

    private int id;
    private Date fechaVenta;
    private float totalMonto;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public NotaVentaM() throws SQLException {
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

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public float getTotalMonto() {
        return totalMonto;
    }

    public void setTotalMonto(float totalMonto) {
        this.totalMonto = totalMonto;
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

    // Crear una nota de venta
    public boolean crearNotaVenta() {
        String sql = "INSERT INTO sales_notes (sale_date, total_amount, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fechaVenta);
            stmt.setFloat(2, totalMonto);
            stmt.setTimestamp(3, creadoEn);
            stmt.setTimestamp(4, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al crear la nota de venta: " + e.getMessage(), e);
        }
    }

    // Leer una nota de venta por ID
    public NotaVentaM leerNotaVenta(int id) {
        String sql = "SELECT * FROM sales_notes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                NotaVentaM notaVenta = new NotaVentaM();
                notaVenta.setId(rs.getInt("id"));
                notaVenta.setFechaVenta(rs.getDate("sale_date"));
                notaVenta.setTotalMonto(rs.getFloat("total_amount"));
                notaVenta.setCreadoEn(rs.getTimestamp("created_at"));
                notaVenta.setActualizadoEn(rs.getTimestamp("updated_at"));
                return notaVenta;
            } else {
                throw new IllegalArgumentException("No existe una nota de venta con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer la nota de venta: " + e.getMessage(), e);
        }
    }

    // Actualizar una nota de venta
    public boolean actualizarNotaVenta() {
        if (!existeNotaVenta(id)) {
            throw new IllegalArgumentException("No existe una nota de venta con el ID proporcionado: " + id);
        }

        String sql = "UPDATE sales_notes SET sale_date = ?, total_amount = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, fechaVenta);
            stmt.setFloat(2, totalMonto);
            stmt.setTimestamp(3, actualizadoEn);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al actualizar la nota de venta: " + e.getMessage(), e);
        }
    }

    // Eliminar una nota de venta
    public boolean eliminarNotaVenta(int id) {
        if (!existeNotaVenta(id)) {
            throw new IllegalArgumentException("No existe una nota de venta con el ID proporcionado: " + id);
        }

        String sql = "DELETE FROM sales_notes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar la nota de venta: " + e.getMessage(), e);
        }
    }

    // Obtener todas las notas de venta
    public List<NotaVentaM> obtenerNotasVenta() {
        List<NotaVentaM> notas = new ArrayList<>();
        String sql = "SELECT * FROM sales_notes";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NotaVentaM notaVenta = new NotaVentaM();
                notaVenta.setId(rs.getInt("id"));
                notaVenta.setFechaVenta(rs.getDate("sale_date"));
                notaVenta.setTotalMonto(rs.getFloat("total_amount"));
                notaVenta.setCreadoEn(rs.getTimestamp("created_at"));
                notaVenta.setActualizadoEn(rs.getTimestamp("updated_at"));
                notas.add(notaVenta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notas;
    }

    // Verificar si una nota de venta existe por ID
    private boolean existeNotaVenta(int id) {
        String sql = "SELECT COUNT(*) FROM sales_notes WHERE id = ?";
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
}