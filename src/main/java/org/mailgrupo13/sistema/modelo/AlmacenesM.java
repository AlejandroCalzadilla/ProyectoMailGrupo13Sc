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


    public String crearAlmacen() {

        if (existeAlmacen(nombre)) {
            throw new IllegalArgumentException("Ya existe una almacen con el mismo nombre: " + nombre);
        }

        String sql = "INSERT INTO warehouses (name, location, description, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, ubicacion);
            stmt.setString(3, descripcion);
            stmt.setTimestamp(4, creadoEn);
            stmt.setTimestamp(5, actualizadoEn);
            stmt.executeUpdate();
            return "Almacén creado";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al crear el almacén";
        }
    }

    private boolean existeAlmacen(String nombre) {
        String sql = "SELECT COUNT(*) FROM warehouses WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
    public AlmacenesM leerAlmacen(int id) {
        String sql = "SELECT * FROM warehouses WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                AlmacenesM almacen = new AlmacenesM();
                almacen.setId(rs.getInt("id"));
                almacen.setNombre(rs.getString("name"));
                almacen.setUbicacion(rs.getString("location"));
                almacen.setDescripcion(rs.getString("description"));
                almacen.setCreadoEn(rs.getTimestamp("created_at"));
                almacen.setActualizadoEn(rs.getTimestamp("updated_at"));
                return almacen;
            } else {
                throw new IllegalArgumentException("No existe un almacén con el ID proporcionado: " + id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al leer el almacén: " + e.getMessage(), e);
        }
    }

    // Actualizar un almacén
    public String eliminarAlmacen(int id) {
        String checkSql = "SELECT COUNT(*) FROM warehouses WHERE id = ?";
        String deleteSql = "DELETE FROM warehouses WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("No existe un almacén con el ID proporcionado: " + id);
            }
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
            return "Almacén eliminado";
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al eliminar el almacén: " + e.getMessage(), e);
        }
    }

    public String actualizarAlmacen() {
        if (existeAlmacen(nombre)) {
            throw new IllegalArgumentException("Ya existe una almacen con el mismo nombre: " + nombre);
        }

        String checkSql = "SELECT COUNT(*) FROM warehouses WHERE id = ?";
        String updateSql = "UPDATE warehouses SET name = ?, location = ?, description = ?, updated_at = ? WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("No existe un almacén con el ID proporcionado: " + id);
            }
            updateStmt.setString(1, nombre);
            updateStmt.setString(2, ubicacion);
            updateStmt.setString(3, descripcion);
            updateStmt.setTimestamp(4, actualizadoEn);
            updateStmt.setInt(5, id);
            updateStmt.executeUpdate();
            return "Almacén actualizado";
        } catch (SQLException e) {
            throw new IllegalArgumentException("Error al actualizar el almacén: " + e.getMessage(), e);
        }
    }

}
