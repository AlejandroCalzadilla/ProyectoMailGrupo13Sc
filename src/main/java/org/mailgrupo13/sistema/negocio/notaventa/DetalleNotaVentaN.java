package org.mailgrupo13.sistema.negocio.notaventa;

import java.sql.Timestamp;

public class DetalleNotaVentaN {

    private int id;
    private int cantidad;
    private float precioVenta;
    private float subtotal;
    private int idNotaVenta;
    private int idMedicamento;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;


    public DetalleNotaVentaN(int cantidad, float precioVenta, float subtotal, int idMedicamento) {
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.subtotal = subtotal;
        this.idMedicamento = idMedicamento;
        this.creadoEn= new Timestamp(System.currentTimeMillis());
        this.actualizadoEn= new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(Timestamp actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

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
}
