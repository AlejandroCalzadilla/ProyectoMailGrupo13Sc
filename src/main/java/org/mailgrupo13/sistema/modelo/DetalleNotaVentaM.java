package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleNotaVentaM {

    private int id;
    private int cantidad;
    private float precioVenta;
    private float subtotal;
    private int idNotaVenta;
    private int idMedicamento;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public DetalleNotaVentaM() throws SQLException {
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public int getIdNotaVenta() {
        return idNotaVenta;
    }

    public void setIdNotaVenta(int idNotaVenta) {
        this.idNotaVenta = idNotaVenta;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
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

    // Crear un detalle de la nota de venta
    public boolean crearDetalleNotaVenta() {
        String sql = "INSERT INTO sales_note_details (quantity, sale_price, subtotal, sales_note_id, medicament_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setFloat(2, precioVenta);
            stmt.setFloat(3, subtotal);
            stmt.setInt(4, idNotaVenta);
            stmt.setInt(5, idMedicamento);
            stmt.setTimestamp(6, creadoEn);
            stmt.setTimestamp(7, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer un detalle de la nota de venta por ID
    public DetalleNotaVentaM leerDetalleNotaVenta(int id) {
        String sql = "SELECT * FROM sales_note_details WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DetalleNotaVentaM detalle = new DetalleNotaVentaM();
                detalle.setId(rs.getInt("id"));
                detalle.setCantidad(rs.getInt("quantity"));
                detalle.setPrecioVenta(rs.getFloat("sale_price"));
                detalle.setSubtotal(rs.getFloat("subtotal"));
                detalle.setIdNotaVenta(rs.getInt("sales_note_id"));
                detalle.setIdMedicamento(rs.getInt("medicament_id"));
                detalle.setCreadoEn(rs.getTimestamp("created_at"));
                detalle.setActualizadoEn(rs.getTimestamp("updated_at"));
                return detalle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el detalle
    }

    // Actualizar un detalle de la nota de venta
    public boolean actualizarDetalleNotaVenta() {
        String sql = "UPDATE sales_note_details SET quantity = ?, sale_price = ?, subtotal = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setFloat(2, precioVenta);
            stmt.setFloat(3, subtotal);
            stmt.setTimestamp(4, actualizadoEn);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un detalle de la nota de venta
    public boolean eliminarDetalleNotaVenta(int id) {
        String sql = "DELETE FROM sales_note_details WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los detalles de una nota de venta
    public List<DetalleNotaVentaM> obtenerDetallesNotaVenta(int idNotaVenta) {
        List<DetalleNotaVentaM> detalles = new ArrayList<>();
        String sql = "SELECT * FROM sales_note_details WHERE sales_note_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idNotaVenta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DetalleNotaVentaM detalle = new DetalleNotaVentaM();
                detalle.setId(rs.getInt("id"));
                detalle.setCantidad(rs.getInt("quantity"));
                detalle.setPrecioVenta(rs.getFloat("sale_price"));
                detalle.setSubtotal(rs.getFloat("subtotal"));
                detalle.setIdNotaVenta(rs.getInt("sales_note_id"));
                detalle.setIdMedicamento(rs.getInt("medicament_id"));
                detalle.setCreadoEn(rs.getTimestamp("created_at"));
                detalle.setActualizadoEn(rs.getTimestamp("updated_at"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }
}
