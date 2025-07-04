package org.mailgrupo13.sistema.negocio.mascotas;

import org.mailgrupo13.sistema.modelo.EspeciesM;
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
    public String obtenerRazas() throws SQLException {
        return mapear(razasM.obtenerRazas());
    }


    public RazasN leerRaza(int id ) throws SQLException {

        RazasM razam= razasM.leerRaza(id);
        RazasN razaN= new RazasN();
        razaN.setId(razam.getId());
        razaN.setNombre(razam.getNombre());
        razaN.setIdEspecie(razam.getIdEspecie());
        razaN.setCreadoEn(razam.getCreadoEn());
        razaN.setActualizadoEn(razam.getActualizadoEn());
       return razaN;

    }



    public String agregarRaza(String nombre, int idEspecie) throws SQLException {
            validarCampos(nombre, idEspecie);
            RazasM razasMObj = cargar(0, nombre, idEspecie);
            razasMObj.crearRaza();
            return "Raza agregada con éxito";

    }

    public boolean actualizarRaza(int id, String nombre, int idEspecie) throws SQLException {
            validarCampos(nombre, idEspecie);
            RazasM razasMObj = cargar(id, nombre, idEspecie);
            return razasMObj.actualizarRaza();

    }

    public boolean eliminarRaza(int id) throws SQLException {
        return razasM.eliminarRaza(id);
    }



    private String mapear(List<RazasM> razasMList) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String format = "%-5s %-20s %-10s  %-20s %-30s %-30s%n";
        sb.append(String.format(format, "ID", "Nombre", "ID Especie","especie" ,  "Creado En", "Actualizado En"));
        sb.append("--------------------------------------------------------------------------------------------\r\n");
        for (RazasM razasM : razasMList) {
            sb.append(String.format(format,
                    razasM.getId(),
                    razasM.getNombre(),
                    razasM.getIdEspecie(),
                    cargarEspecie(razasM.getIdEspecie()),
                    razasM.getCreadoEn(),
                    razasM.getActualizadoEn()));
        }
        return sb.toString();
    }

     private String cargarEspecie(int id ) throws SQLException {
        EspeciesN especieN = new EspeciesN();
        return especieN.leerEspecie(id).getNombre();


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

    @Override
    public String toString() {
        try {
            return "Raza : " + "id=" + id +
                    ", nombre='" + nombre + '\'' +
                    ", idEspecie=" + idEspecie +
                    ",  especie= "+  cargarEspecie(idEspecie)   +
                    ", creadoEn=" + creadoEn +
                    ", actualizadoEn=" + actualizadoEn +
                    '}';
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}