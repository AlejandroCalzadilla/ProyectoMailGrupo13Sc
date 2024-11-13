package org.mailgrupo13.sistema.negocio.notacompra;

import org.mailgrupo13.sistema.modelo.DetalleNotaCompraM;
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
    private int almacenId;
    private int userId;
    private NotaCompraService notaCompraService;

    public NotaCompraN() throws SQLException {
        notaCompraService = new NotaCompraService();
    }




    public String obtenerNotaCompras() throws SQLException {
      return notaCompraService.obtenerNotaCompras();
    }


    // Create NotaCompra
    public String crearNotaCompra(String fechaCompra,int proovedor_id ,int almacenid,int user_id,List<DetalleNotaCompraN> detalles) throws SQLException {
         notaCompraService.crearNotaCompra( fechaCompra, proovedor_id , almacenid, user_id, detalles);
        return "Nota de compra creada con éxito";
    }

    // Read NotaCompra by ID
    public String leerNotaCompra(int id) throws SQLException {
       return notaCompraService.leerNotaCompraConDetalles(id);
    }

    // Update NotaCompra
    public String actualizarNotaCompra(int id,String fechaCompra,int proovedor_id ,int almacenid,int user_id,List<DetalleNotaCompraN> detalles) throws SQLException {
        notaCompraService.actualizarrNotaCompra(id, fechaCompra, proovedor_id , almacenid, user_id, detalles);
        return "Nota de compra actualizada con éxito";
    }


    public String eliminarNotaCompra(int id) throws SQLException {
        notaCompraService.eliminarNotaCompra(id);
        return "Nota de compra eliminada con éxito";
    }




    @Override
    public String toString() {
        return "NotaCompraN{" +
                "id=" + id +
                ", fechaCompra=" + fechaCompra +
                ", montoTotal=" + montoTotal +
                ", proveedorId=" + proveedorId +
                ", creadoEn=" + creadoEn +
                ", actualizadoEn=" + actualizadoEn +
                ", almacenId=" + almacenId +
                ", userId=" + userId +
                '}';
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

}