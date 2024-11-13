package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaVentaM {

    private int id;
    private Date fechaVenta;
    private float totalMonto;
    private int warehouseId;
    private int userId;
    private int customerId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

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

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    // Constructor
    public NotaVentaM() throws SQLException {
        conexion = Conexion.getInstancia();
        conn = conexion.getConnection();
    }



    public List<NotaVentaM> obtenerNotasVenta() throws SQLException {
        List<NotaVentaM> notasVenta = new ArrayList<>();
        String sql = "SELECT * FROM sales_notes";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NotaVentaM notaVenta = new NotaVentaM();
                notaVenta.setId(rs.getInt("id"));
                notaVenta.setFechaVenta(rs.getDate("sale_date"));
                notaVenta.setTotalMonto(rs.getFloat("total_amount"));
                notaVenta.setWarehouseId(rs.getInt("warehouse_id"));
                notaVenta.setUserId(rs.getInt("user_id"));
                notaVenta.setCustomerId(rs.getInt("customer_id"));
                notaVenta.setCreadoEn(rs.getTimestamp("created_at"));
                notaVenta.setActualizadoEn(rs.getTimestamp("updated_at"));
                notasVenta.add(notaVenta);
            }
        }
        return notasVenta;
    }






    // Create a sales note and update inventory
    public void crearNotaVenta(NotaVentaM notaVenta, List<DetalleNotaVentaM> detalles) throws SQLException {
        String sqlNotaVenta = "INSERT INTO sales_notes (sale_date, total_amount, warehouse_id, user_id, customer_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmtNotaVenta = conn.prepareStatement(sqlNotaVenta, Statement.RETURN_GENERATED_KEYS)) {
            stmtNotaVenta.setDate(1, notaVenta.getFechaVenta());
            stmtNotaVenta.setFloat(2, notaVenta.getTotalMonto());
            stmtNotaVenta.setInt(3, notaVenta.getWarehouseId());
            stmtNotaVenta.setInt(4, notaVenta.getUserId());
            stmtNotaVenta.setInt(5, notaVenta.getCustomerId());
            stmtNotaVenta.setTimestamp(6, notaVenta.getCreadoEn());
            stmtNotaVenta.setTimestamp(7, notaVenta.getActualizadoEn());
            stmtNotaVenta.executeUpdate();

            ResultSet generatedKeys = stmtNotaVenta.getGeneratedKeys();
            if (generatedKeys.next()) {
                notaVenta.setId(generatedKeys.getInt(1));
            }

            for (DetalleNotaVentaM detalle : detalles) {
                detalle.setIdNotaVenta(notaVenta.getId());
                detalle.crearDetalleNotaVenta();
                actualizarInventario(detalle);
            }
        }
    }

    // Read a sales note by ID
    public NotaVentaM leerNotaVenta(int id) throws SQLException {
        String sql = "SELECT * FROM sales_notes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                NotaVentaM notaVenta = new NotaVentaM();
                notaVenta.setId(rs.getInt("id"));
                notaVenta.setFechaVenta(rs.getDate("sale_date"));
                notaVenta.setTotalMonto(rs.getFloat("total_amount"));
                notaVenta.setWarehouseId(rs.getInt("warehouse_id"));
                notaVenta.setUserId(rs.getInt("user_id"));
                notaVenta.setCustomerId(rs.getInt("customer_id"));
                notaVenta.setCreadoEn(rs.getTimestamp("created_at"));
                notaVenta.setActualizadoEn(rs.getTimestamp("updated_at"));
                return notaVenta;
            } else {
                throw new IllegalArgumentException("No existe una nota de venta con el ID proporcionado: " + id);
            }
        }
    }

    // Update a sales note
    // Update a sales note and its details
    public void actualizarNotaVenta(NotaVentaM notaVenta, List<DetalleNotaVentaM> detalles) throws SQLException {
        String sqlNotaVenta = "UPDATE sales_notes SET sale_date = ?, total_amount = ?, warehouse_id = ?, user_id = ?, customer_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmtNotaVenta = conn.prepareStatement(sqlNotaVenta)) {
            stmtNotaVenta.setDate(1, notaVenta.getFechaVenta());
            stmtNotaVenta.setFloat(2, notaVenta.getTotalMonto());
            stmtNotaVenta.setInt(3, notaVenta.getWarehouseId());
            stmtNotaVenta.setInt(4, notaVenta.getUserId());
            stmtNotaVenta.setInt(5, notaVenta.getCustomerId());
            stmtNotaVenta.setTimestamp(6, notaVenta.getActualizadoEn());
            stmtNotaVenta.setInt(7, notaVenta.getId());
            stmtNotaVenta.executeUpdate();

            for (DetalleNotaVentaM detalle : detalles) {
                detalle.setIdNotaVenta(notaVenta.getId());
                if (detalle.existeDetalleNotaVenta(detalle.getId())) {
                    detalle.actualizarDetalleNotaVenta();
                } else {
                    detalle.crearDetalleNotaVenta();
                }
                actualizarInventario(detalle);
            }
        }
    }



    // Delete a sales note by ID
    public void eliminarNotaVenta(int id) throws SQLException {
        String sql = "DELETE FROM sales_notes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Search sales notes by customer ID
    public List<NotaVentaM> buscarPorCliente(int customerId) throws SQLException {
        List<NotaVentaM> notasVenta = new ArrayList<>();
        String sql = "SELECT * FROM sales_notes WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NotaVentaM notaVenta = new NotaVentaM();
                notaVenta.setId(rs.getInt("id"));
                notaVenta.setFechaVenta(rs.getDate("sale_date"));
                notaVenta.setTotalMonto(rs.getFloat("total_amount"));
                notaVenta.setWarehouseId(rs.getInt("warehouse_id"));
                notaVenta.setUserId(rs.getInt("user_id"));
                notaVenta.setCustomerId(rs.getInt("customer_id"));
                notaVenta.setCreadoEn(rs.getTimestamp("created_at"));
                notaVenta.setActualizadoEn(rs.getTimestamp("updated_at"));
                notasVenta.add(notaVenta);
            }
        }
        return notasVenta;
    }

    // Update inventory after a sale
    private void actualizarInventario(DetalleNotaVentaM detalle) throws SQLException {
        String sqlInventario = "UPDATE inventory SET stock = stock - ? WHERE medicament_id = ? AND warehouse_id = ?";
        try (PreparedStatement stmtInventario = conn.prepareStatement(sqlInventario)) {
            stmtInventario.setInt(1, detalle.getCantidad());
            stmtInventario.setInt(2, detalle.getIdMedicamento());
            stmtInventario.setInt(3, detalle.getIdNotaVenta());
            stmtInventario.executeUpdate();
        }
    }
}