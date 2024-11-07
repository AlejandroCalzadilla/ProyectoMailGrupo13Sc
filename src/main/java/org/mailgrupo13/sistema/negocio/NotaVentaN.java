package org.mailgrupo13.sistema.negocio;

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
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private NotaVentaM notaVentaM;

    public NotaVentaN() throws SQLException {
        notaVentaM = new NotaVentaM();
    }

    // Getters and Setters
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
    public List<NotaVentaN> obtenerNotasVenta() throws SQLException {
        return mapear(notaVentaM.obtenerNotasVenta());
    }

    public String agregarNotaVenta(Date fechaVenta, float totalMonto) throws SQLException {
        try {
            validarCampos(fechaVenta, totalMonto);
            NotaVentaM notaVentaMObj = cargar(0, fechaVenta, totalMonto);
            notaVentaMObj.crearNotaVenta();
            return "Nota de venta agregada con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar la nota de venta: " + e.getMessage();
        }
    }

    public boolean actualizarNotaVenta(int id, Date fechaVenta, float totalMonto) throws SQLException {
        try {
            validarCampos(fechaVenta, totalMonto);
            NotaVentaM notaVentaMObj = cargar(id, fechaVenta, totalMonto);
            return notaVentaMObj.actualizarNotaVenta();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarNotaVenta(int id) throws SQLException {
        return notaVentaM.eliminarNotaVenta(id);
    }

    private List<NotaVentaN> mapear(List<NotaVentaM> notaVentaMList) throws SQLException {
        List<NotaVentaN> notaVentaNList = new ArrayList<>();
        for (NotaVentaM notaVentaM : notaVentaMList) {
            NotaVentaN notaVentaN = new NotaVentaN();
            notaVentaN.setId(notaVentaM.getId());
            notaVentaN.setFechaVenta(notaVentaM.getFechaVenta());
            notaVentaN.setTotalMonto(notaVentaM.getTotalMonto());
            notaVentaN.setCreadoEn(notaVentaM.getCreadoEn());
            notaVentaN.setActualizadoEn(notaVentaM.getActualizadoEn());
            notaVentaNList.add(notaVentaN);
        }
        return notaVentaNList;
    }

    private NotaVentaM cargar(int id, Date fechaVenta, float totalMonto) throws SQLException {
        NotaVentaM notaVentaMObj = new NotaVentaM();
        notaVentaMObj.setId(id);
        notaVentaMObj.setFechaVenta(fechaVenta);
        notaVentaMObj.setTotalMonto(totalMonto);
        notaVentaMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        notaVentaMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return notaVentaMObj;
    }

    private void validarCampos(Date fechaVenta, float totalMonto) {
        if (fechaVenta == null) {
            throw new IllegalArgumentException("La fecha de venta no puede estar vacía");
        }
        if (totalMonto <= 0) {
            throw new IllegalArgumentException("El monto total debe ser mayor que 0");
        }
    }
}