package org.mailgrupo13.sistema.modelo;

import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotasM {

    private int id;
    private String nombre;
    private Float peso;
    private String color;
    private Date fechaNacimiento;
    private String urlFoto;
    private int idCliente;
    private int idRaza;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public MascotasM() throws SQLException {
        conexion = Conexion.getInstancia();
        conn = conexion.getConnection();
    }

    // Getters y Setters
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
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

    // Métodos CRUD

    // Crear una mascota
    public boolean crearMascota() {
        String sql = "INSERT INTO pets (name, weight, color, birthdate, picture_url, customer_id, breed_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setFloat(2, peso);
            stmt.setString(3, color);
            stmt.setDate(4, fechaNacimiento);
            stmt.setString(5, urlFoto);
            stmt.setInt(6, idCliente);
            stmt.setInt(7, idRaza);
            stmt.setTimestamp(8, creadoEn);
            stmt.setTimestamp(9, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer una mascota por ID
    public MascotasM leerMascota(int id) {
        String sql = "SELECT * FROM pets WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                MascotasM mascota = new MascotasM();
                mascota.setId(rs.getInt("id"));
                mascota.setNombre(rs.getString("name"));
                mascota.setPeso(rs.getFloat("weight"));
                mascota.setColor(rs.getString("color"));
                mascota.setFechaNacimiento(rs.getDate("birthdate"));
                mascota.setUrlFoto(rs.getString("picture_url"));
                mascota.setIdCliente(rs.getInt("customer_id"));
                mascota.setIdRaza(rs.getInt("breed_id"));
                mascota.setCreadoEn(rs.getTimestamp("created_at"));
                mascota.setActualizadoEn(rs.getTimestamp("updated_at"));
                return mascota;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra la mascota
    }

    // Actualizar una mascota
    public boolean actualizarMascota() {
        String sql = "UPDATE pets SET name = ?, weight = ?, color = ?, birthdate = ?, picture_url = ?, customer_id = ?, breed_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setFloat(2, peso);
            stmt.setString(3, color);
            stmt.setDate(4, fechaNacimiento);
            stmt.setString(5, urlFoto);
            stmt.setInt(6, idCliente);
            stmt.setInt(7, idRaza);
            stmt.setTimestamp(8, actualizadoEn);
            stmt.setInt(9, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar una mascota
    public boolean eliminarMascota(int id) {
        String sql = "DELETE FROM pets WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todas las mascotas
    public List<MascotasM> obtenerMascotas() {
        List<MascotasM> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM pets";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MascotasM mascota = new MascotasM();
                mascota.setId(rs.getInt("id"));
                mascota.setNombre(rs.getString("name"));
                mascota.setPeso(rs.getFloat("weight"));
                mascota.setColor(rs.getString("color"));
                mascota.setFechaNacimiento(rs.getDate("birthdate"));
                mascota.setUrlFoto(rs.getString("picture_url"));
                mascota.setIdCliente(rs.getInt("customer_id"));
                mascota.setIdRaza(rs.getInt("breed_id"));
                mascota.setCreadoEn(rs.getTimestamp("created_at"));
                mascota.setActualizadoEn(rs.getTimestamp("updated_at"));
                mascotas.add(mascota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mascotas;
    }
}
