package org.mailgrupo13.sistema.negocio.mascotas;

import org.mailgrupo13.sistema.modelo.MascotasM;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MascotasN {
    private int id;
    private String nombre;
    private Float peso;
    private String color;
    private java.sql.Date fechaNacimiento;
    private String urlFoto;
    private int idCliente;
    private int idRaza;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;
    private MascotasM mascotasM;

    public MascotasN() throws SQLException {
        mascotasM = new MascotasM();
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

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public java.sql.Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(java.sql.Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(int idRaza) {
        this.idRaza = idRaza;
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
    public List<MascotasN> obtenerMascotas() throws SQLException {
        return mapear(mascotasM.obtenerMascotas());
    }

    public String agregarMascota(String nombre, Float peso, String color, String fechaNacimiento, String urlFoto, int idCliente, int idRaza) throws SQLException {
        try {
            validarCampos(nombre, peso, color, fechaNacimiento, urlFoto, idCliente, idRaza);
            MascotasM mascotasMObj = cargar(0, nombre, peso, color, fechaNacimiento, urlFoto, idCliente, idRaza);
            mascotasMObj.crearMascota();
            return "Mascota agregada con éxito";
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return "Error al agregar la mascota: " + e.getMessage();
        }
    }

    public boolean actualizarMascota(int id, String nombre, Float peso, String color, String fechaNacimiento, String urlFoto, int idCliente, int idRaza) throws SQLException {
        try {
            validarCampos(nombre, peso, color, fechaNacimiento, urlFoto, idCliente, idRaza);
            MascotasM mascotasMObj = cargar(id, nombre, peso, color, fechaNacimiento, urlFoto, idCliente, idRaza);
            return mascotasMObj.actualizarMascota();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean eliminarMascota(int id) throws SQLException {
        return mascotasM.eliminarMascota(id);
    }

    private List<MascotasN> mapear(List<MascotasM> mascotasMList) throws SQLException {
        List<MascotasN> mascotasNList = new ArrayList<>();
        for (MascotasM mascotasM : mascotasMList) {
            MascotasN mascotasN = new MascotasN();
            mascotasN.setId(mascotasM.getId());
            mascotasN.setNombre(mascotasM.getNombre());
            mascotasN.setPeso(mascotasM.getPeso());
            mascotasN.setColor(mascotasM.getColor());
            mascotasN.setFechaNacimiento(mascotasM.getFechaNacimiento());
            mascotasN.setUrlFoto(mascotasM.getUrlFoto());
            mascotasN.setIdCliente(mascotasM.getIdCliente());
            mascotasN.setIdRaza(mascotasM.getIdRaza());
            mascotasN.setCreadoEn(mascotasM.getCreadoEn());
            mascotasN.setActualizadoEn(mascotasM.getActualizadoEn());
            mascotasNList.add(mascotasN);
        }
        return mascotasNList;
    }

    private MascotasM cargar(int id, String nombre, Float peso, String color, String fechaNacimiento, String urlFoto, int idCliente, int idRaza) throws SQLException {
        MascotasM mascotasMObj = new MascotasM();
        mascotasMObj.setId(id);
        mascotasMObj.setNombre(nombre);
        mascotasMObj.setPeso(peso);
        mascotasMObj.setColor(color);
        mascotasMObj.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimiento));
        mascotasMObj.setUrlFoto(urlFoto);
        mascotasMObj.setIdCliente(idCliente);
        mascotasMObj.setIdRaza(idRaza);
        mascotasMObj.setCreadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        mascotasMObj.setActualizadoEn(Timestamp.valueOf(java.time.LocalDateTime.now()));
        return mascotasMObj;
    }

    private void validarCampos(String nombre, Float peso, String color, String fechaNacimiento, String urlFoto, int idCliente, int idRaza) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (peso == null || peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que 0");
        }
        if (color == null || color.isEmpty()) {
            throw new IllegalArgumentException("El color no puede estar vacío");
        }
        if (!esFechaValida(fechaNacimiento)) {
            throw new IllegalArgumentException("La fecha de nacimiento no es válida");
        }
        if (urlFoto == null || urlFoto.isEmpty()) {
            throw new IllegalArgumentException("La URL de la foto no puede estar vacía");
        }
        if (idCliente <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser mayor que 0");
        }
        if (idRaza <= 0) {
            throw new IllegalArgumentException("El ID de la raza debe ser mayor que 0");
        }
    }

    // Método para validar la fecha en formato "yyyy-MM-dd"
    public static boolean esFechaValida(String fechaStr) {
        try {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            java.time.LocalDate.parse(fechaStr, formatter);
            return true;
        } catch (java.time.format.DateTimeParseException e) {
            return false;
        }
    }
}