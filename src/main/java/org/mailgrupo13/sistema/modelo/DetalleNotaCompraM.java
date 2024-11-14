package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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










    // Leer un detalle de nota de compra por ID
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
            } else {
                throw new IllegalArgumentException("No existe un detalle de nota de compra con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer el detalle de la nota de compra: " + e.getMessage(), e);
        }
    }




    // Get total purchases by month
    public List<Map<String, Object>> obtenerComprasPorMes() throws SQLException {
        String sql = "SELECT DATE_TRUNC('month', purchase_date) AS month, SUM(total_amount) AS total_purchases FROM purchase_note GROUP BY month ORDER BY month";
        List<Map<String, Object>> resultados = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("month", rs.getDate("month"));
                fila.put("total_purchases", rs.getFloat("total_purchases"));
                resultados.add(fila);
            }
        }
        return resultados;
    }

    // Get number of purchases by supplier
    public List<Map<String, Object>> obtenerComprasPorProveedor() throws SQLException {
        String sql = "SELECT supplier_id, COUNT(*) AS total_purchases FROM purchase_note GROUP BY supplier_id ORDER BY total_purchases DESC";
        List<Map<String, Object>> resultados = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("supplier_id", rs.getInt("supplier_id"));
                fila.put("total_purchases", rs.getInt("total_purchases"));
                resultados.add(fila);
            }
        }
        return resultados;
    }

    // Get total sales by month
















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

    // Verificar si un detalle de nota de compra existe por ID
    private boolean existeDetalleNotaCompra(int id) {
        String sql = "SELECT COUNT(*) FROM purchase_note_details WHERE id = ?";
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

    // Verificar si una nota de compra existe por ID
    private boolean existeNotaCompra(int notaCompraId) {
        String sql = "SELECT COUNT(*) FROM purchase_note WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notaCompraId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Verificar si un medicamento existe por ID
    private boolean existeMedicamento(int medicamentoId) {
        String sql = "SELECT COUNT(*) FROM medicaments WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, medicamentoId);
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