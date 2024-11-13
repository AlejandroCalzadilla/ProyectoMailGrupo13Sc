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
    private int almacenId;
    private int userId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private Conexion conexion;
    private Connection conn;

    public NotaCompraM() throws SQLException {
        conexion = Conexion.getInstancia();
        conn = conexion.getConnection();
    }

    // Getters and Setters

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

    public int getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(int almacenId) {
        this.almacenId = almacenId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    // CRUD Methods




    public List<NotaCompraM> obtenerNotasCompra() throws SQLException {
        List<NotaCompraM> notasCompra = new ArrayList<>();
        String sql = "SELECT * FROM purchase_note";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NotaCompraM notaCompra = new NotaCompraM();
                notaCompra.setId(rs.getInt("id"));
                notaCompra.setFechaCompra(rs.getDate("purchase_date"));
                notaCompra.setMontoTotal(rs.getFloat("total_amount"));
                notaCompra.setProveedorId(rs.getInt("supplier_id"));
                notaCompra.setAlmacenId(rs.getInt("warehouse_id"));
                notaCompra.setUserId(rs.getInt("user_id"));
                notaCompra.setCreadoEn(rs.getTimestamp("created_at"));
                notaCompra.setActualizadoEn(rs.getTimestamp("updated_at"));
                notasCompra.add(notaCompra);
            }
        }
        return notasCompra;
    }








    // Create NotaCompra with details
    public void crearNotaCompra(NotaCompraM notaCompra, List<DetalleNotaCompraM> detalles) throws SQLException {
        String sqlNota = "INSERT INTO purchase_note (purchase_date, total_amount, supplier_id, warehouse_id, user_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO purchase_note_details (quantity, purchase_price, percentage, subtotal, purchase_note_id, medicament_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlUpdateInventory = "UPDATE inventory SET stock = stock + ?, price = ? WHERE medicament_id = ?";

        try {
            conn.setAutoCommit(false);

            // Insert NotaCompra
            try (PreparedStatement stmtNota = conn.prepareStatement(sqlNota, Statement.RETURN_GENERATED_KEYS)) {
                stmtNota.setDate(1, notaCompra.getFechaCompra());
                stmtNota.setFloat(2, notaCompra.getMontoTotal());
                stmtNota.setInt(3, notaCompra.getProveedorId());
                stmtNota.setInt(4, notaCompra.getAlmacenId());
                stmtNota.setInt(5, notaCompra.getUserId());
                stmtNota.setTimestamp(6, notaCompra.getCreadoEn());
                stmtNota.setTimestamp(7, notaCompra.getActualizadoEn());
                stmtNota.executeUpdate();

                ResultSet generatedKeys = stmtNota.getGeneratedKeys();
                if (generatedKeys.next()) {
                    notaCompra.setId(generatedKeys.getInt(1));
                }
            }

            // Insert DetalleNotaCompra and update Inventory
            for (DetalleNotaCompraM detalle : detalles) {
                try (PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle)) {
                    stmtDetalle.setInt(1, detalle.getCantidad());
                    stmtDetalle.setFloat(2, detalle.getPrecioCompra());
                    stmtDetalle.setFloat(3, detalle.getPorcentaje());
                    stmtDetalle.setFloat(4, detalle.getSubtotal());
                    stmtDetalle.setInt(5, notaCompra.getId());
                    stmtDetalle.setInt(6, detalle.getMedicamentoId());
                    stmtDetalle.setTimestamp(7, detalle.getCreadoEn());
                    stmtDetalle.setTimestamp(8, detalle.getActualizadoEn());
                    stmtDetalle.executeUpdate();
                }

                // Update Inventory
                float newPrice = (detalle.getCantidad() * detalle.getPrecioCompra()) + (detalle.getPorcentaje() * (detalle.getCantidad() * detalle.getPrecioCompra()));
                try (PreparedStatement stmtUpdateInventory = conn.prepareStatement(sqlUpdateInventory)) {
                    stmtUpdateInventory.setInt(1, detalle.getCantidad());
                    stmtUpdateInventory.setFloat(2, newPrice);
                    stmtUpdateInventory.setInt(3, detalle.getMedicamentoId());
                    stmtUpdateInventory.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // Read NotaCompra by ID
    public NotaCompraM leerNotaCompra(int id) throws SQLException {
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
                notaCompra.setAlmacenId(rs.getInt("warehouse_id"));
                notaCompra.setUserId(rs.getInt("user_id"));
                notaCompra.setCreadoEn(rs.getTimestamp("created_at"));
                notaCompra.setActualizadoEn(rs.getTimestamp("updated_at"));
                return notaCompra;
            } else {
                throw new IllegalArgumentException("No existe una nota de compra con el ID proporcionado: " + id);
            }
        }
    }

    // Update NotaCompra with details
    public void actualizarNotaCompra(NotaCompraM notaCompra, List<DetalleNotaCompraM> detalles) throws SQLException {
        String sqlNota = "UPDATE purchase_note SET purchase_date = ?, total_amount = ?, supplier_id = ?, warehouse_id = ?, user_id = ?, updated_at = ? WHERE id = ?";
        String sqlDetalle = "UPDATE purchase_note_details SET quantity = ?, purchase_price = ?, percentage = ?, subtotal = ?, updated_at = ? WHERE id = ?";
        String sqlUpdateInventory = "UPDATE inventory SET stock = stock + ?, price = ? WHERE medicament_id = ?";

        try {
            conn.setAutoCommit(false);

            // Update NotaCompra
            try (PreparedStatement stmtNota = conn.prepareStatement(sqlNota)) {
                stmtNota.setDate(1, notaCompra.getFechaCompra());
                stmtNota.setFloat(2, notaCompra.getMontoTotal());
                stmtNota.setInt(3, notaCompra.getProveedorId());
                stmtNota.setInt(4, notaCompra.getAlmacenId());
                stmtNota.setInt(5, notaCompra.getUserId());
                stmtNota.setTimestamp(6, notaCompra.getActualizadoEn());
                stmtNota.setInt(7, notaCompra.getId());
                stmtNota.executeUpdate();
            }

            // Update DetalleNotaCompra and Inventory
            for (DetalleNotaCompraM detalle : detalles) {
                try (PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle)) {
                    stmtDetalle.setInt(1, detalle.getCantidad());
                    stmtDetalle.setFloat(2, detalle.getPrecioCompra());
                    stmtDetalle.setFloat(3, detalle.getPorcentaje());
                    stmtDetalle.setFloat(4, detalle.getSubtotal());
                    stmtDetalle.setTimestamp(5, detalle.getActualizadoEn());
                    stmtDetalle.setInt(6, detalle.getId());
                    stmtDetalle.executeUpdate();
                }

                // Update Inventory
                float newPrice = (detalle.getCantidad() * detalle.getPrecioCompra()) + (detalle.getPorcentaje() * (detalle.getCantidad() * detalle.getPrecioCompra()));
                try (PreparedStatement stmtUpdateInventory = conn.prepareStatement(sqlUpdateInventory)) {
                    stmtUpdateInventory.setInt(1, detalle.getCantidad());
                    stmtUpdateInventory.setFloat(2, newPrice);
                    stmtUpdateInventory.setInt(3, detalle.getMedicamentoId());
                    stmtUpdateInventory.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // Delete NotaCompra by ID
    public void eliminarNotaCompra(int id) throws SQLException {
        String sqlNota = "DELETE FROM purchase_note WHERE id = ?";
        String sqlDetalle = "DELETE FROM purchase_note_details WHERE purchase_note_id = ?";

        try {
            conn.setAutoCommit(false);

            // Delete DetalleNotaCompra
            try (PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle)) {
                stmtDetalle.setInt(1, id);
                stmtDetalle.executeUpdate();
            }

            // Delete NotaCompra
            try (PreparedStatement stmtNota = conn.prepareStatement(sqlNota)) {
                stmtNota.setInt(1, id);
                stmtNota.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}