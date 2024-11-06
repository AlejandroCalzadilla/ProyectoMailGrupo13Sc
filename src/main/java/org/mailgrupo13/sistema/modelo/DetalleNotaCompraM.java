package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleNotaCompraM {

    private int id;
    private int cantidad;
    private float precioCompra;
    private float porcentaje;
    private float subtotal;
    private int notaCompraId;
    private int medicamentoId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public DetalleNotaCompraM() throws SQLException {
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

    public float getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(float precioCompra) {
        this.precioCompra = precioCompra;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public int getNotaCompraId() {
        return notaCompraId;
    }

    public void setNotaCompraId(int notaCompraId) {
        this.notaCompraId = notaCompraId;
    }

    public int getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(int medicamentoId) {
        this.medicamentoId = medicamentoId;
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

    // Crear un detalle de nota de compra
    public boolean crearDetalleNotaCompra() {
        String sql = "INSERT INTO purchase_note_details (quantity, purchase_price, percentage, subtotal, purchase_note_id, medicament_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setFloat(2, precioCompra);
            stmt.setFloat(3, porcentaje);
            stmt.setFloat(4, subtotal);
            stmt.setInt(5, notaCompraId);
            stmt.setInt(6, medicamentoId);
            stmt.setTimestamp(7, creadoEn);
            stmt.setTimestamp(8, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer un detalle de nota de compra por ID (Devuelve un objeto DetalleNotaCompraM completo)
    public DetalleNotaCompraM leerDetalleNotaCompra(int id) {
        String sql = "SELECT * FROM purchase_note_details WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DetalleNotaCompraM detalle = new DetalleNotaCompraM();
                detalle.setId(rs.getInt("id"));
                detalle.setCantidad(rs.getInt("quantity"));
                detalle.setPrecioCompra(rs.getFloat("purchase_price"));
                detalle.setPorcentaje(rs.getFloat("percentage"));
                detalle.setSubtotal(rs.getFloat("subtotal"));
                detalle.setNotaCompraId(rs.getInt("purchase_note_id"));
                detalle.setMedicamentoId(rs.getInt("medicament_id"));
                detalle.setCreadoEn(rs.getTimestamp("created_at"));
                detalle.setActualizadoEn(rs.getTimestamp("updated_at"));
                return detalle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el detalle de la nota de compra
    }

    // Actualizar un detalle de nota de compra
    public boolean actualizarDetalleNotaCompra() {
        String sql = "UPDATE purchase_note_details SET quantity = ?, purchase_price = ?, percentage = ?, subtotal = ?, purchase_note_id = ?, medicament_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setFloat(2, precioCompra);
            stmt.setFloat(3, porcentaje);
            stmt.setFloat(4, subtotal);
            stmt.setInt(5, notaCompraId);
            stmt.setInt(6, medicamentoId);
            stmt.setTimestamp(7, actualizadoEn);
            stmt.setInt(8, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un detalle de nota de compra
    public boolean eliminarDetalleNotaCompra(int id) {
        String sql = "DELETE FROM purchase_note_details WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los detalles de las notas de compra por ID de la nota de compra
    public List<DetalleNotaCompraM> obtenerDetallesPorNotaCompra(int notaCompraId) {
        List<DetalleNotaCompraM> detalles = new ArrayList<>();
        String sql = "SELECT * FROM purchase_note_details WHERE purchase_note_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notaCompraId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DetalleNotaCompraM detalle = new DetalleNotaCompraM();
                detalle.setId(rs.getInt("id"));
                detalle.setCantidad(rs.getInt("quantity"));
                detalle.setPrecioCompra(rs.getFloat("purchase_price"));
                detalle.setPorcentaje(rs.getFloat("percentage"));
                detalle.setSubtotal(rs.getFloat("subtotal"));
                detalle.setNotaCompraId(rs.getInt("purchase_note_id"));
                detalle.setMedicamentoId(rs.getInt("medicament_id"));
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
