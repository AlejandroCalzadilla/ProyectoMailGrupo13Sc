package org.mailgrupo13.sistema.negocio.notacompra;

import java.sql.Timestamp;

public class DetalleNotaCompraN {

    private int id;
    private int cantidad;
    private float precioCompra;
    private float porcentaje;
    private float subtotal;
    private int notaCompraId;
    private int medicamentoId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;


    public DetalleNotaCompraN(int cantidad, float precioCompra, float porcentaje, float subtotal, int medicamentoId) {
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.porcentaje = porcentaje;
        this.subtotal = subtotal;
        this.medicamentoId = medicamentoId;
        this.creadoEn= new Timestamp(System.currentTimeMillis());
        this.actualizadoEn= new Timestamp(System.currentTimeMillis());

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
}
