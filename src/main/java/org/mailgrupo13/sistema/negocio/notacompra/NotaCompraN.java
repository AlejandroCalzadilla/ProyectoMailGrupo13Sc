package org.mailgrupo13.sistema.negocio.notacompra;

import org.mailgrupo13.sistema.modelo.NotaCompraM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NotaCompraN {
    private int id;
    private Date fechaCompra;
    private float montoTotal;
    private int proveedorId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private NotaCompraM notaCompraM;

    public NotaCompraN() throws SQLException {
        notaCompraM = new NotaCompraM();
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
    public List<NotaCompraN> obtenerNotasCompra() throws SQLException {
        return mapear(notaCompraM.obtenerNotasCompra());
    }

    public String agregarNotaCompra(Date fechaCompra, float montoTotal, int proveedorId) throws SQLException {
        try {
            validarCampos(fechaCompra, montoTotal, proveedorId);
            NotaCompraM notaCompraMObj = cargar(0, fechaCompra, montoTotal, proveedorId);
            notaCompraMObj.crearNotaCompra();
            return "Nota de compra agregada con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar la nota de compra: " + e.getMessage();
        }
    }

    public boolean actualizarNotaCompra(int id, Date fechaCompra, float montoTotal, int proveedorId) throws SQLException {
        try {
            validarCampos(fechaCompra, montoTotal, proveedorId);
            NotaCompraM notaCompraMObj = cargar(id, fechaCompra, montoTotal, proveedorId);
            return notaCompraMObj.actualizarNotaCompra();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarNotaCompra(int id) throws SQLException {
        return notaCompraM.eliminarNotaCompra(id);
    }

    private List<NotaCompraN> mapear(List<NotaCompraM> notaCompraMList) throws SQLException {
        List<NotaCompraN> notaCompraNList = new ArrayList<>();
        for (NotaCompraM notaCompraM : notaCompraMList) {
            NotaCompraN notaCompraN = new NotaCompraN();
            notaCompraN.setId(notaCompraM.getId());
            notaCompraN.setFechaCompra(notaCompraM.getFechaCompra());
            notaCompraN.setMontoTotal(notaCompraM.getMontoTotal());
            notaCompraN.setProveedorId(notaCompraM.getProveedorId());
            notaCompraN.setCreadoEn(notaCompraM.getCreadoEn());
            notaCompraN.setActualizadoEn(notaCompraM.getActualizadoEn());
            notaCompraNList.add(notaCompraN);
        }
        return notaCompraNList;
    }

    private NotaCompraM cargar(int id, Date fechaCompra, float montoTotal, int proveedorId) throws SQLException {
        NotaCompraM notaCompraMObj = new NotaCompraM();
        notaCompraMObj.setId(id);
        notaCompraMObj.setFechaCompra(fechaCompra);
        notaCompraMObj.setMontoTotal(montoTotal);
        notaCompraMObj.setProveedorId(proveedorId);
        notaCompraMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        notaCompraMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return notaCompraMObj;
    }

    private void validarCampos(Date fechaCompra, float montoTotal, int proveedorId) {
        if (fechaCompra == null) {
            throw new IllegalArgumentException("La fecha de compra no puede estar vacía");
        }
        if (montoTotal <= 0) {
            throw new IllegalArgumentException("El monto total debe ser mayor que 0");
        }
        if (proveedorId <= 0) {
            throw new IllegalArgumentException("El ID del proveedor debe ser mayor que 0");
        }
    }
}