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
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Float getPeso() { return peso; }
    public void setPeso(Float peso) { this.peso = peso; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdRaza() { return idRaza; }
    public void setIdRaza(int idRaza) { this.idRaza = idRaza; }

    public Timestamp getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }

    public Timestamp getActualizadoEn() { return actualizadoEn; }
    public void setActualizadoEn(Timestamp actualizadoEn) { this.actualizadoEn = actualizadoEn; }

    // Métodos CRUD

    // Crear una mascota
    public boolean crearMascota() {
        if (!existeCliente(idCliente)) {
            throw new IllegalArgumentException("No existe un cliente con el ID proporcionado: " + idCliente);
        }
        if (!existeRaza(idRaza)) {
            throw new IllegalArgumentException("No existe una raza con el ID proporcionado: " + idRaza);
        }

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
            throw new IllegalArgumentException("Error al crear la mascota: " + e.getMessage(), e);
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
            } else {
                throw new IllegalArgumentException("No existe una mascota con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer la mascota: " + e.getMessage(), e);
        }
    }

    // Actualizar una mascota
    public boolean actualizarMascota() {
        if (!existeCliente(idCliente)) {
            throw new IllegalArgumentException("No existe un cliente con el ID proporcionado: " + idCliente);
        }
        if (!existeRaza(idRaza)) {
            throw new IllegalArgumentException("No existe una raza con el ID proporcionado: " + idRaza);
        }

        String checkSql = "SELECT COUNT(*) FROM pets WHERE id = ?";
        String sql = "UPDATE pets SET name = ?, weight = ?, color = ?, birthdate = ?, picture_url = ?, customer_id = ?, breed_id = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("No existe una mascota con el ID proporcionado: " + id);
            }

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
            throw new IllegalArgumentException("Error al actualizar la mascota: " + e.getMessage(), e);
        }
    }

    // Eliminar una mascota
    public boolean eliminarMascota(int id) {
        String checkSql = "SELECT COUNT(*) FROM pets WHERE id = ?";
        String deleteSql = "DELETE FROM pets WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("No existe una mascota con el ID proporcionado: " + id);
            }

            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar la mascota: " + e.getMessage(), e);
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

    // Verificar si un cliente existe por ID
    private boolean existeCliente(int idCliente) {
        String sql = "SELECT COUNT(*) FROM customers WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Verificar si una raza existe por ID
    private boolean existeRaza(int idRaza) {
        String sql = "SELECT COUNT(*) FROM breeds WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRaza);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}