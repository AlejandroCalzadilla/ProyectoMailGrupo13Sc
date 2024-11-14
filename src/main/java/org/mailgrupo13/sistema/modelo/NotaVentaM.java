package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String crearNotaVenta(NotaVentaM notaVenta, List<DetalleNotaVentaM> detalles) throws SQLException {
        String sqlNotaVenta = "INSERT INTO sales_notes (sale_date, total_amount, warehouse_id, user_id, customer_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            conn.setAutoCommit(false); // Start transaction

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

                    manejarInventario(detalle,notaVenta.getWarehouseId());
                }

                conn.commit(); // Commit transaction
                return "Nota de venta creada con éxito";
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                throw new SQLException("Error al crear la nota de venta: " + e.getMessage(), e);
            } finally {
                conn.setAutoCommit(true); // Reset auto-commit mode
            }
        } catch (SQLException e) {
            throw new SQLException("Error al manejar la transacción: " + e.getMessage(), e);
        }
    }

    // Update a sales note and its details
    public String actualizarNotaVenta(NotaVentaM notaVenta, List<DetalleNotaVentaM> detalles) throws SQLException {
        String sqlNotaVenta = "UPDATE sales_notes SET sale_date = ?, total_amount = ?, warehouse_id = ?, user_id = ?, customer_id = ?, updated_at = ? WHERE id = ?";
        try {
            conn.setAutoCommit(false); // Start transaction

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
                    manejarInventario(detalle,notaVenta.getWarehouseId());
                }

                conn.commit(); // Commit transaction
                return "Nota de venta actualizada con éxito";
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                throw new SQLException("Error al actualizar la nota de venta: " + e.getMessage(), e);
            } finally {
                conn.setAutoCommit(true); // Reset auto-commit mode
            }
        } catch (SQLException e) {
            throw new SQLException("Error al manejar la transacción: " + e.getMessage(), e);
        }
    }

    // Handle inventory updates
    private void manejarInventario(DetalleNotaVentaM detalle,int idalmacen) throws SQLException {
        String sqlSelect = "SELECT id, stock FROM inventory WHERE medicament_id = ? AND warehouse_id = ?";
        String sqlUpdate = "UPDATE inventory SET stock = stock - ? WHERE id = ?";

        try (PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {
            stmtSelect.setInt(1, detalle.getIdMedicamento());
            stmtSelect.setInt(2, idalmacen);
            ResultSet rs = stmtSelect.executeQuery();

            if (rs.next()) {
                int inventoryId = rs.getInt("id");
                try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {

                    if(rs.getInt("stock")<detalle.getCantidad()){
                        throw new IllegalArgumentException("No hay suficiente stock para el medicamento con ID: " +
                                detalle.getIdMedicamento() + " en el almacen con ID: " + idalmacen);
                    }
                    stmtUpdate.setInt(1, detalle.getCantidad());
                    stmtUpdate.setInt(2, inventoryId);
                    stmtUpdate.executeUpdate();

                }
            } else {
                throw new IllegalArgumentException("No hay sufieciente stock para el medicamento con ID: " +
                        detalle.getIdMedicamento() + " en el almacen con ID: " + idalmacen);
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




    // Delete a sales note by ID
    // Delete a sales note by ID and update inventory
    public void eliminarNotaVenta(int id) throws SQLException {
        String sqlNota = "DELETE FROM sales_notes WHERE id = ?";
        String sqlDetalle = "DELETE FROM sales_note_details WHERE sales_note_id = ?";
        String sqlSelectDetalles = "SELECT medicament_id, quantity, warehouse_id FROM sales_note_details WHERE sales_note_id = ?";
        String sqlUpdateInventory = "UPDATE inventory SET stock = stock + ? WHERE medicament_id = ? AND warehouse_id = ?";

        try {
            conn.setAutoCommit(false); // Start transaction

            // Select DetalleNotaVenta to get medicament_id, quantity, and warehouse_id
            List<int[]> detalles = new ArrayList<>();
            try (PreparedStatement stmtSelectDetalles = conn.prepareStatement(sqlSelectDetalles)) {
                stmtSelectDetalles.setInt(1, id);
                ResultSet rs = stmtSelectDetalles.executeQuery();
                while (rs.next()) {
                    detalles.add(new int[]{rs.getInt("medicament_id"), rs.getInt("quantity"), rs.getInt("warehouse_id")});
                }
            }

            // Update inventory stock
            for (int[] detalle : detalles) {
                try (PreparedStatement stmtUpdateInventory = conn.prepareStatement(sqlUpdateInventory)) {
                    stmtUpdateInventory.setInt(1, detalle[1]);
                    stmtUpdateInventory.setInt(2, detalle[0]);
                    stmtUpdateInventory.setInt(3, detalle[2]);
                    stmtUpdateInventory.executeUpdate();
                }
            }

            // Delete DetalleNotaVenta
            try (PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle)) {
                stmtDetalle.setInt(1, id);
                stmtDetalle.executeUpdate();
            }

            // Delete NotaVenta
            try (PreparedStatement stmtNota = conn.prepareStatement(sqlNota)) {
                stmtNota.setInt(1, id);
                stmtNota.executeUpdate();
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            conn.rollback(); // Rollback transaction on error
            throw e;
        } finally {
            conn.setAutoCommit(true); // Reset auto-commit mode
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


    // Get total sales by month
    public String obtenerVentasPorMes() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-10s %-15s%n";
        sb.append("Ventas por Mes ----------------------\n");
        sb.append(String.format(format, "Mes", "Total Ventas"));
        sb.append("-------------------------------------\n");

        String sql = "SELECT DATE_TRUNC('month', sale_date) AS month, SUM(total_amount) AS total_sales FROM sales_notes GROUP BY month ORDER BY month";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sb.append(String.format(format, rs.getDate("month"), rs.getFloat("total_sales")));
            }
        }
        return sb.toString();
    }

    // Get number of sales by customer
    public String obtenerVentasPorCliente() throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-15s %-15s%n";
        sb.append("Ventas por Cliente ------------------\n");
        sb.append(String.format(format, "Cliente ID", "Total Ventas"));
        sb.append("-------------------------------------\n");

        String sql = "SELECT customer_id, COUNT(*) AS total_sales FROM sales_notes GROUP BY customer_id ORDER BY total_sales DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sb.append(String.format(format, rs.getInt("customer_id"), rs.getInt("total_sales")));
            }
        }
        return sb.toString();
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