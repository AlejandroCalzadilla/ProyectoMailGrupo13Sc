package org.mailgrupo13.sistema.negocio;

import org.mailgrupo13.sistema.modelo.InventarioM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class InventarioN {
    private int id;
    private int stock;
    private float precio;
    private int medicamentoId;
    private int bodegaId;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private InventarioM inventarioM;

    public InventarioN() throws SQLException {
        inventarioM = new InventarioM();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(int medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public int getBodegaId() {
        return bodegaId;
    }

    public void setBodegaId(int bodegaId) {
        this.bodegaId = bodegaId;
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
    public List<InventarioN> obtenerInventarios() throws SQLException {
        return mapear(inventarioM.obtenerInventarios());
    }

    public String agregarInventario(int stock, float precio, int medicamentoId, int bodegaId) throws SQLException {
        try {
            validarCampos(stock, precio, medicamentoId, bodegaId);
            InventarioM inventarioMObj = cargar(0, stock, precio, medicamentoId, bodegaId);
            inventarioMObj.crearInventario();
            return "Inventario agregado con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar el inventario: " + e.getMessage();
        }
    }

    public boolean actualizarInventario(int id, int stock, float precio, int medicamentoId, int bodegaId) throws SQLException {
        try {
            validarCampos(stock, precio, medicamentoId, bodegaId);
            InventarioM inventarioMObj = cargar(id, stock, precio, medicamentoId, bodegaId);
            return inventarioMObj.actualizarInventario();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarInventario(int id) throws SQLException {
        return inventarioM.eliminarInventario(id);
    }

    private List<InventarioN> mapear(List<InventarioM> inventarioMList) throws SQLException {
        List<InventarioN> inventarioNList = new ArrayList<>();
        for (InventarioM inventarioM : inventarioMList) {
            InventarioN inventarioN = new InventarioN();
            inventarioN.setId(inventarioM.getId());
            inventarioN.setStock(inventarioM.getStock());
            inventarioN.setPrecio(inventarioM.getPrecio());
            inventarioN.setMedicamentoId(inventarioM.getMedicamentoId());
            inventarioN.setBodegaId(inventarioM.getBodegaId());
            inventarioN.setCreadoEn(inventarioM.getCreadoEn());
            inventarioN.setActualizadoEn(inventarioM.getActualizadoEn());
            inventarioNList.add(inventarioN);
        }
        return inventarioNList;
    }

    private InventarioM cargar(int id, int stock, float precio, int medicamentoId, int bodegaId) throws SQLException {
        InventarioM inventarioMObj = new InventarioM();
        inventarioMObj.setId(id);
        inventarioMObj.setStock(stock);
        inventarioMObj.setPrecio(precio);
        inventarioMObj.setMedicamentoId(medicamentoId);
        inventarioMObj.setBodegaId(bodegaId);
        inventarioMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        inventarioMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return inventarioMObj;
    }

    private void validarCampos(int stock, float precio, int medicamentoId, int bodegaId) {
        if (stock <= 0) {
            throw new IllegalArgumentException("El stock debe ser mayor que 0");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0");
        }
        if (medicamentoId <= 0) {
            throw new IllegalArgumentException("El ID del medicamento debe ser mayor que 0");
        }
        if (bodegaId <= 0) {
            throw new IllegalArgumentException("El ID de la bodega debe ser mayor que 0");
        }
    }
}