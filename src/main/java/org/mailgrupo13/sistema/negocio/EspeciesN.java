package org.mailgrupo13.sistema.negocio;

import org.mailgrupo13.sistema.modelo.ClienteM;
import org.mailgrupo13.sistema.modelo.EspeciesM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EspeciesN {
    private int id;
    private String nombre;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    EspeciesM especieM;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    ClienteM cliente;

    public EspeciesN() throws SQLException {
        cliente = new ClienteM();
    }


    public String agregarCliente(String nombre) throws SQLException {
        try {
            validarCampos(nombre);
            EspeciesM especieMObj = cargar(0, nombre);
            return "Cliente agregado con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar el cliente "+ e;
        }
    }

    public boolean actualizarEspecie(int id, String nombre) throws SQLException {
        try{
            validarCampos(nombre);
            EspeciesM clienteMObj=cargar(id,nombre);
            return clienteMObj.actualizarEspecie();
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Método para eliminar un cliente por ID
    public boolean eliminarEspecie(int id) throws SQLException {
        return especieM.eliminarEspecie(id);
    }



    public List<EspeciesN> obtenerEspecies() throws SQLException {
        return  mapear(especieM.obtenerEspecies());
    }














    public List<EspeciesN> mapear(List<EspeciesM> especiesM) throws SQLException {
        List<EspeciesN> clientesN = new ArrayList<>();

        for (EspeciesM clienteM : especiesM) {
            EspeciesN especieN = new EspeciesN();
            especieN.setId(clienteM.getId());
            especieN.setNombre(clienteM.getNombre());
            especieN.setCreadoEn(clienteM.getCreadoEn());
            especieN.setActualizadoEn(clienteM.getActualizadoEn());
            clientesN.add(especieN);
        }
        return clientesN;
    }

    public EspeciesM cargar(int id, String nombre) throws SQLException {

        EspeciesM especieMObj = new EspeciesM();
        especieMObj.setId(id);
        especieMObj.setNombre(nombre);
        especieMObj.setCreadoEn(Timestamp.valueOf(LocalDateTime.now()));
        especieMObj.setActualizadoEn(Timestamp.valueOf(LocalDateTime.now()));
        return especieMObj;
    }



    private void validarCampos(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }

    }



}
