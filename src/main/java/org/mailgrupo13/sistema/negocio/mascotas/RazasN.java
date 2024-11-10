package org.mailgrupo13.sistema.negocio.mascotas;

import org.mailgrupo13.sistema.modelo.RazasM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RazasN {
    private int id;
    private String nombre;
    private int idEspecie;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private RazasM razasM;

    public RazasN() throws SQLException {
        razasM = new RazasM();
    }

    // Getters and Setters
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

    public int getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(int idEspecie) {
        this.idEspecie = idEspecie;
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
    public List<RazasN> obtenerRazas() throws SQLException {
        return mapear(razasM.obtenerRazas());
    }

    public String agregarRaza(String nombre, int idEspecie) throws SQLException {
        try {
            validarCampos(nombre, idEspecie);
            RazasM razasMObj = cargar(0, nombre, idEspecie);
            razasMObj.crearRaza();
            return "Raza agregada con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar la raza: " + e.getMessage();
        }
    }

    public boolean actualizarRaza(int id, String nombre, int idEspecie) throws SQLException {
        try {
            validarCampos(nombre, idEspecie);
            RazasM razasMObj = cargar(id, nombre, idEspecie);
            return razasMObj.actualizarRaza();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarRaza(int id) throws SQLException {
        return razasM.eliminarRaza(id);
    }

    private List<RazasN> mapear(List<RazasM> razasMList) throws SQLException {
        List<RazasN> razasNList = new ArrayList<>();
        for (RazasM razasM : razasMList) {
            RazasN razasN = new RazasN();
            razasN.setId(razasM.getId());
            razasN.setNombre(razasM.getNombre());
            razasN.setIdEspecie(razasM.getIdEspecie());
            razasN.setCreadoEn(razasM.getCreadoEn());
            razasN.setActualizadoEn(razasM.getActualizadoEn());
            razasNList.add(razasN);
        }
        return razasNList;
    }

    private RazasM cargar(int id, String nombre, int idEspecie) throws SQLException {
        RazasM razasMObj = new RazasM();
        razasMObj.setId(id);
        razasMObj.setNombre(nombre);
        razasMObj.setIdEspecie(idEspecie);
        razasMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        razasMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return razasMObj;
    }

    private void validarCampos(String nombre, int idEspecie) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (idEspecie <= 0) {
            throw new IllegalArgumentException("El ID de la especie debe ser mayor que 0");
        }
        if (nombre.length() < 3) {
            throw new IllegalArgumentException("El nombre es muy corto");
        }
    }
}