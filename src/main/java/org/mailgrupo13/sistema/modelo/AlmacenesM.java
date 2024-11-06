package org.mailgrupo13.sistema.modelo;
import org.mailgrupo13.sistema.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AlmacenesM {

    private int id;
    private String nombre;
    private String ubicacion;
    private String descripcion;
    private Timestamp creadoEn;
    private Timestamp actualizadoEn;

    private Conexion conexion;
    private Connection conn;

    // Constructor
    public AlmacenesM() throws SQLException {
        conexion = conexion.getInstancia();
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    // Crear un almacén
    public boolean crearAlmacen() {
        String sql = "INSERT INTO warehouses (name, location, description, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, ubicacion);
            stmt.setString(3, descripcion);
            stmt.setTimestamp(4, creadoEn);
            stmt.setTimestamp(5, actualizadoEn);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los almacenes
    public List<AlmacenesM> obtenerAlmacenes() {
        List<AlmacenesM> almacenes = new ArrayList<>();
        String sql = "SELECT * FROM warehouses";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AlmacenesM almacen = new AlmacenesM();
                almacen.setId(rs.getInt("id"));
                almacen.setNombre(rs.getString("name"));
                almacen.setUbicacion(rs.getString("location"));
                almacen.setDescripcion(rs.getString("description"));
                almacen.setCreadoEn(rs.getTimestamp("created_at"));
                almacen.setActualizadoEn(rs.getTimestamp("updated_at"));
                almacenes.add(almacen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return almacenes;
    }

    // Leer un almacén por ID
    public boolean leerAlmacen(int id) {
        String sql = "SELECT * FROM warehouses WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.id = rs.getInt("id");
                nombre = rs.getString("name");
                ubicacion = rs.getString("location");
                descripcion = rs.getString("description");
                creadoEn = rs.getTimestamp("created_at");
                actualizadoEn = rs.getTimestamp("updated_at");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Actualizar un almacén
    public boolean actualizarAlmacen() {
        String sql = "UPDATE warehouses SET name = ?, location = ?, description = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, ubicacion);
            stmt.setString(3, descripcion);
            stmt.setTimestamp(4, actualizadoEn);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un almacén
    public boolean eliminarAlmacen(int id) {
        String sql = "DELETE FROM warehouses WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
