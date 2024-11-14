package org.mailgrupo13.sistema.negocio.notacompra;

import org.mailgrupo13.sistema.modelo.DetalleNotaCompraM;
import org.mailgrupo13.sistema.modelo.InventarioM;
import org.mailgrupo13.sistema.modelo.NotaCompraM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NotaCompraService {
    private NotaCompraM notaCompraM;
    private InventarioM inventarioM;
    private DetalleNotaCompraM detalleNotaCompraM;
    public NotaCompraService() throws SQLException {
        notaCompraM = new NotaCompraM();
        inventarioM = new InventarioM();
        detalleNotaCompraM = new DetalleNotaCompraM();
    }




    public String obtenerNotaCompras() throws SQLException {
        List<NotaCompraM> notaCompras = notaCompraM.obtenerNotasCompra();
        StringBuilder sb = new StringBuilder();
        String notaCompraFormat = "%-5s %-10s %-15s %-15s %-10s %-10s %-10s  %-30s%n";
        sb.append("NotaCompra:\r\n");
        sb.append(String.format(notaCompraFormat, "ID", "Fecha", "Monto Total", "Proveedor ID", "Almacen ID", "User ID", "Creado En", "Actualizado En"));
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\r\n");
        for (NotaCompraM notaCompra : notaCompras) {
            sb.append(String.format(notaCompraFormat,
                    notaCompra.getId(),
                    notaCompra.getFechaCompra(),
                    notaCompra.getMontoTotal(),
                    notaCompra.getProveedorId(),
                    notaCompra.getAlmacenId(),
                    notaCompra.getUserId(),
                    notaCompra.getCreadoEn(),
                    notaCompra.getActualizadoEn()));
        }
        return sb.toString();
    }




    public void crearNotaCompra(String fechaCompra,int proovedor_id ,int almacenid,int user_id,List<DetalleNotaCompraN> detalles) throws SQLException {
        float montoTotal = 0;
        for (DetalleNotaCompraN detalle : detalles) {
            montoTotal += detalle.getSubtotal();
        }
        notaCompraM.crearNotaCompra(cargar( 0,Date.valueOf(fechaCompra), montoTotal, proovedor_id, almacenid, user_id), cargardetalle(detalles));
    }


    public NotaCompraN leerNotaCompra(int id) throws SQLException {
        return  mapear(notaCompraM.leerNotaCompra(id));
    }


    public String actualizarrNotaCompra(int id, String fechaCompra,int proovedor_id ,int almacenid,int user_id,List<DetalleNotaCompraN> detalles) throws SQLException {
        float montoTotal = 0;
        for (DetalleNotaCompraN detalle : detalles) {
            montoTotal += detalle.getSubtotal();
        }
        notaCompraM.actualizarNotaCompra(cargar( id,Date.valueOf(fechaCompra), montoTotal, proovedor_id, almacenid, user_id), cargardetalle(detalles));
       return "Nota de compra actualizada con éxito";
    }

    // Delete NotaCompra by ID
    public void eliminarNotaCompra(int id) throws SQLException {
        notaCompraM.eliminarNotaCompra(id);
    }


    // Helper methods
    private NotaCompraN mapear(NotaCompraM notaCompraM) throws SQLException {
        NotaCompraN notaCompraN = new NotaCompraN();
        notaCompraN.setId(notaCompraM.getId());
        notaCompraN.setFechaCompra(notaCompraM.getFechaCompra());
        notaCompraN.setMontoTotal(notaCompraM.getMontoTotal());
        notaCompraN.setProveedorId(notaCompraM.getProveedorId());
        notaCompraN.setAlmacenId(notaCompraM.getAlmacenId());
        notaCompraN.setUserId(notaCompraM.getUserId());
        notaCompraN.setCreadoEn(notaCompraM.getCreadoEn());
        notaCompraN.setActualizadoEn(notaCompraM.getActualizadoEn());
        return notaCompraN;
    }

    private NotaCompraM cargar(int id, Date fechaCompra, float montoTotal, int proveedorId, int almacenId, int userId) throws SQLException {
        NotaCompraM notaCompraMObj = new NotaCompraM();
        notaCompraMObj.setId(id);
        notaCompraMObj.setFechaCompra(fechaCompra);
        notaCompraMObj.setMontoTotal(montoTotal);
        notaCompraMObj.setProveedorId(proveedorId);
        notaCompraMObj.setAlmacenId(almacenId);
        notaCompraMObj.setUserId(userId);
        notaCompraMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        notaCompraMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return notaCompraMObj;
    }


    private List<DetalleNotaCompraM> cargardetalle(List<DetalleNotaCompraN> detallen) throws SQLException {
        List<DetalleNotaCompraM> detallesM = new ArrayList<>();
        for (DetalleNotaCompraN detalleN : detallen) {
            DetalleNotaCompraM detalleM = new DetalleNotaCompraM();
            detalleM.setId(detalleN.getId());
            detalleM.setCantidad(detalleN.getCantidad());
            detalleM.setPrecioCompra(detalleN.getPrecioCompra());
            detalleM.setPorcentaje(detalleN.getPorcentaje());
            detalleM.setSubtotal(detalleN.getSubtotal());
            detalleM.setNotaCompraId(detalleN.getNotaCompraId());
            detalleM.setMedicamentoId(detalleN.getMedicamentoId());
            detalleM.setCreadoEn(detalleN.getCreadoEn());
            detalleM.setActualizadoEn(detalleN.getActualizadoEn());
            detallesM.add(detalleM);
        }
        return detallesM;
    }





    public String leerNotaCompraConDetalles(int id) throws SQLException {
        NotaCompraM notaCompra = notaCompraM.leerNotaCompra(id);
        List<DetalleNotaCompraM> detalles = detalleNotaCompraM.obtenerDetallesPorNotaCompra(id);

        StringBuilder sb = new StringBuilder();
        String notaCompraFormat = "%-5s %-20s %-15s %-15s %-10s %-10s %-10s  %-30s%n";
        sb.append("NotaCompra:\r\n");
        sb.append(String.format(notaCompraFormat, "ID", "Fecha", "Monto Total", "Proveedor ID", "Almacen ID", "User ID", "Creado En", "Actualizado En"));
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        sb.append(String.format(notaCompraFormat,
                notaCompra.getId(),
                notaCompra.getFechaCompra(),
                notaCompra.getMontoTotal(),
                notaCompra.getProveedorId(),
                notaCompra.getAlmacenId(),
                notaCompra.getUserId(),
                notaCompra.getCreadoEn(),
                notaCompra.getActualizadoEn()));

        String detalleFormat = "%-5s %-10s %-10s %-15s %-15s %-10s %-10s %-30s %-30s%n";
        sb.append("\r\nDetalles:\r\n");
        sb.append(String.format(detalleFormat, "ID", "id_nota" ,"Cantidad", "Precio Compra", "Porcentaje", "Subtotal", "Medicamento ID", "Creado En", "Actualizado En"));
        sb.append("------------------------------------------------------------------------------------------------------------------------\n");
        for (DetalleNotaCompraM detalle : detalles) {
            sb.append(String.format(detalleFormat,
                    detalle.getId(),
                    detalle.getNotaCompraId(),
                    detalle.getCantidad(),
                    detalle.getPrecioCompra(),
                    detalle.getPorcentaje(),
                    detalle.getSubtotal(),
                    detalle.getMedicamentoId(),
                    detalle.getCreadoEn(),
                    detalle.getActualizadoEn()));
        }
        return sb.toString();
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
