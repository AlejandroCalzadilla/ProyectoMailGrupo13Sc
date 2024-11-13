package org.mailgrupo13.sistema.negocio.notaventa;

import org.mailgrupo13.sistema.modelo.DetalleNotaVentaM;
import org.mailgrupo13.sistema.modelo.NotaVentaM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NotaVentaN {
    private int id;
    private Date fechaVenta;
    private float totalMonto;
    private int warehouseId;
    private int userId;
    private int customerId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private NotaVentaService notaVentaService;

    public NotaVentaN() throws SQLException {
        notaVentaService = new NotaVentaService();
    }

    // Getters and Setters
    // ...

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


     public String obtenerNotaVentas() throws SQLException {
         return notaVentaService.obtenerNotaVentas();
     }

    // Create NotaVenta
    public String crearNotaVenta(String fechaVenta, int warehouseId, int userId, int customerId, List<DetalleNotaVentaN> detalles) throws SQLException {
        notaVentaService.crearNotaVenta(fechaVenta, warehouseId, userId, customerId, detalles);
        return "Nota de venta creada con éxito";
    }

    // Read NotaVenta by ID
    public NotaVentaN leerNotaVenta(int id) throws SQLException {
        return notaVentaService.leerNotaVenta(id);
    }

    // Update NotaVenta
    public String actualizarNotaVenta(int id, String fechaVenta, int warehouseId, int userId, int customerId, List<DetalleNotaVentaN> detalles) throws SQLException {
        notaVentaService.actualizarNotaVenta(id, fechaVenta, warehouseId, userId, customerId, detalles);
        return "Nota de venta actualizada con éxito";
    }

    // Delete NotaVenta by ID
    public String eliminarNotaVenta(int id) throws SQLException {
        notaVentaService.eliminarNotaVenta(id);
        return "Nota de venta eliminada con éxito";
    }

    @Override
    public String toString() {
        return "NotaVentaN{" +
                "id=" + id +
                ", fechaVenta=" + fechaVenta +
                ", totalMonto=" + totalMonto +
                ", warehouseId=" + warehouseId +
                ", userId=" + userId +
                ", customerId=" + customerId +
                ", creadoEn=" + creadoEn +
                ", actualizadoEn=" + actualizadoEn +
                '}';
    }
}