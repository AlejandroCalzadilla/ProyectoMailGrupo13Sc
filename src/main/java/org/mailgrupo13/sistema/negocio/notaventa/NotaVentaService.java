package org.mailgrupo13.sistema.negocio.notaventa;

import org.mailgrupo13.sistema.modelo.DetalleNotaVentaM;
import org.mailgrupo13.sistema.modelo.InventarioM;
import org.mailgrupo13.sistema.modelo.NotaVentaM;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NotaVentaService {
    private NotaVentaM notaVentaM;
    private InventarioM inventarioM;
    private DetalleNotaVentaM detalleNotaVentaM;

    public NotaVentaService() throws SQLException {
        notaVentaM = new NotaVentaM();
        inventarioM = new InventarioM();
        detalleNotaVentaM = new DetalleNotaVentaM();
    }




    public  String obtenerNotaVentas() throws SQLException {
        List<NotaVentaM> notaVentas = notaVentaM.obtenerNotasVenta();
        StringBuilder sb = new StringBuilder();
        String notaVentaFormat = "%-5s %-10s %-15s %-15s %-10s %-10s %-10s  %-30s%n";
        sb.append("NotaVenta:\n");
        sb.append(String.format(notaVentaFormat, "ID", "Fecha", "Monto Total", "Warehouse ID", "User ID", "Customer ID", "Creado En", "Actualizado En"));
        sb.append("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (NotaVentaM notaVenta : notaVentas) {
            sb.append(String.format(notaVentaFormat,
                    notaVenta.getId(),
                    notaVenta.getFechaVenta(),
                    notaVenta.getTotalMonto(),
                    notaVenta.getWarehouseId(),
                    notaVenta.getUserId(),
                    notaVenta.getCustomerId(),
                    notaVenta.getCreadoEn(),
                    notaVenta.getActualizadoEn()));
        }
        return sb.toString();
    }



    public void crearNotaVenta(String fechaVenta, int warehouseId, int userId, int customerId, List<DetalleNotaVentaN> detalles) throws SQLException {
        float totalMonto = 0;
        for (DetalleNotaVentaN detalle : detalles) {
            totalMonto += detalle.getSubtotal();
        }
        notaVentaM.crearNotaVenta(cargar(0, Date.valueOf(fechaVenta), totalMonto, warehouseId, userId, customerId), cargardetalle(detalles));
    }

    public NotaVentaN leerNotaVenta(int id) throws SQLException {
        return mapear(notaVentaM.leerNotaVenta(id));
    }

    public void actualizarNotaVenta(int id, String fechaVenta, int warehouseId, int userId, int customerId, List<DetalleNotaVentaN> detalles) throws SQLException {
        float totalMonto = 0;
        for (DetalleNotaVentaN detalle : detalles) {
            totalMonto += detalle.getSubtotal();
        }
        notaVentaM.actualizarNotaVenta(cargar(id, Date.valueOf(fechaVenta), totalMonto, warehouseId, userId, customerId), cargardetalle(detalles));
    }

    public void eliminarNotaVenta(int id) throws SQLException {
        notaVentaM.eliminarNotaVenta(id);
    }

    private NotaVentaN mapear(NotaVentaM notaVentaM) throws SQLException {
        NotaVentaN notaVentaN = new NotaVentaN();
        notaVentaN.setId(notaVentaM.getId());
        notaVentaN.setFechaVenta(notaVentaM.getFechaVenta());
        notaVentaN.setTotalMonto(notaVentaM.getTotalMonto());
        notaVentaN.setWarehouseId(notaVentaM.getWarehouseId());
        notaVentaN.setUserId(notaVentaM.getUserId());
        notaVentaN.setCustomerId(notaVentaM.getCustomerId());
        notaVentaN.setCreadoEn(notaVentaM.getCreadoEn());
        notaVentaN.setActualizadoEn(notaVentaM.getActualizadoEn());
        return notaVentaN;
    }

    private NotaVentaM cargar(int id, Date fechaVenta, float totalMonto, int warehouseId, int userId, int customerId) throws SQLException {
        NotaVentaM notaVentaMObj = new NotaVentaM();
        notaVentaMObj.setId(id);
        notaVentaMObj.setFechaVenta(fechaVenta);
        notaVentaMObj.setTotalMonto(totalMonto);
        notaVentaMObj.setWarehouseId(warehouseId);
        notaVentaMObj.setUserId(userId);
        notaVentaMObj.setCustomerId(customerId);
        notaVentaMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        notaVentaMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return notaVentaMObj;
    }

    private List<DetalleNotaVentaM> cargardetalle(List<DetalleNotaVentaN> detallen) throws SQLException {
        List<DetalleNotaVentaM> detallesM = new ArrayList<>();
        for (DetalleNotaVentaN detalleN : detallen) {
            DetalleNotaVentaM detalleM = new DetalleNotaVentaM();
            detalleM.setId(detalleN.getId());
            detalleM.setCantidad(detalleN.getCantidad());
            detalleM.setPrecioVenta(detalleN.getPrecioVenta());
            detalleM.setSubtotal(detalleN.getSubtotal());
            detalleM.setIdNotaVenta(detalleN.getIdNotaVenta());
            detalleM.setIdMedicamento(detalleN.getIdMedicamento());
            detalleM.setCreadoEn(detalleN.getCreadoEn());
            detalleM.setActualizadoEn(detalleN.getActualizadoEn());
            detallesM.add(detalleM);
        }
        return detallesM;
    }
}